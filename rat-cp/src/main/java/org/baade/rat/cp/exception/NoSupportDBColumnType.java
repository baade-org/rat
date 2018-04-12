package org.baade.rat.cp.exception;

public class NoSupportDBColumnType extends RuntimeException{

    public NoSupportDBColumnType(Class<?> clazz, String fieldName, Throwable cause) {
        super("@Entity Class:[" + clazz + "] field:[" + fieldName + "] not found getter or setter method.", cause);
    }

    public NoSupportDBColumnType(Class<?> clazz, String fieldName) {
        super("@Entity Class:[" + clazz + "] field:[" + fieldName + "] not found getter or setter method.");
    }
}
