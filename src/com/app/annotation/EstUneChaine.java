package com.app.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
// Annotation accessible à l'execution

@Target(ElementType.FIELD)
public @interface EstUneChaine {

	String mess();
}
