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

public class LacksPermissionNode extends AbstractRenderableNode {

	private final Expression<?> value;
    private final BodyNode body;

    public LacksPermissionNode(int lineNumber, Expression<?> value, BodyNode body) {
        super(lineNumber);
        this.value = value;
        this.body = body;
    }

    @Override
    public void render(PebbleTemplateImpl self, Writer writer, EvaluationContext context) throws PebbleException, IOException {
    	// evaluate permission
    	Subject subject = ResolveUtils.resolveSubject();
    	boolean permitted = false;
    	if (subject != null) {
    		Object evaluatedPermission = value.evaluate(self, context);
    		permitted = ResolveUtils.resolvePermitted(subject, evaluatedPermission);
    	}
    	// render node if it is not permitted
    	if (!permitted) body.render(self, writer, context);
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }

}