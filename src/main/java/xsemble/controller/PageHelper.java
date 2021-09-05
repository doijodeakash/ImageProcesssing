/* 
 * Copyright (C) 2020 10Xofy. All rights reserved.
 */
package xsemble.controller;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public class PageHelper {
    
    private static final Map<String,Map<String,String>> pageActionsMap =
     new HashMap<String,Map<String,String>>();
    private static final Map<String,Map<String,String>> pageOutArgMap =
     new HashMap<String,Map<String,String>>();

    static {
        Map<String,String> map;

        map = new HashMap<String,String>();
        map.put("orientation", "/x/changeorientation");
        map.put("watermark", "/x/imagewatermarking");
        map.put("sharpenimage", "/x/sharpi");
        map.put("compress", "/x/compress");
        map.put("mirrorimage", "/x/mirrorimage");
        map.put("blurimage", "/x/bluri");
        map.put("negativeimage", "/x/negativei");
        map.put("resolution", "/x/resolution");
        map.put("facedetection", "/x/facedetection");
        map.put("rotateimg", "/x/rotateimg");
        map.put("ocr", "/x/ocri");
        map.put("greyscaleimage", "/x/greyscale");
        pageActionsMap.put("#LoadImage", map);
        map = new HashMap<String,String>();
        map.put("orientation", "/x/changeorientation");
        map.put("watermark", "/x/imagewatermarking");
        map.put("sharpenimage", "/x/sharpi");
        map.put("compress", "/x/compress");
        map.put("mirrorimage", "/x/mirrorimage");
        map.put("blurimage", "/x/bluri");
        map.put("negativeimage", "/x/negativei");
        map.put("resolution", "/x/resolution");
        map.put("rotateimg", "/x/rotateimg");
        map.put("facedetection", "/x/facedetection");
        map.put("ocr", "/x/ocri");
        map.put("greyscaleimage", "/x/greyscale");
        pageActionsMap.put("#OutputPage", map);



    }
    
    public static String getPageActionUrl(String pageid, String ret, HttpServletRequest request) {
    	Map<String,String> map = pageActionsMap.get(pageid);
        return map == null ? null : request.getContextPath() + map.get(ret);
    }

    public static String getVarname4OutArg(String pageid, String arg) {
    	Map<String,String> map = pageOutArgMap.get(pageid);
        return map == null? null : map.get(arg);
    }
}
