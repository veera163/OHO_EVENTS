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
import ohopro.com.ohopro.domains.LeaveResponse;

/**
 * Created by sai on 31-12-2016.
 */

public class LeavesAdapter extends RecyclerView.Adapter<LeavesAdapter.LeavesViewHolder> {

    ArrayList<LeaveResponse> leaveResponses;
    private Context context;
    private LeavesListener leavesListener;

    public LeavesAdapter(ArrayList<LeaveResponse> leaveResponses) {
        this.leaveResponses = leaveResponses;
    }

    @Override
    public LeavesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        return new LeavesAdapter.LeavesViewHolder(layoutInflater.inflate(R.layout.row_leave, parent, false));
    }

    @Override
    public void onBindViewHolder(LeavesViewHolder holder, int position) {
        final LeaveResponse leaveResponse = leaveResponses.get(position);

        holder.tv_start_date.setText(leaveResponse.getStartDate());
        holder.tv_end_date.setText(leaveResponse.getEndDate());
        holder.tv_num_of_days.setText(leaveResponse.getNumberOfDays());
        holder.tv_status.setText(leaveResponse.getStatus());

        holder.ll_bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                leavesListener.onLeaveClick(leaveResponse);
            }
        });
    }

    @Override
    public int getItemCount() {
        return leaveResponses.size();
    }

    public void setLeavesListener(LeavesListener leavesListener) {
        this.leavesListener = leavesListener;
    }

    public class LeavesViewHolder extends RecyclerView.ViewHolder {
        TextView tv_num_of_days, tv_end_date, tv_start_date, tv_status;
        LinearLayout ll_bill;

        public LeavesViewHolder(View itemView) {
            super(itemView);
            ll_bill = (LinearLayout) itemView.findViewById(R.id.ll_bill);
            tv_start_date = (TextView) itemView.findViewById(R.id.tv_start_date);
            tv_num_of_days = (TextView) itemView.findViewById(R.id.tv_num_of_days);
            tv_end_date = (TextView) itemView.findViewById(R.id.tv_end_date);
            tv_status = (TextView) itemView.findViewById(R.id.tv_status);
        }
    }

    public interface LeavesListener {
        void onLeaveClick(LeaveResponse leaveResponse);
    }
}
