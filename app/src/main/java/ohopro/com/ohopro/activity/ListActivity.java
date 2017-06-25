package ohopro.com.ohopro.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Pattern;

import ohopro.com.ohopro.R;
import ohopro.com.ohopro.adapter.BillsAdapter;
import ohopro.com.ohopro.adapter.LeavesAdapter;
import ohopro.com.ohopro.adapter.MoneyReqsAdapter;
import ohopro.com.ohopro.adapter.VendorReqsAdapter;
import ohopro.com.ohopro.appserviceurl.ServiceURL;
import ohopro.com.ohopro.busnesslayer.CommonBL;
import ohopro.com.ohopro.busnesslayer.DataListener;
import ohopro.com.ohopro.domains.BillDomain;
import ohopro.com.ohopro.domains.EmployeesDomain;
import ohopro.com.ohopro.domains.LeaveResponse;
import ohopro.com.ohopro.domains.VendorReqDomain;
import ohopro.com.ohopro.domains.WalletRespDOmain;
import ohopro.com.ohopro.fragments.ListOfBillsFragment;
import ohopro.com.ohopro.utility.AppConstant;
import ohopro.com.ohopro.utility.CurrentDate;
import ohopro.com.ohopro.utility.CustomAlertDialog;
import ohopro.com.ohopro.utility.PreferenceUtils;
import ohopro.com.ohopro.views.CustomProgressLoader;
import ohopro.com.ohopro.webaccess.Response;
import ohopro.com.ohopro.webaccess.ServiceMethods;

public class ListActivity extends AppCompatActivity
        implements DatePickerDialog.OnDateSetListener,
        DataListener,
        ListOfBillsFragment.BillsSelectionListener,
        CustomAlertDialog.DialogController,
        LeavesAdapter.LeavesListener,
        MoneyReqsAdapter.WalletReqsListener,
        VendorReqsAdapter.OnVendorFormClick {

    private DatePickerDialog datePickerDialog;
    private ImageView img_back;
    private RecyclerView rv_leaves;
    private Context context;
    private PreferenceUtils preferenceUtils;
    private final String BILLAPPROVAL = "billApproval";
    private BillDomain billDomain;
    private CustomProgressLoader customProgressLoader;
    private String role = AppConstant.APP_NOT_AVAILABLE;
    private LeaveResponse leaveResponse;
    private WalletRespDOmain walletRespDOmain;
    private AlertDialog dialog;
    private AlertDialog.Builder builder;
    Pattern MOBILEPATTERN = Pattern.compile("^[7-9][0-9]{9}$");
    private ArrayList<VendorReqDomain> vendorReqDomains;
    private VendorReqsAdapter vendorReqsAdapter;
    private boolean isEmployeesLoaded = false;
    private VendorReqDomain vendorReqDomain;
    private ArrayList<EmployeesDomain> employeesDomains;
    private CharSequence[] employeesList;
    private AlertDialog alertDialog;
    private Toolbar toolbar;


    @Override
    public void onBackPressed() {
        gotoHomeActivity();
    }

    private void gotoHomeActivity() {
        Intent intent = new Intent(this, DashBoardActivity.class);
        startActivity(intent);
        finish();
    }

    private ArrayList<LeaveResponse> leaveResponses;
    private ArrayList<BillDomain> billDomains;
    private ArrayList<WalletRespDOmain> walletRespDOmains;
    private LeavesAdapter leavesAdapter;
    private BillsAdapter billsAdapter;
    private MoneyReqsAdapter moneyReqsAdapter;
    private CustomAlertDialog customAlertDialog;
    private TextView tv_not_found;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaves);
        context = ListActivity.this;
        preferenceUtils = new PreferenceUtils(context);
        customAlertDialog = new CustomAlertDialog();
        customProgressLoader = new CustomProgressLoader(context);
        builder = new AlertDialog.Builder(context);
        Calendar calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, this, calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        initViewController();
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        getDataFromOtherActivity();

        //getAllLeaves();
    }

    private void getDataFromOtherActivity() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
            if (bundle.containsKey(AppConstant.CLASS_FROM)) {
                if (bundle.getString(AppConstant.CLASS_FROM).equalsIgnoreCase(AppConstant.DASHBOARD)) {
                    role = bundle.getString(AppConstant.ROLE);
                    if (bundle.getString(AppConstant.ROLE).equalsIgnoreCase(AppConstant.ROLEEMPLOYEE)) {
                        if (bundle.getString(AppConstant.ACTION).equalsIgnoreCase(AppConstant.GETLEAVES))
                            getAllLeaves();
                    } else {
                        if (bundle.getString(AppConstant.URL).equalsIgnoreCase(AppConstant.BILLS))
                            getBills(bundle.getString(AppConstant.ACTION));
                        else if (bundle.getString(AppConstant.URL).equalsIgnoreCase(AppConstant.GETMONEYREQS))
                            getMoneyReqs(bundle.getString(AppConstant.ACTION));
                        else if (bundle.getString(AppConstant.URL).equalsIgnoreCase(AppConstant.GETVENDORREQS))
                            seeAllVendorReqs(bundle.getString(AppConstant.ACTION));
                        else
                            getLeaves(bundle.getString(AppConstant.ACTION));
                    }
                }
            }
    }

    private void seeAllVendorReqs(String status) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ServiceURL.getRequestUrl(ServiceMethods.WS_APP_VENDORSLIST));
        stringBuilder.append("/");
        stringBuilder.append(status);

        if (new CommonBL(this, context).getAllVendors(stringBuilder.toString())) {
            customProgressLoader.showProgressDialog();
        }
    }

    private void getMoneyReqs(String action) {
        if (new CommonBL(this, this).getMoneyReqs(creatUrlMoneyReqs(action))) {
            customProgressLoader.showProgressDialog();
        }
    }

    private String creatUrlMoneyReqs(String action) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(ServiceURL.getRequestUrl(ServiceMethods.WS_MONEY_REQS));
        stringBuilder.append(action);

        return stringBuilder.toString();
    }

    private void getLeaves(String action) {
        if (new CommonBL(this, this).getLeavesForManger(creatUrl(action, ServiceMethods.GETLEAVES_MGR))) {
            customProgressLoader.showProgressDialog();
        }
    }

    private void getBills(String action) {
        if (new CommonBL(this, this).getBills(creatUrl(action, ServiceMethods.GETBILLS_MGR))) {
            customProgressLoader.showProgressDialog();
        }
    }

    private String creatUrl(String action, String ServiceMethod) {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(ServiceURL.getRequestUrl(ServiceMethod));
        urlBuilder.append("/");
        urlBuilder.append(action);

        return urlBuilder.toString();
    }

    private void getAllLeaves() {
        if (new CommonBL(this, this).getLeaves(creatUrl())) {
            customProgressLoader.showProgressDialog();
        }
    }

    private String creatUrl() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ServiceURL.getRequestUrl(ServiceMethods.WS_APP_GET_LEAVES));
        stringBuilder.append("/");
        stringBuilder.append(preferenceUtils.getUserMobile());
        return stringBuilder.toString();
    }

    private void initViewController() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tv_not_found = (TextView) findViewById(R.id.tv_not_found);
        img_back = (ImageView) findViewById(R.id.img_back);
        rv_leaves = (RecyclerView) findViewById(R.id.rv_leaves);
        rv_leaves.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        //AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        final Drawable upArrow = ContextCompat.getDrawable(context, R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        toolbar.setNavigationIcon(upArrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        StringBuilder dateBuilder = new StringBuilder();
        dateBuilder.append(i2);
        dateBuilder.append("/");
        dateBuilder.append(i1 + 1);
        dateBuilder.append("/");
        dateBuilder.append(i);
        tv_start_dates.setText(dateBuilder.toString());
    }

    @Override
    public void dataRetreived(Response data) {
        customProgressLoader.dismissProgressDialog();
        if (data.servicemethod.equalsIgnoreCase(ServiceMethods.WS_APP_GET_LEAVES)) {
            if (!data.isError) {
                leaveResponses = (ArrayList<LeaveResponse>) data.data;
                if (leaveResponses.size() > 0) {
                    leavesAdapter = new LeavesAdapter(leaveResponses);
                    leavesAdapter.setLeavesListener(this);
                    rv_leaves.setAdapter(leavesAdapter);
                } else {
                    tv_not_found.setVisibility(View.VISIBLE);
                    rv_leaves.setVisibility(View.GONE);
                }
            }
        } else if (data.servicemethod.equalsIgnoreCase(ServiceMethods.GETBILLS_MGR)) {
            billDomains = (ArrayList<BillDomain>) data.data;
            if (billDomains.size() > 0) {
                billsAdapter = new BillsAdapter(billDomains);
                billsAdapter.registerAListener(this);
                rv_leaves.setAdapter(billsAdapter);
            } else {
                tv_not_found.setVisibility(View.VISIBLE);
                rv_leaves.setVisibility(View.GONE);
            }
        } else if (data.servicemethod.equalsIgnoreCase(ServiceMethods.WS_APP_APPROVEBILL)) {
            if (!data.isError) {
                int statuscode = (int) data.data;
                if (statuscode == 200)
                    billDomains.remove(billDomain);

                billsAdapter.notifyDataSetChanged();

            }
        } else if (data.servicemethod.equalsIgnoreCase(ServiceMethods.GETLEAVES_MGR)) {
            if (!data.isError) {
                leaveResponses = (ArrayList<LeaveResponse>) data.data;
                if (leaveResponses.size() > 0) {
                    leavesAdapter = new LeavesAdapter(leaveResponses);
                    leavesAdapter.setLeavesListener(this);
                    rv_leaves.setAdapter(leavesAdapter);
                } else {
                    tv_not_found.setVisibility(View.VISIBLE);
                    rv_leaves.setVisibility(View.GONE);
                }
            }
        } else if (data.servicemethod.equalsIgnoreCase(ServiceMethods.WS_UPDATE_LEAVE_MNGR)) {
            if (!data.isError) {
                int statuscode = (int) data.data;
                if (statuscode == 200)
                    leaveResponses.remove(leaveResponse);

                leavesAdapter.notifyDataSetChanged();

            }
        } else if (data.servicemethod.equalsIgnoreCase(ServiceMethods.WS_MONEY_REQS)) {
            if (!data.isError) {
                walletRespDOmains = (ArrayList<WalletRespDOmain>) data.data;
                if (walletRespDOmains.size() > 0) {
                    moneyReqsAdapter = new MoneyReqsAdapter(walletRespDOmains);
                    moneyReqsAdapter.setLeavesListener(this);
                    rv_leaves.setAdapter(moneyReqsAdapter);
                } else {
                    tv_not_found.setVisibility(View.VISIBLE);
                    rv_leaves.setVisibility(View.GONE);
                }
            }
        } else if (data.servicemethod.equalsIgnoreCase(ServiceMethods.APPROVE_MONEY_REQ)) {
            if (!data.isError) {
                int statuscode = (int) data.data;
                if (statuscode == 200)
                    walletRespDOmains.remove(walletRespDOmain);

                moneyReqsAdapter.notifyDataSetChanged();

            }
        } else if (data.servicemethod.equalsIgnoreCase(ServiceMethods.WS_UPDATELEAVE)) {
            if (!data.isError) {
                int statuscode = (int) data.data;
                if (statuscode == 200) {
                    dialog.dismiss();
                    customProgressLoader.dismissProgressDialog();
                    getDataFromOtherActivity();
                }
            }
        } else if (data.servicemethod.equalsIgnoreCase(ServiceMethods.WS_APP_VENDORSLIST)) {
            if (!data.isError) {
                vendorReqDomains = (ArrayList<VendorReqDomain>) data.data;
                vendorReqsAdapter = new VendorReqsAdapter(vendorReqDomains, this);
                rv_leaves.setAdapter(vendorReqsAdapter);
                customProgressLoader.dismissProgressDialog();
            }
        } else if (data.servicemethod.equalsIgnoreCase(ServiceMethods.GET_ALL_EMPLOYEES)) {
            if (!data.isError) {
                isEmployeesLoaded = true;
                employeesDomains = (ArrayList<EmployeesDomain>) data.data;
                employeesList = new CharSequence[employeesDomains.size()];

                for (int i = 0; i < employeesDomains.size(); i++)
                    employeesList[i] = employeesDomains.get(i).getFirstname() + " " +
                            employeesDomains.get(i).getLastname();

                assignFormToEmployee();
                customProgressLoader.dismissProgressDialog();
            }
        } else if (data.servicemethod.equalsIgnoreCase(ServiceMethods.WS_APP_ASSIGN_TO_EMP)) {
            if (!data.isError) {
                customProgressLoader.dismissProgressDialog();
                alertDialog.dismiss();
            }
        }
    }

    @Override
    public void opennetworksetting() {

    }

    @Override
    public void onBillSelected(BillDomain billDomain) {
        this.billDomain = billDomain;
        if (billDomain.getStatus().equalsIgnoreCase(AppConstant.PENDINGSTATUS)) {
            /*Bundle bundle = new Bundle();
            bundle.putString(CustomAlertDialog.MESSAGE, "would you Like to Approve this Bill");
            customAlertDialog.setArguments(bundle);
            customAlertDialog.show(getSupportFragmentManager(), BILLAPPROVAL);*/
            final EditText edt_comment;
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            View view = getLayoutInflater().inflate(R.layout.dialog_approveleave, null);
            builder.setView(view);
            edt_comment = (EditText) view.findViewById(R.id.edt_comment);
            builder.setPositiveButton("Approve", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    // if (!edt_comment.getText().toString().equalsIgnoreCase(""))
                    //approveALeavesaveInServer(leaveResponse, edt_comment.getText().toString(), AppConstant.APPROVEDSTATUS);
                    approveBill(edt_comment.getText().toString(), AppConstant.APPROVEDSTATUS);
                    //else
                    //  Toast.makeText(context, "Please Enter Comment", Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("Reject", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    if (!edt_comment.getText().toString().equalsIgnoreCase(""))
                        //approveALeavesaveInServer(leaveResponse, edt_comment.getText().toString(), AppConstant.APPROVEDSTATUS);
                        approveBill(edt_comment.getText().toString(), AppConstant.REJECT);
                    else
                        Toast.makeText(context, "Please Enter Comment", Toast.LENGTH_SHORT).show();
                }
            });

            builder.create().show();
        } else {
            Toast.makeText(context, "These are Approved Bills", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void clickonPositive(String tag) {
        /*if (tag.equalsIgnoreCase(BILLAPPROVAL))
            approveBill(edt_comment.getText().toString());*/
    }

    private void approveBill(String comment, String status) {
        if (new CommonBL(this, context).approveABill(creatUrlBillApproval(), comment, status, billDomain.getBillId())) {
            customProgressLoader.showProgressDialog();
        }
    }

    private String creatUrlBillApproval() {


        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ServiceURL.getRequestUrl(ServiceMethods.WS_APP_APPROVEBILL));
        //stringBuilder.append(billDomain.getBillId());

        return stringBuilder.toString();
    }

    @Override
    public void clickonNegative(String tag) {
    }


    @Override
    public void onLeaveClick(LeaveResponse leaveResponse) {
        if (role.equalsIgnoreCase(AppConstant.ROLEMANAGER))
            if (leaveResponse.getStatus().equalsIgnoreCase(AppConstant.PENDINGSTATUS))
                approveALeave(leaveResponse);
            else
                Toast.makeText(context, "Already Approved", Toast.LENGTH_SHORT).show();
        else if (role.equalsIgnoreCase(AppConstant.ROLEEMPLOYEE))
            if (leaveResponse.getStatus().equalsIgnoreCase(AppConstant.APPROVEDSTATUS))
                Toast.makeText(context, "Already Approved", Toast.LENGTH_SHORT).show();
            else
                updateLeave(leaveResponse);

    }

    TextView tv_start_dates;
    Button btn_submit_bill, btn_back;

    private void updateLeave(final LeaveResponse leaveResponse) {
        final EditText edt_reason, edt_num_of_days, edt_emergenct_number;

        View view = getLayoutInflater().inflate(R.layout.fragment_request_leave, null);
        builder.setView(view);
        edt_emergenct_number = (EditText) view.findViewById(R.id.edt_emergenct_number);
        edt_reason = (EditText) view.findViewById(R.id.edt_reason);
        edt_num_of_days = (EditText) view.findViewById(R.id.edt_num_of_days);
        tv_start_dates = (TextView) view.findViewById(R.id.tv_start_dates);
        btn_submit_bill = (Button) view.findViewById(R.id.btn_submit_bill);
        btn_back = (Button) view.findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        tv_start_dates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });
        btn_submit_bill.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View view) {
                                                   if (tv_start_dates.getText().toString().equalsIgnoreCase("")) {
                                                       Toast.makeText(context, "Please Select Date", Toast.LENGTH_SHORT).show();
                                                       return;
                                                   }

                                                   if (new CurrentDate(tv_start_dates.getText().toString(), CurrentDate.SLASH_DD_MM_YYYY).getDate().before(new CurrentDate().getDate())) {
                                                       Toast.makeText(context, "date Should not be past Date", Toast.LENGTH_SHORT).show();
                                                       return;
                                                   }
                                                   leaveResponse.setStartDate(tv_start_dates.getText().toString());

                                                   if (edt_num_of_days.getText().toString().equalsIgnoreCase("")) {
                                                       Toast.makeText(context, "Please Enter Number of Days", Toast.LENGTH_SHORT).show();
                                                       return;
                                                   } else {
                                                       leaveResponse.setNumberOfDays(edt_num_of_days.getText().toString());
                                                   }

                                                   if (!MOBILEPATTERN.matcher(edt_emergenct_number.getText().toString()).matches()) {
                                                       Toast.makeText(context, "Please Enter Valid Emergency Number ", Toast.LENGTH_SHORT).show();
                                                       return;
                                                   } else {
                                                       leaveResponse.setEmergencyContact(edt_emergenct_number.getText().toString());
                                                   }


                                                   leaveResponse.setReason(edt_reason.getText().toString());
                                                   leaveResponse.setEndDate(getDateFromGivenInputs(tv_start_dates.getText().toString(),
                                                           Integer.parseInt(edt_num_of_days.getText().toString())));
                                                   updateLeaveRequest(
                                                           ServiceURL.getRequestUrl(ServiceMethods.WS_APP_POST_LEAVE),
                                                           leaveResponse);

                                               }
                                           }

        );

        dialog = builder.create();
        dialog.show();

    }

    private String getDateFromGivenInputs(String date, int numOfDays) {
        CurrentDate currentDate = new CurrentDate(date, CurrentDate.SLASH_DD_MM_YYYY);
        return currentDate.dateAfterDays(numOfDays, CurrentDate.SLASH_DD_MM_YYYY);
    }

    private void updateLeaveRequest(String requestUrl, LeaveResponse leaveResponse) {
        if (new CommonBL(this, context).updateLeave(requestUrl, leaveResponse)) {
            customProgressLoader.showProgressDialog();
        }
    }

    private void approveALeave(final LeaveResponse leaveResponse) {
        this.leaveResponse = leaveResponse;
        final EditText edt_comment;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = getLayoutInflater().inflate(R.layout.dialog_approveleave, null);
        builder.setView(view);
        edt_comment = (EditText) view.findViewById(R.id.edt_comment);
        builder.setPositiveButton("Approve", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                //if (!edt_comment.getText().toString().equalsIgnoreCase(""))
                approveALeavesaveInServer(leaveResponse, edt_comment.getText().toString(), AppConstant.APPROVEDSTATUS);
                //else
                //  Toast.makeText(context, "Please Enter Comment", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Reject", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                if (!edt_comment.getText().toString().equalsIgnoreCase(""))
                    approveALeavesaveInServer(leaveResponse, edt_comment.getText().toString(), AppConstant.REJECT);
                else
                    Toast.makeText(context, "Please Enter Comment", Toast.LENGTH_SHORT).show();
            }
        });

        builder.create().show();
    }

    private void approveALeavesaveInServer(LeaveResponse leaveResponse, String comment, String status) {
        if (new CommonBL(this, context).updateLeaveByManager(
                ServiceURL.getRequestUrl(ServiceMethods.WS_UPDATE_LEAVE_MNGR),
                leaveResponse.getLeaveRequestId(), comment, status)) {

        }
    }

    @Override
    public void onReqClick(WalletRespDOmain walletRespDOmain) {
        this.walletRespDOmain = walletRespDOmain;
        if (new CommonBL(this, context).approveMoneyReq(creatUrlMoneyApproval(walletRespDOmain.getCashRequestId()))) {

        }
    }

    private String creatUrlMoneyApproval(String cashRequestId) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ServiceURL.getRequestUrl(ServiceMethods.APPROVE_MONEY_REQ));
        stringBuilder.append(cashRequestId);
        return stringBuilder.toString();
    }

    @Override
    public void onClickOnVendorForm(VendorReqDomain vendorReqDomain) {
        this.vendorReqDomain = vendorReqDomain;
        if (!isEmployeesLoaded)
            getAllEmployees();
        else
            assignFormToEmployee();
    }

    private void assignFormToEmployee() {
        TextView tv_vendor_name, tv_role, tv_company_name, tv_address, tv_service_group;
        final TextView tv_service_type, tv_service, tv_assign_to;
        Button btn_back, btn_update;
        View view = getLayoutInflater().inflate(R.layout.dialog_vendor_form, null);
        tv_vendor_name = (TextView) view.findViewById(R.id.tv_vendor_name);
        tv_role = (TextView) view.findViewById(R.id.tv_role);
        tv_company_name = (TextView) view.findViewById(R.id.tv_company_name);
        tv_address = (TextView) view.findViewById(R.id.tv_address);
        tv_service_group = (TextView) view.findViewById(R.id.tv_service_group);
        tv_service_type = (TextView) view.findViewById(R.id.tv_service_type);
        tv_service = (TextView) view.findViewById(R.id.tv_service);
        tv_assign_to = (TextView) view.findViewById(R.id.tv_assign_to);
        btn_back = (Button) view.findViewById(R.id.btn_back);
        btn_update = (Button) view.findViewById(R.id.btn_update);
        builder.setView(view);
        builder.setTitle("Vendor Form");
        alertDialog = builder.create();

        tv_vendor_name.setText(vendorReqDomain.getTitle() + " " + vendorReqDomain.getFirstName() + " " +
                vendorReqDomain.getSurname());
        tv_role.setText(vendorReqDomain.getDesignation());
        tv_company_name.setText(vendorReqDomain.getCompanyName());
        tv_address.setText(vendorReqDomain.getAddressLine1() + "," + vendorReqDomain.getAddressLine2());
        tv_service_group.setText(vendorReqDomain.getProductOrServiceGroup());
        tv_service_type.setText(vendorReqDomain.getProductOrServiceType());
        tv_service.setText(vendorReqDomain.getProductsOrServices());
        tv_assign_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setArrayToDialog(employeesList
                        , "Employees",
                        tv_assign_to);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateInServerAssignFormToEmployee(tv_assign_to.getText().toString());
            }
        });
        alertDialog.show();
    }

    private void updateInServerAssignFormToEmployee(String employeeName) {
        if (new CommonBL(this, context).assignFormToVendor(
                ServiceURL.getRequestUrl(ServiceMethods.WS_APP_ASSIGN_TO_EMP),
                vendorReqDomain.getEnquiryFormId(), employeeName)) {
            customProgressLoader.showProgressDialog();
        }
    }

    private void setArrayToDialog(final CharSequence[] charSequences, String tittle, final TextView textView) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(tittle);
        builder.setCancelable(false);
        builder.setItems(charSequences, new AlertDialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                textView.setText(charSequences[i]);
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

    private void getAllEmployees() {
        if (new CommonBL(this, context).getAllEmployees(getUrlforAllEmployees())) {
            customProgressLoader.showProgressDialog();
        }
    }

    private String getUrlforAllEmployees() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ServiceURL.getRequestUrl(ServiceMethods.GET_ALL_EMPLOYEES));
        stringBuilder.append(preferenceUtils.getUserMobile());
        return stringBuilder.toString();
    }
}
