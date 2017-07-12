package ohopro.com.ohopro.parsers;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import ohopro.com.ohopro.domains.ErrorDomain;
import ohopro.com.ohopro.domains.LeaveBalanceDomain;
import ohopro.com.ohopro.utility.AppConstant;

/**
 * Created by sai on 31-12-2016.
 */
public class GetLeaveBalanceHandler extends BaseHandler {
    LeaveBalanceDomain leaveBalanceDomain = new LeaveBalanceDomain();
    String errorMessage = AppConstant.NO_RESPONSE;
    Gson gson = new Gson();
    private ErrorDomain errorDomain;

    public GetLeaveBalanceHandler(String response) {
        getDataFromResponse(response);
    }

    private void getDataFromResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);

            if (jsonObject.has("error")) {
                errorDomain = gson.fromJson(response, ErrorDomain.class);
                errorMessage = AppConstant.ERROR;
            } else {
                leaveBalanceDomain = gson.fromJson(response, LeaveBalanceDomain.class);
                errorMessage = AppConstant.OK_RESPONSE;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Object getData() {
        if (errorMessage.equalsIgnoreCase(AppConstant.OK_RESPONSE))
            return leaveBalanceDomain;
        else
            return errorDomain;
    }

    @Override
    public String getErrorData() {
        return errorMessage;
    }
}
