package ohopro.com.ohopro.parsers;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import ohopro.com.ohopro.domains.ErrorDomain;
import ohopro.com.ohopro.domains.WalletDomain;
import ohopro.com.ohopro.utility.AppConstant;

/**
 * Created by sai on 03-01-2017.
 */
public class GetWalletBalanceHandler extends BaseHandler {

    String errorMessage = AppConstant.NO_RESPONSE;
    WalletDomain walletDomain = new WalletDomain();
    Gson gson = new Gson();
    ErrorDomain errorDomain;

    public GetWalletBalanceHandler(String response) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (jsonObject.has("error")) {
            errorDomain = gson.fromJson(response, ErrorDomain.class);
            errorMessage = AppConstant.ERROR;
        } else {
            walletDomain = gson.fromJson(response, WalletDomain.class);
            errorMessage = AppConstant.OK_RESPONSE;
        }

    }

    @Override
    public Object getData() {
        if (errorMessage.equalsIgnoreCase(AppConstant.OK_RESPONSE))
            return walletDomain;
        else
            return errorDomain;
    }

    @Override
    public String getErrorData() {
        return AppConstant.OK_RESPONSE;
    }
}
