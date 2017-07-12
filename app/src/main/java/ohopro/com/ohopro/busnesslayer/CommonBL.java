package ohopro.com.ohopro.busnesslayer;

import android.content.Context;

import com.google.gson.Gson;

import java.util.ArrayList;

import ohopro.com.ohopro.domains.BillDomain;
import ohopro.com.ohopro.domains.LeaveResponse;
import ohopro.com.ohopro.domains.VendorEnquaryDomain;
import ohopro.com.ohopro.domains.VendorReqDomain;
import ohopro.com.ohopro.utility.AppConstant;
import ohopro.com.ohopro.utility.PreferenceUtils;
import ohopro.com.ohopro.webaccess.BaseWA;
import ohopro.com.ohopro.webaccess.BuildReqParameter;
import ohopro.com.ohopro.webaccess.ServiceMethods;

/**
 * Created by sai on 05-03-2016.
 */
public class CommonBL extends BaseBL {
    PreferenceUtils preferenceUtils;
    private Gson gson = new Gson();

    public CommonBL(DataListener listener, Context mContext) {
        super(listener, mContext);
        preferenceUtils = new PreferenceUtils(mContext);
        if (preferenceUtils.isLoggedIn())
            AppConstant.HEADER = "Bearer " + preferenceUtils.getAccessToken();
        else
            AppConstant.HEADER = "Basic Y2xpZW50YXBwOjEyMzQ1Ng==";
    }

    public boolean submitABill(String requesturl, String date, String amount, String billNumber, String userMobile, String userEmail, String image, String purpose, String provider) {
        return new BaseWA(this, mContext).startDataDownload(ServiceMethods.WS_APP_SUBMITABILL, requesturl,
                BuildReqParameter.getParamsToPostBill(date, amount, billNumber, userMobile, userEmail, image, purpose, provider));
    }

    public boolean getBills(String requestUrl, String typeofBills, String userMobile) {

        return new BaseWA(this, mContext).startDataDownload(ServiceMethods.WS_APP_GET_BILLS,
                requestUrl,
                BuildReqParameter.ParamsToGetBills(typeofBills, userMobile));
    }

    public boolean doLogin(String url) {
        return new BaseWA(this, mContext).startDataDownload(ServiceMethods.WS_APP_AUTHENTICATION,
                url, new String());
    }

    public boolean getUserDetails(String reqUrl) {

        return new BaseWA(this, mContext).startDataDownload(ServiceMethods.WS_APP_GET_USERDET,
                reqUrl,
                BuildReqParameter.getmethodrequest("GET"));
    }

    public boolean postLeave(String requestUrl, String startDate, String emergencyContact, String enddate, String numOfDays, String reason, String userMobile, String userEmail, String userName) {
        return new BaseWA(this, mContext).startDataDownload(ServiceMethods.WS_APP_POST_LEAVE, requestUrl,
                BuildReqParameter.ParamsPostLeave(
                        startDate, enddate, emergencyContact, numOfDays, reason,
                        userEmail, userMobile, userName
                ));
    }

    public boolean getLeaves(String requestUrl) {
        return new BaseWA(this, mContext).startDataDownload(ServiceMethods.WS_APP_GET_LEAVES,
                requestUrl,
                new String());
    }

    public boolean getBills(String url) {
        return new BaseWA(this, mContext).startDataDownload(ServiceMethods.GETBILLS_MGR,
                url,
                new String());
    }

    public boolean getLeavesBalance(String reqUrl) {
        return new BaseWA(this, mContext).startDataDownload(ServiceMethods.WS_APP_GET_LEAVE_BALANCE, reqUrl,
                BuildReqParameter.getmethodrequest("GET"));
    }

    public boolean approveABill(String reqUrl, String comment, String status, String billId) {
        return new BaseWA(this, mContext).startDataDownload(ServiceMethods.WS_APP_APPROVEBILL, reqUrl, BuildReqParameter.creatupdateBillParams(comment, status, billId));
    }

    public boolean getLeavesForManger(String reqUrl) {
        return new BaseWA(this, mContext).startDataDownload(ServiceMethods.GETLEAVES_MGR,
                reqUrl,
                BuildReqParameter.getmethodrequest("GET"));
    }

    public boolean updateLeaveByManager(String requestUrl, String leaveRequestId, String comment, String status) {
        return new BaseWA(this, mContext).startDataDownload(ServiceMethods.WS_UPDATE_LEAVE_MNGR,
                requestUrl,
                BuildReqParameter.getParamsUpdateLeave(leaveRequestId, comment, status
                ));
    }

    public boolean addMoneyToWallet(String requestUrl, String amount, String employeeContact) {
        return new BaseWA(this, mContext).startDataDownload(ServiceMethods.WS_APP_ADD_MONEY,
                requestUrl,
                BuildReqParameter.getParamsForAddMoney(amount, employeeContact));
    }

    public boolean getWalletBalance(String reqUrl) {
        return new BaseWA(this, mContext).startDataDownload(ServiceMethods.WS_APP_GET_WALLET_BAL,
                reqUrl,
                BuildReqParameter.getmethodrequest("GET"));
    }

    public boolean requestMoneyToWallet(String requestUrl, String amount, String userMobile, String userName) {
        return new BaseWA(this, mContext).startDataDownload(ServiceMethods.WS_APP_ADD_MONEY_REQ,
                requestUrl,
                BuildReqParameter.paramsForMoneyReq(amount, userMobile, userName));
    }

    public boolean getMoneyReqs(String reqUrl) {
        return new BaseWA(this, mContext).startDataDownload(ServiceMethods.WS_MONEY_REQS,
                reqUrl,
                BuildReqParameter.getmethodrequest("GET"));
    }

    public boolean approveMoneyReq(String reqUrl) {
        return new BaseWA(this, mContext).startDataDownload(ServiceMethods.APPROVE_MONEY_REQ,
                reqUrl,
                new String());
    }

    public boolean updateaBill(String requestUrl, BillDomain billDomain) {
        return new BaseWA(this, mContext).startDataDownload(ServiceMethods.WS_UPDATEBILL, requestUrl, gson.toJson(billDomain));
    }

    public boolean updateLeave(String requestUrl, LeaveResponse leaveResponse) {
        return new BaseWA(this, mContext).startDataDownload(ServiceMethods.WS_UPDATELEAVE, requestUrl, gson.toJson(leaveResponse));
    }

    public boolean postVendorForm(String requestUrl, VendorEnquaryDomain vendorEnquaryDomain) {
        return new BaseWA(this, mContext).startDataDownload(ServiceMethods.WS_APP_VENDOR_FORM, requestUrl,
                BuildReqParameter.getParmsForVendorForm(vendorEnquaryDomain)
        );
    }

    public boolean getAllVendorServiceCatogories(String requestUrl) {
        return new BaseWA(this, mContext).startDataDownload(ServiceMethods.WS_LIST_SERVICECATOGIIES,
                requestUrl,
                BuildReqParameter.getmethodrequest("GET"));
    }

    public boolean getServices(String reqUrl, String serviceGroups, String serviceTypes) {
        return new BaseWA(this, mContext).startDataDownload(ServiceMethods.WS_APP_LIST_SERVICES,
                reqUrl,
                BuildReqParameter.getParamsGetServices(serviceGroups, serviceTypes));
    }

    public boolean getAllVendors(String reqUrl) {
        return new BaseWA(this, mContext).startDataDownload(ServiceMethods.WS_APP_VENDORSLIST,
                reqUrl,
                BuildReqParameter.getmethodrequest("get"));
    }

    public boolean getAllEmployees(String urlforAllEmployees) {
        return new BaseWA(this, mContext).startDataDownload(ServiceMethods.GET_ALL_EMPLOYEES,
                urlforAllEmployees,
                BuildReqParameter.getmethodrequest("GET"));
    }

    public boolean assignFormToVendor(String requestUrl, String enquiryFormId, String employeeName) {
        return new BaseWA(this, mContext).startDataDownload(ServiceMethods.WS_APP_ASSIGN_TO_EMP,
                requestUrl,
                BuildReqParameter.paramsForAssignForm(enquiryFormId, employeeName));
    }

    public boolean getDasBoardStates(String reqUrl) {
        return new BaseWA(this, mContext).startDataDownload(ServiceMethods.WS_APP_DASHBOARDSTATE,
                reqUrl,
                BuildReqParameter.getmethodrequest("GET"));
    }

    public boolean uploadAgreement(String creatUrl, String stardate, String endDate, String desc, String userId, String userName, String imgeurl) {
        return new BaseWA(this, mContext).startDataDownload(ServiceMethods.UPLOADAGREEMENT,
                creatUrl,
                BuildReqParameter.getParamsUploadDoc(stardate, endDate, desc, userId, userName, imgeurl));
    }

    public boolean getAllAgreements(String url) {
        return new BaseWA(this, mContext).startDataDownload(ServiceMethods.GETALLAGREEMENTS, url, BuildReqParameter.getmethodrequest("GET"));
    }

    public boolean postServices(String requestUrl, String documentName, String documentType, String issuedBy, String description, String vendorId, ArrayList<String> urls) {
        return new BaseWA(this, mContext).startDataDownload(ServiceMethods.WS_APP_POST_SERVICEDOCS, requestUrl,
                BuildReqParameter.paramsPostServices(documentName, documentType, issuedBy, description, vendorId, urls));
    }

    public boolean getAllCountries(String requestUrl) {
        return new BaseWA(this, mContext).startDataDownload(ServiceMethods.WS_APP_GET_COUNTRIES, requestUrl, BuildReqParameter.getmethodrequest("GET"));
    }

    public boolean getAllStates(String requestUrl) {
        return new BaseWA(this, mContext).startDataDownload(ServiceMethods.WS_APP_GET_STATES, requestUrl, BuildReqParameter.getmethodrequest("GET"));
    }

    public boolean getAllCities(String url) {
        return new BaseWA(this, mContext).startDataDownload(ServiceMethods.WS_APP_GET_CITIES, url, BuildReqParameter.getmethodrequest("GET"));
    }

    public boolean updateVendorForm(String requestUrl, VendorReqDomain vendorEnquaryDomain) {
        return new BaseWA(this, mContext).startDataDownload(ServiceMethods.WS_APP_UPDATE_VENDORFORM, requestUrl,
                gson.toJson(vendorEnquaryDomain)
        );
    }
}

