/*
 * Copyright (c) 2016 Stacktips {link: http://stacktips.com}.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ohopro.com.ohopro.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Stream;

import ohopro.com.ohopro.Api.CalenderEntryApi;
import ohopro.com.ohopro.Api.SingleDateApi;
import ohopro.com.ohopro.Calender.CalendarListener;
import ohopro.com.ohopro.Calender.CustomCalendarView;
import ohopro.com.ohopro.Calender.DayDecorator;
import ohopro.com.ohopro.Calender.DayView;
import ohopro.com.ohopro.Calender.utils.CalendarUtils;
import ohopro.com.ohopro.R;
import ohopro.com.ohopro.adapter.CalEntryAdpter;
import ohopro.com.ohopro.domains.AppController;
import ohopro.com.ohopro.domains.CalenderEntryRes;
import ohopro.com.ohopro.domains.ProductCalendarEntry;
import ohopro.com.ohopro.domains.SingleDateRes;
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

public class CalendarDayDecoratorActivity extends AppCompatActivity {
    CustomCalendarView calendarView;
    private TextView selectedDateTv;
    String pid;
    String url;
    String title;
    String productquality;
    String dateString;
    private PreferenceUtils preferenceUtils;
    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    CalenderEntryApi availabilityApi;
    List<ProductCalendarEntry> Infos;
    DayView dayView;
     public  static String veera,cdate;
    int cout;
    long milliseconds,start,end;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_decorator);
        selectedDateTv = (TextView) findViewById(R.id.selected_date);
        preferenceUtils=new PreferenceUtils(CalendarDayDecoratorActivity.this);
        Infos=new ArrayList<ProductCalendarEntry>();
        dayView=new DayView(CalendarDayDecoratorActivity.this);
        Bundle bundle = getIntent().getExtras();
        pid = String.valueOf(bundle.getString("pid"));
        Log.e("pid",String.valueOf(pid));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //  availdates(pid);
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
       /* SimpleDateFormat timeStampFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
        Date myDate = new Date();
        cdate = timeStampFormat.format(myDate);
        try {
            Date d = timeStampFormat.parse(cdate);
            milliseconds = d.getTime();
            //selectedDateTv.setText(String.valueOf(milliseconds));
        } catch (ParseException e) {
            e.printStackTrace();
        }*/

        //Initialize CustomCalendarView from layout
        calendarView = (CustomCalendarView) findViewById(R.id.calendarview);

        //Initialize calendar with date
        Calendar currentCalendar = Calendar.getInstance(Locale.getDefault());

        //Show monday as first date of week
        calendarView.setFirstDayOfWeek(Calendar.MONDAY);

        //Show/hide overflow days of a month
        calendarView.setShowOverflowDate(false);

        //call refreshCalendar to update calendar the view
        calendarView.refreshCalendar(currentCalendar);

        //Handling custom calendar events
        calendarView.setCalendarListener(new CalendarListener() {
            @Override
            public void onDateSelected(Date date) {

/*
                if (CalendarUtils.isPastDay(date)) {
*/                  SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                    cdate=df.format(date);
                    SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        Date d = f.parse(cdate);
                        long milliseconds = d.getTime();
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                        dateString = formatter.format(new Date(milliseconds));
                        Intent i=new Intent(CalendarDayDecoratorActivity.this,ProductAvailDates.class);
                        i.putExtra("id",pid);
                        i.putExtra("data",dateString);
                        i.putExtra("productquality",productquality);
                        startActivity(i);
                       // Toast.makeText(CalendarDayDecoratorActivity.this,String.valueOf(dateString),Toast.LENGTH_LONG).show();
                        Log.e("veera",pid);
                       // veera.setText(String.valueOf(dateString));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


               /* } else {

                    selectedDateTv.setText("Selected date is disabled!");
                }*/
            }

            @Override
            public void onMonthChanged(Date date) {

                SimpleDateFormat df = new SimpleDateFormat("MM-yyyy");

                Toast.makeText(CalendarDayDecoratorActivity.this, df.format(date), Toast.LENGTH_SHORT).show();

            }
        });

        //adding calendar day decorators
        List<DayDecorator> decorators = new ArrayList<>();
        decorators.add(new DisabledColorDecorator());
        calendarView.setDecorators(decorators);
        calendarView.refreshCalendar(currentCalendar);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }

    private class DisabledColorDecorator implements DayDecorator {
        @Override
        public void decorate(final DayView dayView) {
            url="http://52.66.156.85/productCalendar/"+pid;
            // url="http://52.66.156.85/productCalendar/59c3a6c1e4b0be67cd3fd72a";
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
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(client)
                    .build();
            availabilityApi = retrofit.create(CalenderEntryApi.class);
            Call<CalenderEntryRes> listCall= availabilityApi.getData(url);
            listCall.enqueue(new Callback<CalenderEntryRes>() {
                @Override
                public void onResponse(Call<CalenderEntryRes> call, retrofit2.Response<CalenderEntryRes> response) {
                    if(response.isSuccessful()){

                        Infos= response.body().getProductCalendarEntries();
                        productquality=response.body().getProductQuantity();
                       /* calEntryAdpter = new CalEntryAdpter(CalendarDayDecoratorActivity.this,Infos);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setAdapter(calEntryAdpter);*/
                        for (int i=0;i<Infos.size();i++){
                            title=Infos.get(i).getTitle();
                            start= Infos.get(i).getStartsAt();
                            end=Infos.get(i).getEndsAt();
                           // Infos.get(i).getColor().getPrimary();
                          //  Infos.get(i).getColor().getSecondary();
                            Log.e("color",String.valueOf(Infos.get(i).getColor().getPrimary()));
                           // Log.e("ram",String.valueOf( Collections.frequency(Infos,String.valueOf(start))));
                            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                             veera=formatter.format(new Date(start));
                            //Log.e("hima",veera);
                               String date_to_string = formatter.format(dayView.getDate());
                                long timeInMilliseconds = dayView.getDate().getTime();
                               // Log.e("hima",String.valueOf(timeInMilliseconds));

                            if(veera.equals(date_to_string)){

                                    if(title.equals("Not Available")){
                                        List<String> list = new ArrayList<String>();
                                         list.add(veera);
                                        Log.e("veera", String.valueOf(list));

                                        Bitmap bmp = BitmapFactory.decodeResource(getResources(),
                                                R.mipmap.ic_circle);
                                        BitmapDrawable bitmapDrawable = new BitmapDrawable(bmp);
                                        bitmapDrawable.setGravity(Gravity.BOTTOM|Gravity.CENTER);
                                        dayView.setBackgroundDrawable(bitmapDrawable);
                                        /* int color = Color.parseColor("#a9afb9");

                                       dayView.setBackgroundColor(R.drawable.btn_back);*/

                                    }

                                   else  if(title.equals("Available")){

                                        Bitmap bmp = BitmapFactory.decodeResource(getResources(),
                                                R.mipmap.ic_circle_avail);
                                        BitmapDrawable bitmapDrawable = new BitmapDrawable(bmp);
                                        bitmapDrawable.setGravity(Gravity.BOTTOM|Gravity.CENTER);
                                        dayView.setBackgroundDrawable(bitmapDrawable);
                                   }

                               }
                        }

                    }

                }

                @Override
                public void onFailure(Call<CalenderEntryRes> call, Throwable t) {

                   // Toast.makeText(CalendarDayDecoratorActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
                    selectedDateTv.setText("No Events Are present ");
                    selectedDateTv.setVisibility(View.VISIBLE);

                }
            });



        }

    }
}
