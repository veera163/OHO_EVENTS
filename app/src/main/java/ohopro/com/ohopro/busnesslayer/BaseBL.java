package ohopro.com.ohopro.busnesslayer;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import ohopro.com.ohopro.webaccess.Response;
import ohopro.com.ohopro.webaccess.WebAccessListner;


/**
 * Created by Sai on 9/16/2015.
 */

/**
 * this class contains the control all over the Business Layer Classes
 **/
public class BaseBL implements WebAccessListner {

    DataListener listener;
    public Context mContext;

    public BaseBL(DataListener listener, Context mContext) {
        this.listener = listener;
        this.mContext = mContext;
    }

    @Override
    public void dataDownloaded(Response data) {

        if (listener instanceof Fragment) {
            ((Fragment) listener).getActivity().runOnUiThread(new DataRetreivedRunnable(listener, data));
        } else if (listener instanceof Activity) {
            ((Activity) listener).runOnUiThread(new DataRetreivedRunnable(listener, data));
        }
    }

    @Override
    public void opennetworksetting() {

        if (listener != null) {
            listener.opennetworksetting();
        }
    }

    class DataRetreivedRunnable implements Runnable {

        DataListener listener;
        Response data;

        DataRetreivedRunnable(DataListener listener, Response data) {

            this.listener = listener;
            this.data = data;
        }

        @Override
        public void run() {

            listener.dataRetreived(data);
        }
    }
}
