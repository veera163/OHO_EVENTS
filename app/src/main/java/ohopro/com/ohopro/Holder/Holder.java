package ohopro.com.ohopro.Holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import ohopro.com.ohopro.R;

/**
 * Created by veera on 27/6/2017.
 */

public class Holder extends RecyclerView.ViewHolder {

   // public ImageView currntimg;

   // public ImageView start,haif_start;

    public TextView pname,group,serviceitem,category,status,start,end,avail;
    public  ImageView pimage;

    public Holder(View itemView) {

        super(itemView);
        pname=(TextView)itemView.findViewById(R.id.pname);
        group=(TextView)itemView.findViewById(R.id.group);
        serviceitem=(TextView)itemView.findViewById(R.id.serviceitem);
        category=(TextView)itemView.findViewById(R.id.category);
        status=(TextView)itemView.findViewById(R.id.status);
        start=(TextView)itemView.findViewById(R.id.start);
        end=(TextView)itemView.findViewById(R.id.end);
        avail=(TextView)itemView.findViewById(R.id.avail);


        pimage=(ImageView)itemView.findViewById(R.id.pimage);
    }
}
