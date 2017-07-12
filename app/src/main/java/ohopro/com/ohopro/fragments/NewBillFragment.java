package ohopro.com.ohopro.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Calendar;

import ohopro.com.ohopro.R;
import ohopro.com.ohopro.appserviceurl.ServiceURL;
import ohopro.com.ohopro.busnesslayer.CommonBL;
import ohopro.com.ohopro.busnesslayer.DataListener;
import ohopro.com.ohopro.domains.ErrorDomain;
import ohopro.com.ohopro.utility.AppConstant;
import ohopro.com.ohopro.utility.CustomAlertDialogSimple;
import ohopro.com.ohopro.utility.PreferenceUtils;
import ohopro.com.ohopro.views.CustomProgressLoader;
import ohopro.com.ohopro.webaccess.Response;
import ohopro.com.ohopro.webaccess.ServiceMethods;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link NewBillFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewBillFragment extends Fragment
        implements DataListener, DatePickerDialog.OnDateSetListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    EditText edt_amount, edt_bill_number, edt_bill_purpose, edt_bill_provider;
    ImageView edt_bill;
    TextView edt_dates;

    Button btn_submit_bill, btn_back;

    PreferenceUtils preferenceUtils;
    Dialog chooseImage;
    CustomProgressLoader progressDialog;
    String image;
    DatePickerDialog datePickerDialog;
    private final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1;
    private final int GALLERY_IMAGE_ACTIVITY_REQUEST_CODE = 2;
    private int PERMISSIONLOCALSTORAGE = 10;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        openGallery();
    }

    public NewBillFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                chooseImage.dismiss();
                Bitmap bmp = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                image = Base64.encodeToString(byteArray, Base64.DEFAULT);
                // convert byte array to Bitmap

                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0,
                        byteArray.length);

                edt_bill.setImageBitmap(bitmap);

            }
        } else if (requestCode == GALLERY_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    chooseImage.dismiss();

                    try {
                        Bitmap bmp = null;
                        bmp = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                        edt_bill.setImageBitmap(bmp);

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] byteArray = stream.toByteArray();

                        image = Base64.encodeToString(byteArray, Base64.DEFAULT);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment NewBillFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewBillFragment newInstance() {
        NewBillFragment fragment = new NewBillFragment();
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
        AppConstant.currentFragmentTag = NewBillFragment.class.getSimpleName();
        preferenceUtils = new PreferenceUtils(getContext());
        progressDialog = new CustomProgressLoader(getContext());
        Calendar calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(getContext(), this, calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        View view = inflater.inflate(R.layout.fragment_new_bill, container, false);
        initViewController(view);

        edt_dates.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View view) {
                                             datePickerDialog.show();
                                         }
                                     }
        );

        edt_bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openChooseSelectImage();

            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.ll_fragment, HomeFragment.newInstance())
                        .commit();
            }
        });
        btn_submit_bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edt_dates.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getContext(), "Enter Date", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edt_amount.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getContext(), "Enter Amount", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edt_bill_number.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getContext(), "Enter Bill Number", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (image == null) {
                    Toast.makeText(getContext(), "Take Image", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edt_bill_purpose.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getContext(), "Enter Bill Purpose", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edt_bill_provider.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getContext(), "Enter Bill Provider", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (new CommonBL(NewBillFragment.this, getContext()).submitABill(
                        getRequiredUrl(),
                        edt_dates.getText().toString(),
                        edt_amount.getText().toString(),
                        edt_bill_number.getText().toString(),
                        preferenceUtils.getUserMobile(),
                        preferenceUtils.getUserEmail(),
                        image,
                        edt_bill_purpose.getText().toString(),
                        edt_bill_provider.getText().toString()
                )) {
                    progressDialog.showProgressDialog();
                }
            }
        });
        return view;
    }

    private void openChooseSelectImage() {
        LinearLayout ll_camera, ll_gallery;
        chooseImage = new Dialog(getContext());
        chooseImage.setContentView(R.layout.dialogimageselection);
        chooseImage.setCanceledOnTouchOutside(true);
        chooseImage.getWindow().getAttributes().width = ViewGroup.LayoutParams.MATCH_PARENT;
        ll_camera = (LinearLayout) chooseImage.findViewById(R.id.ll_camera);
        ll_gallery = (LinearLayout) chooseImage.findViewById(R.id.ll_gallery);

        ll_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCamera();
            }

            private void openCamera() {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,
                        CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
            }
        });

        ll_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }


        });

        chooseImage.show();
    }

    private void openGallery() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"),
                    GALLERY_IMAGE_ACTIVITY_REQUEST_CODE);
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSIONLOCALSTORAGE);
        }
    }

    private String getRequiredUrl() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ServiceURL.getRequestUrl(ServiceMethods.WS_APP_SUBMITABILL));
        //stringBuilder.append(AppConstant.ACCESSTOKEINFO);
        //stringBuilder.append(preferenceUtils.getAccessToken());
        return stringBuilder.toString();
    }

    private void initViewController(View view) {
        edt_amount = (EditText) view.findViewById(R.id.edt_amount);
        edt_bill = (ImageView) view.findViewById(R.id.edt_bill);
        edt_bill_number = (EditText) view.findViewById(R.id.edt_bill_number);
        edt_bill_provider = (EditText) view.findViewById(R.id.edt_bill_provider);
        edt_bill_purpose = (EditText) view.findViewById(R.id.edt_bill_purpose);
        edt_dates = (TextView) view.findViewById(R.id.edt_dates);

        btn_submit_bill = (Button) view.findViewById(R.id.btn_submit_bill);
        btn_back = (Button) view.findViewById(R.id.btn_back);
        final Drawable upArrow = ContextCompat.getDrawable(getContext(), R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(ContextCompat.getColor(getContext(), R.color.gray), PorterDuff.Mode.SRC_ATOP);
        btn_back.setCompoundDrawablesWithIntrinsicBounds(upArrow, null, null, null);
    }

    // TODO: Rename method, update argument and hook method into UI event


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
        if (data.servicemethod.equalsIgnoreCase(ServiceMethods.WS_APP_SUBMITABILL)) {
            if (!data.isError) {
                progressDialog.dismissProgressDialog();
                if (data.data instanceof Integer) {
                    Integer statusCode = (Integer) data.data;
                    if (statusCode == HttpURLConnection.HTTP_OK) {
                        getFragmentManager().beginTransaction()
                                .replace(R.id.ll_fragment, HomeFragment.newInstance())
                                .commit();
                        Toast.makeText(getContext(), "Your Bill Successfully Updated", Toast.LENGTH_SHORT).show();
                    }
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
        dateBuilder.append(i2);
        dateBuilder.append("-");
        dateBuilder.append(i1 + 1);
        dateBuilder.append("-");
        dateBuilder.append(i);

        edt_dates.setText(dateBuilder.toString());
    }

}
