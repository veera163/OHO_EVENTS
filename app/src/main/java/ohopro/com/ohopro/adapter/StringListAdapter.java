package ohopro.com.ohopro.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ohopro.com.ohopro.R;

/**
 * Created by sai on 12-02-2017.
 */

public class StringListAdapter extends RecyclerView.Adapter<StringListAdapter.StringListViewHolder> {

    ArrayList<String> strings;

    public StringListAdapter(ArrayList<String> strings) {
        this.strings = strings;
    }

    @Override
    public StringListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.row_string_adapter, null);
        return new StringListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StringListViewHolder holder, final int position) {
        holder.tv_url.setText(strings.get(position));
        holder.img_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strings.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return strings.size();
    }

    public class StringListViewHolder extends RecyclerView.ViewHolder {
        TextView tv_url;
        ImageView img_remove;


        public StringListViewHolder(View itemView) {
            super(itemView);
            tv_url = (TextView) itemView.findViewById(R.id.tv_url);
            img_remove = (ImageView) itemView.findViewById(R.id.img_remove);
        }
    }
}
