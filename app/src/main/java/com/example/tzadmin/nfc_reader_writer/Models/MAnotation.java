package com.example.tzadmin.nfc_reader_writer.Models;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by velor on 6/21/17.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MAnotation {
    boolean PrimaryKey() default false;
    boolean Sortable() default false;
    String FieldName() default "";
    String DefaultValue() default "-1";
}
