package ohopro.com.ohopro.parsers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import ohopro.com.ohopro.domains.CountryCodeDomain;
import ohopro.com.ohopro.domains.ErrorDomain;
import ohopro.com.ohopro.utility.AppConstant;

/**
 * Created by sai on 08-07-2017.
 */


class NamesGetterHandler extends BaseHandler {
    ArrayList<CountryCodeDomain> codeDomains = new ArrayList<>();
    ErrorDomain errorDomain;
    Gson gson = new Gson();

    public NamesGetterHandler(String response) {
        getDataFromResponse(response);
    }

    private void getDataFromResponse(String response) {
        try {
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                codeDomains = gson.fromJson(response, new TypeToken<ArrayList<CountryCodeDomain>>() {
                }.getType());
            }
        } catch (JSONException e) {
            e.printStackTrace();
            errorDomain = gson.fromJson(response, ErrorDomain.class);
        }
    }

    @Override
    public Object getData() {
        if (errorDomain == null)
            return codeDomains;
        else
            return errorDomain;
    }

    @Override
    public String getErrorData() {
        return AppConstant.OK_RESPONSE;
    }
}
