package org.baade.rat.core.utils;

public class StringUtils {

    public static boolean isBlank(String str){

        return str == null || "".equals(str.trim());
    }
}
