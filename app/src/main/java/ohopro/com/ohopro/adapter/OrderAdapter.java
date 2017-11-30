package ohopro.com.ohopro.adapter;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import ohopro.com.ohopro.R;
import ohopro.com.ohopro.domains.AddressDomain;
import ohopro.com.ohopro.domains.OrderDomain;
import ohopro.com.ohopro.utility.CustomAlertDialogSimple;

/**
 * Created by sai on 21-11-2017.
 */

public class OrderAdapter extends
        RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    ArrayList<OrderDomain> orderDomains;
    Context context;
    AlertDialog.Builder addressBuilder;
    AlertDialog addressDialog;

    public OrderAdapter(ArrayList<OrderDomain> orderDomains) {
        this.orderDomains = orderDomains;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.row_order, parent, false);
        addressBuilder = new AlertDialog.Builder(context);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {
        OrderDomain orderDomain = orderDomains.get(position);

        holder.tv_user_name.setText(orderDomain.getUserName());
        holder.tv_email.setText(orderDomain.getUserEmail());
        holder.tv_user_cnct_number.setText(orderDomain.getUserPhone());
        holder.tv_ordered_date.setText(String.valueOf(orderDomain.getOrderDate()));
        holder.tv_product_name.setText(orderDomain.getProductName());
        holder.tv_calculations.setText("Rs. " + orderDomain.getAmountForUnit() + "x" + orderDomain.getQty() + " = Rs. " + orderDomain.getTotalAmount());
        holder.tv_status.setText(orderDomain.getStatusDescription());
        holder.tv_shipping_address.setTag(orderDomain.getShippingAddress());
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

            tv_contact_name.setText(addressDomain.getContactName());
            tv_address_line_1.setText(addressDomain.getAddressLine1());
            tv_address_line_2.setText(addressDomain.getAddressLine2());
            tv_city.setText(addressDomain.getCity());
            tv_other_city.setText(addressDomain.getOtherCity());
            tv_state.setText(addressDomain.getState());
            tv_country.setText(addressDomain.getCountry());
            tv_pin_code.setText(addressDomain.getPincode());
            tv_phone_number.setText(addressDomain.getPhoneNumber());
            btn_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addressDialog.dismiss();
                }
            });
            tv_tittle.setText(tittle);
            addressDialog.show();

        } else {
            new CustomAlertDialogSimple(context).showAlertDialog("Error", "No Address Available");
        }
    }

    @Override
    public int getItemCount() {
        return orderDomains.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView tv_user_name, tv_user_cnct_number, tv_email, tv_ordered_date;
        TextView tv_product_name, tv_calculations, tv_status, tv_billing_address, tv_shipping_address;

        public OrderViewHolder(View itemView) {
            super(itemView);
            tv_billing_address = (TextView) itemView.findViewById(R.id.tv_billing_address);
            tv_shipping_address = (TextView) itemView.findViewById(R.id.tv_shipping_address);
            tv_status = (TextView) itemView.findViewById(R.id.tv_status);
            tv_user_name = (TextView) itemView.findViewById(R.id.tv_user_name);
            tv_user_cnct_number = (TextView) itemView.findViewById(R.id.tv_user_cnct_number);
            tv_email = (TextView) itemView.findViewById(R.id.tv_email);
            tv_ordered_date = (TextView) itemView.findViewById(R.id.tv_ordered_date);
            tv_product_name = (TextView) itemView.findViewById(R.id.tv_product_name);
            tv_calculations = (TextView) itemView.findViewById(R.id.tv_calculations);
        }
    }
}
