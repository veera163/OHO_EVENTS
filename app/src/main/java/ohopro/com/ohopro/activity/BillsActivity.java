package ohopro.com.ohopro.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import ohopro.com.ohopro.R;
import ohopro.com.ohopro.adapter.BillPagerAdapter;
import ohopro.com.ohopro.appserviceurl.ServiceURL;
import ohopro.com.ohopro.busnesslayer.CommonBL;
import ohopro.com.ohopro.busnesslayer.DataListener;
import ohopro.com.ohopro.domains.BillDomain;
import ohopro.com.ohopro.fragments.ListOfBillsFragment;
import ohopro.com.ohopro.views.CustomProgressLoader;
import ohopro.com.ohopro.webaccess.Response;
import ohopro.com.ohopro.webaccess.ServiceMethods;

public class BillsActivity extends AppCompatActivity
        implements ActionBar.TabListener,
        ListOfBillsFragment.BillsSelectionListener,
        DatePickerDialog.OnDateSetListener, DataListener {

    private ViewPager viewpager;
    private TabLayout tabs;
    ImageView edt_bill;

    DisplayImageOptions displayImageOptions;
    private android.support.v7.app.ActionBar actionBar;
    private ArrayList<Fragment> fragments;
    private ArrayList<String> strings;
    private BillPagerAdapter billPagerAdapter;

    DatePickerDialog datePickerDialog;
    ImageView img_back;
    Dialog dialog;
    private Dialog chooseImage;




    private final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1;
    private final int GALLERY_IMAGE_ACTIVITY_REQUEST_CODE = 2;
    private String image;
    Toolbar toolbar;
    private Context context;
    private CustomProgressLoader progressDialog;

    @Override
    public void onBackPressed() {
        gotoHomeActivity();
    }

    private void gotoHomeActivity() {
        Intent intent = new Intent(this, DashBoardActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bills);
        context = BillsActivity.this;
        progressDialog = new CustomProgressLoader(context);
        dialog = new Dialog(context);
        Calendar calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, this, calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        displayImageOptions = new DisplayImageOptions.Builder()
                .showImageOnFail(R.mipmap.open_cam)
                .showImageOnLoading(R.mipmap.open_cam)
                .showImageForEmptyUri(R.mipmap.open_cam)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .delayBeforeLoading(100)
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

        initViewController();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        /*img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });*/
    }

    private void initViewController() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        img_back = (ImageView) findViewById(R.id.img_back);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        final Drawable upArrow = ContextCompat.getDrawable(context, R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        toolbar.setNavigationIcon(upArrow);
        fragments = new ArrayList<>();
        strings = new ArrayList<>();
        strings.add("Approved");
        strings.add("Other");
        fragments.add(ListOfBillsFragment.newInstance("Approved"));
        fragments.add(ListOfBillsFragment.newInstance("Pending"));
        billPagerAdapter = new BillPagerAdapter(getSupportFragmentManager(), fragments, strings);
        viewpager.setAdapter(billPagerAdapter);
        tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewpager);
    }


    @Override
    public void onTabSelected(android.support.v7.app.ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

    }

    @Override
    public void onTabUnselected(android.support.v7.app.ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(android.support.v7.app.ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

    }

    @Override
    public void onBillSelected(BillDomain billDomain) {
        if (billDomain.getStatus().equalsIgnoreCase("PENDING"))
            openBillEditDialog(billDomain);
        else
            Toast.makeText(this, "your Bill Approved", Toast.LENGTH_SHORT).show();
    }

    TextView edt_dates;

    private void openChooseSelectImage() {
        LinearLayout ll_camera, ll_gallery;
        chooseImage = new Dialog(this);
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

            private void openGallery() {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"),
                        GALLERY_IMAGE_ACTIVITY_REQUEST_CODE);
            }
        });

        chooseImage.show();
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
                        bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
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


    private void openBillEditDialog(final BillDomain billDomain) {
        final EditText edt_amount, edt_bill_number, edt_bill_provider, edt_bill_purpose;
        Button btn_back, btn_submit_bill;
        dialog.setContentView(R.layout.fragment_new_bill);
        dialog.getWindow().getAttributes().width = ViewGroup.LayoutParams.MATCH_PARENT;
        edt_dates = (TextView) dialog.findViewById(R.id.edt_dates);
        edt_amount = (EditText) dialog.findViewById(R.id.edt_amount);
        edt_bill_provider = (EditText) dialog.findViewById(R.id.edt_bill_provider);
        edt_bill_purpose = (EditText) dialog.findViewById(R.id.edt_bill_purpose);
        edt_bill_number = (EditText) dialog.findViewById(R.id.edt_bill_number);
        edt_bill = (ImageView) dialog.findViewById(R.id.edt_bill);
        btn_back = (Button) dialog.findViewById(R.id.btn_back);
        btn_submit_bill = (Button) dialog.findViewById(R.id.btn_submit_bill);
        btn_submit_bill.setText("Update Bill");
        edt_dates.setText(billDomain.getBillDate());
        edt_amount.setText(billDomain.getBillAmount());
        edt_bill_provider.setText(billDomain.getBillProvider());
        edt_bill_purpose.setText(billDomain.getPurpose());
        edt_bill_number.setText(billDomain.getBillNumber());
        final Drawable upArrow = ContextCompat.getDrawable(context, R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(ContextCompat.getColor(context, R.color.gray), PorterDuff.Mode.SRC_ATOP);
        btn_back.setCompoundDrawablesWithIntrinsicBounds(upArrow, null, null, null);
        edt_bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openChooseSelectImage();
            }
        });
        edt_dates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });

        ImageLoader.getInstance().displayImage(billDomain.getBillImageUrl(), edt_bill, displayImageOptions, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                loadedImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                image = Base64.encodeToString(byteArray, Base64.DEFAULT);

            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btn_submit_bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edt_dates.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(context, "Enter Date", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    billDomain.setBillDate(edt_dates.getText().toString());
                }

                if (edt_amount.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(context, "Enter Amount", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    billDomain.setBillAmount(edt_amount.getText().toString());
                }

                if (edt_bill_number.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(context, "Enter Bill Number", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    billDomain.setBillNumber(edt_bill_number.getText().toString());
                }

                if (image == null) {
                    Toast.makeText(context, "Take Image", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    billDomain.setBillImage(image);
                }

                if (edt_bill_purpose.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(context, "Enter Bill Purpose", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    billDomain.setPurpose(edt_bill_purpose.getText().toString());
                }

                if (edt_bill_provider.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(context, "Enter Bill Provider", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    billDomain.setBillProvider(edt_bill_provider.getText().toString());
                }

                if (new CommonBL(BillsActivity.this, context).updateaBill(
                        ServiceURL.getRequestUrl(ServiceMethods.WS_APP_SUBMITABILL),
                        billDomain
                )) {
                    progressDialog.showProgressDialog();
                }
            }
        });
        dialog.show();
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

    @Override
    public void dataRetreived(Response data) {
        if (data.servicemethod.equalsIgnoreCase(ServiceMethods.WS_UPDATEBILL)) {
            if (!data.isError) {
                int status = (int) data.data;
                if (status == 200) {
                    dialog.dismiss();
                    progressDialog.dismissProgressDialog();
                }
            }
        }

    }

    @Override
    public void opennetworksetting() {

    }
}
