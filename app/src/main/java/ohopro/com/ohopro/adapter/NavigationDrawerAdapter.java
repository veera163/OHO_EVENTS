package ohopro.com.ohopro.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;

import ohopro.com.ohopro.R;

/**
 * Created by sai on 03-02-2017.
 */

public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.ViewHolder> {
    ArrayList<String> strings;
    NavigationListener navigationListener;

    public interface NavigationListener {
        void clickOnItem(String s);
    }

    public NavigationDrawerAdapter(ArrayList<String> strings) {
        this.strings = strings;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        navigationListener = (NavigationListener) context;
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return new ViewHolder(layoutInflater.inflate(R.layout.row_navigator, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.tv_type.setText(strings.get(position));
        holder.ll_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigationListener.clickOnItem(holder.tv_type.getText().toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return strings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_type;
        FrameLayout ll_type;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_type = (TextView) itemView.findViewById(R.id.tv_type);
            ll_type = (FrameLayout) itemView.findViewById(R.id.ll_type);
        }
    }
}
