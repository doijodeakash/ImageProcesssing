/* 
 * Copyright (C) 2020 10Xofy. All rights reserved.
 
 */
package X.ImageProcessing.oprn;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

// USER-IMPORT-BLOCK BEGIN -- Do not modify anything outside this block or this comment line.

// TODO Add imports here.

// USER-IMPORT-BLOCK END -- Do not modify anything outside this block or this comment line.

/**

*/

public class OCRCI {
	private static final Logger logger = LoggerFactory.getLogger(OCRCI.class);
// USER-CODE-BLOCK BEGIN -- Do not modify anything outside this block or this comment line.        

	// TODO Add any instance/static variables here.

// USER-CODE-BLOCK END -- Do not modify anything outside this block or this comment line.

	/**
	 * This function is automatically called by Xsemble framework when the method is
	 * encountered.
	 *
	 * @param inargs                    The incoming arguments to this node as a map
	 *                                  of name vs value
	 * @param currentSession            Identifier of the current session and
	 *                                  request
	 * @param currentNode               Identifier of the method instance
	 * @param params                    Configuration parameters of the method
	 * @param externalExitpath2urlMap   Mapping of exitpaths that connect outside
	 *                                  the layer, with the target URLs
	 * @param externalOutarg2varnameMap Mapping of outargs that connect outside the
	 *                                  layer, key being "<exitpath>/<outarg>' and
	 *                                  value being the inarg name of the next Entry
	 *                                  instance
	 * @return A map of outputs pertaining to the method execution
	 */
	public Map<String, Object> run(Map<String, Object> inargs, String currentSession, String currentNode,
			Map<String, String> params, Map<String, String> externalExitpath2urlMap,
			Map<String, String> externalOutarg2varnameMap) {
		logger.info("Session:" + currentSession + " - Entered method " + currentNode);
		//// In arguments (inargs)
		java.lang.String contextPath_in = (java.lang.String) inargs.get("contextPath");
		byte[] imageData_in = (byte[]) inargs.get("imageData");
		//// Out arguments (outargs)
		java.lang.String output_out = null;
		java.lang.String sourceImageEncoded_out = null;

		// Exit path
		String exitpath = "next"; // Possible values are "next"
// USER-CODE-BLOCK BEGIN -- Do not modify anything outside this block or this comment line.

		sourceImageEncoded_out = Base64.getEncoder().encodeToString(imageData_in);

		Tesseract tesseract = new Tesseract();
		System.out.println(contextPath_in);
		System.out.println(System.getenv("TESSDATA_PREFIX"));
		tesseract.setDatapath(contextPath_in + "WEB-INF\\classes\\tessdata");
		// tesseract.setDatapath(System.getenv("TESSDATA_PREFIX"));

		try {
			// the path of your tess data folder
			// inside the extracted file
			output_out = tesseract.doOCR(ImageIO.read(new ByteArrayInputStream(imageData_in)));
			// output_out = data_out;

			// path of your image file
			// System.out.print(output_out);
		} catch (TesseractException | IOException e) {
			e.printStackTrace();
		} finally {
			System.gc();
		}

// USER-CODE-BLOCK END -- Do not modify anything outside this block or this comment line.

		// Create outargs and populate it
		HashMap<String, Object> outData = new HashMap<String, Object>();
		outData.put("_exitPath", exitpath);
		outData.put("output", output_out);
		outData.put("sourceImageEncoded", sourceImageEncoded_out);

		return outData;
	}

// USER-CODE-BLOCK BEGIN -- Do not modify anything outside this block or this comment line.

	// TODO: Add any required methods here.

// USER-CODE-BLOCK END -- Do not modify anything outside this block or this comment line.
}
