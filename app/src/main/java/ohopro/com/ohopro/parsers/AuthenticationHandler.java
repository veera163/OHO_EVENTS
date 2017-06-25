package ohopro.com.ohopro.parsers;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import ohopro.com.ohopro.domains.AuthenticationDomain;
import ohopro.com.ohopro.domains.ErrorDomain;
import ohopro.com.ohopro.utility.AppConstant;

/**
 * Created by sai on 15-12-2016.
 */
public class AuthenticationHandler extends BaseHandler {

    AuthenticationDomain authenticationDomain = new AuthenticationDomain();
    Gson gson = new Gson();
    ErrorDomain errorDomain;
    String errorMessage = AppConstant.NO_RESPONSE;
    Object finalResp;

    public AuthenticationHandler(String response) {

        getDataFromResponse(response);
    }

    private void getDataFromResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);

            if (jsonObject.has("access_token")) {
                authenticationDomain = gson.fromJson(response, AuthenticationDomain.class);
                errorMessage = AppConstant.OK_RESPONSE;
                finalResp = authenticationDomain;
            } else if (jsonObject.has("error")) {
                errorMessage = AppConstant.ERROR;
                errorDomain = gson.fromJson(response, ErrorDomain.class);
                finalResp = errorDomain;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object getData() {
        return finalResp;
    }

    @Override
    public String getErrorData() {
        return errorMessage;
    }
}
