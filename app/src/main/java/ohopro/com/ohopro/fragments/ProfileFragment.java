package ohopro.com.ohopro.fragments;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ohopro.com.ohopro.R;
import ohopro.com.ohopro.busnesslayer.DataListener;
import ohopro.com.ohopro.fragmentDialogs.ChangePasswordDialog;
import ohopro.com.ohopro.utility.AppConstant;
import ohopro.com.ohopro.utility.PreferenceUtils;
import ohopro.com.ohopro.webaccess.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment implements DataListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    EditText edt_name, edt_email, edt_mobile_number;

    PreferenceUtils preferenceUtils;
    Context context;

    LinearLayout ll_edit, ll_password;
    ImageView img_profile;

    TextView tv_type;

    ChangePasswordDialog changePasswordDialog;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        AppConstant.currentFragmentTag = ProfileFragment.class.getSimpleName();
        context = getContext();
        changePasswordDialog = new ChangePasswordDialog();
        changePasswordDialog.setCancelable(false);
        preferenceUtils = new PreferenceUtils(context);

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        initViewController(view);

        ll_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tv_type.getText().toString().equalsIgnoreCase("edit"))
                    gotoClickEditMethod();
                else
                    gotoClickOnSaveMethod();
            }
        });

        ll_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPopUpChangepwd();
            }
        });
        return view;
    }

    private void openPopUpChangepwd() {
        changePasswordDialog.show(getFragmentManager(), "dialog");
    }

    private void gotoClickOnSaveMethod() {
        tv_type.setText("edit");
        edt_name.setEnabled(false);
        /*if (new CommonBL(this, context).updateUserInfo(
                ServiceURL.getRequestUrl(ServiceMethods.WS_APP_UPDATEPROFILE),
                edt_name.getText().toString(),
                (String) edt_name.getTag(),
                preferenceUtils.getUserEmail())) {

        }*/

    }

    private void gotoClickEditMethod() {
        tv_type.setText("save");
        edt_name.setEnabled(true);
    }

    private void initViewController(View view) {
        tv_type = (TextView) view.findViewById(R.id.tv_type);
        ll_edit = (LinearLayout) view.findViewById(R.id.ll_edit);
        img_profile = (ImageView) view.findViewById(R.id.img_profile);
        ll_password = (LinearLayout) view.findViewById(R.id.ll_password);
        edt_email = (EditText) view.findViewById(R.id.edt_email);
        edt_name = (EditText) view.findViewById(R.id.edt_name);
        edt_mobile_number = (EditText) view.findViewById(R.id.edt_mobile_number);
        final Drawable upArrow = ContextCompat.getDrawable(getContext(), R.drawable.ic_account_circle_black_24dp);
        upArrow.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        img_profile.setImageDrawable(upArrow);
        edt_email.setEnabled(false);
        edt_mobile_number.setEnabled(false);
        edt_name.setEnabled(false);

        updateUserInfo();
    }

    private void updateUserInfo() {
        edt_email.setText(preferenceUtils.getUserEmail());
        edt_name.setText(preferenceUtils.getUserName());
        edt_mobile_number.setText(preferenceUtils.getUserMobile());
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
       /* if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/


    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void dataRetreived(Response data) {

    }

    @Override
    public void opennetworksetting() {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
