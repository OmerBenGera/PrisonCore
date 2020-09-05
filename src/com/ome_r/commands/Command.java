package com.ome_r.commands;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Command {

	String name();
	String aliases();
	String args();
	String desc();
	String perm();
	int minArgs();
	int maxArgs();
	
}
