package com.hectorlopezfernandez.pebbleshiro;

import java.io.IOException;
import java.io.Writer;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.mitchellbosecke.pebble.error.PebbleException;
import com.mitchellbosecke.pebble.extension.NodeVisitor;
import com.mitchellbosecke.pebble.node.AbstractRenderableNode;
import com.mitchellbosecke.pebble.node.BodyNode;
import com.mitchellbosecke.pebble.template.EvaluationContext;
import com.mitchellbosecke.pebble.template.PebbleTemplateImpl;

public class AuthenticatedNode extends AbstractRenderableNode {

    private final BodyNode body;

    public AuthenticatedNode(int lineNumber, BodyNode body) {
        super(lineNumber);
        this.body = body;
    }

    @Override
    public void render(PebbleTemplateImpl self, Writer writer, EvaluationContext context) throws PebbleException, IOException {
    	// check if authenticated
    	Subject subject = SecurityUtils.getSubject();
    	boolean authenticated = subject != null && subject.isAuthenticated();
    	// render node if authenticated
    	if (authenticated) body.render(self, writer, context);
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }

}