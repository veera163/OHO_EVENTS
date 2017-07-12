package ohopro.com.ohopro.parsers;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import ohopro.com.ohopro.domains.ErrorDomain;
import ohopro.com.ohopro.domains.UserDomain;
import ohopro.com.ohopro.utility.AppConstant;

/**
 * Created by sai on 15-12-2016.
 */
public class UserDetHandler extends BaseHandler {
    UserDomain userDetDomain;
    String errorMessage = AppConstant.NO_RESPONSE;
    Gson gson = new Gson();
    ErrorDomain errorDomain;

    public UserDetHandler(String response) {

        getDataFromResponse(response);
    }

    private void getDataFromResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.has("type")) {
                errorMessage = AppConstant.OK_RESPONSE;
                userDetDomain = gson.fromJson(response, UserDomain.class);
            } else {
                errorMessage = AppConstant.ERROR;
                errorDomain = gson.fromJson(response, ErrorDomain.class);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object getData() {
        if (errorMessage.equalsIgnoreCase(AppConstant.OK_RESPONSE))
            return userDetDomain;
        else
            return errorDomain;
    }

    @Override
    public String getErrorData() {
        return AppConstant.OK_RESPONSE;
    }
}
