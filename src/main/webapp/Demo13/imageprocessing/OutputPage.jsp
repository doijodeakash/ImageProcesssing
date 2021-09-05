<% /*
-- File: src/main/webapp/Demo13/imageprocessing/OutputPage.jsp 
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
java.lang.String encoded_in = (java.lang.String) inargs.get("encoded");
java.lang.String output_in = (java.lang.String) inargs.get("output");
java.lang.String sourceImage_in = (java.lang.String) inargs.get("sourceImage");

		//// External exit paths (connect to another layer)
		// mirrorimage: {Connector tech 'HTTP/GET'}
		// rotateimg: {Connector tech 'HTTP/GET'}
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
%>
<%! Logger logger = LoggerFactory.getLogger(getClass()); %>
<% logger.info("Session:"+ currentSession+" - Entered page " + pageid); %>
<%
// USER-CODE-BLOCK BEGIN -- Do not modify anything outside this block or this comment line. %>

<html>
<head>
<title>Image Processing : Output</title>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"
	class="jsbin"></script>
<link href="../css/style.css" rel="stylesheet" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
     <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<%@include file="/include/header.txt"%>
<script>	
<%@include file="/script/imagepro.js" %>
function downloadImage(){
	var a = document.createElement("a"); //Create <a>
    a.href = "data:image/jpg;base64," + "<%=encoded_in%>";
    a.download = "OutPut.jpg"; //File name Here
    a.click(); //Downloaded file
}
</script>
<style>
img {
	width: 370px;
	height: 520px;
}

.imageContainer {
	margin: .5rem 0;
	display: flex;
	align-content: space-between;
	justify-content: space-evenly;
	align-items: center;
	/*     padding: 20px; */
}

.imageOperation {
	display: flex;
	flex-direction: column;
}

.header {
	display: inline-block;
}
</style>


</head>
<body>
	<%
//BufferedImage bImage = ImageIO.read(new File("/home/visruth/Desktop/Visruth.jpg"));//give the path of an image
//ByteArrayOutputStream baos = new ByteArrayOutputStream();
//ImageIO.write( bImage, "jpg", baos );
//baos.flush();
//byte[] imageInByteArray = baos.toByteArray();
//baos.close();
//String b64 = javax.xml.bind.DatatypeConverter.printBase64Binary(imageInByteArray);
%>
	<form method='post'>
		<div class="imageContainer">
			<div class="ioimage container">
				<!-- <p>As of v6, Java SE provides JAXB</p> -->
				<p>Original Image</p>
				<input type="hidden" name="sourceImage"
					value="data:image/jpg;base64,<%=sourceImage_in%>" /> <label
					class="image-radio"> <input type="radio" name="image_radio"
					value="sourceImage" checked="checked" /> <img
					class="img-responsive image"
					src="data:image/jpg;base64, <%=sourceImage_in%>" alt="" />
				</label>
				<!-- <button type="button" onclick="removeUpload()" class="remove-image">Remove <span class="image-title">Uploaded Image</span></button> -->
			</div>
			<div class="imageOperation">
				<button type="submit" onclick="ocri()" class="upload-image">
					<span class="image-title">OCR</span>
				</button>
				<button type="submit" onclick="mirrori()" class="upload-image">
					<span class="image-title">Mirror Image</span>
				</button>
				<button type="button" name="watermark" class="upload-image" data-toggle="modal" data-target="#myModal">
					<span class="image-title">WaterMark Image</span>
				</button>
				<button type="submit" onclick="orientationi()" class="upload-image">
					<span class="image-title">Change Orientation</span>
				</button>
				<!-- <button type="submit" onclick="rotateimg()" class="upload-image">
					<span class="image-title">Rotate</span>
				</button> -->
				<button type="button" name="rotate" class="upload-image"  data-toggle="modal" data-target="#myModal">
				<span class="image-title">Rotate</span>
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
			<div class="ioimage container">
				<!-- <p>As of v6, Java SE provides JAXB</p> -->
				<p>Output Image</p>
				<input type="hidden" name="outputImage"
					value="data:image/jpg;base64,<%=encoded_in%>" /> <label
					class="image-radio"> <input type="radio" name="image_radio"
					value="outputImage" /> <img class="img-responsive image"
					src="data:image/jpg;base64,<%=encoded_in%>" alt="" />
				</label>
				<%-- <a href="data:image/jpg;base64, <%=encoded_in%>" class="upload-image" download="outupt.jpg">download</a> --%>
				<button type="button" onclick="downloadImage()"
					class="upload-image middle">
					<span class="image-title text">download</span>
				</button>
			</div>
		</div>
		
		<!-- Bootstrap Modal -->
<div class="modal fade mt-5 " id="myModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true" style="padding:100px">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <!-- <div class="modal-header">
        <h2>Signup        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">×</span>
        </button></h2>

      </div> -->
	<div class="rotate-body mt-2" id="rotatepopup">
		<label class="rotateImg">
		<input type="radio" onchange="rotateimg()" name="rotate" value="1"/>Rotate Left</label> 
		<label class="rotateImg">
		<input type="radio" onchange="rotateimg()" name="rotate" value="-1" />Rotate Right</label>
	</div>
	<div class="rotate-body" id="watermarkpopup">
		<input type="text" placeholder="WaterMark Text" name="watermarkText" maxlength="20" max="20" min="5"/>
		<button type="submit" onclick="watermarki()" class="upload-image" >
			<span class="image-title">Add WaterMark</span>
		</button>
	</div>
<!-- 	<div class="modal-footer">
<button type="button" class="remove-image  btn btn-secondary" data-dismiss="modal"><span class="image-title">Close</span></button>
        <button type="submit" onclick="rotateimg()" class="upload-image btn btn-primary">
					<span class="image-title">Rotate</span>
				</button> -->
      </div>
    </div>
  </div>
</div>
		
		<pre>
		<%=output_in != null ? output_in : ""%>
</pre>
	</form>
</body>
</html>

<%// USER-CODE-BLOCK END -- Do not modify anything outside this block or this comment line. %> 

