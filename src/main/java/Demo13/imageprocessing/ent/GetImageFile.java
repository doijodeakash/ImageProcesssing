/* 
 * Copyright (C) 2020 10Xofy. All rights reserved.
 */

package Demo13.imageprocessing.ent;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// USER-IMPORT-BLOCK BEGIN -- Do not modify anything outside this block or this comment line.

// TODO: Add imports here.

// USER-IMPORT-BLOCK END -- Do not modify anything outside this block or this comment line.

/**
Send byte data of array to the next method
*/
public class GetImageFile
{
	private static final Logger logger = LoggerFactory.getLogger(GetImageFile.class);
// USER-CODE-BLOCK BEGIN -- Do not modify anything outside this block or this comment line.        

	// TODO: Add any instance/static variables here.
		
// USER-CODE-BLOCK END -- Do not modify anything outside this block or this comment line.

	/**
	 * This method is called towards the end of the request.
	 * Thus, if you initialize any resources for the request context in onEntryBegin() method,
	 * then this method may be used for cleaning those up.
	 */
	public void onEntryEnd() {

// USER-CODE-BLOCK BEGIN -- Do not modify anything outside this block or this comment line.        

		// TODO: Add any resource cleanups to execute on request end
		
// USER-CODE-BLOCK END -- Do not modify anything outside this block or this comment line.
    }

	/**
	 * This function is automatically called by Xsemble framework when the entry point is encountered.
	 *
	 * @param request The incoming HTTP request (as in Java EE / Jakarta EE servlet specification)
	 * @param inargs The incoming arguments to this node as a map of name vs value
	 * @param currentSession Identifier of the current session and request
	 * @param currentNode Identifier of the entry point instance
	 * @param params Configuration parameters of the entry point node
	 * @param externalExitpath2urlMap Mapping of exitpaths that connect outside the layer, with the target URLs
	 * @param externalOutarg2varnameMap Mapping of outargs that connect outside the layer, key being "<exitpath>/<outarg>' and value being the inarg name of the next Entry instance
	 */
	public Map<String,Object> onEntryBegin(HttpServletRequest request, Map<String,Object> inargs, String currentSession, String currentNode,
	  Map<String,String> params, Map<String,String> externalExitpath2urlMap, Map<String,String> externalOutarg2varnameMap)
	{
		logger.info("Session:"+ currentSession +" - encountered entry point " + currentNode);
		//// Out arguments (outargs)
		byte[] imageData_out = null;
		java.lang.String contextPath_out = null;
		contextPath_out = request.getServletContext().getRealPath("");
		java.lang.Integer degree_out = -1;
		java.lang.String watermarkText_out = request.getParameter("watermarkText");

		// Exit path
		String exitpath = "next";  // Possible values are "next" 
// USER-CODE-BLOCK BEGIN -- Do not modify anything outside this block or this comment line.
 
				String simgData = "";
		String outimgdata = "";
		degree_out = request.getParameter("rotate") != null ? Integer.parseInt(request.getParameter("rotate")) : 1;

		System.out.println(degree_out);

		if (request.getParameter("outputImage") == null && request.getParameter("sourceImage") == null) {

			boolean isMultipartContent = ServletFileUpload.isMultipartContent(request);
			if (!isMultipartContent) {
				return null;
			}
			FileItem fileContent;
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			try {
				imageData_out = (upload.parseRequest(request)).get(0).get();

			} catch (Exception e) {
				System.out.println(e);
			}
		}

		// request.getParameter("image_radio");

		if ("sourceImage".equals(request.getParameter("image_radio"))) {
			simgData = request.getParameter("sourceImage");
			simgData = simgData.substring(22);
			imageData_out = Base64.getDecoder().decode(simgData);
		}
		if ("outputImage".equals(request.getParameter("image_radio"))) {
			outimgdata = request.getParameter("outputImage");
			outimgdata = outimgdata.substring(22);
			imageData_out = Base64.getDecoder().decode(outimgdata);
		}

// USER-CODE-BLOCK END -- Do not modify anything outside this block or this comment line.

		// Create outargs and populate it
		HashMap<String, Object> outData = new HashMap<String, Object>();
		outData.put("_exitPath",exitpath);
		outData.put("imageData", imageData_out);
		outData.put("contextPath", contextPath_out);
		outData.put("degree", degree_out);
		outData.put("watermarkText", watermarkText_out);

		return outData;
	}
}

// USER-CODE-BLOCK BEGIN -- Do not modify anything outside this block or this comment line.
 
		// TODO: Add any required methods here.

// USER-CODE-BLOCK END -- Do not modify anything outside this block or this comment line.
