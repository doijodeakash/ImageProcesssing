/* 
 * Copyright (C) 2020 10Xofy. All rights reserved.
 
 */
package X.ImageProcessing.oprn;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
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

public class ImageWatermarking {
	private static final Logger logger = LoggerFactory.getLogger(ImageWatermarking.class);
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

		java.lang.String watermarkText_in = (java.lang.String) inargs.get("watermarkText");

		//// Out arguments (outargs)
		// java.lang.String outputImagePath_out = null;
		java.lang.String encoded_out = "";
		java.lang.String sourceImageEncoded_out = null;

		// Exit path
		String exitpath = "next"; // Possible values are "next"
// USER-CODE-BLOCK BEGIN -- Do not modify anything outside this block or this comment line.
		sourceImageEncoded_out = encoded_out = Base64.getEncoder().encodeToString(imageData_in);
		BufferedImage img = null;

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		// read source image file

		try {
			// Read image
			ByteArrayInputStream bais = new ByteArrayInputStream(imageData_in);
			img = ImageIO.read(bais);
		} catch (IOException e) {
			System.out.println("Error: " + e);
		}

		// create BufferedImage object of same width and
		// height as of input image
		BufferedImage temp = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);

		// Create graphics object and add original
		// image to it
		Graphics graphics = temp.getGraphics();
		graphics.drawImage(img, 0, 0, null);

		// Set font for the watermark text
		graphics.setFont(new Font("Brush Script MT", Font.PLAIN, 60));
		graphics.setColor(new Color(245, 200, 245));

		// Setting watermark text
		String watermark = "Watermark Applied";

		// Add the watermark text at (width/5, height/3)
		// location
		graphics.drawString(watermarkText_in, img.getWidth() / 2, img.getHeight() / 2);

		// releases any system resources that it is using
		graphics.dispose();

		try {
			ImageIO.write(temp, "png", bos);
		} catch (IOException e) {
			System.out.println(e);
		} finally {
			System.gc();
		}
		byte[] byteImageData_out = bos.toByteArray();
		encoded_out = Base64.getEncoder().encodeToString(byteImageData_out);

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
