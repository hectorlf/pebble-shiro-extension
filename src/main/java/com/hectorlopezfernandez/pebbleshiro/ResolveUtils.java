package com.hectorlopezfernandez.pebbleshiro;

import org.apache.shiro.subject.Subject;

final class ResolveUtils {

	private ResolveUtils() {
		// not instantiable
	}

	public static Object resolvePrincipal(Subject subject, Object type) {
    	assert subject != null;
		if (type == null) {
			return subject.getPrincipal();
		} else if (type instanceof Class) {
			return subject.getPrincipals().byType((Class<?>)type);
		} else if (type instanceof String) {
			try {
				Class<?> c = Class.forName((String)type);
				return subject.getPrincipals().byType(c);
			} catch (ClassNotFoundException cnfe) {
				return null;
			}
		} else {
		 	throw new IllegalArgumentException("ShiroExtension only supports String and Class types of principal. Actual argument was: " + type.getClass().getName());
		}
    }

}