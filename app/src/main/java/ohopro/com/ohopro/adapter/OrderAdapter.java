package ohopro.com.ohopro.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ohopro.com.ohopro.Api.UpdateOrderApi;
import ohopro.com.ohopro.R;
import ohopro.com.ohopro.activity.ProductAvailDates;
import ohopro.com.ohopro.domains.AddressDomain;
import ohopro.com.ohopro.domains.AppController;
import ohopro.com.ohopro.domains.OrderDomain;
import ohopro.com.ohopro.domains.SingleEntry;
import ohopro.com.ohopro.domains.UpdateOrderRes;
import ohopro.com.ohopro.utility.AppConstant;
import ohopro.com.ohopro.utility.CustomAlertDialogSimple;
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
 * Created by sai on 21-11-2017.
 */

public class OrderAdapter extends
        RecyclerView.Adapter<OrderAdapter.OrderViewHolder> implements AdapterView.OnItemSelectedListener {
    ArrayList<OrderDomain> orderDomains;
    Context context;
    AlertDialog.Builder addressBuilder;
    AlertDialog addressDialog;
    long shipdate;
    String status;
    String shiptitle,con_num,turl;
    LinearLayout spinlay,consignlay,track;
    Spinner spinner,spinner2;
    private PreferenceUtils preferenceUtils;
    EditText ed_consign,ed_url;
    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    String origin="updateStatus";
    EditText ed_des;
    AlertDialog alertDialogBuilder;
    String item,orderId,orderPlacedId,description;
    public OrderAdapter(ArrayList<OrderDomain> orderDomains) {
        this.orderDomains = orderDomains;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.row_order, parent, false);
        preferenceUtils=new PreferenceUtils(context);
        addressBuilder = new AlertDialog.Builder(context);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {
        OrderDomain orderDomain = orderDomains.get(position);
        shipdate=(orderDomain.getOrderDate());
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        String dateString = formatter.format(new Date(shipdate));
        holder.tv_user_name.setText(orderDomain.getUserName());
        holder.tv_email.setText(orderDomain.getUserEmail());
        holder.order_id.setText(String.valueOf(orderDomain.getOrderId()));
        holder.tv_user_cnct_number.setText(orderDomain.getUserPhone());
        holder.tv_ordered_date.setText(dateString);
        holder.tv_product_name.setText(orderDomain.getProductName());
        status=String.valueOf(orderDomain.getStatus());
        Log.e("values",String.valueOf(orderDomain.getStatus()));
        holder.tv_calculations.setText("Rs. " + orderDomain.getAmountForUnit() + "x" + orderDomain.getQty() + " = Rs. " + orderDomain.getTotalAmount());
        holder.tv_status.setText(orderDomain.getStatusInDetail());
        orderId=String.valueOf(orderDomain.getOrderId());
        orderPlacedId=String.valueOf(orderDomain.getOrderPlacedId());
        holder.tv_shipping_address.setTag(orderDomain.getShippingAddress());
        holder.tv_shipping_address.setTextColor(Color.RED );
        holder.tv_shipping_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddressDomain addressDomain = (AddressDomain) view.getTag();
                displayAddress(addressDomain, "Shipping Address");
            }
        });
        holder.tv_billing_address.setTag(orderDomain.getBillingAddress());
        holder.tv_billing_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddressDomain addressDomain = (AddressDomain) view.getTag();
                displayAddress(addressDomain, "Billing Address");
            }
        });

        if(status.equals("ORDER_COMPLETED") || status.equals("ORDER_CANCELLED")|| status.equals("ORDER_ABORTED")){
            holder.change_order.setVisibility(View.GONE);
        }else{

            holder.change_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    showInputDialog();
                }
            });
        }


    }

    private void showInputDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View promptView = layoutInflater.inflate(R.layout.update_status, null);
        alertDialogBuilder = new AlertDialog.Builder(context).create();
        alertDialogBuilder.setView(promptView);
        alertDialogBuilder.show();
         spinner = (Spinner)promptView.findViewById(R.id.spinner);
        spinner2 = (Spinner)promptView.findViewById(R.id.spinner2);
        consignlay=(LinearLayout)promptView.findViewById(R.id.consignlay);
        track=(LinearLayout)promptView.findViewById(R.id.track);
        ed_consign=(EditText)promptView.findViewById(R.id.consignmentNumber);
        ed_url=(EditText)promptView.findViewById(R.id.trackingUrl);

        spinlay=(LinearLayout)promptView.findViewById(R.id.spinlay);
        Button update=(Button)promptView.findViewById(R.id.Update);
        Button cancel=(Button)promptView.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertDialogBuilder.dismiss();
            }
        });
        ed_des=(EditText)promptView.findViewById(R.id.textArea_information);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean isValid = true;
                View focusView = null;
                ed_des.setError(null);
                description=String.valueOf(ed_des.getText());
                con_num=String.valueOf(ed_consign.getText());
                turl=String.valueOf(ed_url.getText());
                if(TextUtils.isEmpty(description)){
                    ed_des.setError("This field is required");
                    focusView = ed_des;
                    isValid = false;
                }
                if (isValid){

                    if(item.equals("ORDER_SHIPPED")) {

                        Updateship(orderId, orderPlacedId, description, item, origin,con_num, shiptitle, turl);
                    }
                    else {

                        UpdateInfo(orderId, orderPlacedId, description, item,origin);

                    }

                }
                else {
                    // There was an error; don't attempt login and focus the first
                    // form field with an error.
                    focusView.requestFocus();
                }

            }
        });
        spinner.setOnItemSelectedListener(this);
        spinner2.setOnItemSelectedListener(this);

        List<String> categories = new ArrayList<String>();
        categories.add("ORDER_CREATED");
        categories.add("ORDER_ACCEPTED");
        categories.add("ORDER_IN_PROGRESS");
        categories.add("ORDER_CANCELLED");
        categories.add("ORDER_SHIPPED");
        categories.add("ORDER_DELIVERED");
        categories.add("ORDER_COMPLETED");
        categories.add("ORDER_ABORTED");
        categories.add("ORDER_RE_SCHEDULE_REQUESTED");

        List<String> shiping = new ArrayList<String>();
        shiping.add("ARAMEX");
        shiping.add("BLUE DART");
        shiping.add("DHL");
        shiping.add("DTDC");
        shiping.add("ECOM EXPRESS");
        shiping.add("FEDEX");
        shiping.add("FIRST FLIGHT");
        shiping.add("GO JAVAS");
        shiping.add("INDIA POST SERVICE");
        shiping.add("Other");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        ArrayAdapter<String> shipAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, shiping);

        // Drop down layout style - list view with radio button
        shipAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner2.setAdapter(shipAdapter);




    }

    private void Updateship(String orderId, String orderPlacedId, String description, String item, String con_num, String shiptitle, String turl,String origin) {

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

        UpdateOrderApi orderApi = retrofit.create(UpdateOrderApi.class);
        JSONObject main=new JSONObject();
        try {
            main.put("orderId",orderId);
            main.put("orderPlacedId",orderPlacedId);
            main.put("description",description);
            main.put("origin",origin);
            main.put("status",item);
            main.put("consignmentNumber",con_num);
            main.put("shippingOrganization",shiptitle);
            main.put("trackingUrl",turl);
            Log.e("values",String.valueOf(main));
            Call<UpdateOrderRes> listCall=orderApi.getUser(main.toString());
            listCall.enqueue(new Callback<UpdateOrderRes>() {
                @Override
                public void onResponse(Call<UpdateOrderRes> call, retrofit2.Response<UpdateOrderRes> response) {

                    if(response.isSuccessful()){

                        Toast.makeText(context,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        alertDialogBuilder.dismiss();
                    }
                    else {
                        Toast.makeText(context,response.body().getMessage(),Toast.LENGTH_SHORT).show();

                    }

                }

                @Override
                public void onFailure(Call<UpdateOrderRes> call, Throwable t) {

                    Toast.makeText(context,t.getMessage(),Toast.LENGTH_SHORT).show();
                    alertDialogBuilder.dismiss();
                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void UpdateInfo(String orderId, String orderPlacedId, String description, String item,String origin) {

      /*  Log.e("values",orderId);
        Log.e("values",orderPlacedId);
        Log.e("values",description);
        Log.e("values",item);*/

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

        UpdateOrderApi orderApi = retrofit.create(UpdateOrderApi.class);
        JSONObject main=new JSONObject();
        try {
            main.put("orderId",orderId);
            main.put("orderPlacedId",orderPlacedId);
            main.put("description",description);
            main.put("origin",origin);
            main.put("status",item);
            Log.e("values",String.valueOf(main));
            Call<UpdateOrderRes> listCall=orderApi.getUser(main.toString());
            listCall.enqueue(new Callback<UpdateOrderRes>() {
                @Override
                public void onResponse(Call<UpdateOrderRes> call, retrofit2.Response<UpdateOrderRes> response) {

                    if(response.isSuccessful()){

                        Toast.makeText(context,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        alertDialogBuilder.dismiss();
                    }
                    else {
                        Toast.makeText(context,response.body().getMessage(),Toast.LENGTH_SHORT).show();

                    }

                }

                @Override
                public void onFailure(Call<UpdateOrderRes> call, Throwable t) {

                    Toast.makeText(context,t.getMessage(),Toast.LENGTH_SHORT).show();
                    alertDialogBuilder.dismiss();
                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void displayAddress(AddressDomain addressDomain, String tittle) {
        if (addressDomain != null) {
            TextView tv_tittle, tv_contact_name, tv_address_line_1, tv_address_line_2, tv_city;
            TextView tv_other_city, tv_state, tv_country, tv_pin_code, tv_phone_number;
            Button btn_ok;

            View addressView = LayoutInflater.from(context).inflate(R.layout.dialog_address, null);
            addressBuilder.setView(addressView);

            tv_tittle = (TextView) addressView.findViewById(R.id.tv_tittle);
            tv_contact_name = (TextView) addressView.findViewById(R.id.tv_contact_name);
            tv_address_line_1 = (TextView) addressView.findViewById(R.id.tv_address_line_1);
            tv_address_line_2 = (TextView) addressView.findViewById(R.id.tv_address_line_2);
            tv_city = (TextView) addressView.findViewById(R.id.tv_city);
            tv_other_city = (TextView) addressView.findViewById(R.id.tv_other_city);
            tv_state = (TextView) addressView.findViewById(R.id.tv_state);
            tv_country = (TextView) addressView.findViewById(R.id.tv_country);
            tv_pin_code = (TextView) addressView.findViewById(R.id.tv_pin_code);
            tv_phone_number = (TextView) addressView.findViewById(R.id.tv_phone_number);
            btn_ok = (Button) addressView.findViewById(R.id.btn_ok);
            addressDialog = addressBuilder.create();
            addressDialog.show();

            tv_contact_name.setText(addressDomain.getContactName());
            tv_address_line_1.setText(addressDomain.getAddressLine1());
            tv_address_line_2.setText(addressDomain.getAddressLine2());
            tv_city.setText(addressDomain.getCity());
            tv_other_city.setText(addressDomain.getOtherCity());
            tv_state.setText(addressDomain.getState());
            tv_country.setText(addressDomain.getCountry());
            tv_pin_code.setText(addressDomain.getPincode());
            tv_phone_number.setText(addressDomain.getPhoneNumber());
            tv_tittle.setText(tittle);

            btn_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addressDialog.dismiss();
                }
            });

        } else {

            new CustomAlertDialogSimple(context).showAlertDialog("Error", "No Address Available");
        }
    }

    @Override
    public int getItemCount() {
        return orderDomains.size();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {



        Spinner spinner = (Spinner) adapterView;
        if(spinner.getId() == R.id.spinner)
        {
            item = adapterView.getItemAtPosition(i).toString();
            if(item.equals("ORDER_SHIPPED")){
                spinlay.setVisibility(View.VISIBLE);
                consignlay.setVisibility(View.VISIBLE);

            }
            else {

                spinlay.setVisibility(View.GONE);
                consignlay.setVisibility(View.GONE);

            }

        }
        else if(spinner.getId() == R.id.spinner2)
        {
            shiptitle = adapterView.getItemAtPosition(i).toString();
            if(shiptitle.equals("Other")){
                track.setVisibility(View.VISIBLE);
            }
            else {
                track.setVisibility(View.GONE);
            }
           // Log.e("time",time);
        }

        // Showing selected spinner item
      //  Toast.makeText(context, "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView tv_user_name, tv_user_cnct_number, tv_email, tv_ordered_date,order_id;
        TextView tv_product_name, tv_calculations, tv_status, tv_billing_address, tv_shipping_address;
        Button change_order;

        public OrderViewHolder(View itemView) {
            super(itemView);
            tv_billing_address = (TextView) itemView.findViewById(R.id.tv_billing_address);
            tv_shipping_address = (TextView) itemView.findViewById(R.id.tv_shipping_address);
            tv_status = (TextView) itemView.findViewById(R.id.tv_status);
            tv_user_name = (TextView) itemView.findViewById(R.id.tv_user_name);
            order_id = (TextView) itemView.findViewById(R.id.order_id);
            tv_user_cnct_number = (TextView) itemView.findViewById(R.id.tv_user_cnct_number);
            tv_email = (TextView) itemView.findViewById(R.id.tv_email);
            tv_ordered_date = (TextView) itemView.findViewById(R.id.tv_ordered_date);
            tv_product_name = (TextView) itemView.findViewById(R.id.tv_product_name);
            tv_calculations = (TextView) itemView.findViewById(R.id.tv_calculations);
            change_order=(Button)itemView.findViewById(R.id.changeorder);
        }
    }
}
