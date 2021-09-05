/* 
 * Copyright (C) 2020 10Xofy. All rights reserved.
 */
package xsemble.controller.n;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import xsemble.controller.Workflow;
import xsemble.controller.Workflow.Node;
import xsemble.controller.Workflow.VarStore;
import xsemble.controller.Workflow.VarStore.ValRecord;


public class Node_InvokeFaceDetection implements Workflow.EntryNode {

    private Demo13.imageprocessing.ent.GetImageFile impl;
    
    @Override
    public String getId() {
        return "#InvokeFaceDetection";
    }

    @Override
    public String toString() {
        return "#InvokeFaceDetection";
    }

    @Override
    public int getType() {
        return 2;
    }

	@Override
	public Map<String, Object> populateInVariables(VarStore varStore, HttpServletRequest request,String sessionId) {

		Map<String,Object> invars = new HashMap<String,Object>();


		return invars;

	}
    
    @Override
    public Map<String, Object> execute(Map<String, Object> inVars, final Workflow.RuntimeContext runtimeContext,
     String sessionId) {
        impl = new Demo13.imageprocessing.ent.GetImageFile();
        Map<String,Object> mOutput = impl.onEntryBegin((HttpServletRequest) runtimeContext.get("request"), inVars, sessionId, "#InvokeFaceDetection",
		  getNodeParams(), getExternalExitpath2urlMap(), getExternalOutarg2varnameMap());

        return mOutput;
    }

    @Override
    public String updateVarStore(Map<String, Object> outVars, VarStore varStore, String sessionId) {
    	boolean b;
        
        b = varStore.setVal(VarStore.STORAGE_REQUEST, "", "imageData", "InvokeFaceDetection", outVars.get("imageData"),  false, false, sessionId);
         
        b = varStore.setVal(VarStore.STORAGE_REQUEST, "", "contextPath", "InvokeFaceDetection", outVars.get("contextPath"),  false, false, sessionId);
         
        b = varStore.setVal(VarStore.STORAGE_REQUEST, "", "degree", "InvokeFaceDetection", outVars.get("degree"),  false, false, sessionId);
 
        return (String)outVars.get("_exitPath");
    }

    @Override
    public Node nextNode(String exitPath, String sessionId) {
    
        if (exitPath.equals("next"))
            return new xsemble.controller.n.Node_FaceDetection();
		return null;

    }
    
    @Override
    public void onRequestEnd() {
    
        impl.onEntryEnd();
	
    }
    
    @Override
    public void onMessage() {
    	
    }
    
    private Map<String,String> getNodeParams() {
		return null;
    }

    private Map<String,String> getExternalExitpath2urlMap() {
		return null;
    }

    private Map<String,String> getExternalOutarg2varnameMap() {
		return null;
    }
}
