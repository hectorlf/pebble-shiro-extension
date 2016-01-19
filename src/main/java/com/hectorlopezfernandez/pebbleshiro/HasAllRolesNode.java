package com.hectorlopezfernandez.pebbleshiro;

import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;

import org.apache.shiro.subject.Subject;

import com.mitchellbosecke.pebble.error.PebbleException;
import com.mitchellbosecke.pebble.extension.NodeVisitor;
import com.mitchellbosecke.pebble.node.AbstractRenderableNode;
import com.mitchellbosecke.pebble.node.BodyNode;
import com.mitchellbosecke.pebble.node.expression.Expression;
import com.mitchellbosecke.pebble.template.EvaluationContext;
import com.mitchellbosecke.pebble.template.PebbleTemplateImpl;

public class HasAllRolesNode extends AbstractRenderableNode {

	private final Expression<?> value;
    private final BodyNode body;

    public HasAllRolesNode(int lineNumber, Expression<?> value, BodyNode body) {
        super(lineNumber);
        this.value = value;
        this.body = body;
    }

    @Override
    public void render(PebbleTemplateImpl self, Writer writer, EvaluationContext context) throws PebbleException, IOException {
    	// check subject and shortcircuit if not logged in
    	Subject subject = ResolveUtils.resolveSubject();
    	if (subject == null) return;

    	// evaluate role names
    	Object evaluatedRoles = value.evaluate(self, context);
    	String[] roles = ResolveUtils.resolveRoles(evaluatedRoles);
    	// check if any of the roles match
    	boolean hasAllRoles = subject.hasAllRoles(Arrays.asList(roles));
    	// render node if it has all roles
    	if (hasAllRoles) body.render(self, writer, context);
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }

}