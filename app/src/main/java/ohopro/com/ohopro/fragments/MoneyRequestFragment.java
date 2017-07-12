package ohopro.com.ohopro.fragments;

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
import android.widget.EditText;
import android.widget.Toast;

import ohopro.com.ohopro.R;
import ohopro.com.ohopro.appserviceurl.ServiceURL;
import ohopro.com.ohopro.busnesslayer.CommonBL;
import ohopro.com.ohopro.busnesslayer.DataListener;
import ohopro.com.ohopro.domains.ErrorDomain;
import ohopro.com.ohopro.utility.CustomAlertDialogSimple;
import ohopro.com.ohopro.utility.PreferenceUtils;
import ohopro.com.ohopro.views.CustomProgressLoader;
import ohopro.com.ohopro.webaccess.Response;
import ohopro.com.ohopro.webaccess.ServiceMethods;

public class MoneyRequestFragment extends Fragment
        implements DataListener {

    EditText edt_contact_num, edt_amount;
    Button btn_add_money, btn_back;
    CustomProgressLoader customProgressLoader;
    PreferenceUtils preferenceUtils;

    public MoneyRequestFragment() {
        // Required empty public constructor
    }


    public static MoneyRequestFragment newInstance() {
        MoneyRequestFragment fragment = new MoneyRequestFragment();
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
        preferenceUtils = new PreferenceUtils(getContext());
        View view = inflater.inflate(R.layout.fragment_add_money, container, false);
        initViewController(view);
        return view;
    }

    private void initViewController(View view) {
        edt_amount = (EditText) view.findViewById(R.id.edt_amount);
        edt_contact_num = (EditText) view.findViewById(R.id.edt_contact_num);
        btn_add_money = (Button) view.findViewById(R.id.btn_add_money);
        btn_back = (Button) view.findViewById(R.id.btn_back);
        edt_contact_num.setVisibility(View.GONE);
        final Drawable upArrow = ContextCompat.getDrawable(getContext(), R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(ContextCompat.getColor(getContext(), R.color.gray), PorterDuff.Mode.SRC_ATOP);
        btn_back.setCompoundDrawablesWithIntrinsicBounds(upArrow, null, null, null);

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

                sendMoneyRequest();
            }
        });

    }

    private void sendMoneyRequest() {
        if (new CommonBL(this, getContext()).requestMoneyToWallet(
                ServiceURL.getRequestUrl(ServiceMethods.WS_APP_ADD_MONEY_REQ),
                edt_amount.getText().toString(),
                preferenceUtils.getUserMobile(),
                preferenceUtils.getUserName()

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
        if (data.servicemethod.equalsIgnoreCase(ServiceMethods.WS_APP_ADD_MONEY_REQ)) {
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
}
