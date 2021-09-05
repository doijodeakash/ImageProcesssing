/* 
 * Copyright (C) 2020 10Xofy. All rights reserved.
 */
package xsemble.controller;


import javax.servlet.AsyncContext;
import javax.servlet.AsyncListener;
import javax.servlet.AsyncEvent;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@WebServlet(urlPatterns="/x/facedetection", name="Entry_x_facedetection" )

public class Entry_x_facedetection  extends HttpServlet  {

    private Workflow.RuntimeContext runtimeContext = null;
    private static final Logger log = LoggerFactory.getLogger(Entry_x_facedetection.class);


    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) {


        runtimeContext = new Workflow.RuntimeContext();
        runtimeContext.put("servletContext", this.getServletContext());
        runtimeContext.put("request", request);
        runtimeContext.put("sessionId", request.getSession().getId());
        runtimeContext.put("requestId",  Workflow.RuntimeContext.uniqid());
        

        runtimeContext.put("response", response);
        new Workflow(runtimeContext).executeWorkflow(new xsemble.controller.n.Node_InvokeFaceDetection());

    }

}
