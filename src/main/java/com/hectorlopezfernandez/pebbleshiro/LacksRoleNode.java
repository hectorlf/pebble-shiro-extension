package com.hectorlopezfernandez.pebbleshiro;

import java.io.IOException;
import java.io.Writer;

import org.apache.shiro.subject.Subject;

import com.mitchellbosecke.pebble.error.PebbleException;
import com.mitchellbosecke.pebble.extension.NodeVisitor;
import com.mitchellbosecke.pebble.node.AbstractRenderableNode;
import com.mitchellbosecke.pebble.node.BodyNode;
import com.mitchellbosecke.pebble.node.expression.Expression;
import com.mitchellbosecke.pebble.template.EvaluationContext;
import com.mitchellbosecke.pebble.template.PebbleTemplateImpl;

public class LacksRoleNode extends AbstractRenderableNode {

	private final Expression<?> value;
    private final BodyNode body;

    public LacksRoleNode(int lineNumber, Expression<?> value, BodyNode body) {
        super(lineNumber);
        this.value = value;
        this.body = body;
    }

    @Override
    public void render(PebbleTemplateImpl self, Writer writer, EvaluationContext context) throws PebbleException, IOException {
    	// evaluate role
    	Subject subject = ResolveUtils.resolveSubject();
    	boolean hasRole = false;
    	if (subject != null) {
    		Object evaluatedRole = value.evaluate(self, context);
    		hasRole = subject.hasRole(ResolveUtils.resolveRole(evaluatedRole));
    	}
    	// render node if it lacks role
    	if (!hasRole) body.render(self, writer, context);
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }

}