public class CMINUSStringUtil
{
    public static String firstCharToUpperCase(String str)
    {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static Integer toInteger(String str)
    {
        if (str.length() > 2 && str.charAt(0) == '0' && (str.charAt(1) == 'x' || str.charAt(1) == 'X'))
        {
            return Integer.parseInt(str.substring(2), 16);
        }
        else if (str.length() > 1 && str.charAt(0) == '0')
        {
            return Integer.parseInt(str.substring(1), 8);
        }
        return Integer.parseInt(str);
    }

    public static Float toFloat(String str)
    {
        return Float.parseFloat(str);
    }

    public static String repeat(String str,int times)
    {
        String result = "";
        for (int i = 0; i < times; i++)
        {
            result = result.concat(str);
        }
        return result;
    }
}
