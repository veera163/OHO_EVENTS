package ohopro.com.ohopro.parsers;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

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
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.has("error_description")) {
                errorDomain = gson.fromJson(response, ErrorDomain.class);
                errorMessage = AppConstant.ERROR;
            } else {
                vendorAgreementDomain = gson.fromJson(response, VendorAgreementDomain.class);
                errorMessage = AppConstant.OK_RESPONSE;
            }

        } catch (JSONException e) {

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
