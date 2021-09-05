/* 
 * Copyright (C) 2021 10Xofy. All rights reserved.
 *
 * The main controller of layer Java_EE of the application Demo13.imageprocessing.
 */
package xsemble.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.*;
import java.util.HashSet;
import java.io.OutputStream;
import java.io.Writer;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.function.*;

import javax.servlet.AsyncContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class Workflow {
    
    private final VarStore varStore;
    private final RuntimeContext runtimeContext;
    private static final Logger log = LoggerFactory.getLogger(Workflow.class);
    private static Properties nodeProperties = null, layerProperties = null;
    
    public Workflow(RuntimeContext runtimeContext) {
        
        this.runtimeContext = runtimeContext;
        varStore = new VarStore(runtimeContext);
        log.info("Session:"+ runtimeContext.get("sessionId") + '/' + runtimeContext.get("requestId") + " - Workflow: Initialized");
    }
    
    public void executeWorkflow(Node beginNode) {
        final String sessionAndRequestId = ""+runtimeContext.get("sessionId") + '/' + runtimeContext.get("requestId");
        log.info("Session:"+ sessionAndRequestId +" - Workflow: Beginning with " + beginNode);
        Node node = beginNode;

        do {
            if (node.getType() == 1 || node.getType() == 4)
                break;
            Map<String,Object> invars = node.populateInVariables(varStore, (HttpServletRequest)runtimeContext.get("request"), sessionAndRequestId);
            log.debug("Session:"+ sessionAndRequestId +" Invars for " + node + ": " +invars);
            Map<String,Object> outvars = node.execute(invars, runtimeContext, sessionAndRequestId);
            log.debug("Session:"+ sessionAndRequestId +" Outvars for " + node + ": " +outvars);
            String exitPath = node.updateVarStore(outvars, varStore, sessionAndRequestId);
            Node next = node.nextNode(exitPath, sessionAndRequestId);
            log.info("Session:"+ sessionAndRequestId + " - Workflow: " + node + " -> "+next);

            node = next;
        }
        while (true);
        
        Map<String,Object> invars = node.populateInVariables(varStore, (HttpServletRequest) runtimeContext.get("request"), sessionAndRequestId);
        node.execute(invars, runtimeContext, sessionAndRequestId);

		runtimeContext.cleanup();
        if (beginNode instanceof EntryNode) ((EntryNode)beginNode).onRequestEnd();
    }
    
    public static String getNodeProperty(String propName, String defaultValue) {
    	if (nodeProperties == null) {
    		nodeProperties = new Properties();
    		try {
	    		nodeProperties.load(Workflow.class.getClassLoader().getResourceAsStream("xsemble-nodes.properties"));
	    	} catch (IOException x) {
	    		return null;
	    	}
    	}
    	return nodeProperties.getProperty(propName.replace(' ','_'), defaultValue);
    }
    
    public static String getLayerProperty(String propName, String defaultValue) {
    	if (layerProperties == null) {
    		layerProperties = new Properties();
    		try {
	    		layerProperties.load(Workflow.class.getClassLoader().getResourceAsStream("xsemble-layers.properties"));
	    	} catch (IOException x) {
	    		return null;
	    	}
    	}
    	return layerProperties.getProperty(propName.replace(' ','_'), defaultValue);
    }
    
    public static String removeTrailingSlash(String s) {
    	s = s.trim();
    	int last = s.length()-1;
    	if (s.charAt(last) == '/')
    		s = s.substring(0,last);
    	return s;
    }
    
    public interface Node {
        public String getId();
        public int getType();
        public Map<String,Object> populateInVariables(final VarStore varStore, final HttpServletRequest request, final String sessionAndRequestId);
        public Map<String,Object> execute(final Map<String,Object> inVars, final RuntimeContext runtimeContext, final String sessionAndRequestId);
        public String updateVarStore(final Map<String,Object> outVars, VarStore varStore, final String sessionAndRequestId);
        public Node nextNode(final String ret, final String sessionAndRequestId);
    }
    
    public interface EntryNode extends Node {
        public void onRequestEnd();
        public void onMessage();
    }
    
    public static class RuntimeContext extends HashMap<String, Object> {
       
        public boolean isAsync() { return this.containsKey("asyncContext"); }
        
        public void addCleanupFunction( Runnable f ) {
        	
			List<Runnable> cleanupFunctions = (List<Runnable>) this.get("cleanupFunctions");
			if (cleanupFunctions == null) {
				cleanupFunctions = new ArrayList<Runnable>();
				this.put("cleanupFunctions", cleanupFunctions);
			}
        	cleanupFunctions.add(f);
		}
		
		public void cleanup() {
			List<Runnable> cleanupFunctions = (List<Runnable>) this.get("cleanupFunctions");
			if (cleanupFunctions == null) return;
			for (int i = cleanupFunctions.size() - 1; i >= 0; i--) {
				cleanupFunctions.get(i).run();
			} 
		}
		
		public static String uniqid() {
		    String generatedString = new java.util.Random().ints(97, 122 + 1)
			      .limit(6)
			      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
			      .toString();
			return generatedString;
		}
    }
    
    public static class VarStore {
        
        public static final Short STORAGE_REQUEST = 1;
        public static final Short STORAGE_SESSION = 2;
        public static final Short STORAGE_APPLICATION = 3;
        
        public class ValRecord {
            public String setterNode;
            public Object val;
            public ValRecord(Object value, String setterNodeName) { val = value; setterNode = setterNodeName; } 
        }

        private final Map<Short,Map<String,Map<String,ValRecord>>> vars = new HashMap<>();;
        
        public VarStore(RuntimeContext runtimeContext) {
            Map<String,Map<String,ValRecord>> varReq = new HashMap<String,Map<String,ValRecord>>();
            Map<String,Map<String,ValRecord>> varSes = initSessionStorage(runtimeContext);
            Map<String,Map<String,ValRecord>> varApp = initApplicationStorage((ServletContext) runtimeContext.get("servletContext"));
            vars.put(STORAGE_REQUEST, varReq);
            vars.put(STORAGE_SESSION, varSes);
            vars.put(STORAGE_APPLICATION, varApp);
        }
        
        public Map<String,ValRecord> getProjVars(Short storeScope, String projPrefix) { return createIfDoesNotExist(storeScope, projPrefix); }
        public Map<String,ValRecord> getVarReq(String projPrefix) { return getProjVars(STORAGE_REQUEST, projPrefix); }
        public Map<String,ValRecord> getVarSes(String projPrefix) { return getProjVars(STORAGE_SESSION, projPrefix); }
        public Map<String,ValRecord> getVarApp(String projPrefix) { return getProjVars(STORAGE_APPLICATION, projPrefix); }
        
        public Object getVal(Short storeScope, String projPrefix, String dnode) {
        	ValRecord r = createIfDoesNotExist(storeScope, projPrefix).get(dnode);
        	return (r == null? null: r.val);
        }

        public boolean setVal(Short storeScope, String projPrefix, String dnode, String setterNode, Object val,
         boolean isConstant, boolean isNullable, String sessionId) {
        	Map<String,ValRecord> projectStorage = createIfDoesNotExist(storeScope, projPrefix);
        	if (isConstant && projectStorage.containsKey(dnode)) {
        		log.warn("Session:" + sessionId + " Attempt to modify a CONSTANT node " + projPrefix + '#' + dnode + " by " + setterNode + " ignored.");
        		return false;
        	}
        	if (!isNullable && val == null) {
        		log.warn("Session:" + sessionId + " Attempt to set null value to a NON-NULLABLE node " + projPrefix + '#' + dnode + " by " + setterNode + " ignored.");
        		return false;
       		}
        	ValRecord r = new ValRecord(val, setterNode);
        	projectStorage.put(dnode, r);
        	return true;
        }

        private Map<String,ValRecord> createIfDoesNotExist(Short storeScope, String key) {
        	Map<String,Map<String,ValRecord>> storage = vars.get(storeScope);
            if (!storage.containsKey(key))
                storage.put(key, new HashMap<String,ValRecord>());
            return storage.get(key);
        }

        public Map<String,Map<String,ValRecord>> initSessionStorage(RuntimeContext runtimeContext)
        {
             if (runtimeContext.get("request") != null) {
            	HttpServletRequest request = (HttpServletRequest) runtimeContext.get("request");
                HttpSession session = request.getSession();
                @SuppressWarnings("unchecked")
                HashMap<String,Map<String,ValRecord>> projSes = (HashMap<String,Map<String,ValRecord>>)session.getAttribute("_mgf");
                if (projSes == null) {
                    projSes = new HashMap<String,Map<String,ValRecord>>();
                    session.setAttribute("_mgf", projSes);
                }
                return projSes;
            }
            else { // Websocket session, stored in servletContext
            	javax.websocket.Session websocketSession = (Session) runtimeContext.get("websocketSession");
                @SuppressWarnings("unchecked")
                HashMap<String,Map<String,ValRecord>> projSes =
                  (HashMap<String,Map<String,ValRecord>>) websocketSession.getUserProperties().get("_mgf");
                
                if (projSes == null) {
                    projSes = new HashMap<String,Map<String,ValRecord>>();
                    websocketSession.getUserProperties().put("_mgf", projSes);
                }
                return projSes;
            }
        }

        public Map<String,Map<String,ValRecord>> initApplicationStorage(ServletContext servletContext)
        {
            if (servletContext == null) return null;
        
            @SuppressWarnings("unchecked")
            Map<String,Map<String,ValRecord>> projApp = (Map<String,Map<String,ValRecord>>)servletContext.getAttribute("_mgf");
            if (projApp == null)
            {
                projApp = new HashMap<String,Map<String,ValRecord>>();
                servletContext.setAttribute("_mgf",projApp);
            }
            return projApp;
        }
    }
    
    public static class OutputStreamGroup extends OutputStream {

        private final Set<OutputStream> outstreams = new HashSet<OutputStream>();

        public void add(OutputStream ostr) {
            outstreams.add(ostr);
        }

        @Override
        public void write(int b) throws IOException {
            for (OutputStream o : outstreams) { o.write(b); }
        }

        @Override
        public void write(byte[] b) throws IOException {
            for (OutputStream o : outstreams) { o.write(b); }
        }

        @Override
        public void write(byte[] b, int off, int len) throws IOException {
            for (OutputStream o : outstreams) { o.write(b, off, len); }
        }

        @Override
        public void flush() throws IOException {
            for (OutputStream o : outstreams) { o.flush(); }
        }

        @Override
        public void close() throws IOException {
            for (OutputStream o : outstreams) { o.close(); }
        }
    }
    
    public static class WriterGroup extends Writer {

        private final Set<Writer> writers = new HashSet<Writer>();

        public void add(Writer ostr) {
            writers.add(ostr);
        }

        @Override
        public void write(int b) throws IOException {
            for (Writer o : writers) { o.write(b); }
        }

        @Override
        public void write(char[] cbuf, int off, int len) throws IOException {
            for (Writer o : writers) { o.write(cbuf, off, len); }
        }
        
        @Override
        public void flush() throws IOException {
            for (Writer o : writers) { o.flush(); }
        }

        @Override
        public void close() throws IOException {
            for (Writer o : writers) { o.close(); }
        }
    }
}
