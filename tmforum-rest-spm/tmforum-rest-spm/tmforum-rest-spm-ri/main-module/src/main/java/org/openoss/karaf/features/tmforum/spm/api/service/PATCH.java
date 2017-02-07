package org.openoss.karaf.features.tmforum.spm.api.service;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;


import javax.ws.rs.HttpMethod;
import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@HttpMethod("PATCH")
public @interface PATCH { }
