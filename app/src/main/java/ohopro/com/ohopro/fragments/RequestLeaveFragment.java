package ohopro.com.ohopro.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import ohopro.com.ohopro.utility.AppConstant;
import ohopro.com.ohopro.utility.CurrentDate;
import ohopro.com.ohopro.utility.PreferenceUtils;
import ohopro.com.ohopro.views.CustomProgressLoader;
import ohopro.com.ohopro.webaccess.Response;
import ohopro.com.ohopro.webaccess.ServiceMethods;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link RequestLeaveFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RequestLeaveFragment extends Fragment
        implements DataListener, DatePickerDialog.OnDateSetListener {

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

    public RequestLeaveFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RequestLeaveFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RequestLeaveFragment newInstance() {
        RequestLeaveFragment fragment = new RequestLeaveFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        AppConstant.currentFragmentTag = RequestLeaveFragment.class.getSimpleName();
        Calendar calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(getContext(), this, calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        preferenceUtils = new PreferenceUtils(getContext());
        customProgressLoader = new CustomProgressLoader(getContext());
        View view = inflater.inflate(R.layout.fragment_request_leave, container, false);

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
                getFragmentManager().beginTransaction().replace(R.id.ll_fragment, HomeFragment.newInstance()).commit();
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
        return view;
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

        final Drawable upArrow = ContextCompat.getDrawable(getContext(), R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(ContextCompat.getColor(getContext(), R.color.gray), PorterDuff.Mode.SRC_ATOP);
        btn_back.setCompoundDrawablesWithIntrinsicBounds(upArrow, null, null, null);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
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
                        Toast.makeText(getContext(), "Leave Request Success fully Done,wait for Manager Approval", Toast.LENGTH_SHORT).show();
                        getFragmentManager().beginTransaction()
                                .replace(R.id.ll_fragment, HomeFragment.newInstance())
                                .commit();
                    } else {
                        Toast.makeText(getContext(), String.valueOf(statuscode), Toast.LENGTH_SHORT).show();

                    }
                }
            }
        }
    }

    @Override
    public void opennetworksetting() {

    }
}
