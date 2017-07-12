package ohopro.com.ohopro.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ohopro.com.ohopro.R;
import ohopro.com.ohopro.appserviceurl.ServiceURL;
import ohopro.com.ohopro.busnesslayer.CommonBL;
import ohopro.com.ohopro.busnesslayer.DataListener;
import ohopro.com.ohopro.domains.ErrorDomain;
import ohopro.com.ohopro.utility.CustomAlertDialog;
import ohopro.com.ohopro.utility.CustomAlertDialogSimple;
import ohopro.com.ohopro.views.CustomProgressLoader;
import ohopro.com.ohopro.webaccess.Response;
import ohopro.com.ohopro.webaccess.ServiceMethods;

public class AddMoneyFragment extends Fragment
        implements DataListener, CustomAlertDialog.DialogController {

    EditText edt_contact_num, edt_amount;
    Button btn_add_money, btn_back;
    CustomProgressLoader customProgressLoader;
    CustomAlertDialog customAlertDialog;
    private String NORMALMESSAGE = "normalMessage";

    public AddMoneyFragment() {
        // Required empty public constructor
    }


    public static AddMoneyFragment newInstance() {
        AddMoneyFragment fragment = new AddMoneyFragment();
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
        customProgressLoader = new CustomProgressLoader(getContext());
        customAlertDialog = new CustomAlertDialog();
        View view = inflater.inflate(R.layout.fragment_add_money, container, false);
        initViewController(view);
        return view;
    }

    private void initViewController(View view) {
        edt_amount = (EditText) view.findViewById(R.id.edt_amount);
        edt_contact_num = (EditText) view.findViewById(R.id.edt_contact_num);
        btn_add_money = (Button) view.findViewById(R.id.btn_add_money);
        btn_back = (Button) view.findViewById(R.id.btn_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.ll_fragment, HomeFragment.newInstance()).commit();
            }
        });

        btn_add_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edt_amount.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getContext(), "Enter Amount", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edt_contact_num.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getContext(), "Enter Employee mobileNumber", Toast.LENGTH_SHORT).show();
                    return;
                }

                addMoneyToEmployee();
            }
        });
    }

    private void addMoneyToEmployee() {
        if (new CommonBL(this, getContext()).addMoneyToWallet(
                ServiceURL.getRequestUrl(ServiceMethods.WS_APP_ADD_MONEY),
                edt_amount.getText().toString(),
                edt_contact_num.getText().toString()
        )) {
            customProgressLoader.showProgressDialog();
        }
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
    public void dataRetreived(Response data) {
        if (data.servicemethod.equalsIgnoreCase(ServiceMethods.WS_APP_ADD_MONEY)) {
            customProgressLoader.dismissProgressDialog();
            if (!data.isError) {
                if (data.data instanceof Integer) {
                    int statuscode = (int) data.data;
                    if (statuscode == 200)
                        getFragmentManager().beginTransaction().replace(R.id.ll_fragment, HomeFragment.newInstance()).commit();
                } else if (data.data instanceof ErrorDomain) {
                    new CustomAlertDialogSimple(getContext()).showAlertDialog(((ErrorDomain) data.data).getError_description());
                }

            }
        }
    }

    @Override
    public void opennetworksetting() {

    }

    @Override
    public void clickonPositive(String tag) {
        customAlertDialog.dismiss();
    }

    @Override
    public void clickonNegative(String tag) {
        customAlertDialog.dismiss();
    }
}
