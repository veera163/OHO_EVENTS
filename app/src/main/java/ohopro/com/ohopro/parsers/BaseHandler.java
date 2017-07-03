package ohopro.com.ohopro.parsers;


import org.xml.sax.helpers.DefaultHandler;

import ohopro.com.ohopro.utility.LoggerUtils;
import ohopro.com.ohopro.webaccess.ServiceMethods;

/**
 * Created by Sai on 9/16/2015.
 */
public abstract class BaseHandler extends DefaultHandler {
    public abstract Object getData();

    public abstract String getErrorData();

    public static BaseHandler getDataParser(String servicemethod, String response) {
        LoggerUtils.info(BaseHandler.class.getSimpleName(), "on BaseHandler");

        LoggerUtils.info(BaseHandler.class.getSimpleName(), response);

        switch (servicemethod) {
            case ServiceMethods.WS_APP_SUBMITABILL:
                return new NullResponseHandler(response);
            case ServiceMethods.WS_APP_GET_BILLS:
                return new GetBillsHandler(response);
            case ServiceMethods.WS_APP_AUTHENTICATION:
                return new AuthenticationHandler(response);
            case ServiceMethods.WS_APP_GET_USERDET:
                return new UserDetHandler(response);
            case ServiceMethods.WS_APP_POST_LEAVE:
                return new NullResponseHandler(response);
            case ServiceMethods.WS_APP_GET_LEAVES:
                return new GerLeavesHandler(response);
            case ServiceMethods.GETBILLS_MGR:
                return new GetBillsHandler(response);
            case ServiceMethods.WS_APP_GET_LEAVE_BALANCE:
                return new GetLeaveBalanceHandler(response);
            case ServiceMethods.WS_APP_APPROVEBILL:
                return new NullResponseHandler(response);
            case ServiceMethods.GETLEAVES_MGR:
                return new GerLeavesHandler(response);
            case ServiceMethods.WS_UPDATE_LEAVE_MNGR:
                return new NullResponseHandler(response);
            case ServiceMethods.WS_APP_ADD_MONEY:
                return new NullResponseHandler(response);
            case ServiceMethods.WS_APP_GET_WALLET_BAL:
                return new GetWalletBalanceHandler(response);
            case ServiceMethods.WS_APP_ADD_MONEY_REQ:
                return new NullResponseHandler(response);
            case ServiceMethods.WS_MONEY_REQS:
                return new MoneyReqsHandler(response);
            case ServiceMethods.APPROVE_MONEY_REQ:
                return new NullResponseHandler(response);
            case ServiceMethods.WS_UPDATEBILL:
                return new NullResponseHandler(response);
            case ServiceMethods.WS_UPDATELEAVE:
                return new NullResponseHandler(response);
            case ServiceMethods.WS_APP_VENDOR_FORM:
                return new MessageHandler(response);
            case ServiceMethods.WS_LIST_SERVICECATOGIIES:
                return new ServiceGroupHandler(response);
            case ServiceMethods.WS_APP_LIST_SERVICES:
                return new ServiceHandler(response);
            case ServiceMethods.WS_APP_VENDORSLIST:
                return new VendorsList(response);
            case ServiceMethods.GET_ALL_EMPLOYEES:
                return new EmployeesHandler(response);
            case ServiceMethods.WS_APP_ASSIGN_TO_EMP:
                return new NullResponseHandler(response);
            case ServiceMethods.WS_APP_DASHBOARDSTATE:
                return new DashBoardStateHandler(response);
            case ServiceMethods.UPLOADAGREEMENT:
                return new NullResponseHandler(response);
            case ServiceMethods.GETALLAGREEMENTS:
                return new GetAllAgreements(response);
            case ServiceMethods.WS_APP_POST_SERVICEDOCS:
                return new NullResponseHandler(response);
        }
        return null;
    }
}
