package com.hectorlopezfernandez.pebbleshiro;

import com.mitchellbosecke.pebble.error.ParserException;
import com.mitchellbosecke.pebble.lexer.Token;
import com.mitchellbosecke.pebble.lexer.TokenStream;
import com.mitchellbosecke.pebble.node.BodyNode;
import com.mitchellbosecke.pebble.node.RenderableNode;
import com.mitchellbosecke.pebble.parser.Parser;
import com.mitchellbosecke.pebble.parser.StoppingCondition;
import com.mitchellbosecke.pebble.tokenParser.AbstractTokenParser;

public class GuestTokenParser extends AbstractTokenParser {
	
	private static final String TOKEN_START = "isGuest";
	private static final String TOKEN_END = "endIsGuest";

    @Override
    public RenderableNode parse(Token token, Parser parser) throws ParserException {
    	TokenStream stream = parser.getStream();
        int lineNumber = token.getLineNumber();

        // skip the start token
        stream.next();
        // end of start tag
        stream.expect(Token.Type.EXECUTE_END);
        
        // parse the body
        BodyNode body = parser.subparse(new StoppingCondition() {
            @Override
            public boolean evaluate(Token token) {
                return token.test(Token.Type.NAME, TOKEN_END);
            }
        });

        // skip the end token
        stream.next();
        // end of end tag
        stream.expect(Token.Type.EXECUTE_END);

        return new GuestNode(lineNumber, body);
    }

    @Override
    public String getTag() {
        return TOKEN_START;
    }

}