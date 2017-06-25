package ohopro.com.ohopro.parsers;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import ohopro.com.ohopro.domains.ErrorDomain;
import ohopro.com.ohopro.domains.WalletRespDOmain;
import ohopro.com.ohopro.utility.AppConstant;

/**
 * Created by sai on 04-01-2017.
 */
public class MoneyReqsHandler extends BaseHandler {
    private  ErrorDomain errorDomain;
    ArrayList<WalletRespDOmain> walletRespDOmains = new ArrayList<>();
    String errorMessage = AppConstant.NO_RESPONSE;
    Gson gson = new Gson();

    public MoneyReqsHandler(String response) {
        try {
            JSONArray jsonArray = new JSONArray(response);
            walletRespDOmains = gson.fromJson((new JsonParser().parse(response)).getAsJsonArray(), new TypeToken<ArrayList<WalletRespDOmain>>() {
            }.getType());
            errorMessage = AppConstant.OK_RESPONSE;
        } catch (JSONException e) {
            errorDomain = gson.fromJson(response, ErrorDomain.class);
            errorMessage = AppConstant.ERROR;

            e.printStackTrace();
        }

    }

    @Override
    public Object getData() {
        if (errorMessage.equalsIgnoreCase(AppConstant.OK_RESPONSE))
            return walletRespDOmains;
        else
            return errorDomain;
    }


    @Override
    public String getErrorData() {
        return errorMessage;
    }
}
