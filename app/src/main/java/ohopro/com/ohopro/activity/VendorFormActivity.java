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
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Pattern;

import ohopro.com.ohopro.R;
import ohopro.com.ohopro.appserviceurl.ServiceURL;
import ohopro.com.ohopro.busnesslayer.CommonBL;
import ohopro.com.ohopro.busnesslayer.DataListener;
import ohopro.com.ohopro.domains.CountryCodeDomain;
import ohopro.com.ohopro.domains.CountryDialcodeDomain;
import ohopro.com.ohopro.domains.ErrorDomain;
import ohopro.com.ohopro.domains.ServiceGroupDomain;
import ohopro.com.ohopro.domains.ServiceOrSupplyLocations;
import ohopro.com.ohopro.domains.ServicesDomain;
import ohopro.com.ohopro.domains.VendorEnquaryDomain;
import ohopro.com.ohopro.domains.VendorReqDomain;
import ohopro.com.ohopro.utility.AppConstant;
import ohopro.com.ohopro.utility.CurrentDate;
import ohopro.com.ohopro.utility.CustomAlertDialogSimple;
import ohopro.com.ohopro.utility.PreferenceUtils;
import ohopro.com.ohopro.views.CustomProgressLoader;
import ohopro.com.ohopro.webaccess.Response;
import ohopro.com.ohopro.webaccess.ServiceMethods;

public class VendorFormActivity extends AppCompatActivity implements DataListener
        , DatePickerDialog.OnDateSetListener {
    EditText edt_first_name, edt_surname, edt_designation, edt_company_name;
    EditText edt_email, edt_phone_number, edt_address_line_1, edt_address_line_2;
    EditText edt_town, edt_postal_code, edt_details_of_enquary;
    TextView tv_service_group, tv_state, edt_city, tv_service_type, tv_enquiry, edt_country, tv_service, tv_follow_update, tv_location, edt_tittle;
    Button btn_back, btn_submit_form;
    LinearLayout ll_parent;
    CustomProgressLoader customProgressLoader;
    String selectedEnquiryType = "NA";
    PreferenceUtils preferenceUtils;
    AlertDialog.Builder builder;
    ArrayList<ServiceGroupDomain> serviceGroupDomains;
    ArrayList<ServicesDomain> serviceDomains;
    PlaceAutocompleteFragment placeAutocompleteFragment;
    private CharSequence[] serviceGroupStrings = new CharSequence[]{};

    private CharSequence[] serviceType = new CharSequence[]{"Sale & Rent Item", "Service & Rent Item"};
    private CharSequence[] enquiryType = new CharSequence[]{"On field", "Tele Call", "Email", "Seller Called", "In Office", "Referal", "Other"};
    private CharSequence[] tittles = new CharSequence[]{"Mr.", "Mrs.", "Ms.", "Others"};
    private CharSequence[] serviceStrings = new CharSequence[]{};
    private EditText edt_website;
    private DatePickerDialog datePickerDialog;
    private ArrayList<CountryCodeDomain> countries;
    private ArrayList<CountryDialcodeDomain> countryDialcodeDomains;
    private String action;
    private String resolution;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                tv_location.setText(place.getAddress());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

    private int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;


    Gson gson = new Gson();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customProgressLoader = new CustomProgressLoader(getContext());
        AppConstant.currentFragmentTag = VendorFormActivity.class.getSimpleName();
        preferenceUtils = new PreferenceUtils(getContext());
        builder = new AlertDialog.Builder(getContext());
        Calendar calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(getContext(), this, calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        countryDialcodeDomains = gson.fromJson(readTitlesRaw(), new TypeToken<ArrayList<CountryDialcodeDomain>>() {
        }.getType());
        getDataFromOtherActivity();
        setContentView(R.layout.vendor_enq_form);
        initViewController();
        getAllServiceCatogiries();
    }

    private void getDataFromOtherActivity() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
            if (bundle.containsKey(AppConstant.ACTION))
                action = bundle.getString(AppConstant.ACTION);
    }

    private Context getContext() {
        return this;
    }

    private void getAllServiceCatogiries() {
        if (new CommonBL(this, getContext()).getAllVendorServiceCatogories(
                ServiceURL.getRequestUrl(ServiceMethods.WS_LIST_SERVICECATOGIIES)
        )) {
            customProgressLoader.showProgressDialog();
        }
    }


    private void initViewController() {
        ll_parent = (LinearLayout) findViewById(R.id.ll_parent);
        btn_back = (Button) findViewById(R.id.btn_back);
        btn_submit_form = (Button) findViewById(R.id.btn_submit_form);
        edt_tittle = (TextView) findViewById(R.id.edt_tittle);
        edt_first_name = (EditText) findViewById(R.id.edt_first_name);
        edt_surname = (EditText) findViewById(R.id.edt_surname);
        edt_designation = (EditText) findViewById(R.id.edt_designation);
        edt_company_name = (EditText) findViewById(R.id.edt_company_name);
        edt_email = (EditText) findViewById(R.id.edt_email);
        edt_phone_number = (EditText) findViewById(R.id.edt_phone_number);
        edt_address_line_1 = (EditText) findViewById(R.id.edt_address_line_1);
        edt_address_line_2 = (EditText) findViewById(R.id.edt_address_line_2);
        edt_town = (EditText) findViewById(R.id.edt_town);
        edt_website = (EditText) findViewById(R.id.edt_website);
        edt_city = (TextView) findViewById(R.id.edt_city);
        tv_state = (TextView) findViewById(R.id.tv_state);
        edt_postal_code = (EditText) findViewById(R.id.edt_postal_code);
        edt_country = (TextView) findViewById(R.id.edt_country);
        edt_details_of_enquary = (EditText) findViewById(R.id.edt_details_of_enquary);
        tv_service_group = (TextView) findViewById(R.id.tv_service_group);
        tv_service_type = (TextView) findViewById(R.id.tv_service_type);
        tv_service = (TextView) findViewById(R.id.tv_service);
        tv_location = (TextView) findViewById(R.id.tv_location);
        tv_follow_update = (TextView) findViewById(R.id.tv_follow_update);
        tv_enquiry = (TextView) findViewById(R.id.tv_enquiry);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        final Drawable upArrow = ContextCompat.getDrawable(getContext(), R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        toolbar.setNavigationIcon(upArrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
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
                    edt_postal_code.requestFocus();
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
                    edt_details_of_enquary.requestFocus();
            }
        });
        edt_country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (countries == null)
                    getCountries();
                else
                    setArrayToDialog(countries, "Select your Country", edt_country);

            }
        });
        tv_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getStates();
            }
        });
        edt_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCities();
            }
        });
        /*edt_country.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b)
                    edt_details_of_enquary.requestFocus();
            }
        });*/

        /*upArrow.setColorFilter(ContextCompat.getColor(getContext(), R.color.gray), PorterDuff.Mode.SRC_ATOP);
        btn_back.setCompoundDrawablesWithIntrinsicBounds(upArrow, null, null, null);
        */
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
                openSelectorForLocationSelection();

            }
        });
        btn_submit_form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkForm();
            }
        });

        configBasedOnAction();
    }

    private void configBasedOnAction() {
        if (action != null)
            if (action.equalsIgnoreCase(AppConstant.EDIT))
                editMode();
    }

    private void editMode() {
        edt_tittle.setText(AppConstant.vendorReqDomain.getTitle());
        edt_first_name.setText(AppConstant.vendorReqDomain.getFirstName());
        edt_surname.setText(AppConstant.vendorReqDomain.getSurname());
        edt_designation.setText(AppConstant.vendorReqDomain.getDesignation());
        edt_company_name.setText(AppConstant.vendorReqDomain.getCompanyName());
        edt_email.setText(AppConstant.vendorReqDomain.getEmail());
        edt_phone_number.setText(AppConstant.vendorReqDomain.getTelephone());
        edt_address_line_1.setText(AppConstant.vendorReqDomain.getAddressLine1());
        edt_address_line_2.setText(AppConstant.vendorReqDomain.getAddressLine2());
        edt_town.setText(AppConstant.vendorReqDomain.getTown());
        edt_website.setText(AppConstant.vendorReqDomain.getWebsite());
        edt_city.setText(AppConstant.vendorReqDomain.getCity());
        tv_state.setText(AppConstant.vendorReqDomain.getState());
        edt_postal_code.setText(AppConstant.vendorReqDomain.getPostalCode());
        edt_country.setText(AppConstant.vendorReqDomain.getCountry());
        edt_details_of_enquary.setText(AppConstant.vendorReqDomain.getTitle());
        tv_service_group.setText(AppConstant.vendorReqDomain.getProductOrServiceGroup());
        tv_service_type.setText(AppConstant.vendorReqDomain.getProductOrServiceType());
        tv_service.setText(AppConstant.vendorReqDomain.getProductsOrServices());
        tv_location.setText(AppConstant.vendorReqDomain.getServiceOrSupplyLocations().get(0).getText());
        tv_follow_update.setText(AppConstant.vendorReqDomain.getFollowUpdate());
        tv_enquiry.setText(AppConstant.vendorReqDomain.getEnquiryType());
        tv_state.setVisibility(View.VISIBLE);
        edt_city.setVisibility(View.VISIBLE);
    }

    private void getCities() {
        String url = ServiceURL.getRequestUrl(ServiceMethods.WS_APP_GET_CITIES);
        url = url.concat(tv_state.getText().toString());
        if (new CommonBL(this, this).getAllCities(url)) {
            customProgressLoader.showProgressDialog();
        }
    }

    private void getStates() {
        String url = ServiceURL.getRequestUrl(ServiceMethods.WS_APP_GET_STATES);
        url = url.concat(edt_country.getText().toString());
        if (new CommonBL(this, this).getAllStates(url)) {
            customProgressLoader.showProgressDialog();
        }
    }

    private void getCountries() {
        if (new CommonBL(this, this).getAllCountries(ServiceURL.getRequestUrl(ServiceMethods.WS_APP_GET_COUNTRIES))) {
            customProgressLoader.showProgressDialog();
        }
    }

    private void openSelectorForLocationSelection() {
        try {
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .build(this);
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }
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
                if (!charSequences[i].toString().equalsIgnoreCase("Other")) {
                    textView.setText(charSequences[i]);
                    dialogInterface.dismiss();
                } else {
                    openEntryForOthersType(textView, dialogInterface);
                }

                if (textView.getId() == R.id.tv_enquiry)
                    selectedEnquiryType = charSequences[i].toString();
            }
        });
        builder.create().show();
    }

    private void openEntryForOthersType(final TextView textView, final DialogInterface dialogInterface) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_other_entry, null);
        builder.setView(view);
        final EditText edt_other = (EditText) view.findViewById(R.id.edt_other);
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface1, int i) {
                if (!edt_other.getText().toString().equalsIgnoreCase("")) {
                    textView.setText(edt_other.getText().toString());
                    dialogInterface1.dismiss();
                    dialogInterface.dismiss();
                } else {
                    new CustomAlertDialogSimple(getContext()).showAlertDialog("please enter your input");
                }
            }
        });
        builder.create().show();
    }

    private void openEntryForResolution() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_other_entry, null);
        builder.setView(view);
        final EditText edt_other = (EditText) view.findViewById(R.id.edt_other);
        TextView tv_tittle = (TextView) view.findViewById(R.id.tv_tittle);
        edt_other.setHeight(1000);
        tv_tittle.setText("Resolution");
        edt_other.setHint("Enter your Resolution");
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface1, int i) {
                if (!edt_other.getText().toString().equalsIgnoreCase("")) {
                    resolution = edt_other.getText().toString();
                    dialogInterface1.dismiss();
                    updateVendorForm();
                } else {
                    new CustomAlertDialogSimple(getContext()).showAlertDialog("please enter your input");
                }
            }
        });
        builder.create().show();
    }


    String countryCode;

    private void setArrayToDialog(final ArrayList<CountryCodeDomain> charSequences, String tittle, final TextView textView) {
        builder.setTitle(tittle);
        builder.setCancelable(false);
        CharSequence[] sequence;
        sequence = new CharSequence[charSequences.size()];
        for (int i = 0; i < charSequences.size(); i++) {
            sequence[i] = charSequences.get(i).getName();
        }
        builder.setItems(sequence, new AlertDialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                textView.setText(charSequences.get(i).getName());
                if (textView.getId() == R.id.edt_country) {
                    countryCode = getCountryCodeByIso(charSequences.get(i).getCode());
                    tv_state.setVisibility(View.VISIBLE);

                } else if (textView.getId() == R.id.tv_state) {
                    edt_city.setVisibility(View.VISIBLE);
                } else if (textView.getId() == R.id.edt_city) {
                    if (charSequences.get(i).getName().equalsIgnoreCase("Other"))
                        openEntryForOthersType(textView, dialogInterface);
                }
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

    private String getCountryCodeByIso(String code) {
        for (CountryDialcodeDomain countryDialcodeDomain : countryDialcodeDomains)
            if (countryDialcodeDomain.getCode().equalsIgnoreCase(code))
                return countryDialcodeDomain.getDial_code();
        return "NA";
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

        if (edt_phone_number.getText().toString().equalsIgnoreCase("")) {
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
        if (tv_state.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(getContext(), "Enter Your State", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!action.equalsIgnoreCase(AppConstant.EDIT))
            createVendorInServer();
        else
            openEntryForResolution();
    }

    private void updateVendorForm() {
        VendorReqDomain vendorEnquaryDomain = AppConstant.vendorReqDomain;
        vendorEnquaryDomain.setTitle(edt_tittle.getText().toString());
        vendorEnquaryDomain.setFirstName(edt_first_name.getText().toString());
        vendorEnquaryDomain.setSurname(edt_surname.getText().toString());
        vendorEnquaryDomain.setDesignation(edt_designation.getText().toString());
        vendorEnquaryDomain.setCompanyName(edt_company_name.getText().toString());
        vendorEnquaryDomain.setEmail(edt_email.getText().toString());
        if (countryCode != null)
            vendorEnquaryDomain.setTelephone(countryCode + edt_phone_number.getText().toString());
        vendorEnquaryDomain.setAddressLine1(edt_address_line_1.getText().toString());
        vendorEnquaryDomain.setAddressLine2(edt_address_line_2.getText().toString());
        vendorEnquaryDomain.setTown(edt_town.getText().toString());
        vendorEnquaryDomain.setCity(edt_city.getText().toString());
        vendorEnquaryDomain.setPostalCode(edt_postal_code.getText().toString());
        vendorEnquaryDomain.setCountry(edt_country.getText().toString());
        vendorEnquaryDomain.setDateOfEnquiry(new CurrentDate().currentDate);
        vendorEnquaryDomain.setEnquiredEmployee(preferenceUtils.getUserName());
        vendorEnquaryDomain.setEnquiredEmployeePhone(preferenceUtils.getUserMobile());
        ArrayList<ServiceOrSupplyLocations> serviceOrSupplyLocationses = new ArrayList<>();
        serviceOrSupplyLocationses.add(new ServiceOrSupplyLocations(tv_location.getText().toString()));
        vendorEnquaryDomain.setServiceOrSupplyLocations(serviceOrSupplyLocationses);
        vendorEnquaryDomain.setProductOrServiceGroup(tv_service_group.getText().toString());
        vendorEnquaryDomain.setProductsOrServices(tv_service.getText().toString());
        vendorEnquaryDomain.setProductOrServiceType(tv_service_type.getText().toString());
        vendorEnquaryDomain.setFollowUpdate(tv_follow_update.getText().toString());
        vendorEnquaryDomain.setWebsite(edt_website.getText().toString());
        vendorEnquaryDomain.setDetailsEnquiry(edt_details_of_enquary.getText().toString());
        vendorEnquaryDomain.setState(tv_state.getText().toString());
        vendorEnquaryDomain.setEnquiryType(selectedEnquiryType);
        if (selectedEnquiryType.equalsIgnoreCase("Other"))
            vendorEnquaryDomain.setOtherEnquiryType(tv_enquiry.getText().toString());

        if (new CommonBL(this, getContext()).updateVendorForm(
                ServiceURL.getRequestUrl(ServiceMethods.WS_APP_VENDOR_FORM),
                vendorEnquaryDomain
        )) {
            customProgressLoader.showProgressDialog();
        }
    }

    private void createVendorInServer() {
        VendorEnquaryDomain vendorEnquaryDomain = new VendorEnquaryDomain();
        vendorEnquaryDomain.setTitle(edt_tittle.getText().toString());
        vendorEnquaryDomain.setFirstName(edt_first_name.getText().toString());
        vendorEnquaryDomain.setSurname(edt_surname.getText().toString());
        vendorEnquaryDomain.setDesignation(edt_designation.getText().toString());
        vendorEnquaryDomain.setCompanyName(edt_company_name.getText().toString());
        vendorEnquaryDomain.setEmail(edt_email.getText().toString());
        if (countryCode != null)
            vendorEnquaryDomain.setTelephone(countryCode + edt_phone_number.getText().toString());
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
        vendorEnquaryDomain.setWebSite(edt_website.getText().toString());
        vendorEnquaryDomain.setDetailsEnquiry(edt_details_of_enquary.getText().toString());
        vendorEnquaryDomain.setState(tv_state.getText().toString());
        vendorEnquaryDomain.setEnquiryType(selectedEnquiryType);
        if (selectedEnquiryType.equalsIgnoreCase("Other"))
            vendorEnquaryDomain.setOtherEnquiryType(tv_enquiry.getText().toString());

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
        if (data.servicemethod.equalsIgnoreCase(ServiceMethods.WS_APP_VENDOR_FORM) ||
                data.servicemethod.equalsIgnoreCase(ServiceMethods.WS_APP_UPDATE_VENDORFORM)) {
            if (!data.isError) {
                customProgressLoader.dismissProgressDialog();
                if (data.data instanceof String) {
                    new CustomAlertDialogSimple(getContext(), new CustomAlertDialogSimple.OnDismisslistener() {
                        @Override
                        public void onDismiss() {
                            finish();
                        }
                    }).showAlertDialog((String) data.data);
                    /*getFragmentManager().beginTransaction()
                            .replace(R.id.ll_fragment, HomeFragment.newInstance())
                            .commit();*/
                } else if (data.data instanceof ErrorDomain) {
                    new CustomAlertDialogSimple(getContext()).showAlertDialog(((ErrorDomain) data.data).getError_description());
                }
            }
        } else if (data.servicemethod.equalsIgnoreCase(ServiceMethods.WS_LIST_SERVICECATOGIIES)) {
            if (!data.isError) {
                customProgressLoader.dismissProgressDialog();
                if (data.data instanceof ArrayList) {
                    serviceGroupDomains = (ArrayList<ServiceGroupDomain>) data.data;
                    serviceGroupStrings = new CharSequence[serviceGroupDomains.size()];
                    for (int i = 0; i < serviceGroupDomains.size(); i++)
                        serviceGroupStrings[i] = serviceGroupDomains.get(i).getEventItemName();
                } else if (data.data instanceof ErrorDomain) {
                    new CustomAlertDialogSimple(getContext()).showAlertDialog(((ErrorDomain) data.data).getError_description());
                }
            }
        } else if (data.servicemethod.equalsIgnoreCase(ServiceMethods.WS_APP_LIST_SERVICES)) {
            if (!data.isError) {
                customProgressLoader.dismissProgressDialog();

                if (data.data instanceof ArrayList) {
                    serviceDomains = (ArrayList<ServicesDomain>) data.data;
                    serviceStrings = new CharSequence[serviceDomains.size()];
                    for (int i = 0; i < serviceDomains.size(); i++)
                        serviceStrings[i] = serviceDomains.get(i).getProductType();
                    if (serviceDomains.size() > 0)
                        setArrayToDialog(serviceStrings, "Services", tv_service);
                    else
                        Toast.makeText(getContext(), "No Services", Toast.LENGTH_SHORT).show();
                } else if (data.data instanceof ErrorDomain) {
                    new CustomAlertDialogSimple(getContext()).showAlertDialog(((ErrorDomain) data.data).getError_description());
                }

            }
        } else if (data.servicemethod.equalsIgnoreCase(ServiceMethods.WS_APP_GET_COUNTRIES)) {
            if (!data.isError) {
                customProgressLoader.dismissProgressDialog();
                if (data.data instanceof ArrayList) {
                    countries = (ArrayList<CountryCodeDomain>) data.data;
                    setArrayToDialog(countries, "Select your Country", edt_country);
                } else if (data.data instanceof ErrorDomain) {
                    new CustomAlertDialogSimple(getContext()).showAlertDialog(((ErrorDomain) data.data).getError_description());
                }
            }
        } else if (data.servicemethod.equalsIgnoreCase(ServiceMethods.WS_APP_GET_STATES)) {
            if (!data.isError) {
                customProgressLoader.dismissProgressDialog();
                if (data.data instanceof ArrayList) {
                    setArrayToDialog((ArrayList<CountryCodeDomain>) data.data, "Select your State", tv_state);
                } else if (data.data instanceof ErrorDomain) {
                    new CustomAlertDialogSimple(getContext()).showAlertDialog(((ErrorDomain) data.data).getError_description());
                }
            }
        } else if (data.servicemethod.equalsIgnoreCase(ServiceMethods.WS_APP_GET_CITIES)) {
            if (!data.isError) {
                customProgressLoader.dismissProgressDialog();
                if (data.data instanceof ArrayList) {
                    setArrayToDialog((ArrayList<CountryCodeDomain>) data.data, "Select your City", edt_city);
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
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        StringBuilder dateBuilder = new StringBuilder();
        dateBuilder.append(String.format("%02d", i2));
        dateBuilder.append("-");
        dateBuilder.append(String.format("%02d", i1 + 1));
        dateBuilder.append("-");
        dateBuilder.append(i);

        tv_follow_update.setText(dateBuilder.toString());
    }

    private String readTitlesRaw() {
        InputStream stream = getContext().getResources().openRawResource(R.raw.country_codes);
        Writer writer = new StringWriter();
        char[] buffer = new char[10240];
        try {
            Reader reader = new BufferedReader(
                    new InputStreamReader(stream, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return writer.toString();
    }
}
