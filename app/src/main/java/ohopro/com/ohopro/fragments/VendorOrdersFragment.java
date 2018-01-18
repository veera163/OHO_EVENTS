package ohopro.com.ohopro.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ohopro.com.ohopro.R;
import ohopro.com.ohopro.activity.VendorOrdersActivity;
import ohopro.com.ohopro.activity.VendorProductAvailbility;
import ohopro.com.ohopro.adapter.OrderAdapter;
import ohopro.com.ohopro.appserviceurl.ServiceURL;
import ohopro.com.ohopro.busnesslayer.CommonBL;
import ohopro.com.ohopro.busnesslayer.DataListener;
import ohopro.com.ohopro.domains.ErrorDomain;
import ohopro.com.ohopro.domains.OrderDomain;
import ohopro.com.ohopro.utility.AppConstant;
import ohopro.com.ohopro.utility.CustomAlertDialogSimple;
import ohopro.com.ohopro.utility.PreferenceUtils;
import ohopro.com.ohopro.views.CustomProgressLoader;
import ohopro.com.ohopro.webaccess.Response;
import ohopro.com.ohopro.webaccess.ServiceMethods;

/**
 * Created by sai on 19-11-2017.
 */

public class VendorOrdersFragment extends Fragment implements VendorOrdersActivity.OrdersUpdate, DataListener {
    private static String TYPE = "type";
    private String type;
    private PreferenceUtils preferenceUtils;
    private CustomProgressLoader customProgressLoader;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    ArrayList<OrderDomain> orderDomains;
    OrderAdapter orderAdapter;

    RecyclerView rlst_bills;
    TextView tv_notifications;

    public static VendorOrdersFragment newInstance(String type) {
        VendorOrdersFragment vendorOrdersFragment = new VendorOrdersFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TYPE, type);
        vendorOrdersFragment.setArguments(bundle);
        return vendorOrdersFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getString(TYPE);
        preferenceUtils = new PreferenceUtils(getContext());
        customProgressLoader = new CustomProgressLoader(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        orderDomains = new ArrayList<>();
        orderAdapter = new OrderAdapter(orderDomains);
        View view = inflater.inflate(R.layout.fragment_list_of_bills, container, false);
        initViewController(view);
        update();
        return view;
    }

    private void initViewController(View view) {
        tv_notifications = (TextView) view.findViewById(R.id.tv_notifications);

      /*  mSwipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.hcontainer);
        mSwipeRefreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW);
        mSwipeRefreshLayout.setOnRefreshListener(new    SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getContext(), "Refresh", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getOnGoingOrders();
                        getCompletedOrders();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });*/
        rlst_bills = (RecyclerView) view.findViewById(R.id.rlst_bills);
        rlst_bills.setLayoutManager(new LinearLayoutManager(getContext()));
        rlst_bills.setAdapter(orderAdapter);

        orderAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                if (orderDomains.size() == 0) {
                    tv_notifications.setVisibility(View.VISIBLE);
                    rlst_bills.setVisibility(View.GONE);
                    tv_notifications.setText("No Orders Found");
                } else {
                    rlst_bills.setVisibility(View.VISIBLE);
                    tv_notifications.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void update() {
        if (type.equalsIgnoreCase(AppConstant.VENDORONGOINGORDER))

            getOnGoingOrders();
        else
            getCompletedOrders();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && getContext() != null) {
            update();
        }
    }

    private void getOnGoingOrders() {
        String url = ServiceURL.getRequestUrl(ServiceMethods.WS_APP_VENDOR_ONGOING_ORDERS);
        url = url.concat(preferenceUtils.getUserId());
        Log.e("url",url);
        if (new CommonBL(this, getContext()).getAllOrder(ServiceMethods.WS_APP_VENDOR_ONGOING_ORDERS, url)) {
            customProgressLoader.showProgressDialog();
        }
    }

    private void getCompletedOrders() {
        String url = ServiceURL.getRequestUrl(ServiceMethods.WS_APP_VENDOR_COMPLETED_ORDERS);
        url = url.concat(preferenceUtils.getUserId());
        Log.e("url",url);
        if (new CommonBL(this, getContext()).getAllOrder(ServiceMethods.WS_APP_VENDOR_COMPLETED_ORDERS, url)) {
            customProgressLoader.showProgressDialog();
        }
    }


    @Override
    public void dataRetreived(Response data) {

        if (data.servicemethod.equalsIgnoreCase(ServiceMethods.WS_APP_VENDOR_ONGOING_ORDERS) ||
                data.servicemethod.equalsIgnoreCase(ServiceMethods.WS_APP_VENDOR_COMPLETED_ORDERS)) {
            if (!data.isError) {
                orderDomains.clear();
                customProgressLoader.dismissProgressDialog();
                if (data.data instanceof ArrayList) {
                    orderDomains.addAll((ArrayList<OrderDomain>) data.data);
                    orderAdapter.notifyDataSetChanged();
                } else {
                    ErrorDomain errorDomain = (ErrorDomain) data.data;
                    new CustomAlertDialogSimple(getContext()).showAlertDialog(errorDomain.getError_description());
                }
            }
        }
    }

    @Override
    public void opennetworksetting() {

    }
}
