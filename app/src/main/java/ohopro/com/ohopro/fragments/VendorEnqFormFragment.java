package ohopro.com.ohopro.fragments;


import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Pattern;

import ohopro.com.ohopro.R;
import ohopro.com.ohopro.appserviceurl.ServiceURL;
import ohopro.com.ohopro.busnesslayer.CommonBL;
import ohopro.com.ohopro.busnesslayer.DataListener;
import ohopro.com.ohopro.domains.ServiceGroupDomain;
import ohopro.com.ohopro.domains.ServicesDomain;
import ohopro.com.ohopro.domains.VendorEnquaryDomain;
import ohopro.com.ohopro.utility.AppConstant;
import ohopro.com.ohopro.utility.CurrentDate;
import ohopro.com.ohopro.utility.PreferenceUtils;
import ohopro.com.ohopro.views.CustomProgressLoader;
import ohopro.com.ohopro.webaccess.Response;
import ohopro.com.ohopro.webaccess.ServiceMethods;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VendorEnqFormFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VendorEnqFormFragment extends Fragment
        implements DataListener, PlaceSelectionListener, DatePickerDialog.OnDateSetListener {
    EditText edt_first_name, edt_surname, edt_designation, edt_company_name;
    EditText edt_email, edt_phone_number, edt_address_line_1, edt_address_line_2;
    EditText edt_town, edt_city, edt_postal_code, edt_country, edt_details_of_enquary;
    TextView tv_service_group, tv_service_type, tv_service, tv_enquiry, tv_follow_update, tv_location, edt_tittle;
    Button btn_back, btn_submit_form;
    LinearLayout ll_parent;
    CustomProgressLoader customProgressLoader;
    PreferenceUtils preferenceUtils;
    AlertDialog.Builder builder;
    ArrayList<ServiceGroupDomain> serviceGroupDomains;
    ArrayList<ServicesDomain> serviceDomains;
    PlaceAutocompleteFragment placeAutocompleteFragment;
    private CharSequence[] serviceGroupStrings;
    private CharSequence[] serviceType = new CharSequence[]{"Service Sale Item", "Service Rent Item" +
            ""};
    private CharSequence[] enquiryType = new CharSequence[]{"On field", "Tele Call", "Email", "Seller Called", "In office", "Referal", "Others"};
    private CharSequence[] tittles = new CharSequence[]{"Mr.", "Mrs.", "Ms.", "Others"};
    private CharSequence[] serviceStrings;
    private EditText edt_website;
    private DatePickerDialog datePickerDialog;

    public VendorEnqFormFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static VendorEnqFormFragment newInstance() {
        VendorEnqFormFragment fragment = new VendorEnqFormFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customProgressLoader = new CustomProgressLoader(getContext());
        AppConstant.currentFragmentTag = VendorEnqFormFragment.class.getSimpleName();
        getAllServiceCatogiries();
        if (getArguments() != null) {
        }
    }

    private void getAllServiceCatogiries() {
        if (new CommonBL(this, getContext()).getAllVendorServiceCatogories(
                ServiceURL.getRequestUrl(ServiceMethods.WS_LIST_SERVICECATOGIIES)
        )) {
            customProgressLoader.showProgressDialog();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        preferenceUtils = new PreferenceUtils(getContext());
        builder = new AlertDialog.Builder(getContext());
        Calendar calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(getContext(), this, calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        View view = inflater.inflate(R.layout.vendor_enq_form, container, false);

        initViewController(view);
        return view;
    }

    private void initViewController(View view) {
        ll_parent = (LinearLayout) view.findViewById(R.id.ll_parent);
        btn_back = (Button) view.findViewById(R.id.btn_back);
        btn_submit_form = (Button) view.findViewById(R.id.btn_submit_form);
        edt_tittle = (TextView) view.findViewById(R.id.edt_tittle);
        edt_first_name = (EditText) view.findViewById(R.id.edt_first_name);
        edt_surname = (EditText) view.findViewById(R.id.edt_surname);
        edt_designation = (EditText) view.findViewById(R.id.edt_designation);
        edt_company_name = (EditText) view.findViewById(R.id.edt_company_name);
        edt_email = (EditText) view.findViewById(R.id.edt_email);
        edt_phone_number = (EditText) view.findViewById(R.id.edt_phone_number);
        edt_address_line_1 = (EditText) view.findViewById(R.id.edt_address_line_1);
        edt_address_line_2 = (EditText) view.findViewById(R.id.edt_address_line_2);
        edt_town = (EditText) view.findViewById(R.id.edt_town);
        edt_website = (EditText) view.findViewById(R.id.edt_website);
        edt_city = (EditText) view.findViewById(R.id.edt_city);
        edt_postal_code = (EditText) view.findViewById(R.id.edt_postal_code);
        edt_country = (EditText) view.findViewById(R.id.edt_country);
        edt_details_of_enquary = (EditText) view.findViewById(R.id.edt_details_of_enquary);
        tv_service_group = (TextView) view.findViewById(R.id.tv_service_group);
        tv_service_type = (TextView) view.findViewById(R.id.tv_service_type);
        tv_service = (TextView) view.findViewById(R.id.tv_service);
        tv_location = (TextView) view.findViewById(R.id.tv_location);
        tv_follow_update = (TextView) view.findViewById(R.id.tv_follow_update);
        tv_enquiry = (TextView) view.findViewById(R.id.tv_enquiry);

        edt_first_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    edt_surname.requestFocus();
                    edt_first_name.clearFocus();
                }
            }
        });
        edt_surname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    edt_designation.requestFocus();
                    edt_surname.clearFocus();
                }
            }
        });
        edt_designation.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b)
                    edt_company_name.requestFocus();
            }
        });
        edt_company_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b)
                    edt_email.requestFocus();
            }
        });
        edt_email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b)
                    edt_website.requestFocus();
            }
        });
        edt_website.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b)
                    edt_phone_number.requestFocus();
            }
        });
        edt_phone_number.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b)
                    edt_address_line_1.requestFocus();
            }
        });
        edt_address_line_1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b)
                    edt_address_line_2.requestFocus();
            }
        });
        edt_address_line_2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b)
                    edt_town.requestFocus();
            }
        });
        edt_town.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b)
                    edt_city.requestFocus();
            }
        });
        edt_city.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b)
                    edt_postal_code.requestFocus();
            }
        });
        edt_postal_code.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b)
                    edt_country.requestFocus();
            }
        });
        edt_country.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                edt_details_of_enquary.requestFocus();
            }
        });
        final Drawable upArrow = ContextCompat.getDrawable(getContext(), R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(ContextCompat.getColor(getContext(), R.color.gray), PorterDuff.Mode.SRC_ATOP);
        btn_back.setCompoundDrawablesWithIntrinsicBounds(upArrow, null, null, null);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.ll_fragment, HomeFragment.newInstance())
                        .commit();
            }
        });

        tv_service_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (serviceGroupStrings.length > 0)
                    setArrayToDialog(serviceGroupStrings, "Service Group", tv_service_group);
                else
                    Toast.makeText(getContext(), "No Service Groups", Toast.LENGTH_SHORT).show();
            }
        });

        tv_enquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setArrayToDialog(enquiryType, "Enquiry Type", tv_enquiry);

            }
        });
        edt_tittle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setArrayToDialog(tittles, "Tittle", edt_tittle);
            }
        });
        tv_follow_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });

        tv_service_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (serviceType.length > 0)
                    setArrayToDialog(serviceType, "Service Type", tv_service_type);
                else
                    Toast.makeText(getContext(), "No Service Groups", Toast.LENGTH_SHORT).show();
            }
        });
        tv_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tv_service_type.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getContext(), "Select your Service Type", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (tv_service_group.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getContext(), "Select your Service Group", Toast.LENGTH_SHORT).show();
                    return;
                }

                getServices(tv_service_group.getText().toString(), tv_service_type.getText().toString());
            }
        });
        tv_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                placeAutocompleteFragment = new PlaceAutocompleteFragment();
               /* getFragmentManager()
                        .beginTransaction()
                        .remove(VendorEnqFormFragment.this)
                        .commit();*/
                ll_parent.setVisibility(View.GONE);
                getActivity().getFragmentManager()
                        .beginTransaction()
                        .add(R.id.ll_fragment, placeAutocompleteFragment, "place")
                        .commit();
                placeAutocompleteFragment.setOnPlaceSelectedListener(VendorEnqFormFragment.this);
            }
        });
        btn_submit_form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkForm();
            }
        });
    }

    private void getServices(String serviceGroups, String serviceTypes) {
        StringBuilder reqUrl = new StringBuilder();
        reqUrl.append(ServiceURL.getRequestUrl(ServiceMethods.WS_APP_LIST_SERVICES));
        /*reqUrl.append(serviceGroups);
        reqUrl.append("/");
        reqUrl.append(serviceTypes);*/

        if (new CommonBL(this, getContext()).getServices(reqUrl.toString(), serviceGroups, serviceTypes)) {
            customProgressLoader.showProgressDialog();
        }
    }

    private void setArrayToDialog(final CharSequence[] charSequences, String tittle, final TextView textView) {
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

    private void checkForm() {
        if (edt_tittle.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(getContext(), "Enter Tittle", Toast.LENGTH_SHORT).show();
            return;
        }

        if (edt_first_name.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(getContext(), "Enter First Name", Toast.LENGTH_SHORT).show();
            return;
        }

        if (edt_surname.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(getContext(), "Enter Sur Name", Toast.LENGTH_SHORT).show();
            return;
        }

        if (edt_designation.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(getContext(), "Enter Designation", Toast.LENGTH_SHORT).show();
            return;
        }
        if (edt_company_name.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(getContext(), "Enter Company Name", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!validateEmail(edt_email.getText().toString())) {
            Toast.makeText(getContext(), "Enter Valid Email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!validateMobileNumber(edt_phone_number.getText().toString())) {
            Toast.makeText(getContext(), "Enter Valid Phone Number", Toast.LENGTH_SHORT).show();
            return;
        }

        if (edt_address_line_1.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(getContext(), "Enter Address Line 1", Toast.LENGTH_SHORT).show();
            return;
        }

        if (edt_address_line_2.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(getContext(), "Enter Address Line 2", Toast.LENGTH_SHORT).show();
            return;
        }

        if (edt_town.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(getContext(), "Enter Town", Toast.LENGTH_SHORT).show();
            return;
        }

        if (edt_city.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(getContext(), "Enter City", Toast.LENGTH_SHORT).show();
            return;
        }

        if (edt_postal_code.getText().toString().length() < 6) {
            Toast.makeText(getContext(), "Enter Valid Postal code", Toast.LENGTH_SHORT).show();
            return;
        }

        if (edt_country.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(getContext(), "Enter Country", Toast.LENGTH_SHORT).show();
            return;
        }
        if (tv_location.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(getContext(), "Enter Your Location", Toast.LENGTH_SHORT).show();
            return;
        }
        if (tv_service_type.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(getContext(), "Enter Your Service Type", Toast.LENGTH_SHORT).show();
            return;
        }
        if (tv_service.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(getContext(), "Enter Your Service", Toast.LENGTH_SHORT).show();
            return;
        }
        if (tv_service_group.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(getContext(), "Enter Your Service Group", Toast.LENGTH_SHORT).show();
            return;
        }
        updateinServer();
    }

    private void updateinServer() {
        VendorEnquaryDomain vendorEnquaryDomain = new VendorEnquaryDomain();
        vendorEnquaryDomain.setTitle(edt_tittle.getText().toString());
        vendorEnquaryDomain.setFirstName(edt_first_name.getText().toString());
        vendorEnquaryDomain.setSurname(edt_surname.getText().toString());
        vendorEnquaryDomain.setDesignation(edt_designation.getText().toString());
        vendorEnquaryDomain.setCompanyName(edt_company_name.getText().toString());
        vendorEnquaryDomain.setEmail(edt_email.getText().toString());
        vendorEnquaryDomain.setTelephone(edt_phone_number.getText().toString());
        vendorEnquaryDomain.setAddressLine1(edt_address_line_1.getText().toString());
        vendorEnquaryDomain.setAddressLine2(edt_address_line_2.getText().toString());
        vendorEnquaryDomain.setTown(edt_town.getText().toString());
        vendorEnquaryDomain.setCity(edt_city.getText().toString());
        vendorEnquaryDomain.setPostalCode(edt_postal_code.getText().toString());
        vendorEnquaryDomain.setCountry(edt_country.getText().toString());
        vendorEnquaryDomain.setDateOfEnquiry(new CurrentDate().currentDate);
        vendorEnquaryDomain.setEnquiredEmployee(preferenceUtils.getUserName());
        vendorEnquaryDomain.setEnquiredEmployeePhone(preferenceUtils.getUserMobile());
        vendorEnquaryDomain.setServiceOrSupplyLocations(tv_location.getText().toString());
        vendorEnquaryDomain.setProductOrServiceGroup(tv_service_group.getText().toString());
        vendorEnquaryDomain.setProductsOrServices(tv_service.getText().toString());
        vendorEnquaryDomain.setProductOrServiceType(tv_service_type.getText().toString());
        vendorEnquaryDomain.setFollowUpdate(tv_follow_update.getText().toString());
        vendorEnquaryDomain.setEnquiryType(tv_enquiry.getText().toString());
        vendorEnquaryDomain.setWebSite(edt_website.getText().toString());
        vendorEnquaryDomain.setDetailsEnquiry(edt_details_of_enquary.getText().toString());

        if (new CommonBL(this, getContext()).postVendorForm(
                ServiceURL.getRequestUrl(ServiceMethods.WS_APP_VENDOR_FORM),
                vendorEnquaryDomain
        )) {
            customProgressLoader.showProgressDialog();
        }
    }

    private boolean validateMobileNumber(String mobilNumber) {
        Pattern MOBILEPATTERN = Pattern.compile("^[7-9][0-9]{9}$");
        return MOBILEPATTERN.matcher(mobilNumber).matches();
    }

    private boolean validateEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    public void dataRetreived(Response data) {
        if (data.servicemethod.equalsIgnoreCase(ServiceMethods.WS_APP_VENDOR_FORM)) {
            if (!data.isError) {
                customProgressLoader.dismissProgressDialog();
                Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                getFragmentManager().beginTransaction()
                        .replace(R.id.ll_fragment, HomeFragment.newInstance())
                        .commit();
            }
        } else if (data.servicemethod.equalsIgnoreCase(ServiceMethods.WS_LIST_SERVICECATOGIIES)) {
            if (!data.isError) {
                serviceGroupDomains = (ArrayList<ServiceGroupDomain>) data.data;
                serviceGroupStrings = new CharSequence[serviceGroupDomains.size()];
                for (int i = 0; i < serviceGroupDomains.size(); i++)
                    serviceGroupStrings[i] = serviceGroupDomains.get(i).getEventItemName();
                customProgressLoader.dismissProgressDialog();

            }
        } else if (data.servicemethod.equalsIgnoreCase(ServiceMethods.WS_APP_LIST_SERVICES)) {
            if (!data.isError) {
                serviceDomains = (ArrayList<ServicesDomain>) data.data;
                serviceStrings = new CharSequence[serviceDomains.size()];
                for (int i = 0; i < serviceDomains.size(); i++)
                    serviceStrings[i] = serviceDomains.get(i).getDesription();
                customProgressLoader.dismissProgressDialog();
                if (serviceDomains.size() > 0)
                    setArrayToDialog(serviceStrings, "Services", tv_service);
                else
                    Toast.makeText(getContext(), "No SErvices", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void opennetworksetting() {

    }

    @Override
    public void onPlaceSelected(Place place) {
        tv_location.setText(place.getAddress());
        getActivity().getFragmentManager()
                .beginTransaction()
                .remove(placeAutocompleteFragment)
                .commit();
        ll_parent.setVisibility(View.VISIBLE);

       /* getFragmentManager()
                .beginTransaction()
                .add(VendorEnqFormFragment.this, "vendorform")
                .commit();*/
    }

    @Override
    public void onError(Status status) {

    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        StringBuilder dateBuilder = new StringBuilder();
        dateBuilder.append(i2);
        dateBuilder.append("-");
        dateBuilder.append(i1 + 1);
        dateBuilder.append("-");
        dateBuilder.append(i);

        tv_follow_update.setText(dateBuilder.toString());
    }
}
