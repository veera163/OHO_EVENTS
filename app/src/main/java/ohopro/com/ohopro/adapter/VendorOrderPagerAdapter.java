package ohopro.com.ohopro.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import ohopro.com.ohopro.fragments.VendorOrdersFragment;
import ohopro.com.ohopro.utility.AppConstant;

/**
 * Created by sai on 19-11-2017.
 */

public class VendorOrderPagerAdapter extends FragmentPagerAdapter {
    ArrayList<String> types;

    public VendorOrderPagerAdapter(FragmentManager fm) {
        super(fm);
        types = new ArrayList<>();
        types.add(AppConstant.VENDORONGOINGORDER);
        types.add(AppConstant.VENDORCMPLTORDER);
    }

    @Override
    public Fragment getItem(int position) {
        return VendorOrdersFragment.newInstance(types.get(position));
    }

    /*@Override
    public int getItemPosition(Object object) {

        VendorOrdersFragment vendorOrdersFragment = (VendorOrdersFragment) object;
        vendorOrdersFragment.update();
        return super.getItemPosition(object);
    }
*/
    @Override
    public int getCount() {
        return types.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return types.get(position);
    }
}
