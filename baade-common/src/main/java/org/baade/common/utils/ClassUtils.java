package org.baade.common.utils;

/**
 * The type Class utils.
 */
public class ClassUtils {


    /**
     * Is inner class boolean.
     *
     * @param name the name
     * @return the boolean
     */
    public static boolean isInnerClass(String name) {
        return name.contains("$");
    }

    /**
     * Is class File boolean.
     *
     * @param name the name
     * @return the boolean
     */
    public static boolean isClass(String name) {
        return name.endsWith(".class");
    }

    /**
     * Is jar File boolean.
     *
     * @param name the name
     * @return the boolean
     */
    public static boolean isJar(String name) {
        return name.endsWith(".jar");
    }

}
