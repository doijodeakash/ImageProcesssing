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

public class ChangeOrientation {
	private static final Logger logger = LoggerFactory.getLogger(ChangeOrientation.class);
	private static final int FLIP_VERTICAL = 1;

	public static final int FLIP_HORIZONTAL = -1;

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
		String encoded_out = "";
		java.lang.String sourceImageEncoded_out = null;

		// Exit path
		String exitpath = "next"; // Possible values are "next"
// USER-CODE-BLOCK BEGIN -- Do not modify anything outside this block or this comment line.

		ByteArrayInputStream bais = new ByteArrayInputStream(imageData_in);

		// input buffered image object

//		try {
//			BufferedImage image = ImageIO.read(bais);
//			// System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//			// loads methods of the opencv library
//			System.load(contextPath_in + "WEB-INF\\classes\\opencv_java452.dll");
//			byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
//			Mat mat = new Mat(image.getHeight(), image.getWidth(), CvType.CV_8UC3);
//			mat.put(0, 0, data);
//
//			// creating a new mat object and putting the modified input mat object by using
//			// flip()
//			Mat newMat = new Mat(image.getHeight(), image.getWidth(), CvType.CV_8UC3);
//			Core.flip(mat, newMat, -1); // flipping the image about both axis
//
//			// converting the newly created mat object to buffered image object
//			byte[] newData = new byte[newMat.rows() * newMat.cols() * (int) (newMat.elemSize())];
//			newMat.get(0, 0, newData);
//			BufferedImage image1 = new BufferedImage(newMat.cols(), newMat.rows(), 5);
//			image1.getRaster().setDataElements(0, 0, newMat.cols(), newMat.rows(), newData);
//
//			ByteArrayOutputStream baos = new ByteArrayOutputStream();
//			ImageIO.write(image1, "jpg", baos);
//			imageData_in = baos.toByteArray();
//			encoded_out = Base64.getEncoder().encodeToString(imageData_in);
//
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			System.gc();
//		}
//		File input = null, output = null;
		// int direction;
		sourceImageEncoded_out = encoded_out = Base64.getEncoder().encodeToString(imageData_in);
		try {
			BufferedImage image = ImageIO.read(bais);
			int width = image.getWidth();

			int height = image.getHeight();

			BufferedImage flipped = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {

					// TODO: 1 Flip Vetically
					flipped.setRGB(x, (height - 1) - y, image.getRGB(x, y));
					// TODO: 2 Flips Horizontally
					// flipped.setRGB((width - 1) - x, y, image.getRGB(x, y));

				}
			}
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(flipped, "jpg", baos);
			imageData_in = baos.toByteArray();
			encoded_out = Base64.getEncoder().encodeToString(imageData_in);
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		// TODO: handle exception

// USER-CODE-BLOCK END -- Do not modify anything outside this block or this comment line.

		// Create outargs and populate it
		HashMap<String, Object> outData = new HashMap<String, Object>();
		outData.put("_exitPath", exitpath);
		outData.put("encoded", encoded_out);
		outData.put("sourceImageEncoded", sourceImageEncoded_out);

		return outData;
	}

// USER-CODE-BLOCK BEGIN -- Do not modify anything outside this block or this comment line.
// USER-CODE-BLOCK END -- Do not modify anything outside this block or this comment line.
}
