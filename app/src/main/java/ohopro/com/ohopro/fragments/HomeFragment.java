package ohopro.com.ohopro.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import ohopro.com.ohopro.R;
import ohopro.com.ohopro.activity.DashBoardActivity;
import ohopro.com.ohopro.appserviceurl.ServiceURL;
import ohopro.com.ohopro.busnesslayer.CommonBL;
import ohopro.com.ohopro.busnesslayer.DataListener;
import ohopro.com.ohopro.domains.DashBoardStatesDomain;
import ohopro.com.ohopro.domains.LeaveBalanceDomain;
import ohopro.com.ohopro.domains.WalletDomain;
import ohopro.com.ohopro.services.AccessTokenService;
import ohopro.com.ohopro.utility.AppConstant;
import ohopro.com.ohopro.utility.PreferenceUtils;
import ohopro.com.ohopro.views.CustomProgressLoader;
import ohopro.com.ohopro.webaccess.Response;
import ohopro.com.ohopro.webaccess.ServiceMethods;

public class HomeFragment extends Fragment
        implements OnChartValueSelectedListener,
        DataListener, AccessTokenService.Updated {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    @Override
    public void onDestroy() {
        super.onDestroy();
        AppConstant.updated = null;
    }

    // TODO: Rename and change types of parameters
    PreferenceUtils preferenceUtils;
    CustomProgressLoader customProgressLoader;
    LinearLayout ll_submit_bill, ll_apply_leave, ll_approved_bills;
    LinearLayout ll_my_bills, ll_pending_bills, ll_all_leave_reqs;
    LinearLayout ll_leaves_left, ll_all_leaves, ll_approveleave, ll_check_wallet_balance;
    LinearLayout ll_add_money, ll_req_advance, ll_approve_advance, ll_vendorform, ll_vendordetails;
    TextView tv_name, tv_money;
    PieChart pie_chart;
    private String[] tags = {"Completed", "Pending"};

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
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
        AppConstant.currentFragmentTag = HomeFragment.class.getSimpleName();
        AppConstant.updated = this;
        preferenceUtils = new PreferenceUtils(getContext());
        customProgressLoader = new CustomProgressLoader(getContext());
        View view = inflater.inflate(R.layout.home_fragments, container, false);
        initViewController(view);
        return view;
    }

    private void initViewController(View view) {
        ll_vendorform = (LinearLayout) view.findViewById(R.id.ll_vendorform);
        ll_vendordetails = (LinearLayout) view.findViewById(R.id.ll_vendordetails);
        ll_my_bills = (LinearLayout) view.findViewById(R.id.ll_my_bills);
        ll_pending_bills = (LinearLayout) view.findViewById(R.id.ll_pending_bills);
        ll_all_leave_reqs = (LinearLayout) view.findViewById(R.id.ll_all_leave_reqs);
        ll_leaves_left = (LinearLayout) view.findViewById(R.id.ll_leaves_left);
        ll_all_leaves = (LinearLayout) view.findViewById(R.id.ll_all_leaves);
        ll_approveleave = (LinearLayout) view.findViewById(R.id.ll_approveleave);
        ll_check_wallet_balance = (LinearLayout) view.findViewById(R.id.ll_check_wallet_balance);
        ll_add_money = (LinearLayout) view.findViewById(R.id.ll_add_money);
        ll_req_advance = (LinearLayout) view.findViewById(R.id.ll_req_advance);
        ll_approve_advance = (LinearLayout) view.findViewById(R.id.ll_approve_advance);
        pie_chart = (PieChart) view.findViewById(R.id.pie_chart);
        ll_apply_leave = (LinearLayout) view.findViewById(R.id.ll_apply_leave);
        ll_approved_bills = (LinearLayout) view.findViewById(R.id.ll_approved_bills);
        ll_submit_bill = (LinearLayout) view.findViewById(R.id.ll_submit_bill);
        tv_money = (TextView) view.findViewById(R.id.tv_money);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_name.setText(preferenceUtils.getUserName());
        adjustUiBasedOnRole();
        ll_submit_bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.ll_fragment, NewBillFragment.newInstance())
                        .commit();
            }
        });

        ll_vendorform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.ll_fragment, VendorEnqFormFragment.newInstance())
                        .commit();
            }
        });
        ll_apply_leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.ll_fragment, RequestLeaveFragment.newInstance())
                        .commit();
            }
        });

        ll_my_bills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((DashBoardActivity) getActivity()).gotoBillsActivity();
            }
        });
        ll_all_leave_reqs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((DashBoardActivity) getActivity()).gotoLeavesListActivity();
            }
        });
        ll_pending_bills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((DashBoardActivity) getActivity()).getBillsListActivity(AppConstant.PENDINGSTATUS, AppConstant.BILLS);
            }
        });

        ll_approved_bills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((DashBoardActivity) getActivity()).getBillsListActivity(AppConstant.APPROVEDSTATUS, AppConstant.BILLS);
            }
        });

        ll_all_leaves.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((DashBoardActivity) getActivity()).getBillsListActivity(AppConstant.PENDINGSTATUS, AppConstant.LEAVES);
            }
        });
        ll_approveleave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((DashBoardActivity) getActivity()).getBillsListActivity(AppConstant.APPROVEDSTATUS, AppConstant.LEAVES);
            }
        });
        ll_add_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.ll_fragment, AddMoneyFragment.newInstance())
                        .commit();
            }
        });
        ll_check_wallet_balance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWalletBalance();
            }
        });
        ll_vendordetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((DashBoardActivity) getActivity()).gotoAllVendorReqs(AppConstant.VENDORIN_PROGRSS);
            }
        });
        ll_req_advance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.ll_fragment, MoneyRequestFragment.newInstance())
                        .commit();
            }
        });
        ll_approve_advance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((DashBoardActivity) getActivity()).gotoMoneyRequestList(AppConstant.PENDINGSTATUS);
            }
        });
        ll_leaves_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLeaveBalance();
            }
        });
        pie_chart.setUsePercentValues(true);
        pie_chart.getDescription().setEnabled(false);
        pie_chart.setExtraOffsets(5, 10, 5, 5);
        pie_chart.setDragDecelerationFrictionCoef(0.95f);
        pie_chart.setDrawHoleEnabled(false);
        pie_chart.setTransparentCircleColor(Color.WHITE);
        pie_chart.setTransparentCircleAlpha(110);

        pie_chart.setTransparentCircleRadius(61f);

        pie_chart.setDrawCenterText(true);

        pie_chart.setRotationAngle(0);
        // enable rotation of the chart by touch
        pie_chart.setRotationEnabled(true);
        pie_chart.setHighlightPerTapEnabled(true);

        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener
        pie_chart.setOnChartValueSelectedListener(this);

        //setData(2, 100);

        pie_chart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // mChart.spin(2000, 0, 360);


        Legend l = pie_chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // entry label styling
        pie_chart.setEntryLabelColor(Color.WHITE);
        pie_chart.setEntryLabelTextSize(12f);
    }

    private void getWalletBalance() {
        if (new CommonBL(this, getContext()).getWalletBalance(creatUrlWalletBalance())) {
            customProgressLoader.showProgressDialog();
        }
    }

    private String creatUrlWalletBalance() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ServiceURL.getRequestUrl(ServiceMethods.WS_APP_GET_WALLET_BAL));
        stringBuilder.append(preferenceUtils.getUserMobile());

        return stringBuilder.toString();
    }

    private void getLeaveBalance() {
        if (new CommonBL(this, getContext()).getLeavesBalance(creatUrlLeaveBalance())) {
            customProgressLoader.showProgressDialog();
        }
    }

    private String creatUrlLeaveBalance() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ServiceURL.getRequestUrl(ServiceMethods.WS_APP_GET_LEAVE_BALANCE));
        stringBuilder.append("/");
        stringBuilder.append(preferenceUtils.getUserMobile());

        return stringBuilder.toString();
    }

    private void adjustUiBasedOnRole() {
        if (preferenceUtils.getUserType().equalsIgnoreCase(AppConstant.ROLEMANAGER)) {
            ll_submit_bill.setVisibility(View.VISIBLE);
            ll_apply_leave.setVisibility(View.VISIBLE);
            ll_approved_bills.setVisibility(View.VISIBLE);
            ll_my_bills.setVisibility(View.VISIBLE);
            ll_pending_bills.setVisibility(View.VISIBLE);
            ll_all_leave_reqs.setVisibility(View.VISIBLE);
            ll_leaves_left.setVisibility(View.VISIBLE);
            ll_all_leaves.setVisibility(View.VISIBLE);
            ll_approveleave.setVisibility(View.VISIBLE);
            ll_check_wallet_balance.setVisibility(View.VISIBLE);
            ll_add_money.setVisibility(View.VISIBLE);
            ll_req_advance.setVisibility(View.VISIBLE);
            ll_approve_advance.setVisibility(View.VISIBLE);
            ll_vendorform.setVisibility(View.GONE);
            ll_vendordetails.setVisibility(View.VISIBLE);
        } else if (preferenceUtils.getUserType().equalsIgnoreCase(AppConstant.ROLEEMPLOYEE)) {
            ll_submit_bill.setVisibility(View.VISIBLE);
            ll_apply_leave.setVisibility(View.VISIBLE);
            ll_approved_bills.setVisibility(View.GONE);
            ll_my_bills.setVisibility(View.VISIBLE);
            ll_pending_bills.setVisibility(View.GONE);
            ll_all_leave_reqs.setVisibility(View.VISIBLE);
            ll_leaves_left.setVisibility(View.VISIBLE);
            ll_all_leaves.setVisibility(View.GONE);
            ll_approveleave.setVisibility(View.GONE);
            ll_check_wallet_balance.setVisibility(View.VISIBLE);
            ll_add_money.setVisibility(View.GONE);
            ll_req_advance.setVisibility(View.VISIBLE);
            ll_approve_advance.setVisibility(View.GONE);
            ll_vendorform.setVisibility(View.VISIBLE);
            ll_vendordetails.setVisibility(View.GONE);
        } else if (preferenceUtils.getUserType().equalsIgnoreCase(AppConstant.ROLEVENDOR)) {
            ll_submit_bill.setVisibility(View.GONE);
            ll_apply_leave.setVisibility(View.GONE);
            ll_approved_bills.setVisibility(View.GONE);
            ll_my_bills.setVisibility(View.GONE);
            ll_pending_bills.setVisibility(View.GONE);
            ll_all_leave_reqs.setVisibility(View.GONE);
            ll_leaves_left.setVisibility(View.GONE);
            ll_all_leaves.setVisibility(View.GONE);
            ll_approveleave.setVisibility(View.GONE);
            ll_check_wallet_balance.setVisibility(View.GONE);
            ll_add_money.setVisibility(View.GONE);
            ll_req_advance.setVisibility(View.GONE);
            ll_approve_advance.setVisibility(View.GONE);
            ll_vendorform.setVisibility(View.GONE);
            ll_vendordetails.setVisibility(View.GONE);
            if (AppConstant.accessUpdated)
                getDashBoardStates();


        }
    }

    private void getDashBoardStates() {
        if (new CommonBL(this, getContext()).getDasBoardStates(createUrl())) {
            //customProgressLoader.showProgressDialog();
        }
    }

    private String createUrl() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ServiceURL.getRequestUrl(ServiceMethods.WS_APP_DASHBOARDSTATE));
        stringBuilder.append(preferenceUtils.getUserId());
        return stringBuilder.toString();
    }

    private void setData(String s) {
        ArrayList<PieEntry> entries = new ArrayList<>();
        PieDataSet dataSet = null;
        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        if (s.equalsIgnoreCase("Products")) {
            for (int i = 0; i < AppConstant.dashBoardStatesDomain.getProductChartData().getData().size(); i++) {
                entries.add(new PieEntry(Float.parseFloat(AppConstant.dashBoardStatesDomain.getProductChartData().getData().get(i)),
                        AppConstant.dashBoardStatesDomain.getProductChartData().getLabels().get(i)));
            }
            dataSet = new PieDataSet(entries, s);
        } else if (s.equalsIgnoreCase("Product Combo")) {
            for (int i = 0; i < AppConstant.dashBoardStatesDomain.getProductComboChartData().getData().size(); i++) {
                entries.add(new PieEntry(Float.parseFloat(AppConstant.dashBoardStatesDomain.getProductComboChartData().getData().get(i)),
                        AppConstant.dashBoardStatesDomain.getProductComboChartData().getLabels().get(i)));
            }
            dataSet = new PieDataSet(entries, s);
        }
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        // add a lot of colors
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        pie_chart.setData(data);

        // undo all highlights
        pie_chart.highlightValues(null);

        pie_chart.invalidate();
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
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    @Override
    public void dataRetreived(Response data) {
        if (data.servicemethod.equalsIgnoreCase(ServiceMethods.WS_APP_GET_LEAVE_BALANCE)) {
            if (!data.isError) {
                customProgressLoader.dismissProgressDialog();
                openDialogforLeaveBalance((LeaveBalanceDomain) data.data);
            }
        } else if (data.servicemethod.equalsIgnoreCase(ServiceMethods.WS_APP_GET_WALLET_BAL)) {
            if (!data.isError) {
                customProgressLoader.dismissProgressDialog();

                WalletDomain walletDomain = (WalletDomain) data.data;
                openDialogtoDisplayAmount(walletDomain);
            }
        } else if (data.servicemethod.equalsIgnoreCase(ServiceMethods.WS_APP_DASHBOARDSTATE)) {
            if (!data.isError) {
                AppConstant.dashBoardStatesDomain = (DashBoardStatesDomain) data.data;
                customProgressLoader.dismissProgressDialog();
                setData("Products");
            }
        }
    }

    private void openDialogtoDisplayAmount(WalletDomain walletDomain) {
        TextView tv_wallet_balance;
        Button btn_ok;
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_wallet_balance, null);
        builder.setView(view);

        tv_wallet_balance = (TextView) view.findViewById(R.id.tv_wallet_balance);
        btn_ok = (Button) view.findViewById(R.id.btn_ok);

        tv_wallet_balance.setText(String.valueOf(walletDomain.getBalance()));
        builder.setTitle("Wallet Balance Sheet");
        final AlertDialog alertDialog = builder.create();
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    private void openDialogforLeaveBalance(LeaveBalanceDomain leaveBalanceDomain) {
        final AlertDialog alertDialog;
        TextView tv_leaves_left, tv_leaves_availed, tv_total_leaves;
        Button btn_ok;
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_leaves_balance, null);
        builder.setView(view);

        btn_ok = (Button) view.findViewById(R.id.btn_ok);
        tv_leaves_availed = (TextView) view.findViewById(R.id.tv_leaves_availed);
        tv_leaves_left = (TextView) view.findViewById(R.id.tv_leaves_left);
        tv_total_leaves = (TextView) view.findViewById(R.id.tv_total_leaves);

        tv_leaves_availed.setText(String.valueOf(leaveBalanceDomain.getLeavesAvailed()));
        tv_leaves_left.setText(String.valueOf(leaveBalanceDomain.getLeavesLeft()));
        tv_total_leaves.setText(String.valueOf(leaveBalanceDomain.getTotalLeaves()));
        builder.setTitle("Leave Balance Sheet");
        alertDialog = builder.create();
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();

    }

    @Override
    public void opennetworksetting() {

    }


    @Override
    public void isUpdated() {
        getDashBoardStates();
    }

    public void updatePieChart(String s) {

        setData(s);

    }
}
