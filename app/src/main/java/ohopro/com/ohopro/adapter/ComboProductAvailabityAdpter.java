package ohopro.com.ohopro.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import ohopro.com.ohopro.Holder.Holder;
import ohopro.com.ohopro.R;
import ohopro.com.ohopro.activity.FullImage;
import ohopro.com.ohopro.domains.ComboRes;
import ohopro.com.ohopro.domains.ProductAvailabityRes;

/**
 * Created by home on 1/4/2018.
 */

public class ComboProductAvailabityAdpter extends RecyclerView.Adapter<Holder> {

    private Context context;
    private List<ComboRes> completedInfos;
    ProductAvailabityRes res;
    String start_id;
    String url;
    public ComboProductAvailabityAdpter(Context context, List<ComboRes> completedInfos) {
        this.context = context;
        this.completedInfos = completedInfos;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.productavilability_list, parent, false);
        return new Holder(itemView);    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {
        holder.pname.setText(completedInfos.get(position).getProductComboName());
        holder.group.setText(completedInfos.get(position).getProductGroup());
        holder.serviceitem.setText(completedInfos.get(position).getProductOrServiceCategory());
        holder.category.setText(completedInfos.get(position).getCategory());
        holder.status.setText(completedInfos.get(position).getStatus());
        if(completedInfos.get(position).getProductImages()==null){

        }
        else {
            Glide.with(context)
                    .load(completedInfos.get(position).getProductImages().get(0).getUrl())
                    .crossFade()
                    .thumbnail(0.5f)
                    .error(R.mipmap.logo)      // optional
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.pimage);
        }

    }

    @Override
    public int getItemCount() {

        return completedInfos.size();
    }
}

