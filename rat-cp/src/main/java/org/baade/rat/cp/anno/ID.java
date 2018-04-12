package org.baade.rat.cp.anno;

import org.baade.rat.cp.ColumnType;
import org.baade.rat.cp.Key;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ID {

    String name() default "";

    int length() default -1;

    ColumnType type() default ColumnType.DEFAULT;

    Key key() default Key.PRIMARY;

    String comment() default "";

    boolean isNull() default true;

    String defaultValue() default "";

}
