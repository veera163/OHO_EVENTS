package ohopro.com.ohopro.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import ohopro.com.ohopro.R;
import ohopro.com.ohopro.activity.DashBoardActivity;
import ohopro.com.ohopro.adapter.StringListAdapter;
import ohopro.com.ohopro.appserviceurl.ServiceURL;
import ohopro.com.ohopro.busnesslayer.CommonBL;
import ohopro.com.ohopro.busnesslayer.DataListener;
import ohopro.com.ohopro.domains.ErrorDomain;
import ohopro.com.ohopro.utility.AppConstant;
import ohopro.com.ohopro.utility.CustomAlertDialogSimple;
import ohopro.com.ohopro.utility.LoggerUtils;
import ohopro.com.ohopro.utility.PreferenceUtils;
import ohopro.com.ohopro.views.CustomProgressLoader;
import ohopro.com.ohopro.webaccess.Response;
import ohopro.com.ohopro.webaccess.ServiceMethods;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link UploadServiceDocumentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UploadServiceDocumentFragment extends Fragment implements DataListener {

    private static final int REQUEST_EXTERNAL_STORAGE = 2;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    DatePickerDialog datePickerDialog;
    TextView tv_upload;
    RecyclerView lst_images;
    StringListAdapter stringListAdapter;
    EditText edt_reason, edt_doc_name, edt_type_of_doc, edt_issued_by;
    Button btn_back, btn_submit_agreement, btn_new_agreement;
    RecyclerView lst_vendor_agreements;

    private final String STARTDATE = "startDate";
    private final String ENDDATE = "endDate";
    private String selection;
    private PreferenceUtils preferenceUtils;
    private CustomProgressLoader customProgressLoader;
    private final int FILE_REQUEST_CODE = 1;
    private File file;
    private int serverResponseCode;
    private AlertDialog alertDialog;
    private ArrayList<String> strings = new ArrayList<>();

    public UploadServiceDocumentFragment() {
        // Required empty public constructor
    }

    public static UploadServiceDocumentFragment newInstance() {
        return new UploadServiceDocumentFragment();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_EXTERNAL_STORAGE) {
            openFileSelector();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_of_agreements, container, false);
        preferenceUtils = new PreferenceUtils(getContext());
        customProgressLoader = new CustomProgressLoader(getContext());
        initViewController(view);
        btn_new_agreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogForUploadAgreement();
                alertDialog.show();
            }
        });

        return view;
    }


    private void openDialogForUploadAgreement() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_upload_service_docs, null);
        initViewControllerUploadAgreement(view);
        //builder.setTitle("New Vendor Service");
        builder.setView(view);
        alertDialog = builder.create();
        btn_submit_agreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edt_reason.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getContext(), "Enter Description", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edt_doc_name.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getContext(), "Enter Document Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edt_issued_by.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getContext(), "Enter Issued By", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edt_type_of_doc.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getContext(), "Enter Type Of Doc", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (strings.size() == 0) {
                    Toast.makeText(getContext(), "Upload atleast one Image", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (new CommonBL(UploadServiceDocumentFragment.this, getContext())
                        .postServices(ServiceURL.getRequestUrl(ServiceMethods.WS_APP_POST_SERVICEDOCS),
                                edt_doc_name.getText().toString(),
                                edt_type_of_doc.getText().toString(),
                                edt_issued_by.getText().toString(),
                                edt_reason.getText().toString(),
                                preferenceUtils.getUserId(),
                                strings)) {
                    customProgressLoader.showProgressDialog();
                }
            }
        });

        tv_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    openFileSelector();
                } else {
                    ActivityCompat.requestPermissions(getActivity(), PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
                }

            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }

    private void initViewController(View view) {
        btn_new_agreement = (Button) view.findViewById(R.id.btn_new_agreement);
        lst_vendor_agreements = (RecyclerView) view.findViewById(R.id.lst_vendor_agreements);
        lst_vendor_agreements.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        btn_new_agreement.setText("New Doc to Upload");
    }

    private void openFileSelector() {
        final String[] ACCEPT_MIME_TYPES = {
                "application/pdf",
                "image/*"
        };
        Intent galleryintent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);

        galleryintent.setType("*/*");
        galleryintent.putExtra(Intent.EXTRA_MIME_TYPES, ACCEPT_MIME_TYPES);
        startActivityForResult(Intent.createChooser(galleryintent, "Select File"), FILE_REQUEST_CODE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FILE_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                this.file = new File(getRealPathFromURI(data.getData()));
                new ImageUploader().execute("null");
            }
        }
    }

    private String getRealPathFromURI(Uri selectedgalleryImageuri) {
        String result;
        String[] proj = {MediaStore.MediaColumns.DATA};
        Cursor cursor = ((DashBoardActivity) getActivity()).getContentResolver().query(selectedgalleryImageuri, proj, null, null, null);
        if (cursor == null) {
            result = selectedgalleryImageuri.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        LoggerUtils.error(UploadServiceDocumentFragment.class.getSimpleName(), "gallery img path is" + result);
        return result;
    }

    private String creatUrl() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ServiceURL.getRequestUrl(ServiceMethods.UPLOADAGREEMENT));
        stringBuilder.append("/vendor/");
        stringBuilder.append(preferenceUtils.getUserId());
        return stringBuilder.toString();
    }

    private void initViewControllerUploadAgreement(View view) {
        edt_reason = (EditText) view.findViewById(R.id.edt_reason);
        edt_doc_name = (EditText) view.findViewById(R.id.edt_doc_name);
        edt_type_of_doc = (EditText) view.findViewById(R.id.edt_type_of_doc);
        edt_issued_by = (EditText) view.findViewById(R.id.edt_issued_by);
        tv_upload = (TextView) view.findViewById(R.id.tv_upload);
        btn_back = (Button) view.findViewById(R.id.btn_back);
        btn_submit_agreement = (Button) view.findViewById(R.id.btn_submit_agreement);
        lst_images = (RecyclerView) view.findViewById(R.id.lst_images);
        lst_images.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        lst_images.setAdapter(stringListAdapter = new StringListAdapter(strings));

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
    public void dataRetreived(Response data) {
        if (data.servicemethod.equalsIgnoreCase(ServiceMethods.WS_APP_POST_SERVICEDOCS)) {
            if (!data.isError) {
                if (data.data instanceof ErrorDomain)
                    new CustomAlertDialogSimple(getContext()).showAlertDialog(((ErrorDomain) data.data).getError_description());
                else
                    alertDialog.dismiss();

                customProgressLoader.dismissProgressDialog();
            }
        }
    }


    public class ImageUploader extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... files) {
            String s = "NA";
            doFileUpload();
            LoggerUtils.info("image url", s);
            return s;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

    }

    @Override
    public void opennetworksetting() {

    }

    private void doFileUpload() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                customProgressLoader.showProgressDialog();
            }
        });
        String urlString = ServiceURL.HOST_URL + "uploadDocument/" + preferenceUtils.getUserId();
        try {
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(urlString);
            FileBody bin1 = new FileBody(file);
            MultipartEntity reqEntity = new MultipartEntity();
            reqEntity.addPart("uploadedfile1", bin1);
            reqEntity.addPart("user", new StringBody("User"));
            post.setEntity(reqEntity);
            post.addHeader("authorization", AppConstant.HEADER);
            HttpResponse response = client.execute(post);
            HttpEntity resEntity = response.getEntity();
            final String response_str = EntityUtils.toString(resEntity);
            if (resEntity != null) {
                Log.i("RESPONSE", response_str);
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        try {
                            Toast.makeText(getContext(), "Upload Complete. Check the server uploads directory.", Toast.LENGTH_LONG).show();
                            customProgressLoader.dismissProgressDialog();
                            JSONObject jsonObject = new JSONObject(response_str);
                            strings.add(jsonObject.getString("docURL"));
                            stringListAdapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        } catch (Exception ex) {
            Log.e("Debug", "error: " + ex.getMessage(), ex);
        }
    }
}
