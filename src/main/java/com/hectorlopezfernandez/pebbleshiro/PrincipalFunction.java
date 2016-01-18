package com.hectorlopezfernandez.pebbleshiro;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.mitchellbosecke.pebble.extension.Function;

public class PrincipalFunction implements Function {

    private final List<String> argumentNames = new ArrayList<>();

    public PrincipalFunction() {
        argumentNames.add("type");
        argumentNames.add("defaultValue");
    }

    @Override
    public List<String> getArgumentNames() {
        return argumentNames;
    }

    @Override
    public Object execute(Map<String, Object> args) {
    	// find subject, if any
    	Subject subject = SecurityUtils.getSubject();
    	Object defaultValue = args.get("defaultValue");
    	if (subject == null) {
    		if (defaultValue != null) return defaultValue;
    		else return null;
    	}

        // resolve principal, if any
		Object principal = ResolveUtils.resolvePrincipal(subject, args.get("type"));
		if (principal == null) {
    		if (defaultValue != null) return defaultValue;
    		else return null;
		}
		
		return principal;
    }

}