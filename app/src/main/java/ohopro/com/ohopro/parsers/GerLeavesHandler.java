package ohopro.com.ohopro.parsers;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import ohopro.com.ohopro.domains.ErrorDomain;
import ohopro.com.ohopro.domains.LeaveResponse;
import ohopro.com.ohopro.utility.AppConstant;

/**
 * Created by sai on 30-12-2016.
 */
public class GerLeavesHandler extends BaseHandler {
    ArrayList<LeaveResponse> leaveResponses = new ArrayList<>();
    String errorMessage = AppConstant.NO_RESPONSE;
    Gson gson = new Gson();
    ErrorDomain errorDomain;

    public GerLeavesHandler(String response) {
        getDataFromResponse(response);
    }

    private void getDataFromResponse(String response) {
        try {
            JSONArray jsonArray = new JSONArray(response);
            errorMessage = AppConstant.OK_RESPONSE;
            leaveResponses = gson.fromJson(new JsonParser().parse(response).getAsJsonArray(), new TypeToken<ArrayList<LeaveResponse>>() {
            }.getType());
        } catch (JSONException e) {
            errorDomain = gson.fromJson(response, ErrorDomain.class);
            errorMessage = AppConstant.ERROR;
            e.printStackTrace();
        }
    }

    @Override
    public Object getData() {
        if (errorMessage.equalsIgnoreCase(AppConstant.OK_RESPONSE))
            return leaveResponses;
        else
            return errorDomain;
    }

    @Override
    public String getErrorData() {
        return AppConstant.OK_RESPONSE;
    }
}
