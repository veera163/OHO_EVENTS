package ohopro.com.ohopro.parsers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import ohopro.com.ohopro.domains.ErrorDomain;
import ohopro.com.ohopro.domains.VendorReqDomain;
import ohopro.com.ohopro.utility.AppConstant;

/**
 * Created by sai on 28-01-2017.
 */
public class VendorsList extends BaseHandler {
    ArrayList<VendorReqDomain> vendorReqDomains = new ArrayList<>();
    String errorMessage = AppConstant.NO_RESPONSE;
    Gson gson = new Gson();
    private ErrorDomain errorDomain;

    public VendorsList(String response) {
        getDataFromResponse(response);
    }

    private void getDataFromResponse(String response) {
        try {
            JSONArray jsonArray = new JSONArray(response);
            vendorReqDomains = gson.fromJson(/*(new JsonParser().parse(response)).getAsJsonArray()*/response, new TypeToken<ArrayList<VendorReqDomain>>() {
            }.getType());
            errorMessage = AppConstant.OK_RESPONSE;
        } catch (JSONException e) {
            e.printStackTrace();
            errorDomain = gson.fromJson(response, ErrorDomain.class);
            errorMessage = AppConstant.ERROR;
            e.printStackTrace();
        }

    }

    @Override
    public Object getData() {
        if (errorMessage.equalsIgnoreCase(AppConstant.OK_RESPONSE))
            return vendorReqDomains;
        else
            return errorDomain;
    }

    @Override
    public String getErrorData() {
        return errorMessage;
    }
}
