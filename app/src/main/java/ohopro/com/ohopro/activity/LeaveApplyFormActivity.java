package ohopro.com.ohopro.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.regex.Pattern;

import ohopro.com.ohopro.R;
import ohopro.com.ohopro.appserviceurl.ServiceURL;
import ohopro.com.ohopro.busnesslayer.CommonBL;
import ohopro.com.ohopro.busnesslayer.DataListener;
import ohopro.com.ohopro.domains.ErrorDomain;
import ohopro.com.ohopro.domains.LeaveResponse;
import ohopro.com.ohopro.fragments.RequestLeaveFragment;
import ohopro.com.ohopro.utility.AppConstant;
import ohopro.com.ohopro.utility.CurrentDate;
import ohopro.com.ohopro.utility.CustomAlertDialogSimple;
import ohopro.com.ohopro.utility.PreferenceUtils;
import ohopro.com.ohopro.views.CustomProgressLoader;
import ohopro.com.ohopro.webaccess.Response;
import ohopro.com.ohopro.webaccess.ServiceMethods;

public class LeaveApplyFormActivity extends AppCompatActivity implements DataListener, DatePickerDialog.OnDateSetListener {

    TextView tv_start_dates;
    EditText edt_reason, edt_num_of_days, edt_emergenct_number;
    CustomProgressLoader customProgressLoader;
    DatePickerDialog datePickerDialog;
    String selection;
    Button btn_submit_bill;
    private final String STARTDATE = "startDate";
    private final String ENDDATE = "endDate";
    PreferenceUtils preferenceUtils;
    Pattern MOBILEPATTERN = Pattern.compile("^[7-9][0-9]{9}$");
    Button btn_back;
    private String action;
    private LeaveResponse leaveResponse;

    private void getDataFromOtherACtivity() {
        Bundle bundle = getIntent().getExtras();
        if (bundle.containsKey(AppConstant.ACTION)) {
            action = bundle.getString(AppConstant.ACTION);
            if (action.equalsIgnoreCase(AppConstant.EDIT))
                leaveResponse = bundle.getParcelable(AppConstant.DATAOBJECT);
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppConstant.currentFragmentTag = RequestLeaveFragment.class.getSimpleName();
        Calendar calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(getContext(), this, calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        preferenceUtils = new PreferenceUtils(getContext());
        customProgressLoader = new CustomProgressLoader(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_request_leave, null);
        setContentView(view);
        getDataFromOtherACtivity();
        initviewcontroller(view);

        tv_start_dates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selection = STARTDATE;
                datePickerDialog.show();
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_submit_bill.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View view) {
                                                   if (tv_start_dates.getText().toString().equalsIgnoreCase("")) {
                                                       Toast.makeText(getContext(), "Please Select Date", Toast.LENGTH_SHORT).show();
                                                       return;
                                                   }

                                                   if (new CurrentDate(tv_start_dates.getText().toString(), CurrentDate.SLASH_DD_MM_YYYY).getDate().before(new CurrentDate().getDate())) {
                                                       Toast.makeText(getContext(), "date Should not be past Date", Toast.LENGTH_SHORT).show();
                                                       return;
                                                   }

                                                   if (edt_num_of_days.getText().toString().equalsIgnoreCase("")) {
                                                       Toast.makeText(getContext(), "Please Enter Number of Days", Toast.LENGTH_SHORT).show();
                                                       return;
                                                   }

                                                   if (!MOBILEPATTERN.matcher(edt_emergenct_number.getText().toString()).matches()) {
                                                       Toast.makeText(getContext(), "Please Enter Valid Emergency Number ", Toast.LENGTH_SHORT).show();
                                                       return;

                                                   }


                                                   if (action != null)
                                                       if (!action.equalsIgnoreCase(AppConstant.EDIT))
                                                           newLeaveRequest(
                                                                   ServiceURL.getRequestUrl(ServiceMethods.WS_APP_POST_LEAVE),
                                                                   tv_start_dates.getText().toString(),
                                                                   edt_emergenct_number.getText().toString(),
                                                                   edt_reason.getText().toString(),
                                                                   getDateFromGivenInputs(tv_start_dates.getText().toString(),
                                                                           Integer.parseInt(edt_num_of_days.getText().toString())),
                                                                   edt_num_of_days.getText().toString(),
                                                                   preferenceUtils
                                                           );
                                                       else
                                                           updateLeaveRequest();

                                                   else
                                                       newLeaveRequest(
                                                               ServiceURL.getRequestUrl(ServiceMethods.WS_APP_POST_LEAVE),
                                                               tv_start_dates.getText().toString(),
                                                               edt_emergenct_number.getText().toString(),
                                                               edt_reason.getText().toString(),
                                                               getDateFromGivenInputs(tv_start_dates.getText().toString(),
                                                                       Integer.parseInt(edt_num_of_days.getText().toString())),
                                                               edt_num_of_days.getText().toString(),
                                                               preferenceUtils
                                                       );

                                               }
                                           }

        );
    }

    private void updateLeaveRequest() {
        if (tv_start_dates.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(this, "Please Select Date", Toast.LENGTH_SHORT).show();
            return;
        }

        if (new CurrentDate(tv_start_dates.getText().toString(), CurrentDate.SLASH_DD_MM_YYYY).getDate().before(new CurrentDate().getDate())) {
            Toast.makeText(this, "date Should not be past Date", Toast.LENGTH_SHORT).show();
            return;
        }
        leaveResponse.setStartDate(tv_start_dates.getText().toString());

        if (edt_num_of_days.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(this, "Please Enter Number of Days", Toast.LENGTH_SHORT).show();
            return;
        } else {
            leaveResponse.setNumberOfDays(edt_num_of_days.getText().toString());
        }

        if (!MOBILEPATTERN.matcher(edt_emergenct_number.getText().toString()).matches()) {
            Toast.makeText(this, "Please Enter Valid Emergency Number ", Toast.LENGTH_SHORT).show();
            return;
        } else {
            leaveResponse.setEmergencyContact(edt_emergenct_number.getText().toString());
        }


        leaveResponse.setReason(edt_reason.getText().toString());
        leaveResponse.setEndDate(getDateFromGivenInputs(tv_start_dates.getText().toString(),
                Integer.parseInt(edt_num_of_days.getText().toString())));

        if (new CommonBL(this, this).updateLeave(
                ServiceURL.getRequestUrl(ServiceMethods.WS_APP_POST_LEAVE), leaveResponse)) {
            customProgressLoader.showProgressDialog();
        }
    }

    private Context getContext() {
        return this;
    }

    private void newLeaveRequest(String requestUrl, String startDate, String emergencyContact, String reason, String enddate, String numOfDays, PreferenceUtils preferenceUtils) {
        if (new CommonBL(this, getContext()).postLeave(
                requestUrl, startDate, emergencyContact, enddate, numOfDays, reason,
                preferenceUtils.getUserMobile(),
                preferenceUtils.getUserEmail(),
                preferenceUtils.getUserName()
        )) {
            customProgressLoader.showProgressDialog();
        }
    }

    private String getDateFromGivenInputs(String date, int numOfDays) {
        CurrentDate currentDate = new CurrentDate(date, CurrentDate.SLASH_DD_MM_YYYY);
        return currentDate.dateAfterDays(numOfDays, CurrentDate.SLASH_DD_MM_YYYY);
    }

    private void initviewcontroller(View view) {
        tv_start_dates = (TextView) view.findViewById(R.id.tv_start_dates);
        edt_reason = (EditText) view.findViewById(R.id.edt_reason);
        edt_num_of_days = (EditText) view.findViewById(R.id.edt_num_of_days);
        btn_submit_bill = (Button) view.findViewById(R.id.btn_submit_bill);
        edt_emergenct_number = (EditText) view.findViewById(R.id.edt_emergenct_number);
        btn_back = (Button) view.findViewById(R.id.btn_back);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        final Drawable upArrow = ContextCompat.getDrawable(getContext(), R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        toolbar.setNavigationIcon(upArrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        configBasedOnEdit();
    }

    private void configBasedOnEdit() {
        if (action != null)
            if (action.equalsIgnoreCase(AppConstant.EDIT))
                onEditMode();
    }

    private void onEditMode() {
        if (leaveResponse != null) {
            edt_emergenct_number.setText(leaveResponse.getEmergencyContact());
            edt_num_of_days.setText(leaveResponse.getNumberOfDays());
            tv_start_dates.setText(leaveResponse.getStartDate());
            edt_reason.setText(leaveResponse.getReason());
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        StringBuilder dateBuilder = new StringBuilder();
        dateBuilder.append(i2);
        dateBuilder.append("/");
        dateBuilder.append(i1 + 1);
        dateBuilder.append("/");
        dateBuilder.append(i);

        if (selection.equalsIgnoreCase(STARTDATE))
            tv_start_dates.setText(dateBuilder.toString());

    }

    @Override
    public void dataRetreived(Response data) {
        if (data.servicemethod.equalsIgnoreCase(ServiceMethods.WS_APP_POST_LEAVE)) {
            if (!data.isError) {
                customProgressLoader.dismissProgressDialog();
                if (data.data instanceof Integer) {
                    int statuscode = (int) data.data;
                    if (statuscode == 200) {
                        new CustomAlertDialogSimple(getContext(), new CustomAlertDialogSimple.OnDismisslistener() {
                            @Override
                            public void onDismiss() {
                                finish();
                            }
                        }).showAlertDialog("Leave Request Success fully Done,wait for Manager Approval");

                        /*getFragmentManager().beginTransaction()
                                .replace(R.id.ll_fragment, HomeFragment.newInstance())
                                .commit();*/
                    } else {
                        Toast.makeText(getContext(), String.valueOf(statuscode), Toast.LENGTH_SHORT).show();

                    }
                } else if (data.data instanceof ErrorDomain) {
                    new CustomAlertDialogSimple(getContext()).showAlertDialog(((ErrorDomain) data.data).getError_description());
                }
            }
        } else if (data.servicemethod.equalsIgnoreCase(ServiceMethods.WS_UPDATELEAVE)) {
            if (!data.isError) {
                customProgressLoader.dismissProgressDialog();

                if (data.data instanceof Integer) {
                    int statuscode = (int) data.data;
                    if (statuscode == 200) {
                        new CustomAlertDialogSimple(this, new CustomAlertDialogSimple.OnDismisslistener() {
                            @Override
                            public void onDismiss() {
                                finish();
                            }
                        }).showAlertDialog("your Leave successfully updated");

                    }
                } else if (data.data instanceof ErrorDomain) {
                    new CustomAlertDialogSimple(this).showAlertDialog(((ErrorDomain) data.data).getError_description());
                }

            }
        }
    }

    @Override
    public void opennetworksetting() {

    }


}
