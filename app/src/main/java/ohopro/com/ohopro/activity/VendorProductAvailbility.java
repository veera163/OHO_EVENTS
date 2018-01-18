package ohopro.com.ohopro.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ohopro.com.ohopro.Api.ProductAvailabilityApi;
import ohopro.com.ohopro.Holder.ClickListener;
import ohopro.com.ohopro.Holder.RecyclerTouchListener;
import ohopro.com.ohopro.R;
import ohopro.com.ohopro.adapter.ProductAvailabilityAdpter;
import ohopro.com.ohopro.domains.AppController;
import ohopro.com.ohopro.domains.ProductAvailabityRes;
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

/**
 * Created by home on 12/27/2017.
 */

public class VendorProductAvailbility extends AppCompatActivity {
    List<ProductAvailabityRes> infos;
    RecyclerView recyclerView;
    ProductAvailabilityAdpter availabilityAdpter;
    ProductAvailabilityApi availabilityApi;
    private PreferenceUtils preferenceUtils;
    ImageView img_back;
    String userid,url,pid;
    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productavailablity);
        img_back=(ImageView)findViewById(R.id.img_back);
        preferenceUtils=new PreferenceUtils(VendorProductAvailbility.this);
        userid=String.valueOf(preferenceUtils.getUserId());
        Log.e("user",preferenceUtils.getUserId());
        infos=new ArrayList<ProductAvailabityRes>();
        recyclerView = (RecyclerView)findViewById(R.id.lst_vendor_productAvaill);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(availabilityAdpter);
        recyclerView.setLayoutManager(new LinearLayoutManager(VendorProductAvailbility.this));
        productavail(userid);
        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.hcontainer);
        mSwipeRefreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW);
        mSwipeRefreshLayout.setOnRefreshListener(new    SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(VendorProductAvailbility.this, "Refresh", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        productavail(userid);
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(VendorProductAvailbility.this,recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                pid= String.valueOf(infos.get(position).getProductId());
                Log.e("pid",pid);
                Intent i1=new Intent(VendorProductAvailbility.this,CalendarDayDecoratorActivity.class);
                i1.putExtra("pid",pid);

                startActivity(i1);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(VendorProductAvailbility.this,DashBoardActivity.class);
                startActivity(i);
            }
        });
    }

    private void productavail(String userid) {

        url="http://52.66.156.85/product/vendor/Rent/"+userid;
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

        availabilityApi = retrofit.create(ProductAvailabilityApi.class);
        Call<List<ProductAvailabityRes>>  listCall= availabilityApi.getData(url);
        listCall.enqueue(new Callback<List<ProductAvailabityRes>>() {
            @Override
            public void onResponse(Call<List<ProductAvailabityRes>> call, retrofit2.Response<List<ProductAvailabityRes>> response) {

                if(response.isSuccessful()){
                    infos=response.body();
                    availabilityAdpter = new ProductAvailabilityAdpter(VendorProductAvailbility.this,infos);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(availabilityAdpter);
                }
                else {

                    Toast.makeText(VendorProductAvailbility.this,response.message(),Toast.LENGTH_LONG).show();

                }

            }
            @Override
            public void onFailure(Call<List<ProductAvailabityRes>> call, Throwable t) {

                Toast.makeText(VendorProductAvailbility.this,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }


}
