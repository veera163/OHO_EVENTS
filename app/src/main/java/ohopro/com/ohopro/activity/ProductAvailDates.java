package ohopro.com.ohopro.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.support.v7.app.AlertDialog;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ohopro.com.ohopro.Api.CalenderSaveApi;
import ohopro.com.ohopro.Api.SingleDateApi;
import ohopro.com.ohopro.R;
import ohopro.com.ohopro.adapter.CalEntryAdpter;
import ohopro.com.ohopro.domains.AppController;
import ohopro.com.ohopro.domains.NONAvailableDates;
import ohopro.com.ohopro.domains.SingleDateRes;
import ohopro.com.ohopro.domains.SingleEntry;
import ohopro.com.ohopro.utility.AppConstant;
import ohopro.com.ohopro.utility.PreferenceUtils;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by home on 12/28/2017.
 */

public class ProductAvailDates extends AppCompatActivity {
    String sdate,id;
    CalEntryAdpter calEntryAdpter;
    SingleDateApi singleDateApi;
    List<SingleDateRes> singleDateRes;
    List<NONAvailableDates> nonAvailableDates;
    SingleEntry singleEntry;
    Calendar myCalendar;
    String productquality;
    TimePickerDialog.OnTimeSetListener onStartTimeListener;
    TimePickerDialog.OnTimeSetListener onEndTimeListener;
    String starttime,endtime;
    RecyclerView recyclerView;
    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    private PreferenceUtils preferenceUtils;
    Button bookslot;
    String slottitle;
    long startMilliseconds,endtMilliseconds;
    AlertDialog alertDialogBuilder;
    EditText ed_sdate,ed_stime,ed_edate,ed_etime;
    ImageView imgstime,imgetime,img_back;
    TextView title;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    Button submit;
    String currdate;
    JSONObject paramObjec;
    String productCalendarEntryId,productName,productId,productQuantity,vendorName,vendorId;
        String primary= "#006400",secondary="#caffca",orderId,draggable,resizable,type,calendarEntryId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.productavail_dates);
        myCalendar = Calendar.getInstance();
        img_back=(ImageView)findViewById(R.id.img_back);
        bookslot=(Button)findViewById(R.id.addsolt);
        preferenceUtils=new PreferenceUtils(ProductAvailDates.this);
        Bundle bundle = getIntent().getExtras();
        sdate = String.valueOf(bundle.getString("data"));
        currdate = sdate;

        productquality=bundle.getString("productquality");
         //Toast.makeText(ProductAvailDates.this,productquality,Toast.LENGTH_LONG).show();
        id=String.valueOf(bundle.getString("id"));

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(ProductAvailDates.this,CalendarDayDecoratorActivity.class);
                i.putExtra("pid",id);
                startActivity(i);
            }
        });
        recyclerView = (RecyclerView)findViewById(R.id.calentry);

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(calEntryAdpter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ProductAvailDates.this));
        single(id,sdate);

        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.hcontainer);
        mSwipeRefreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW);
        mSwipeRefreshLayout.setOnRefreshListener(new    SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(ProductAvailDates.this, "Refresh", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        single(id,sdate);
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });

        bookslot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInputDialog();

            }
        });
    }

    private void showInputDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
        View promptView = layoutInflater.inflate(R.layout.activity_addslot, null);
        alertDialogBuilder = new AlertDialog.Builder(this).create();
        alertDialogBuilder.setView(promptView);
        alertDialogBuilder.show();
        title=(TextView) promptView.findViewById(R.id.stitle);
        ed_sdate = (EditText) promptView.findViewById(R.id.sdate);
        ed_stime = (EditText) promptView.findViewById(R.id.stime);
        ed_edate = (EditText) promptView.findViewById(R.id.edate);
        ed_etime = (EditText) promptView.findViewById(R.id.etime);
        imgstime=(ImageView) promptView.findViewById(R.id.stimeimg);
        imgetime=(ImageView) promptView.findViewById(R.id.etimeimg);
        submit=(Button)promptView.findViewById(R.id.submit);
        DateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = outputFormat.parse(currdate);
            String outputDateStr = outputFormat.format(date);
            ed_sdate.setText(outputDateStr);
            ed_edate.setText(outputDateStr);

        } catch (ParseException e) {
            e.printStackTrace();
        }


       /* final DatePickerDialog.OnDateSetListener sdat = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub

               // myCalendar.set(2013, 05, 23);
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabe();
            }
        };

        final DatePickerDialog.OnDateSetListener edat = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabe1();
            }
        };
        imgsdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new DatePickerDialog(ProductAvailDates.this, sdat, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        imgedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new DatePickerDialog(ProductAvailDates.this, edat, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

*/        imgstime.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                new TimePickerDialog(ProductAvailDates.this, onStartTimeListener, myCalendar
                        .get(Calendar.HOUR), myCalendar.get(Calendar.MINUTE), false).show();
            }
        });
        imgetime.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                new TimePickerDialog(ProductAvailDates.this, onEndTimeListener, myCalendar
                        .get(Calendar.HOUR), myCalendar.get(Calendar.MINUTE), false).show();
            }
        });


        onStartTimeListener = new TimePickerDialog.OnTimeSetListener() {

            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

               /* if (hour == 0) {
                    hour += 12;
                    format = "AM";
                } else if (hour == 12) {
                    format = "PM";
                } else if (hour > 12) {
                    hour -= 12;
                    format = "PM";
                } else {
                    format = "AM";
                }
*/
                StringBuilder sb = new StringBuilder();
                String hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
                String minuteString = minute < 10 ? "0" + minute : "" + minute;

                sb.append(hourString).append( ":" ).append(minuteString)/*.append( ":" ).append("00")*/;
                ed_stime.setText(sb);
                //time.setText(hourOfDay + " : " + minute);
               // currenttime=time.getText().toString();
                myCalendar.set(Calendar.HOUR, hourOfDay);
                myCalendar.set(Calendar.MINUTE, minute);

            }
        };

        onEndTimeListener = new TimePickerDialog.OnTimeSetListener() {

            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                StringBuilder sb = new StringBuilder();
                String hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
                String minuteString = minute < 10 ? "0" + minute : "" + minute;

                sb.append(hourString).append( ":" ).append(minuteString)/*.append( ":" ).append("00")*/;
                ed_etime.setText(sb);
                //time.setText(hourOfDay + " : " + minute);
                // currenttime=time.getText().toString();
                myCalendar.set(Calendar.HOUR, hourOfDay);
                myCalendar.set(Calendar.MINUTE, minute);

            }
        };
    submit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        String stime=ed_stime.getText().toString();
        String etime=ed_etime.getText().toString();

        boolean isValid = true;
            View focusView = null;
            ed_stime.setError(null);
            if(TextUtils.isEmpty(stime)){
                ed_stime.setError(getString(R.string.error_field_required));
                focusView = ed_stime;
                isValid = false;
            }
            else if(TextUtils.isEmpty(etime)) {
                ed_etime.setError(getString(R.string.error_field_required));
                focusView = ed_etime;
                isValid = false;
            }

            if (isValid){

                starttime=ed_sdate.getText().toString()+" "+stime;
                endtime=ed_edate.getText().toString()+" "+etime;
                 Log.e("data",starttime);
                Log.e("data",endtime);


                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                try {
                    Date sDate = sdf.parse(starttime);
                    startMilliseconds = sDate.getTime();
                    Date eDate = sdf.parse(endtime);
                    endtMilliseconds = eDate.getTime();
                    slottitle=title.getText().toString();
                    updateSlot(slottitle,startMilliseconds,endtMilliseconds,productCalendarEntryId,productName,
                            productId,productQuantity,vendorName,vendorId,orderId,draggable,resizable,type,calendarEntryId,primary,secondary,nonAvailableDates);
                    System.out.println("Date in milli :: " + startMilliseconds);
                    //Toast.makeText(ProductAvailDates.this,String.valueOf(startMilliseconds),Toast.LENGTH_LONG).show();

                } catch (ParseException e) {
                    e.printStackTrace();
                }



            }
            else {
                // There was an error; don't attempt login and focus the first
                // form field with an error.
                focusView.requestFocus();
            }


       // alertDialogBuilder.dismiss();

    }
});



    }

    private void updateSlot(String slottitle, long startMilliseconds, long endtMilliseconds, String productCalendarEntryId, String productName, String productId, String productQuantity, String vendorName, String vendorId, String orderId, String draggable, String resizable, String type, String calendarEntryId, String primary, String secondary, List<NONAvailableDates> nonAvailableDates) {

        if (preferenceUtils.isLoggedIn()) {
            AppConstant.HEADER = "Bearer " + preferenceUtils.getAccessToken();
            Log.e("key",preferenceUtils.getAccessToken());
        }
        else {
            AppConstant.HEADER = "Basic Y2xpZW50YXBwOjEyMzQ1Ng==";
        }
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .header("authorization", AppConstant.HEADER)
                        .header("content-type", "application/json")
                        .method(original.method(), original.body())
                        .build();
                return chain.proceed(request);
            }
        });
        OkHttpClient client = httpClient.build();
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppController.BaseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        CalenderSaveApi saveApi = retrofit.create(CalenderSaveApi.class);

        JSONObject main=new JSONObject();
        try {
            main.put("productCalendarEntryId",productCalendarEntryId);
            main.put("productName",productName);
            main.put("productId",productId);
            main.put("productQuantity",productQuantity);
            main.put("vendorName",vendorName);
            main.put("vendorId",vendorId);
            JSONArray  productCalendarEntries= new JSONArray();
            JSONArray  array1= new JSONArray();

            JSONObject obj = new JSONObject();
            JSONObject colorObj = new JSONObject();
            colorObj.put("primary",primary);
            colorObj.put(" secondary",secondary);
            obj.put("calendarEntryId",calendarEntryId);
            obj.put("title",slottitle);
            obj.put("startsAt",startMilliseconds);
            obj.put("endsAt",endtMilliseconds);
            obj.put("color",colorObj);
            obj.put("orderId",orderId);

            productCalendarEntries.put(obj);
            main.put("productCalendarEntries",productCalendarEntries);
            main.put("nonAvailableDates",array1);
            main.put("draggable",draggable);
            main.put("resizable",resizable);
            main.put("type",type);
            Log.e("values",main.toString());
            Call<SingleEntry> listCall=saveApi.getUser(main.toString());
            listCall.enqueue(new Callback<SingleEntry>() {
                @Override
                public void onResponse(Call<SingleEntry> call, retrofit2.Response<SingleEntry> response) {

                    if(response.isSuccessful()){

                        alertDialogBuilder.dismiss();
                        Toast.makeText(ProductAvailDates.this,response.message(),Toast.LENGTH_LONG).show();

                    }
                    else {

                        Toast.makeText(ProductAvailDates.this,response.message(),Toast.LENGTH_LONG).show();

                    }
                }

                @Override
                public void onFailure(Call<SingleEntry> call, Throwable t) {

                    Toast.makeText(ProductAvailDates.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private void updateLabe() {
        String myFormat = "dd/MM/yyyy" + ""; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        ed_sdate.setText(sdf.format(myCalendar.getTime()));
        Log.e("date",String.valueOf(sdf.format(myCalendar.getTime())));
       // currentday=date.getText().toString();
    }

    private void updateLabe1() {
        String myFormat = "dd/MM/yyyy" +
                ""; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        ed_edate.setText(sdf.format(myCalendar.getTime()));
        Log.e("date",String.valueOf(sdf.format(myCalendar.getTime())));
        // currentday=date.getText().toString();
    }

    private void single(String id, String sdate) {

        if (preferenceUtils.isLoggedIn()) {
            AppConstant.HEADER = "Bearer " + preferenceUtils.getAccessToken();
            Log.e("key",preferenceUtils.getAccessToken());
        }
        else {
            AppConstant.HEADER = "Basic Y2xpZW50YXBwOjEyMzQ1Ng==";
        }
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .header("authorization", AppConstant.HEADER)
                        .header("content-type", "application/json")
                        .method(original.method(), original.body())
                        .build();
                return chain.proceed(request);
            }
        });
        OkHttpClient client = httpClient.build();
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppController.BaseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
         singleDateApi = retrofit.create(SingleDateApi.class);
        try {
            paramObjec = new JSONObject();
            paramObjec.put("productId",id);
            paramObjec.put("startDate",sdate);
            Call<SingleEntry> listCall=singleDateApi.getUser(paramObjec.toString());
            listCall.enqueue(new Callback<SingleEntry>() {
                @Override
                public void onResponse(Call<SingleEntry> call, retrofit2.Response<SingleEntry> response) {

                    if(response.isSuccessful()){
                        //Log.e("calue",(paramObjec.toString()));
                        singleEntry=response.body();
                        productCalendarEntryId= String.valueOf(singleEntry.getProductCalendarEntryId());
                         productName=String.valueOf(singleEntry.getProductName());
                         productId=String.valueOf(singleEntry.getProductId());
                         productQuantity=String.valueOf(singleEntry.getProductQuantity());
                         vendorName=String.valueOf(singleEntry.getVendorName());
                         vendorId=String.valueOf(singleEntry.getVendorId());
                        draggable=String.valueOf(singleEntry.getDraggable());
                        resizable=String.valueOf(singleEntry.getResizable());
                        type=String.valueOf(singleEntry.getType());
                        singleDateRes=response.body().getProductCalendarEntries();
                        nonAvailableDates=response.body().getNonAvailableDates();

                        Log.e("productCalendarEntryId",nonAvailableDates.toString());
                        int value=singleDateRes.size();
                        for(int i=0;i<value;i++){
                            calendarEntryId=String.valueOf(singleDateRes.get(i).getCalendarEntryId());
                            orderId=String.valueOf(singleDateRes.get(i).getOrderId());
                            Log.e("productCalendarEntryId",orderId);

                        }
                        Log.e("calue",String.valueOf(value));
                        if(value>0){
                            calEntryAdpter = new CalEntryAdpter(ProductAvailDates.this,singleDateRes);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setAdapter(calEntryAdpter);
                        }
                        else {

                            Toast.makeText(ProductAvailDates.this,"No Events are Present",Toast.LENGTH_LONG).show();
                        }

                    }
                    else {

                        Toast.makeText(ProductAvailDates.this,response.message(),Toast.LENGTH_LONG).show();

                    }
                }

                @Override
                public void onFailure(Call<SingleEntry> call, Throwable t) {

                    Toast.makeText(ProductAvailDates.this,t.getMessage(),Toast.LENGTH_LONG).show();

                }
            });

        } catch (JSONException e) {

            e.printStackTrace();
        }


    }
}
