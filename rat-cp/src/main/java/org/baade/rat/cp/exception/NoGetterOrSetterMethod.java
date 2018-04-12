package org.baade.rat.cp.exception;

public class NoGetterOrSetterMethod extends RuntimeException{

    public NoGetterOrSetterMethod(Class<?> clazz, String fieldName, Throwable cause) {
        super("@Entity Class:[" + clazz + "] field:[" + fieldName + "] not found getter or setter method.", cause);
    }

    public NoGetterOrSetterMethod(Class<?> clazz, String fieldName) {
        super("@Entity Class:[" + clazz + "] field:[" + fieldName + "] not found getter or setter method.");
    }
}
