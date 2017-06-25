package ohopro.com.ohopro.webaccess;

/**
 * Created by Sai on 9/16/2015.
 */
public class Response
{
    public String servicemethod,errormessage;
    public boolean isError;
    public Object data;


    public Response(String servicemethod, boolean isError, Object data,String errormessage) {
        this.servicemethod = servicemethod;
        this.isError = isError;
        this.data = data;
        this.errormessage=errormessage;

    }
}
