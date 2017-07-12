package ohopro.com.ohopro.parsers;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import ohopro.com.ohopro.domains.DashBoardStatesDomain;
import ohopro.com.ohopro.domains.ErrorDomain;
import ohopro.com.ohopro.utility.AppConstant;

/**
 * Created by sai on 01-02-2017.
 */
public class DashBoardStateHandler extends BaseHandler {
    DashBoardStatesDomain dashBoardStatesDomain = new DashBoardStatesDomain();
    String errorMessage = AppConstant.NO_RESPONSE;
    Gson gson = new Gson();
    private ErrorDomain errorDomain;

    public DashBoardStateHandler(String response) {

        getDataFromResponse(response);
    }

    private void getDataFromResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.has("error")) {
                errorDomain = gson.fromJson(response, ErrorDomain.class);
                errorMessage = AppConstant.ERROR;
            } else {
                dashBoardStatesDomain = gson.fromJson(response, DashBoardStatesDomain.class);
                errorMessage = AppConstant.OK_RESPONSE;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Object getData() {
        if (errorMessage.equalsIgnoreCase(AppConstant.OK_RESPONSE))
            return dashBoardStatesDomain;
        else
            return errorDomain;
    }

    @Override
    public String getErrorData() {
        return AppConstant.OK_RESPONSE;
    }
}
