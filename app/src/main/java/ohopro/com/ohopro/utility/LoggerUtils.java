package ohopro.com.ohopro.utility;

import android.util.Log;

/**
 * Created by Sai on 9/14/2015.
 */
public class LoggerUtils
{
    public static boolean isLogEnabled         = true;
    public static final String SERVICE_LOG_TAG = "TestServiceLog";
    public static final String APP_TAG         = "TestProject";

    public static void error(String tag, String msg)
    {
        if(isLogEnabled)
            Log.e(tag, msg);
    }

    public static void info(String tag, String msg)
    {
        if(isLogEnabled)
            Log.i(tag, msg);
    }

    public static void verbose(String tag, String msg)
    {
        if(isLogEnabled)
            Log.v(tag, msg);
    }

    public static void debug(String tag, String msg)
    {
        if(isLogEnabled)
            Log.d(tag, msg);
    }

    public static void printMessage(String msg)
    {
        if (isLogEnabled)
            System.out.println(msg);
    }
}
