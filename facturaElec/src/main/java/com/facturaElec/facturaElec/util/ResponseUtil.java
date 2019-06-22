package com.facturaElec.facturaElec.util;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author FabianCamayo
 */
public class ResponseUtil {
    //Metodos usados para respuestas JSON 

    public static Map<String, Object> mapOK(Object obj) {
        Map<String, Object> modelMap = new HashMap<>(3);
        modelMap.put("success", true);
        modelMap.put("total", obj != null ? 1 : 0);
        modelMap.put("data", obj);
        return modelMap;
    }

    public static Map<String, Object> mapOk(String msg) {
        Map<String, Object> modelMap = new HashMap(2);
        modelMap.put("success", true);
        modelMap.put("message", msg);
        return modelMap;
    }

    public static Map<String, Object> mapError(String msg) {
        Map<String, Object> modelMap = new HashMap(2);
        modelMap.put("success", false);
        modelMap.put("message", msg);
        return modelMap;
    }
}
