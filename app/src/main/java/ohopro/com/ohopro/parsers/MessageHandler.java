package ohopro.com.ohopro.parsers;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import ohopro.com.ohopro.domains.ErrorDomain;
import ohopro.com.ohopro.utility.AppConstant;

/**
 * Created by sai on 04-07-2017.
 */

class MessageHandler extends BaseHandler {
    private String errorMessage;
    private ErrorDomain errorDomain;
    private Gson gson = new Gson();
    private String message;

   /* {
        "messageType":"success",
         "message":"Vendor Details Saved Successfully",
          "objectId":null
    }*/

    public MessageHandler(String response) {
        getDataFromResponse(response);
    }

    private void getDataFromResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.has("messageType")) {
                message = jsonObject.getString("message");
                errorMessage = AppConstant.OK_RESPONSE;
            } else if (jsonObject.has("error")) {
                errorDomain = gson.fromJson(response, ErrorDomain.class);
                errorMessage = AppConstant.ERROR;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object getData() {
        if (errorMessage.equalsIgnoreCase(AppConstant.OK_RESPONSE))
            return message;
        else
            return errorDomain;    }

    @Override
    public String getErrorData() {
        return errorMessage;
    }
}
