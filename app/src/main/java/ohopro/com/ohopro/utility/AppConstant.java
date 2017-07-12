package ohopro.com.ohopro.utility;


import ohopro.com.ohopro.activity.DashBoardActivity;
import ohopro.com.ohopro.domains.DashBoardStatesDomain;
import ohopro.com.ohopro.domains.VendorReqDomain;
import ohopro.com.ohopro.services.AccessTokenService;

/**
 * Created by sai on 03-03-2016.
 */
public class AppConstant {

    public static final String ACCESSTOKEINFO = "?access_token=";
    //public static final String ROLEMANAGER = "ROLE_MANAGER";
    public static final String ROLEMANAGER = "ROLE_MANAGER";
    //public static final String ROLEEMPLOYEE = "ROLE_EMP";
    public static final String ROLEEMPLOYEE = "E";
    public static final String DASHBOARD = DashBoardActivity.class.getSimpleName();
    public static final String GETLEAVES = "get leaves";
    public static final String ACTION = "action";
    public static final String PENDINGSTATUS = "PENDING";
    public static final String APPROVEDSTATUS = "APPROVED";
    public static final String BILLS = "bills";
    public static final String LEAVES = "leaves";
    public static final String URL = "url";
    public static final String ROLE = "role";
    public static final String GETMONEYREQS = "getmoneyReqs";
    public static final int REQ_PUT = 3;
    public static final String GETVENDORREQS = "seeallvendors";
    public static final String VENDORIN_PROGRSS = "IN_PROGRESS";
    public static final String VENDORENQUIRED  = "ENQUIRED ";
    public static final String ROLEVENDOR = "V";
    public static final String EDIT = "edit";
    public static String CHECK_NETWORK_CONN = "Please Check your Connetion";
    public static String DEFAULT_MOB_NUM = "0000000000";
    public static String APP_PREF_NAME = "OhoProPref";
    public static String CHECK_PLAY_SERVICE = "check your play " +
            "store services";
    public static String APP_LOGIN_MSG = "Login Message";
    public static String APP_NOT_AVAILABLE = "NA";
    public static String NO_DATA = "No Data Found";
    public static String CLASS_FROM = "class from";
    public static String NO_RESPONSE = "No ServiceOrSupplyLocations";
    public static String OK_RESPONSE = "ok response";
    public static String FBALREADY_REGISTER = "fb already register";
    public static String GPLUSALREADY_REGISTER = "google already register";
    public static int REQ_POST = 1;
    public static int REQ_GET = 2;
    public static String CONNECT_TO_NETWORK = "Connect to Network";
    public static String APP_TEMP_PREF_NAME = "tempOhoPropPreff";
    public static String currentFragmentTag = AppConstant.APP_NOT_AVAILABLE;
    public static String HEADER = AppConstant.APP_NOT_AVAILABLE;
    public static String REJECT = "REJECTED";
    public static DashBoardStatesDomain dashBoardStatesDomain;
    public static boolean accessUpdated = false;
    public static AccessTokenService.Updated updated;
    public static String ERROR = "error";
    public static String DATAOBJECT="dataObject";
    public static VendorReqDomain vendorReqDomain;
}
