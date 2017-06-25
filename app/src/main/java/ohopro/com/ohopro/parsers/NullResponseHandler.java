package ohopro.com.ohopro.parsers;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import ohopro.com.ohopro.domains.ErrorDomain;
import ohopro.com.ohopro.utility.AppConstant;

/**
 * Created by sai on 13-12-2016.
 */
public class NullResponseHandler extends BaseHandler {
    Integer statusCode = 0;
    String errorMessage = AppConstant.NO_RESPONSE;
    Gson gson = new Gson();
    ErrorDomain errorDomain = new ErrorDomain();

    public NullResponseHandler(String response) {

        getDataFromResponse(response);
    }

    private void getDataFromResponse(String response) {

        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.has("statusCode")) {
                statusCode = jsonObject.getInt("statusCode");
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
            return statusCode;
        else
            return errorDomain;
    }

    @Override
    public String getErrorData() {
        return errorMessage;
    }
}
