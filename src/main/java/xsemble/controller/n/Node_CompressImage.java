/* 
 * Copyright (C) 2020 10Xofy. All rights reserved.
 */
package xsemble.controller.n;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.function.*;

import xsemble.controller.Workflow;
import xsemble.controller.Workflow.Node;
import xsemble.controller.Workflow.VarStore;
import xsemble.controller.Workflow.VarStore.ValRecord;


public class Node_CompressImage implements Workflow.Node {

	private X.ImageProcessing.oprn.CompressAnImage impl;
    
    @Override
    public String getId() {
        return "#CompressImage";
    }

    @Override
    public String toString() {
        return "#CompressImage";
    }

    @Override
    public int getType() {
        return 0;
    }

    @Override
    public Map<String, Object> populateInVariables(VarStore varStore, HttpServletRequest request, String sessionId) {
        
        Map<String,Object> invars = new HashMap<String,Object>();
        
        invars.put("imageData", varStore.getVal(VarStore.STORAGE_REQUEST, "", "imageData"));
        
        invars.put("contextPath", varStore.getVal(VarStore.STORAGE_REQUEST, "", "contextPath"));

        return invars;
    }
    
	@Override
    public Map<String, Object> execute(Map<String, Object> inVars,final Workflow.RuntimeContext runtimeContext,
     String sessionId) {
		X.ImageProcessing.oprn.CompressAnImage m = new X.ImageProcessing.oprn.CompressAnImage();
    	Map<String,Object> mOutput = m.run(inVars, sessionId, "#CompressImage",
    	  getNodeParams(), getExternalExitpath2urlMap(), getExternalOutarg2varnameMap());
		return mOutput;

    }

    @Override
	public String updateVarStore(Map<String, Object> outVars, VarStore varStore, String sessionId) {
		boolean b;

    	b = varStore.setVal(VarStore.STORAGE_REQUEST, "", "sourceImage", "CompressImage", outVars.get("sourceImageEncoded"), false, false, sessionId);
 
    	b = varStore.setVal(VarStore.STORAGE_REQUEST, "", "encoded", "CompressImage", outVars.get("encoded"), false, false, sessionId);
 
        return (String)outVars.get("_exitPath");
    }

    @Override
    public Node nextNode(String exitPath, String sessionId) {
		if (exitPath.equals("next"))
			return new xsemble.controller.n.Node_OutputPage();

		return null;
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
