public class CMINUSListenerExt extends CMINUSBaseListener
{
    @Override
    public void enterProgram(CMINUSParser.ProgramContext ctx)
    {
        super.enterProgram(ctx);
        System.out.println(ctx.getText());
    }
}
