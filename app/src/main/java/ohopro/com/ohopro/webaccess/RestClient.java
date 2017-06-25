package ohopro.com.ohopro.webaccess;


import ohopro.com.ohopro.appserviceurl.ServiceURL;
import ohopro.com.ohopro.utility.AppConstant;
import ohopro.com.ohopro.utility.LoggerUtils;

/**
 * Created by Sai on 9/16/2015.
 */
public class RestClient {

    public String sendRequest(String servicemethod, String REQURL, String parameters) {
        LoggerUtils.info(RestClient.class.getSimpleName(), "on sendrequest");

        int reqtype = ServiceURL.getRequestTypemethod(servicemethod);

        if (reqtype == AppConstant.REQ_POST) {
            return new HttpHelper().sendPOSTRequest(REQURL, parameters);
        } else if (reqtype == AppConstant.REQ_GET) {
            return new HttpHelper().sendGETRequest(REQURL);
        } else if (reqtype == AppConstant.REQ_PUT) {
            return new HttpHelper().sendPUTRequest(REQURL, parameters);
        } else {
            return AppConstant.NO_RESPONSE;
        }
    }
}
