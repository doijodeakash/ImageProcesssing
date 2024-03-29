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

// TODO Add imports here.

// USER-IMPORT-BLOCK END -- Do not modify anything outside this block or this comment line.

/**

*/

public class RotateImageByDegree {
	private static final Logger logger = LoggerFactory.getLogger(RotateImageByDegree.class);
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
		java.lang.Integer rotate_in = (java.lang.Integer) inargs.get("degree");
		//// Out arguments (outargs)
		java.lang.String sourceImageEncoded_out = null;
		java.lang.String encoded_out = null;

		// Exit path
		String exitpath = "next"; // Possible values are "next"
// USER-CODE-BLOCK BEGIN -- Do not modify anything outside this block or this comment line.
		sourceImageEncoded_out = encoded_out = Base64.getEncoder().encodeToString(imageData_in);
		System.out.println(rotate_in);
		try {
			BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageData_in));

			int width = image.getWidth();
			int height = image.getHeight();

			BufferedImage rotated = new BufferedImage(height, width, image.getType());

			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {

					switch (rotate_in) {
					case 1:
						// rotate left 1
						rotated.setRGB(y, (width - 1) - x, image.getRGB(x, y));

						break;
					case -1:
						// rotate right -1
						rotated.setRGB((height - 1) - y, x, image.getRGB(x, y));
					}
				}
			}
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ImageIO.write(rotated, "jpg", bos);
			imageData_in = bos.toByteArray();
			encoded_out = Base64.getEncoder().encodeToString(imageData_in);

		} catch (IOException ex) {
			ex.printStackTrace();
		}

// USER-CODE-BLOCK END -- Do not modify anything outside this block or this comment line.

		// Create outargs and populate it
		HashMap<String, Object> outData = new HashMap<String, Object>();
		outData.put("_exitPath", exitpath);
		outData.put("sourceImageEncoded", sourceImageEncoded_out);
		outData.put("encoded", encoded_out);

		return outData;
	}

// USER-CODE-BLOCK BEGIN -- Do not modify anything outside this block or this comment line.

// TODO: Add any required methods here.

// USER-CODE-BLOCK END -- Do not modify anything outside this block or this comment line.
}
