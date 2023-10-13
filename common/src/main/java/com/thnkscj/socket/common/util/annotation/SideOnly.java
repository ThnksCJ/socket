package com.thnkscj.socket.common.util.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that a certain class, constructor
 * or method should only be called from one side.
 * This annotation should only be used in the
 * 'common' project
 */
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.TYPE, ElementType.CONSTRUCTOR, ElementType.METHOD})
public @interface SideOnly {

    Side value();
}
