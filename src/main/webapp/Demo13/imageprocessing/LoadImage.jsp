<% /*
-- File: src/main/webapp/Demo13/imageprocessing/LoadImage.jsp 
-- Copyright (C) 2020 10Xofy. All rights reserved.
-- 
*/%>
<%@ page import="org.slf4j.Logger, org.slf4j.LoggerFactory, xsemble.controller.PageHelper"%> 
<%// USER-IMPORT-BLOCK BEGIN -- Do not modify anything outside this block or this comment line.

// TODO Add imports here.

// USER-IMPORT-BLOCK END -- Do not modify anything outside this block or this comment line.
%>
<%
		// The input arguments of the page 
java.util.Map<String, Object> inargs = (java.util.Map<String, Object>) request.getAttribute("_inargs");
// Configuration parameters of the page
java.util.Map<String, String> params = (java.util.Map<String, String>) request.getAttribute("_params");
// Mapping of exitpaths that connect outside the layer, with the URLs that they point to
java.util.Map<String, String> externalExitpath2urlMap = (java.util.Map<String, String>) request
    .getAttribute("_externalExitpath2urlMap");
// Mapping of outargs that connect outside the layer, key being "<exitpath>/<outarg>' and value being the inarg name of the next Entry instance
java.util.Map<String, String> externalOutarg2varnameMap = (java.util.Map<String, String>) request
    .getAttribute("_externalOutarg2varnameMap");
// Identifier of the page instance
String pageid = (String) request.getAttribute("_currentNode");
// Identifier of the current session and request
String currentSession = (String) request.getAttribute("_currentSession");
//// In arguments (inargs)
java.lang.String output_in = (java.lang.String) inargs.get("output");

		//// External exit paths (connect to another layer)
		// mirrorimage: {Connector tech 'HTTP/GET'}
		// watermark: {Connector tech 'HTTP/GET'}
		// orientation: {Connector tech 'HTTP/GET'}
		// ocr: {Connector tech 'HTTP/GET'}
		// facedetection: {Connector tech 'HTTP/GET'}
		// blurimage: {Connector tech 'HTTP/GET'}
		// negativeimage: {Connector tech 'HTTP/GET'}
		// greyscaleimage: {Connector tech 'HTTP/GET'}
		// sharpenimage: {Connector tech 'HTTP/GET'}
		// resolution: {Connector tech 'HTTP/GET'}
		// compress: {Connector tech 'HTTP/GET'}
		// rotateimg: {Connector tech 'HTTP/GET'}
%>
<%! Logger logger = LoggerFactory.getLogger(getClass()); %>
<% logger.info("Session:"+ currentSession+" - Entered page " + pageid); %>
<%
// USER-CODE-BLOCK BEGIN -- Do not modify anything outside this block or this comment line. %>

<html>
<head>
<script
  src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script
  src="https://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"
  class="jsbin"></script>
<link href="./css/style.css" rel="stylesheet" />
<%@include file="/include/header.txt"%>

<script>
  
<%@include file="/script/imagepro.js" %>
  
</script>
</head>
<body>
  <%-- <%=PageHelper.getPageActionUrl(pageid, "ocri", request)%> --%>
  <%-- <form action=<%=PageHelper.getPageActionUrl(pageid, "next", request) %> method="post" enctype="multipart/form-data"> --%>
  <form action=<%=PageHelper.getPageActionUrl(pageid, "ocri", request)%>
    method='post' enctype="multipart/form-data">
    <!-- <h1>Image Processing<span>With jQuery and CSS</span></h1> -->
    <div class="file-upload">
      <button class="file-upload-btn" type="button"
        onclick="$('.file-upload-input').trigger( 'click' )">Add
        Image</button>

      <div class="image-upload-wrap">
        <input class="file-upload-input" type='file' name="file"
          onchange="readURL(this);" accept="image/*" />
        <div class="drag-text">
          <h3>Drag and drop a file or select add Image</h3>
        </div>
      </div>
      <div class="file-upload-content">
        <input type="hidden" id="imagePath" name="imagePath" value="a">
        <img name="sourceImage" class="file-upload-image" src="#"
          id="sourceImage" alt="your image" />
        <div class="image-title-wrap">
          <button type="button" onclick="removeUpload()" class="remove-image">
            Remove <span class="image-title">Uploaded Image</span>
          </button>
          <button type="submit" onclick="ocri()" class="upload-image">
            <span class="image-title">OCR</span>
          </button>
          <button type="submit" onclick="mirrori()" class="upload-image">
            <span class="image-title">Mirror Image</span>
          </button>
          <button type="submit" onclick="watermarki()" class="upload-image">
            <span class="image-title">WaterMark Image</span>
          </button>
          <button type="submit" onclick="rotateimg()" class="upload-image">
            <span class="image-title">Rotate</span>
          </button>
          <button type="submit" onclick="orientationi()" class="upload-image">
            <span class="image-title">Change Orientation</span>
          </button>
          <button type="submit" onclick="facedetectioni()"
            class="upload-image">
            <span class="image-title">Face Detection</span>
          </button>
          <button type="submit" onclick="bluri()" class="upload-image">
            <span class="image-title">Blur Image</span>
          </button>
          <button type="submit" onclick="negativei()" class="upload-image">
            <span class="image-title">Negative Image</span>
          </button>
          <button type="submit" onclick="greyscalei()" class="upload-image">
            <span class="image-title">Grey Scale Image</span>
          </button>
          <button type="submit" onclick="sharpeni()" class="upload-image">
            <span class="image-title">Sharpen Image</span>
          </button>
          <button type="submit" onclick="resolutioni()" class="upload-image">
            <span class="image-title">Change Resolution Image</span>
          </button>
          <button type="submit" onclick="compressi()" class="upload-image">
            <span class="image-title">Compress Image</span>
          </button>
        </div>
      </div>
    </div>
  </form>

</body>
</html>

<%// USER-CODE-BLOCK END -- Do not modify anything outside this block or this comment line. %> 

