package ohopro.com.ohopro.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import ohopro.com.ohopro.R;
import ohopro.com.ohopro.domains.VendorReqDomain;

/**
 * Created by sai on 28-01-2017.
 */

public class VendorReqsAdapter extends RecyclerView.Adapter<VendorReqsAdapter.VendorReqsViewHolder> {

    ArrayList<VendorReqDomain> vendorReqDomains;
    OnVendorFormClick onVendorFormClick;

    public VendorReqsAdapter(ArrayList<VendorReqDomain> vendorReqDomains, OnVendorFormClick onVendorFormClick) {
        this.vendorReqDomains = vendorReqDomains;
        this.onVendorFormClick = onVendorFormClick;
    }

    @Override
    public VendorReqsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = ((LayoutInflater) parent
                .getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.row_vendor, parent, false);

        return new VendorReqsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VendorReqsViewHolder holder, int position) {
        final VendorReqDomain vendorReqDomain = vendorReqDomains.get(position);
        holder.tv_vendor_name.setText(
                vendorReqDomain.getTitle() + " " + vendorReqDomain.getFirstName() + " " + vendorReqDomain.getSurname()
        );

        holder.tv_status.setText(vendorReqDomain.getStatus());
        holder.tv_service.setText(vendorReqDomain.getProductsOrServices());
        holder.tv_service_type.setText(vendorReqDomain.getProductOrServiceType());
        holder.ll_vendorform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onVendorFormClick.onClickOnVendorForm(vendorReqDomain);
            }
        });
    }

    @Override
    public int getItemCount() {
        return vendorReqDomains.size();
    }

    class VendorReqsViewHolder extends RecyclerView.ViewHolder {
        TextView tv_vendor_name, tv_service, tv_service_type, tv_status;
        LinearLayout ll_vendorform;

        VendorReqsViewHolder(View itemView) {
            super(itemView);
            tv_service = (TextView) itemView.findViewById(R.id.tv_service);
            tv_vendor_name = (TextView) itemView.findViewById(R.id.tv_vendor_name);
            tv_service_type = (TextView) itemView.findViewById(R.id.tv_service_type);
            tv_status = (TextView) itemView.findViewById(R.id.tv_status);
            ll_vendorform = (LinearLayout) itemView.findViewById(R.id.ll_vendorform);
        }
    }

    public interface OnVendorFormClick {
        public void onClickOnVendorForm(VendorReqDomain vendorReqDomain);
    }
}
