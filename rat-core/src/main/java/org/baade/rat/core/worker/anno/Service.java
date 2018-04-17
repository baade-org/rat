package org.baade.rat.core.worker.anno;

import org.baade.rat.core.worker.IWorker;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Service {

    Class<? extends IWorker> bind();
}
