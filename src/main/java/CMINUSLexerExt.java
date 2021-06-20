import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.LexerNoViableAltException;

public class CMINUSLexerExt extends CMINUSLexer
{
    protected int _tokenErrors = 0;

    public CMINUSLexerExt(CharStream input) {
        super(input);
    }

    @Override
    public void notifyListeners(LexerNoViableAltException e)
    {
        this._tokenErrors++;
        super.notifyListeners(e);
    }

    public int getNumberOfTokenErrors()
    {
        return this._tokenErrors;
    }

}