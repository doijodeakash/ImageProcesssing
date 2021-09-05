/* 
 * Copyright (C) 2020 10Xofy. All rights reserved.
 */
package xsemble.controller.n;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.AsyncContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import xsemble.controller.Workflow;
import xsemble.controller.Workflow.Node;
import xsemble.controller.Workflow.VarStore;

public class Node_OutputPage implements Workflow.Node {

    @Override
    public String getId() {
        return "#OutputPage";
    }

    @Override
    public String toString() {
        return "#OutputPage";
    }

    @Override
    public int getType() {
        return 1;
    }

    @Override
    public Map<String, Object> populateInVariables(VarStore varStore, HttpServletRequest request, String sessionId) {
        
        Map<String,Object> invars = new HashMap<String,Object>();

        invars.put("output", varStore.getVal(VarStore.STORAGE_REQUEST, "", "output"));

        invars.put("sourceImage", varStore.getVal(VarStore.STORAGE_REQUEST, "", "sourceImage"));

        invars.put("encoded", varStore.getVal(VarStore.STORAGE_REQUEST, "", "encoded"));

        return invars;
    }        

	@Override
	public Map<String, Object> execute(Map<String, Object> inVars, final Workflow.RuntimeContext runtimeContext, String sessionId) {
		try {

			HttpServletRequest request = (HttpServletRequest) runtimeContext.get("request");      
			request.setAttribute("_inargs", inVars);
			request.setAttribute("_params", getNodeParams());
			request.setAttribute("_externalExitpath2urlMap", getExternalExitpath2urlMap());
			request.setAttribute("_externalOutarg2varnameMap", getExternalOutarg2varnameMap());
			request.setAttribute("_currentNode", "#OutputPage");
			request.setAttribute("_currentSession", sessionId);
			if (runtimeContext.isAsync()) {
				AsyncContext asyncContext = (AsyncContext) runtimeContext.get("asyncContext");
				asyncContext.dispatch((ServletContext) runtimeContext.get("servletContext"), "/Demo13/imageprocessing/OutputPage.jsp");
			} else {
				ServletContext servletContext = (ServletContext) runtimeContext.get("servletContext");
				servletContext.getRequestDispatcher("/Demo13/imageprocessing/OutputPage.jsp").forward((ServletRequest)runtimeContext.get("request"), (ServletResponse)runtimeContext.get("response"));
			}
			return null;
		}
		catch (Exception exc) {
			throw new RuntimeException(exc);
		}	
              
	}

    @Override
    public String updateVarStore(Map<String, Object> outVars, VarStore varStore, String sessionId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Node nextNode(String exitPath, String sessionId) {

        throw new UnsupportedOperationException("Node \"#OutputPage\" does not support exit path " + exitPath);
    }

    private Map<String,String> getNodeParams() {
		return null;
    }

    private Map<String,String> getExternalExitpath2urlMap() {
    	Map<String,String> map = new HashMap<>();

		map.put("blurimage", Workflow.removeTrailingSlash(Workflow.getLayerProperty("Java_EE/Deployment URL", "http://localhost:8080/<WAR filename>/")) + "/x/bluri");
		map.put("compress", Workflow.removeTrailingSlash(Workflow.getLayerProperty("Java_EE/Deployment URL", "http://localhost:8080/<WAR filename>/")) + "/x/compress");
		map.put("facedetection", Workflow.removeTrailingSlash(Workflow.getLayerProperty("Java_EE/Deployment URL", "http://localhost:8080/<WAR filename>/")) + "/x/facedetection");
		map.put("greyscaleimage", Workflow.removeTrailingSlash(Workflow.getLayerProperty("Java_EE/Deployment URL", "http://localhost:8080/<WAR filename>/")) + "/x/greyscale");
		map.put("mirrorimage", Workflow.removeTrailingSlash(Workflow.getLayerProperty("Java_EE/Deployment URL", "http://localhost:8080/<WAR filename>/")) + "/x/mirrorimage");
		map.put("negativeimage", Workflow.removeTrailingSlash(Workflow.getLayerProperty("Java_EE/Deployment URL", "http://localhost:8080/<WAR filename>/")) + "/x/negativei");
		map.put("ocr", Workflow.removeTrailingSlash(Workflow.getLayerProperty("Java_EE/Deployment URL", "http://localhost:8080/<WAR filename>/")) + "/x/ocri");
		map.put("orientation", Workflow.removeTrailingSlash(Workflow.getLayerProperty("Java_EE/Deployment URL", "http://localhost:8080/<WAR filename>/")) + "/x/changeorientation");
		map.put("resolution", Workflow.removeTrailingSlash(Workflow.getLayerProperty("Java_EE/Deployment URL", "http://localhost:8080/<WAR filename>/")) + "/x/resolution");
		map.put("rotateimg", Workflow.removeTrailingSlash(Workflow.getLayerProperty("Java_EE/Deployment URL", "http://localhost:8080/<WAR filename>/")) + "/x/rotateimg");
		map.put("sharpenimage", Workflow.removeTrailingSlash(Workflow.getLayerProperty("Java_EE/Deployment URL", "http://localhost:8080/<WAR filename>/")) + "/x/sharpi");
		map.put("watermark", Workflow.removeTrailingSlash(Workflow.getLayerProperty("Java_EE/Deployment URL", "http://localhost:8080/<WAR filename>/")) + "/x/imagewatermarking");
		return map;
    }

    private Map<String,String> getExternalOutarg2varnameMap() {
		return null;
    }

}
