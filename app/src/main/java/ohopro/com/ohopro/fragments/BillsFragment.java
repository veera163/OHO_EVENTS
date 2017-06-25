package ohopro.com.ohopro.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ohopro.com.ohopro.adapter.BillPagerAdapter;
import ohopro.com.ohopro.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link BillsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BillsFragment extends Fragment {
    TabLayout tabs;
    ViewPager viewpager;

    ArrayList<Fragment> fragments;
    ArrayList<String> strings;
    BillPagerAdapter billPagerAdapter;

    public BillsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment BillsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BillsFragment newInstance() {
        BillsFragment fragment = new BillsFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragments = new ArrayList<>();
        strings = new ArrayList<>();

        strings.add("Pending");
        strings.add("Approved");
        fragments.add(ListOfBillsFragment.newInstance("Pending"));
        fragments.add(ListOfBillsFragment.newInstance("Approved"));
        billPagerAdapter = new BillPagerAdapter(getFragmentManager(), fragments, strings);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bills, container, false);
        initViewController(view);
        viewpager.setAdapter(billPagerAdapter);
        tabs.setupWithViewPager(viewpager);

        return view;
    }

    private void initViewController(View view) {
        tabs = (TabLayout) view.findViewById(R.id.tabs);
        viewpager = (ViewPager) view.findViewById(R.id.viewpager);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
