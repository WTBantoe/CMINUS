import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class CMINUS
{
    static int indent = 0;

    public static void main(String[] args) throws Exception {
        if (args.length != 1)
        {
            System.err.println("Please choose ONE input file!");
        }
        else
        {
            CharStream input = CharStreams.fromFileName(args[0]);

            CMINUSLexerExt lexer = new CMINUSLexerExt(input);

            lexer.removeErrorListeners();
            lexer.addErrorListener(new CMINUSLexerErrorListener());

            CommonTokenStream tokens = new CommonTokenStream(lexer);
            CMINUSParser parser = new CMINUSParser(tokens);

            parser.removeErrorListeners();
            parser.addErrorListener(new CMINUSParserErrorListener());
            parser.setErrorHandler(new CMINUSErrorStrategy());

            ParseTree tree = parser.program();
            if (lexer.getNumberOfTokenErrors() == 0 && parser.getNumberOfSyntaxErrors() == 0)
            {
                System.out.println(CMINUSTreeDisplayer.getSyntaxTree(parser, tree));
            }
        }
    }

}