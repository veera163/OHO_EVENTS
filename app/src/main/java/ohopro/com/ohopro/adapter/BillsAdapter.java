package ohopro.com.ohopro.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;

import ohopro.com.ohopro.R;
import ohopro.com.ohopro.domains.BillDomain;
import ohopro.com.ohopro.fragments.ListOfBillsFragment;

/**
 * Created by sai on 14-12-2016.
 */

public class BillsAdapter extends RecyclerView.Adapter<BillsAdapter.BillsViewHolder> {
    private final DisplayImageOptions displayImageOptions;
    ArrayList<BillDomain> billDomains;
    Context context;
    ListOfBillsFragment.BillsSelectionListener billsSelectionListener;

    public BillsAdapter(ArrayList<BillDomain> billDomains) {
        this.billDomains = billDomains;

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
    }

    @Override
    public BillsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        return new BillsViewHolder(layoutInflater.inflate(R.layout.row_bill, parent, false));
    }

    @Override
    public void onBindViewHolder(BillsViewHolder holder, final int position) {
        BillDomain billDomain = billDomains.get(position);
        holder.tv_bill_amount.setText(billDomain.getBillAmount());
        holder.tv_bill_number.setText(billDomain.getBillNumber());
        holder.tv_bill_date.setText(billDomain.getBillDate());
        holder.tv_status.setText(billDomain.getStatus());
        holder.tv_purpose.setText(billDomain.getPurpose());
        holder.tv_provider.setText(billDomain.getBillProvider());
        ImageLoader.getInstance().displayImage(billDomain.getBillImageUrl(),
                holder.img_bill);
        holder.ll_bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                billsSelectionListener.onBillSelected(billDomains.get(position));
            }
        });
    }

    public void registerAListener(ListOfBillsFragment.BillsSelectionListener billsSelectionListener) {
        this.billsSelectionListener = billsSelectionListener;
    }

    @Override
    public int getItemCount() {
        return billDomains.size();
    }

    public class BillsViewHolder extends RecyclerView.ViewHolder {
        TextView tv_bill_number, tv_bill_date, tv_bill_amount, tv_status, tv_purpose, tv_provider;
        LinearLayout ll_bill;
        ImageView img_bill;

        public BillsViewHolder(View itemView) {
            super(itemView);
            img_bill = (ImageView) itemView.findViewById(R.id.img_bill);
            ll_bill = (LinearLayout) itemView.findViewById(R.id.ll_bill);
            tv_bill_amount = (TextView) itemView.findViewById(R.id.tv_bill_amount);
            tv_provider = (TextView) itemView.findViewById(R.id.tv_provider);
            tv_purpose = (TextView) itemView.findViewById(R.id.tv_purpose);
            tv_bill_number = (TextView) itemView.findViewById(R.id.tv_bill_number);
            tv_bill_date = (TextView) itemView.findViewById(R.id.tv_bill_date);
            tv_status = (TextView) itemView.findViewById(R.id.tv_status);
        }
    }
}
