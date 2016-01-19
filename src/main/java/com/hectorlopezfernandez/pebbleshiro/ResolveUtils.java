package com.hectorlopezfernandez.pebbleshiro;

import java.lang.reflect.Array;
import java.util.Collection;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.subject.Subject;

final class ResolveUtils {

	private ResolveUtils() {
		// not instantiable
	}

	public static Subject resolveSubject() {
		return SecurityUtils.getSubject();
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

	public static String[] resolveRoles(Object rolesParam) {
		String[] roles = null;
    	if (rolesParam instanceof String) {
    		roles = ((String) rolesParam).split(",");
    	} else if (rolesParam != null && rolesParam.getClass().isArray()) {
    		if (rolesParam.getClass().getComponentType() == String.class) {
    			roles = (String[]) rolesParam;
    		} else {
    			// array of class different than String?? you twisted fucker
    			int evaluatedRolesLength = Array.getLength(rolesParam);
    			roles = new String[evaluatedRolesLength];
    			for (int i = 0; i < evaluatedRolesLength; i++) {
    				Object role = Array.get(rolesParam, i);
    				roles[i] = role != null ? role.toString() : "";
    			}
    		}
    	} else if (rolesParam instanceof Collection<?>) {
    		Collection<?> collection = (Collection<?>) rolesParam;
			roles = new String[collection.size()];
			int i = 0;
			for (Object element : collection) {
				roles[i] = element != null ? element.toString() : "";
				i++;
			}
    	} else {
    		throw new IllegalArgumentException("ShiroExtension only supports String, Array and Collection types of roles. Actual argument was: " + (rolesParam == null ? "null" : rolesParam.getClass().getName()));
    	}
    	return roles;
	}

	public static String resolveRole(Object roleParam) {
    	if (roleParam == null) throw new IllegalArgumentException("Role argument can't be null for a hasRole block");
    	return roleParam.toString();
	}

	public static boolean resolvePermitted(Subject subject, Object permission) {
		boolean permitted = false;
    	if (permission instanceof String) {
    		permitted = subject.isPermitted((String) permission);
    	} else if (permission instanceof Permission) {
    		permitted = subject.isPermitted((Permission) permission);
    	} else {
    		throw new IllegalArgumentException("ShiroExtension only supports String and Permission types of permission. Actual argument was: " + (permission == null ? "null" : permission.getClass().getName()));
    	}
    	return permitted;
	}

}