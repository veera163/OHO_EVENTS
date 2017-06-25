package ohopro.com.ohopro.appserviceurl;


import ohopro.com.ohopro.utility.AppConstant;
import ohopro.com.ohopro.webaccess.ServiceMethods;

/**
 * Created by sai on 9/16/2015.
 */
public class ServiceURL {
    public static String HOST_URL = "http://52.204.106.22/";
    private static String SUBMITBILL = "bills/submit";
    private static String GETBILLS = "bills/user/status";
    private static String AUTHENTICATION = "oauth/token?grant_type=password&scope=read+write&client_secret=123456&client_id=clientapp&password=";
    private static String GETUSERDETAILS = "userOperations";
    private static String POSTLEAVE = "leavemgmt/submit";
    private static String GETAPPLIEDLEAVES = "leavemgmt/user";
    private static String GETBILLSMGR = "bills/status";
    private static String GETLEAVEBALANCE = "leavemgmt/balance";
    private static String APPROVEBILL = "bills/approve/";
    private static String GETLEAVESMANAGER = "leavemgmt/status";
    private static String UPDATELEAVE = "leavemgmt/approve";
    private static String ADD_MONEY = "wallet/addMoney";
    private static String WALLET_BAL = "wallet/balance/";
    private static String MONEY_REQ = "cashRequest/submit";
    private static String MONEYREQS = "cashRequest/status/";
    private static String APPROVEMONEYREQ = "cashRequest/approveCashRequest/";
    private static String POSTVENDORFORM = "vendorenquiry/submit";
    private static String SERVICECATOGIRIES = "eventItems";
    private static String LISTSERVICES = "productTypes/findByGroupandCategory";
    private static String LISTOFVENDORS = "/vendorenquiry/status/";
    private static String GETALLEMPLOYEES = "/vendorenquiry /mgr/reportingEmployees/";
    private static String ASSIGNFORMTOEMPLOYEE = "/vendorenquiry/assign/";
    private static String DASHBOARDSTATE = "vendorDashBoardStats/";
    private static String AGREEMENTFORM = "/vendorAgreementDocs";
    private static String UPLOADSERVICE = "/vendorDocs";

    public static int getRequestTypemethod(String methodname) {
        switch (methodname) {
            case ServiceMethods.WS_APP_SUBMITABILL:
                return AppConstant.REQ_POST;
            case ServiceMethods.WS_APP_GET_BILLS:
                return AppConstant.REQ_POST;
            case ServiceMethods.WS_APP_AUTHENTICATION:
                return AppConstant.REQ_POST;
            case ServiceMethods.WS_APP_GET_USERDET:
                return AppConstant.REQ_GET;
            case ServiceMethods.WS_APP_POST_LEAVE:
                return AppConstant.REQ_POST;
            case ServiceMethods.WS_APP_GET_LEAVES:
                return AppConstant.REQ_POST;
            case ServiceMethods.GETBILLS_MGR:
                return AppConstant.REQ_GET;
            case ServiceMethods.WS_APP_GET_LEAVE_BALANCE:
                return AppConstant.REQ_POST;
            case ServiceMethods.WS_APP_APPROVEBILL:
                return AppConstant.REQ_POST;
            case ServiceMethods.GETLEAVES_MGR:
                return AppConstant.REQ_GET;
            case ServiceMethods.WS_UPDATE_LEAVE_MNGR:
                return AppConstant.REQ_POST;
            case ServiceMethods.WS_APP_ADD_MONEY:
                return AppConstant.REQ_POST;
            case ServiceMethods.WS_APP_GET_WALLET_BAL:
                return AppConstant.REQ_GET;
            case ServiceMethods.WS_APP_ADD_MONEY_REQ:
                return AppConstant.REQ_POST;
            case ServiceMethods.WS_MONEY_REQS:
                return AppConstant.REQ_GET;
            case ServiceMethods.APPROVE_MONEY_REQ:
                return AppConstant.REQ_POST;
            case ServiceMethods.WS_UPDATEBILL:
                return AppConstant.REQ_PUT;
            case ServiceMethods.WS_UPDATELEAVE:
                return AppConstant.REQ_PUT;
            case ServiceMethods.WS_APP_VENDOR_FORM:
                return AppConstant.REQ_POST;
            case ServiceMethods.WS_LIST_SERVICECATOGIIES:
                return AppConstant.REQ_GET;
            case ServiceMethods.WS_APP_LIST_SERVICES:
                return AppConstant.REQ_POST;
            case ServiceMethods.WS_APP_VENDORSLIST:
                return AppConstant.REQ_GET;
            case ServiceMethods.GET_ALL_EMPLOYEES:
                return AppConstant.REQ_GET;
            case ServiceMethods.WS_APP_ASSIGN_TO_EMP:
                return AppConstant.REQ_POST;
            case ServiceMethods.WS_APP_DASHBOARDSTATE:
                return AppConstant.REQ_GET;
            case ServiceMethods.UPLOADAGREEMENT:
                return AppConstant.REQ_POST;
            case ServiceMethods.GETALLAGREEMENTS:
                return AppConstant.REQ_GET;
            case ServiceMethods.WS_APP_POST_SERVICEDOCS:
                return AppConstant.REQ_POST;
        }
        return 0;

    }

    public static String getRequestUrl(String methodname) {
        switch (methodname) {
            case ServiceMethods.WS_APP_SUBMITABILL:
                return HOST_URL + SUBMITBILL;
            case ServiceMethods.WS_APP_GET_BILLS:
                return HOST_URL + GETBILLS;
            case ServiceMethods.WS_APP_AUTHENTICATION:
                return HOST_URL + AUTHENTICATION;
            case ServiceMethods.WS_APP_GET_USERDET:
                return HOST_URL + GETUSERDETAILS;
            case ServiceMethods.WS_APP_POST_LEAVE:
                return HOST_URL + POSTLEAVE;
            case ServiceMethods.WS_APP_GET_LEAVES:
                return HOST_URL + GETAPPLIEDLEAVES;
            case ServiceMethods.GETBILLS_MGR:
                return HOST_URL + GETBILLSMGR;
            case ServiceMethods.WS_APP_GET_LEAVE_BALANCE:
                return HOST_URL + GETLEAVEBALANCE;
            case ServiceMethods.WS_APP_APPROVEBILL:
                return HOST_URL + APPROVEBILL;
            case ServiceMethods.GETLEAVES_MGR:
                return HOST_URL + GETLEAVESMANAGER;
            case ServiceMethods.WS_UPDATE_LEAVE_MNGR:
                return HOST_URL + UPDATELEAVE;
            case ServiceMethods.WS_APP_ADD_MONEY:
                return HOST_URL + ADD_MONEY;
            case ServiceMethods.WS_APP_GET_WALLET_BAL:
                return HOST_URL + WALLET_BAL;
            case ServiceMethods.WS_APP_ADD_MONEY_REQ:
                return HOST_URL + MONEY_REQ;
            case ServiceMethods.WS_MONEY_REQS:
                return HOST_URL + MONEYREQS;
            case ServiceMethods.APPROVE_MONEY_REQ:
                return HOST_URL + APPROVEMONEYREQ;
            case ServiceMethods.WS_APP_VENDOR_FORM:
                return HOST_URL + POSTVENDORFORM;
            case ServiceMethods.WS_LIST_SERVICECATOGIIES:
                return HOST_URL + SERVICECATOGIRIES;
            case ServiceMethods.WS_APP_LIST_SERVICES:
                return HOST_URL + LISTSERVICES;
            case ServiceMethods.WS_APP_VENDORSLIST:
                return HOST_URL + LISTOFVENDORS;
            case ServiceMethods.GET_ALL_EMPLOYEES:
                return HOST_URL + GETALLEMPLOYEES;
            case ServiceMethods.WS_APP_ASSIGN_TO_EMP:
                return HOST_URL + ASSIGNFORMTOEMPLOYEE;
            case ServiceMethods.WS_APP_DASHBOARDSTATE:
                return HOST_URL + DASHBOARDSTATE;
            case ServiceMethods.UPLOADAGREEMENT:
                return HOST_URL + AGREEMENTFORM;
            case ServiceMethods.WS_APP_POST_SERVICEDOCS:
                return HOST_URL + UPLOADSERVICE;
        }
        return "NA";
    }

}
