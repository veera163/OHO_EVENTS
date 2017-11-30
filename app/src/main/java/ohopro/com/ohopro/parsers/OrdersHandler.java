package ohopro.com.ohopro.parsers;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import ohopro.com.ohopro.domains.ErrorDomain;
import ohopro.com.ohopro.domains.OrderDomain;
import ohopro.com.ohopro.utility.AppConstant;

/**
 * Created by sai on 21-11-2017.
 */

class OrdersHandler extends BaseHandler {
    ArrayList<OrderDomain> orderDomains;
    Gson gson = new Gson();
    ErrorDomain errorDomain;

    public OrdersHandler(String response) {
        getDataFromResponse(response);
    }

    private void getDataFromResponse(String response) {

        if (response.contains("error_description"))
            errorDomain = gson.fromJson(response, ErrorDomain.class);
        else {
            if (!response.equalsIgnoreCase("[]")) {
                orderDomains = gson.fromJson(new JsonParser().parse(response), new TypeToken<ArrayList<OrderDomain>>() {
                }.getType());
            } else {
                orderDomains = new ArrayList<>();
            }
        }
    }

    @Override
    public Object getData() {
        if (errorDomain == null)
            return orderDomains;
        else
            return errorDomain;
    }

    @Override
    public String getErrorData() {
        return AppConstant.OK_RESPONSE;
    }
}
