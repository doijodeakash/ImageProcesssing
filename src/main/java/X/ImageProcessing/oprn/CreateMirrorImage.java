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

public class CreateMirrorImage {
	private static final Logger logger = LoggerFactory.getLogger(CreateMirrorImage.class);
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
		// java.lang.String sendOutputImagePath_out = null;
		java.lang.String encoded_out = "";
		java.lang.String sourceImageEncoded_out = null;

		// Exit path
		String exitpath = "next"; // Possible values are "next"
// USER-CODE-BLOCK BEGIN -- Do not modify anything outside this block or this comment line.
		sourceImageEncoded_out = encoded_out = Base64.getEncoder().encodeToString(imageData_in);
		// BufferedImage for source image
		BufferedImage simg = null;

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		// read source image file

		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(imageData_in);
			simg = ImageIO.read(bais);
		} catch (IOException e) {
			System.out.println("Error: " + e);
		}
		// get source image dimension
		int width = simg.getWidth();
		int height = simg.getHeight();
		// BufferedImage for mirror image
		BufferedImage mimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		// create mirror image pixel by pixel
//		for (int y = 0; y < height; y++) {
//			for (int lx = 0, rx = width * 2 - 1; lx < width; lx++, rx--) {
//				// lx starts from the left side of the image
//				// rx starts from the right side of the image
//				// get source pixel value
//				int p = simg.getRGB(lx, y);
//				// set mirror image pixel value - both left and right
//				mimg.setRGB(lx, y, p);
//				mimg.setRGB(rx, y, p);
//			}
//		}

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {

				// TODO: 1 Flip Vetically
				// mimg.setRGB(x, (height - 1) - y, mimg.getRGB(x, y));
				// TODO: 2 Flips Horizontally
				mimg.setRGB((width - 1) - x, y, simg.getRGB(x, y));

			}
		}

		// save mirror image
		try {
			simg = mimg;
			ImageIO.write(simg, "png", bos);
		} catch (IOException e) {
			System.out.println("Error: " + e);
		} finally {
			System.gc();
		}
		byte[] byteImageData_out = bos.toByteArray();
		encoded_out = Base64.getEncoder().encodeToString(byteImageData_out);
		// System.out.println(encoded_out);

// USER-CODE-BLOCK END -- Do not modify anything outside this block or this comment line.

		// Create outargs and populate it
		HashMap<String, Object> outData = new HashMap<String, Object>();
		outData.put("_exitPath", exitpath);
//		outData.put("sendOutputImagePath", sendOutputImagePath_out);
		outData.put("encoded", encoded_out);
		outData.put("sourceImageEncoded", sourceImageEncoded_out);

		return outData;
	}

// USER-CODE-BLOCK BEGIN -- Do not modify anything outside this block or this comment line.
// USER-CODE-BLOCK END -- Do not modify anything outside this block or this comment line.
}
