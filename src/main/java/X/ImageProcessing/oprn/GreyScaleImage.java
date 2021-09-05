/* 
 * Copyright (C) 2020 10Xofy. All rights reserved.
 
 */
package X.ImageProcessing.oprn;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// USER-IMPORT-BLOCK BEGIN -- Do not modify anything outside this block or this comment line.
// USER-IMPORT-BLOCK END -- Do not modify anything outside this block or this comment line.

/**

*/

public class GreyScaleImage {
	private static final Logger logger = LoggerFactory.getLogger(GreyScaleImage.class);
// USER-CODE-BLOCK BEGIN -- Do not modify anything outside this block or this comment line.        
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
		// java.lang.String outputImagePath_out = null;
		java.lang.String encoded_out = "";
		java.lang.String sourceImageEncoded_out = null;

		// Exit path
		String exitpath = "next"; // Possible values are "next"
// USER-CODE-BLOCK BEGIN -- Do not modify anything outside this block or this comment line.
		sourceImageEncoded_out = encoded_out = Base64.getEncoder().encodeToString(imageData_in);
		BufferedImage img = null;
		// read image
		try {

			ByteArrayInputStream bais = new ByteArrayInputStream(imageData_in);
			img = ImageIO.read(bais);

		} catch (IOException e) {
			System.out.println(e);
		}

		// get image's width and height
		int width = img.getWidth();
		int height = img.getHeight();

		// convert to greyscale
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				// Here (x,y)denotes the coordinate of image
				// for modifying the pixel value.
				int p = img.getRGB(x, y);

				int a = (p >> 24) & 0xff;
				int r = (p >> 16) & 0xff;
				int g = (p >> 8) & 0xff;
				int b = p & 0xff;

				// calculate average
				int avg = (r + g + b) / 3;

				// replace RGB value with avg
				p = (a << 24) | (avg << 16) | (avg << 8) | avg;

				img.setRGB(x, y, p);
			}
		}

		// write image
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ImageIO.write(img, "png", bos);
			imageData_in = bos.toByteArray();
			encoded_out = Base64.getEncoder().encodeToString(imageData_in);
		} catch (IOException e) {
			System.out.println(e);
		} finally {
			System.gc();
		}

// USER-CODE-BLOCK END -- Do not modify anything outside this block or this comment line.

		// Create outargs and populate it
		HashMap<String, Object> outData = new HashMap<String, Object>();
		outData.put("_exitPath", exitpath);
		// outData.put("outputImagePath", outputImagePath_out);
		outData.put("encoded", encoded_out);
		outData.put("sourceImageEncoded", sourceImageEncoded_out);
		return outData;
	}

// USER-CODE-BLOCK BEGIN -- Do not modify anything outside this block or this comment line.
// USER-CODE-BLOCK END -- Do not modify anything outside this block or this comment line.
}
