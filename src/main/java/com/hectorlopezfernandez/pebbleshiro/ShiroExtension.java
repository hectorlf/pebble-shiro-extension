package com.hectorlopezfernandez.pebbleshiro;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mitchellbosecke.pebble.extension.AbstractExtension;
import com.mitchellbosecke.pebble.extension.Filter;
import com.mitchellbosecke.pebble.extension.Function;
import com.mitchellbosecke.pebble.tokenParser.TokenParser;

public class ShiroExtension extends AbstractExtension {
	
    public ShiroExtension() {
    }

    @Override
    public Map<String, Filter> getFilters() {
        return Collections.emptyMap();
    }
    
    @Override
    public Map<String, Function> getFunctions() {
    	Map<String, Function> functions = new HashMap<>(1);
    	functions.put("principal", new PrincipalFunction());
        return functions;
    }

    @Override
    public List<TokenParser> getTokenParsers() {
        List<TokenParser> parsers = new ArrayList<>(10);
        parsers.add(new AuthenticatedTokenParser());
        parsers.add(new GuestTokenParser());
        parsers.add(new HasAllRolesTokenParser());
        parsers.add(new HasAnyRolesTokenParser());
        parsers.add(new HasPermissionTokenParser());
        parsers.add(new HasRoleTokenParser());
        parsers.add(new LacksPermissionTokenParser());
        parsers.add(new LacksRoleTokenParser());
        parsers.add(new NotAuthenticatedTokenParser());
        parsers.add(new UserTokenParser());
        return parsers;
    }

}