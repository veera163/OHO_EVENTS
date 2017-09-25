package ohopro.com.ohopro.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import ohopro.com.ohopro.R;
import ohopro.com.ohopro.adapter.NavigationDrawerAdapter;
import ohopro.com.ohopro.appserviceurl.ServiceURL;
import ohopro.com.ohopro.busnesslayer.CommonBL;
import ohopro.com.ohopro.busnesslayer.DataListener;
import ohopro.com.ohopro.domains.DashBoardStatesDomain;
import ohopro.com.ohopro.fragmentDialogs.ChangePasswordDialog;
import ohopro.com.ohopro.fragments.HomeFragment;
import ohopro.com.ohopro.fragments.NotificationsFragment;
import ohopro.com.ohopro.fragments.ProfileFragment;
import ohopro.com.ohopro.fragments.UploadAgreementFragment;
import ohopro.com.ohopro.fragments.UploadServiceDocumentFragment;
import ohopro.com.ohopro.services.AccessTokenService;
import ohopro.com.ohopro.utility.AppConstant;
import ohopro.com.ohopro.utility.CustomAlertDialog;
import ohopro.com.ohopro.utility.LoggerUtils;
import ohopro.com.ohopro.utility.PreferenceUtils;
import ohopro.com.ohopro.views.BadgeDrawable;
import ohopro.com.ohopro.views.CustomProgressLoader;
import ohopro.com.ohopro.webaccess.Response;
import ohopro.com.ohopro.webaccess.ServiceMethods;

public class DashBoardActivity extends AppCompatActivity
        implements CustomAlertDialog.DialogController,
        ChangePasswordDialog.ChangePasswordListener,
        DataListener, NavigationDrawerAdapter.NavigationListener {

    private Toolbar toolbar;
    private CustomProgressLoader customProgressLoader;
    private FragmentManager fragmentTransaction;
    private Fragment fragment = HomeFragment.newInstance();
    private PreferenceUtils preferenceUtils;
    private String tag;
    private final String HOMEFRAGMENT = "Home";
    private final String PROFILEFRAGMENT = "Profile";
    private final String UPLOADAGREEMENT = "uploadAgreement";
    private final String BILLSFRAGMENT = "bills";
    private final String NOTIFICATIONSFRAG = "notifications";
    private DrawerLayout navigation_drawer;
    private BottomNavigationView bottom_nav_view;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private CustomAlertDialog customAlertDialog;
    private final String SIGNOUT = "signout";
    private final String EXIT = "exit";
    private Context context;
    private ArrayList<String> strings = new ArrayList<>();
    private RecyclerView left_drawer;
    private boolean homeEnabled = false;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (homeEnabled)
            actionBarDrawerToggle.syncState();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        initViewController();
        context = DashBoardActivity.this;
        customProgressLoader = new CustomProgressLoader(context);
        preferenceUtils = new PreferenceUtils(context);

        if (preferenceUtils.getUserType().equalsIgnoreCase(AppConstant.ROLEVENDOR)) {
            //getDashBoardStates();
            homeEnabled = true;
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            actionBarDrawerToggle = new ActionBarDrawerToggle(this, navigation_drawer, toolbar,
                    R.string.accept, R.string.accept) {
                @Override
                public void onDrawerOpened(View drawerView) {
                    super.onDrawerOpened(drawerView);
                    invalidateOptionsMenu();
                }

                @Override
                public void onDrawerClosed(View drawerView) {
                    super.onDrawerClosed(drawerView);
                    invalidateOptionsMenu();
                }
            };

            navigation_drawer.addDrawerListener(actionBarDrawerToggle);
            createDataForNavigation();
            left_drawer.setAdapter(new NavigationDrawerAdapter(strings));

        } else {
            homeEnabled = false;
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(false);
            navigation_drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
        updatingAccessToken();

        LoggerUtils.info(DashBoardActivity.class.getSimpleName(), "refresh token" + preferenceUtils.getRefreshToken());
        customAlertDialog = new CustomAlertDialog();
        fragmentTransaction = getSupportFragmentManager();
        fragmentTransaction.beginTransaction()
                .add(R.id.ll_fragment, fragment, HOMEFRAGMENT)
                .commit();
        MenuItem itemCart = bottom_nav_view.getMenu().findItem(R.id.notifications);
        LayerDrawable icon = (LayerDrawable) itemCart.getIcon();
        setBadgeCount(context, icon, "9");

        bottom_nav_view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        tag = HOMEFRAGMENT;
                        fragment = HomeFragment.newInstance();
                        fragmentTransaction.beginTransaction()
                                .replace(R.id.ll_fragment, fragment, HOMEFRAGMENT)
                                .commit();
                        break;

                    case R.id.profile:
                        tag = PROFILEFRAGMENT;
                        fragment = ProfileFragment.newInstance();
                        fragmentTransaction.beginTransaction()
                                .replace(R.id.ll_fragment, fragment, PROFILEFRAGMENT)
                                .commit();
                        break;

                /*    case R.id.bills:
                        tag = BILLSFRAGMENT;
                        fragment = BillsFragment.newInstance();
                        fragmentTransaction.beginTransaction()
                                .replace(R.id.ll_fragment, fragment, BILLSFRAGMENT)
                                .commit();
                        break;
*/
                    case R.id.notifications:
                        tag = NOTIFICATIONSFRAG;
                        fragment = NotificationsFragment.newInstance();
                        fragmentTransaction.beginTransaction()
                                .replace(R.id.ll_fragment, fragment, NOTIFICATIONSFRAG)
                                .commit();
                        break;
                }
                return true;
            }
        });


    }

    private void createDataForNavigation() {
        strings.add("Dash Board");
        strings.add("Products");
        strings.add("Food Menu Items");
        strings.add("Upload Service Document");
        strings.add("Upload Agreement Document");
        strings.add("Product Availability");
        strings.add("Product Combo");
    }

    private void getDashBoardStates() {
        if (new CommonBL(this, context).getDasBoardStates(createUrl())) {
            //customProgressLoader.showProgressDialog();
        }
    }

    private String createUrl() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ServiceURL.getRequestUrl(ServiceMethods.WS_APP_DASHBOARDSTATE));
        stringBuilder.append(preferenceUtils.getUserId());
        return stringBuilder.toString();
    }

    private void updatingAccessToken() {
        Intent intent = new Intent(context, AccessTokenService.class);
        startService(intent);
    }

    private void setBadgeCount(Context context, LayerDrawable icon, String count) {
        BadgeDrawable badge;
        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_badge);
        if (reuse != null && reuse instanceof BadgeDrawable) {
            badge = (BadgeDrawable) reuse;
        } else {
            badge = new BadgeDrawable(context);
        }
        badge.setCount(count);
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_badge, badge);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tittlemenu, menu);
        return true;
    }


    private void initViewController() {
        left_drawer = (RecyclerView) findViewById(R.id.left_drawer);
        left_drawer.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        navigation_drawer = (DrawerLayout) findViewById(R.id.navigation_drawer);
        navigation_drawer.closeDrawers();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        bottom_nav_view = (BottomNavigationView) findViewById(R.id.bottom_nav_view);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.exit:
                Bundle bundle = new Bundle();
                bundle.putString(CustomAlertDialog.MESSAGE, "would you Like to close this App");
                customAlertDialog.setArguments(bundle);
                customAlertDialog.show(getSupportFragmentManager(), EXIT);
                break;
            case R.id.signout:
                bundle = new Bundle();
                bundle.putString(CustomAlertDialog.MESSAGE, "would you Like to close this App");
                customAlertDialog.setArguments(bundle);
                customAlertDialog.show(getSupportFragmentManager(), SIGNOUT);
                break;
        }
        return true;
    }

    public void gotoBillsActivity() {

        Intent intent = new Intent(context, BillsActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        if (AppConstant.currentFragmentTag.equalsIgnoreCase(HomeFragment.class.getSimpleName()) ||
                AppConstant.currentFragmentTag.equalsIgnoreCase(ProfileFragment.class.getSimpleName()) ||
                AppConstant.currentFragmentTag.equalsIgnoreCase(NotificationsFragment.class.getSimpleName())) {
            Bundle bundle = new Bundle();
            bundle.putString(CustomAlertDialog.MESSAGE, "would you Like to close this App");
            customAlertDialog.setArguments(bundle);
            customAlertDialog.show(getSupportFragmentManager(), EXIT);
        } else
            getSupportFragmentManager().beginTransaction().replace(R.id.ll_fragment, HomeFragment.newInstance()).commit();
    }

    @Override
    public void clickonPositive(String tag) {
        switch (tag) {
            case EXIT:
                finish();
                break;
            case SIGNOUT:
                gotoLoginScreen();
                break;
        }
    }

    private void gotoLoginScreen() {
        Intent intent = new Intent(this, SignOutActivity.class);
        startActivity(intent);
        finish();

        /*Intent service = new Intent(this, AccessTokenService.class);
        stopService(service);
        preferenceUtils.doLogout();*/
    }

    @Override
    public void clickonNegative(String tag) {

    }

    @Override
    public void clickOnCancel() {

    }

    @Override
    public void clickOnUpdate(String oldNewPwd, String newPwd, String cnfmPwd) {

    }

    public void gotoMoneyRequestList(String urlType) {
        Intent intent = new Intent(context, ListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(AppConstant.CLASS_FROM, AppConstant.DASHBOARD);
        bundle.putString(AppConstant.ACTION, urlType);
        bundle.putString(AppConstant.ROLE, AppConstant.ROLEMANAGER);
        bundle.putString(AppConstant.URL, AppConstant.GETMONEYREQS);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    public void gotoAllVendorReqs(String vendorStatus) {
        Intent intent = new Intent(context, ListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(AppConstant.CLASS_FROM, AppConstant.DASHBOARD);
        bundle.putString(AppConstant.ACTION, vendorStatus);
        bundle.putString(AppConstant.ROLE, AppConstant.ROLEMANAGER);
        bundle.putString(AppConstant.URL, AppConstant.GETVENDORREQS);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    public void gotoLeavesListActivity() {
        Intent intent = new Intent(context, ListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(AppConstant.CLASS_FROM, AppConstant.DASHBOARD);
        bundle.putString(AppConstant.ACTION, AppConstant.GETLEAVES);
        bundle.putString(AppConstant.ROLE, AppConstant.ROLEEMPLOYEE);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    public void getBillsListActivity(String pendingstatus, String urltype) {
        Intent intent = new Intent(context, ListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(AppConstant.CLASS_FROM, AppConstant.DASHBOARD);
        bundle.putString(AppConstant.ACTION, pendingstatus);
        bundle.putString(AppConstant.URL, urltype);
        bundle.putString(AppConstant.ROLE, AppConstant.ROLEMANAGER);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    @Override
    public void dataRetreived(Response data) {
        if (data.servicemethod.equalsIgnoreCase(ServiceMethods.WS_APP_DASHBOARDSTATE)) {
            if (!data.isError) {

                AppConstant.dashBoardStatesDomain = (DashBoardStatesDomain) data.data;
                customProgressLoader.dismissProgressDialog();
            }
        }
    }

    @Override
    public void opennetworksetting() {

    }

    @Override
    public void clickOnItem(String s) {
        /*if (fragment instanceof HomeFragment) {
            HomeFragment homeFragment = (HomeFragment) fragment;
            homeFragment.updatePieChart(s);
            navigation_drawer.closeDrawers();
        }*/
        if (s.equalsIgnoreCase("Upload Agreement Document")) {
            AppConstant.updated = null;
            navigation_drawer.closeDrawers();
            fragment = UploadAgreementFragment.newInstance();
            fragmentTransaction.beginTransaction()
                    .replace(R.id.ll_fragment, fragment, UPLOADAGREEMENT)
                    .commit();
        } else if (s.equalsIgnoreCase("Upload Service Document")) {
            AppConstant.updated = null;
            navigation_drawer.closeDrawers();
            fragment = UploadServiceDocumentFragment.newInstance();
            fragmentTransaction.beginTransaction()
                    .replace(R.id.ll_fragment, fragment, UPLOADAGREEMENT)
                    .commit();
        } else if (s.equalsIgnoreCase("Dash Board")) {
            AppConstant.updated = null;
            navigation_drawer.closeDrawers();
            fragment = HomeFragment.newInstance();
            fragmentTransaction.beginTransaction()
                    .replace(R.id.ll_fragment, fragment, UPLOADAGREEMENT)
                    .commit();
        }
    }
}
