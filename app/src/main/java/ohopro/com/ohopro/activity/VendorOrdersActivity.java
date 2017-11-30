package ohopro.com.ohopro.activity;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import ohopro.com.ohopro.R;
import ohopro.com.ohopro.adapter.VendorOrderPagerAdapter;

public class VendorOrdersActivity extends AppCompatActivity {
    private ViewPager viewpager;
    private TabLayout tabs;
    Toolbar toolbar;
    ImageView img_back;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bills);
        context = VendorOrdersActivity.this;
        initViewController();
    }

    private void initViewController() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        img_back = (ImageView) findViewById(R.id.img_back);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        final Drawable upArrow = ContextCompat.getDrawable(context, R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        toolbar.setNavigationIcon(upArrow);
        viewpager.setAdapter(new VendorOrderPagerAdapter(getSupportFragmentManager()));
        tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewpager);

    }

    public interface OrdersUpdate {
        void update();
    }
}
