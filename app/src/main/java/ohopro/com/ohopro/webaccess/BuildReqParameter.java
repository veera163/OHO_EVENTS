package ohopro.com.ohopro.webaccess;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Sai on 9/16/2015.
 */
public class BuildReqParameter {

    static Gson gson = new Gson();

    public static String getmethodrequest(String get) {
        return get;
    }


    public static String getParamsToPostBill(String date, String amount, String billNumber, String userMobile, String userEmail, String image, String purpose, String provider) {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("phone", userMobile);
            jsonObject.put("emailId", userEmail);
            jsonObject.put("billNumber", billNumber);
            jsonObject.put("billAmount", amount);
            jsonObject.put("billDate", date);
            jsonObject.put("billProvider", provider);
            jsonObject.put("purpose; ", purpose);
            jsonObject.put("billImage", image);
            jsonObject.put("billImageName", "jpg");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    public static String ParamsToGetBills(String typeofBills, String userMobile) {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("status", typeofBills.toUpperCase());
            jsonObject.put("phone", userMobile);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject.toString();
    }

    public static String ParamsPostLeave(String startDate, String enddate, String emergencyContact, String numOfDays, String reason, String userEmail, String userMobile, String userName) {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("employeeName", userName);
            jsonObject.put("phoneNumber", userMobile);
            jsonObject.put("emailId", userEmail);
            jsonObject.put("startDate", startDate);
            jsonObject.put("endDate", enddate);
            jsonObject.put("reason", reason);
            jsonObject.put("emergencyContact", emergencyContact);
            jsonObject.put("numberOfDays", numOfDays);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    public static String getParamsUpdateLeave(String leaveRequestId, String comment, String status) {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("leaveRequestId", leaveRequestId);
            jsonObject.put("comments", comment);
            jsonObject.put("status", status);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject.toString();
    }

    public static String getParamsForAddMoney(String amount, String employeeContact) {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("amountTobeUpdated", amount);
            jsonObject.put("phone", employeeContact);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject.toString();
    }

    public static String paramsForMoneyReq(String amount, String userMobile, String userName) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("requestorPhone", amount);
            jsonObject.put("requestedAmount", userMobile);
            jsonObject.put("requestedBy", userName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }


    public static String creatupdateBillParams(String comment, String status, String billId) {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("comment", comment);
            jsonObject.put("status", status);
            jsonObject.put("billId", billId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject.toString();
    }

    public static String paramsForAssignForm(String enquiryFormId, String employeeName) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("enquiryFormId", enquiryFormId);
            jsonObject.put("employeeName", employeeName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    public static String getParamsUploadDoc(String stardate, String endDate, String desc, String userId, String userName, String imgeurl) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("agreementStartDate", stardate);
            jsonObject.put("agreementExpirationDate", endDate);
            jsonObject.put("agreementDocUrl", stardate);
            jsonObject.put("description", desc);
            jsonObject.put("vendorId", userId);
            jsonObject.put("vendorName", userName);
            jsonObject.put("agreementDocUrl", imgeurl);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    public static String paramsPostServices(String documentName, String documentType, String issuedBy, String description, String vendorId, ArrayList<String> urls) {
        JSONObject jsonObject = new JSONObject();
        JSONArray documents = new JSONArray();

        try {
            jsonObject.put("vendorDocName", documentName);
            jsonObject.put("type", documentType);
            jsonObject.put("issuedBy", issuedBy);
            jsonObject.put("vendorId", vendorId);
            jsonObject.put("description", description);
            for (int i = 0; i < urls.size(); i++) {
                JSONObject document = new JSONObject();
                document.put("url", urls.get(i));
                document.put("docName", "");
                documents.put(document);
            }
            jsonObject.put("documents", documents);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    public static String getParamsGetServices(String serviceGroups, String serviceTypes) {

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("productTypeGroup", serviceGroups);
            jsonObject.put("productOrServiceCategory", serviceTypes);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject.toString();
    }
}
