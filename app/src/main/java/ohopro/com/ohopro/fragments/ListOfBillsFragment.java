package ohopro.com.ohopro.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import ohopro.com.ohopro.R;
import ohopro.com.ohopro.adapter.BillsAdapter;
import ohopro.com.ohopro.appserviceurl.ServiceURL;
import ohopro.com.ohopro.busnesslayer.CommonBL;
import ohopro.com.ohopro.busnesslayer.DataListener;
import ohopro.com.ohopro.domains.BillDomain;
import ohopro.com.ohopro.domains.ErrorDomain;
import ohopro.com.ohopro.utility.CustomAlertDialogSimple;
import ohopro.com.ohopro.utility.PreferenceUtils;
import ohopro.com.ohopro.views.CustomProgressLoader;
import ohopro.com.ohopro.webaccess.Response;
import ohopro.com.ohopro.webaccess.ServiceMethods;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ListOfBillsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListOfBillsFragment extends Fragment
        implements DataListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TYPEOFBILLS = "param1";
    BillsSelectionListener billsSelectionListener;

    ArrayList<BillDomain> billDomains;
    BillsAdapter billsAdapter;
    LinearLayout ll_parent;
    CustomProgressLoader customProgressLoader;
    // TODO: Rename and change types of parameters
    private String typeofBills;

    RecyclerView rlst_bills;
    PreferenceUtils preferenceUtils;

    public ListOfBillsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ListOfBillsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListOfBillsFragment newInstance(String typeofBills) {
        ListOfBillsFragment fragment = new ListOfBillsFragment();
        Bundle args = new Bundle();
        args.putString(TYPEOFBILLS, typeofBills);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            typeofBills = getArguments().getString(TYPEOFBILLS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        preferenceUtils = new PreferenceUtils(getContext());
        customProgressLoader = new CustomProgressLoader(getContext());
        View view = inflater.inflate(R.layout.fragment_list_of_bills, container, false);
        initViewController(view);
        getUserBillsBasedOnStatus();
        return view;
    }

    private void getUserBillsBasedOnStatus() {
        if (new CommonBL(this, getContext()).getBills(
                ServiceURL.getRequestUrl(ServiceMethods.WS_APP_GET_BILLS),
                typeofBills,
                preferenceUtils.getUserMobile()
        )) {
            customProgressLoader.showProgressDialog();
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        Toast.makeText(getContext(), typeofBills, Toast.LENGTH_SHORT).show();

    }

    private void initViewController(View view) {
        ll_parent = (LinearLayout) view.findViewById(R.id.ll_parent);
        rlst_bills = (RecyclerView) view.findViewById(R.id.rlst_bills);
        rlst_bills.setLayoutManager(new LinearLayoutManager(getContext()));
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof BillsSelectionListener)
            billsSelectionListener = (BillsSelectionListener) context;

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void dataRetreived(Response data) {
        if (data.servicemethod.equalsIgnoreCase(ServiceMethods.WS_APP_GET_BILLS)) {
            customProgressLoader.dismissProgressDialog();
            if (!data.isError) {
                if (data.data instanceof ArrayList) {
                    billDomains = (ArrayList<BillDomain>) data.data;
                    billsAdapter = new BillsAdapter(billDomains);
                    billsAdapter.registerAListener(new BillsSelectionListener() {
                        @Override
                        public void onBillSelected(BillDomain billDomain) {
                            ListOfBillsFragment.this.billsSelectionListener.onBillSelected(billDomain);
                        }
                    });
                    rlst_bills.setAdapter(billsAdapter);
                } else if (data.data instanceof ErrorDomain) {
                    new CustomAlertDialogSimple(getContext()).showAlertDialog(((ErrorDomain) data.data).getError_description());
                }
            }
        }
    }

    @Override
    public void opennetworksetting() {

    }


    public interface BillsSelectionListener {
        void onBillSelected(BillDomain billDomain);
    }
}
