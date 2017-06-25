package ohopro.com.ohopro.parsers;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import ohopro.com.ohopro.domains.ErrorDomain;
import ohopro.com.ohopro.domains.VendorAgreementDomain;
import ohopro.com.ohopro.utility.AppConstant;

/**
 * Created by sai on 10-02-2017.
 */
class GetAllAgreements extends BaseHandler {
    private VendorAgreementDomain vendorAgreementDomain = new VendorAgreementDomain();
    Gson gson = new Gson();
    String errorMessage = AppConstant.NO_RESPONSE;
    private ErrorDomain errorDomain;

    GetAllAgreements(String response) {
        getDataFromResponse(response);
    }

    private void getDataFromResponse(String response) {
        try {
            JSONArray jsonArray = new JSONArray(response);
            vendorAgreementDomain = gson.fromJson(response, VendorAgreementDomain.class);
            errorMessage = AppConstant.OK_RESPONSE;
        } catch (JSONException e) {
            errorDomain = gson.fromJson(response, ErrorDomain.class);
            errorMessage = AppConstant.ERROR;
            e.printStackTrace();
            e.printStackTrace();
        }

    }

    @Override
    public Object getData() {
        if (errorMessage.equalsIgnoreCase(AppConstant.OK_RESPONSE))
            return vendorAgreementDomain;
        else
            return errorDomain;
    }

    @Override
    public String getErrorData() {
        return errorMessage;
    }
}
