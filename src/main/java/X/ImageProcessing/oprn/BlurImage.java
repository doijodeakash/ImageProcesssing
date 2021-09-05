/* 
 * Copyright (C) 2020 10Xofy. All rights reserved.
 
 */
package X.ImageProcessing.oprn;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// USER-IMPORT-BLOCK BEGIN -- Do not modify anything outside this block or this comment line.
// USER-IMPORT-BLOCK END -- Do not modify anything outside this block or this comment line.

/**

*/

public class BlurImage {
	private static final Logger logger = LoggerFactory.getLogger(BlurImage.class);
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
		java.lang.String encoded_out = null;
		java.lang.String sourceImageEncoded_out = null;

		// Exit path
		String exitpath = "next"; // Possible values are "next"
// USER-CODE-BLOCK BEGIN -- Do not modify anything outside this block or this comment line.
		sourceImageEncoded_out = encoded_out = Base64.getEncoder().encodeToString(imageData_in);
		try {
			System.load(contextPath_in + "WEB-INF\\classes\\opencv_java452.dll");
			// System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
			Mat source = Imgcodecs.imdecode(new MatOfByte(imageData_in), Imgcodecs.IMREAD_UNCHANGED);
			Mat destination = new Mat(source.rows(), source.cols(), source.type());
			Imgproc.GaussianBlur(source, destination, new Size(45, 45), 0);
			MatOfByte mob = new MatOfByte();
			Imgcodecs.imencode(".jpg", destination, mob);
			imageData_in = mob.toArray();
			encoded_out = Base64.getEncoder().encodeToString(imageData_in);

		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
		} finally {
			System.gc();
		}

// USER-CODE-BLOCK END -- Do not modify anything outside this block or this comment line.

		// Create outargs and populate it
		HashMap<String, Object> outData = new HashMap<String, Object>();
		outData.put("_exitPath", exitpath);
		outData.put("encoded", encoded_out);
		outData.put("sourceImageEncoded", sourceImageEncoded_out);

		return outData;
	}

// USER-CODE-BLOCK BEGIN -- Do not modify anything outside this block or this comment line.

//	public static BufferedImage Mat2BufferedImage(Mat mat) throws IOException {
//		// Encoding the image
//		MatOfByte matOfByte = new MatOfByte();
//		Imgcodecs.imencode(".jpg", mat, matOfByte);
//		// Storing the encoded Mat in a byte array
//		byte[] byteArray = matOfByte.toArray();
//		// Preparing the Buffered Image
//		InputStream in = new ByteArrayInputStream(byteArray);
//		BufferedImage bufImage = ImageIO.read(in);
//		return bufImage;
//	}

// USER-CODE-BLOCK END -- Do not modify anything outside this block or this comment line.
}
