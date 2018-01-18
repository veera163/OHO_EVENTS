package ohopro.com.ohopro.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import ohopro.com.ohopro.Holder.Holder;
import ohopro.com.ohopro.R;
import ohopro.com.ohopro.activity.CalendarDayDecoratorActivity;
import ohopro.com.ohopro.domains.CalenderEntryRes;
import ohopro.com.ohopro.domains.ProductCalendarEntry;
import ohopro.com.ohopro.domains.SingleDateRes;

/**
 * Created by home on 1/2/2018.
 */

public class CalEntryAdpter extends RecyclerView.Adapter<Holder> {

    private Context context;
    private List<SingleDateRes> entryList;
    Long start,end;

    public CalEntryAdpter(Context context, List<SingleDateRes> entryList) {
        this.context = context;
        this.entryList = entryList;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.entrylist, parent, false);
        return new Holder(itemView);    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {



      /*  holder.start.setText(String.valueOf(entryList.get(position).getStartsAt()));
        holder.end.setText(String.valueOf(entryList.get(position).getEndsAt()));
        holder.avail.setText(entryList.get(position).getTitle());*/

        start= Long.valueOf(entryList.get(position).getStartsAt());
        end= Long.valueOf(entryList.get(position).getEndsAt());
        SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String cdate=form.format(new Date(start));
        String fdate=form.format( new Date(end));
            holder.start.setText(cdate);
            holder.end.setText(fdate);
            holder.avail.setText(entryList.get(position).getTitle());

    }
    @Override
    public int getItemCount() {

        return entryList.size();
    }
}
