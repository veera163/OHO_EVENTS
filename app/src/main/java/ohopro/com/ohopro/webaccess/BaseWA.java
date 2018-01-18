package ohopro.com.ohopro.webaccess;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import ohopro.com.ohopro.R;
import ohopro.com.ohopro.parsers.BaseHandler;
import ohopro.com.ohopro.utility.AppConstant;
import ohopro.com.ohopro.utility.ConnectionDetector;
import ohopro.com.ohopro.utility.LoggerUtils;


/**
 * Created by Sai on 9/16/2015.
 */
public class BaseWA implements HttpListener {

    private WebAccessListner listener;

    private Context mContext;

    AlertDialog alertDialog;

    public BaseWA(WebAccessListner listener, Context mContext) {

        LoggerUtils.info(BaseWA.class.getSimpleName(), "on BaseWA");

        this.listener = listener;
        this.mContext = mContext;
    }

    public boolean startDataDownload(String servicemethod, String REQURL, String parameters) {
        /*try {
            REQURL = URLEncoder.encode(REQURL, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/
        REQURL = REQURL.replaceAll(" ", "%20");
        if (new ConnectionDetector(mContext).isConnectedToInternet()) {

            LoggerUtils.info(BaseWA.class.getSimpleName(), "starting download" + parameters);

            HTTPSimulator downloader = new HTTPSimulator(this, servicemethod, REQURL, parameters);

            downloader.start();

            return true;
        }
        else {
            showAlertDialog(mContext, "Internet Connetion", "Please check internet connection!!", false);

            return false;
        }
    }

    class HTTPSimulator extends Thread {
        HttpListener listener;
        String servicemethod;
        String parameters = "null";
        String REQURL = "NA";

        public HTTPSimulator(HttpListener listener, String servicemethod, String REQURL, String parameters) {
            LoggerUtils.info(BaseWA.class.getSimpleName(), "on HTTPSimulator");

            this.listener = listener;
            this.servicemethod = servicemethod;
            this.parameters = parameters;
            this.REQURL = REQURL;
        }

        @Override
        public void run() {

            Response response = new Response(servicemethod, true, "Unable to connect server, please try again.", "Unable to connect server");
            String isResponse = null;

            try {
                if (!parameters.equalsIgnoreCase("null")) {
                    isResponse = new RestClient().sendRequest(servicemethod, REQURL, parameters);
                }

                LoggerUtils.info(BaseWA.class.getSimpleName(), "DOWNLOAD COMPLETE" + isResponse);

                if (isResponse != null) {
                    if (!isResponse.equalsIgnoreCase(AppConstant.NO_RESPONSE)) {
                        BaseHandler baseHandler = BaseHandler.getDataParser(servicemethod, isResponse);

                        //LoggerUtils.error(BaseWA.class.getSimpleName(),"errormesg is"+baseHandler.getErrorData());

                        if (baseHandler != null) {

                            if (baseHandler.getErrorData().equalsIgnoreCase(AppConstant.OK_RESPONSE) && baseHandler.getData() != null) {
                                LoggerUtils.info(BaseWA.class.getSimpleName(), "ON IF OF BaseHandler");
                                response.isError = false;
                                response.data = baseHandler.getData();
                            } else if (baseHandler.getErrorData().equalsIgnoreCase(AppConstant.ERROR) && baseHandler.getData() != null) {
                                LoggerUtils.info(BaseWA.class.getSimpleName(), "ON ELSE IF OF BaseHandler" + AppConstant.FBALREADY_REGISTER);

                                response.isError = false;

                                response.data = baseHandler.getData();

                            } else if (baseHandler.getErrorData().equalsIgnoreCase(AppConstant.GPLUSALREADY_REGISTER) && baseHandler.getData() != null) {
                                LoggerUtils.info(BaseWA.class.getSimpleName(), "ON ELSE IF OF BaseHandler" + AppConstant.GPLUSALREADY_REGISTER);

                                response.isError = false;

                                response.data = baseHandler.getData();
                            } else {
                                LoggerUtils.info(BaseWA.class.getSimpleName(), "ON ELSE OF BaseHandler");
                                String errorMsg = baseHandler.getErrorData();
                                if (errorMsg != null && !errorMsg.equalsIgnoreCase("")) {
                                    response.errormessage = baseHandler.getErrorData();
                                    response.data = baseHandler.getErrorData();
                                    response.isError = true;
                                }
                            }
                        } else {
                            LoggerUtils.error(BaseWA.class.getSimpleName(), "No Data parse!!");
                        }
                    } else {
                        response.errormessage = AppConstant.NO_RESPONSE;
                        response.data = AppConstant.NO_RESPONSE;
                        response.isError = true;
                        LoggerUtils.error(BaseWA.class.getSimpleName(), AppConstant.NO_RESPONSE);
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                //LoggerUtils.error(BaseWA.class.getSimpleName(), ex.getMessage());
            } finally {
                LoggerUtils.info(BaseWA.class.getSimpleName(), "DATA DOWNLOAD");
            }

            listener.onResponseReceived(response);

        }
    }

    @Override
    public void onResponseReceived(Response response) {

        this.respondWithData(response);
    }

    /**
     * Method to respond for the data
     *
     * @param data
     */
    protected void respondWithData(Response data) {
        listener.dataDownloaded(data);
    }

    /**
     * Function to display simple Alert Dialog
     *
     * @param context - application context
     * @param title   - alert dialog title
     * @param message - alert message
     * @param status  - success/failure (used to set icon)
     *                - pass null if you don't want icon
     */
    private void showAlertDialog(Context context, String title, String message,
                                 Boolean status) {
        alertDialog = new AlertDialog.Builder(context).create();

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);

        //disable touch outside dialog.
        alertDialog.setCanceledOnTouchOutside(false);

        if (status != null)
            // Setting alert dialog icon
            alertDialog.setIcon((status) ? R.mipmap.ic_launcher : R.mipmap.ic_launcher);

        // Setting OK Button
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Setting", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

                open_device_setting_screen();


            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    private void open_device_setting_screen() {

        listener.opennetworksetting();
    }
}
