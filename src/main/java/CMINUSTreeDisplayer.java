import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.runtime.tree.Tree;

import java.util.Arrays;
import java.util.List;

public class CMINUSTreeDisplayer
{
    public static String getSyntaxTree(Parser parser, ParseTree root)
    {
        StringBuilder buffer = new StringBuilder();
        buildSyntaxTree(root, buffer, 0, Arrays.asList(parser.getRuleNames()));
        return buffer.toString();
    }

    private static void buildSyntaxTree(ParseTree root, StringBuilder buffer, int offset, List<String> ruleNames)
    {
        if (root instanceof ParserRuleContext)
        {
            ParserRuleContext context = (ParserRuleContext) root;
            if (context.children != null && context.children.size() > 0 && !"<EOF>".equals(context.children.get(0).getText())) {
                String text = getNodeText(context, ruleNames);
                Integer line = getLineNumber(context);
                buffer  .append(CMINUSStringUtil.repeat(" ",offset))
                        .append(text)
                        .append("(")
                        .append(line)
                        .append(")")
                        .append("\n");
                for (ParseTree child : context.children)
                {
                    buildSyntaxTree(child, buffer, offset + 2, ruleNames);
                }
            }
        }
        else
        {
            String text = getNodeText(root, ruleNames);
            buffer  .append(CMINUSStringUtil.repeat(" ",offset))
                    .append(text)
                    .append("\n");
        }
    }

    private static String getNodeText(Tree node, List<String> ruleNames)
    {
        if (node instanceof RuleContext)
        {
            int ruleIndex = ((RuleContext)node).getRuleContext().getRuleIndex();
            String ruleName = ruleNames.get(ruleIndex);
            return CMINUSStringUtil.firstCharToUpperCase(ruleName);
        }
        else if (node instanceof TerminalNode)
        {
            Token token = ((TerminalNode)node).getSymbol();
            if (token != null)
            {
                int tokenType= token.getType();
                switch (tokenType)
                {
                    case CMINUSLexer.ID:    return "ID: " + token.getText();
                    case CMINUSLexer.TYPE:  return "TYPE: " + token.getText();
                    case CMINUSLexer.INT:   return "INT: " + CMINUSStringUtil.toInteger(token.getText());
                    case CMINUSLexer.FLOAT: return "FLOAT: " + CMINUSStringUtil.toFloat(token.getText());
                    default: return CMINUSLexer.VOCABULARY.getSymbolicName(tokenType);
                }
            }
        }
        else if (node instanceof ErrorNode)
        {
            return node.toString();
        }
        return null;
    }

    private static Integer getLineNumber(ParseTree root)
    {
        if (root instanceof TerminalNode)
        {
            TerminalNode node = (TerminalNode)root;
            return node.getSymbol().getLine();
        }
        else
        {
            ParserRuleContext context = (ParserRuleContext) root;
            return getLineNumber(context.getChild(0));
        }
    }
}