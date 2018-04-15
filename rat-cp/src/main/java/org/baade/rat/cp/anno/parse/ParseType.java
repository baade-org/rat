package org.baade.rat.cp.anno.parse;

import org.baade.rat.cp.anno.Column;
import org.baade.rat.cp.anno.Entity;
import org.baade.rat.cp.anno.ID;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Optional;

public enum ParseType {

    COLUMN(Column.class, new ColumnParser()),

    ID(ID.class, new IDParser()),

    ENTITY(Entity.class, new EntityParser()),

    CLASS_MODEL(null, new ClassModelParser()),;
    private Class<? extends Annotation> annotation;

    private IParser parser;

    ParseType(Class<? extends Annotation> annotation, IParser parser) {
        this.annotation = annotation;
        this.parser = parser;
    }

    public Class<? extends Annotation> getAnnotation() {
        return annotation;
    }

    public IParser getParser() {
        return parser;
    }

    public static ParseType find(Class<? extends Annotation> annotation) {
        Optional<ParseType> optionalType = Arrays.stream(ParseType.values()).filter(c -> c.getAnnotation() == annotation).findAny();
        if (!optionalType.isPresent()) {
            return null;
        }
        return optionalType.get();
    }
}
