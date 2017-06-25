package innominatebit.aaruush17;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import innominatebit.aaruush17.Adapters.ViewPagerAdapter;
import innominatebit.aaruush17.Authentication.MainLogin;
import innominatebit.aaruush17.Authentication.StockLogin;
import innominatebit.aaruush17.Fragments.Events;
import innominatebit.aaruush17.Fragments.Hub;
import innominatebit.aaruush17.Fragments.Timeline;
import innominatebit.aaruush17.Fragments.Workshops;
import innominatebit.aaruush17.Storage.LocalStorage;

public class Dashboard extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    private Typeface logo;

    private LocalStorage session;

    private NavigationView nview;

    private DrawerLayout drawerLayout;

    private ActionBarDrawerToggle dtoogle;

    private Intent stockmarket;

    private Intent mainlogin;

    private TextView header;

    private TextView name;

    private Toolbar toolbar;

    private ViewPager viewpager;

    private TabLayout tablayout;

    private ViewPagerAdapter adapter;


    // Google Declarations

    private GoogleSignInOptions gso;

    private GoogleApiClient gapiclient;


    // Facebook Declarations

    private LoginManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initializing Fonts

        logo = Typeface.createFromAsset(getAssets(), "fonts/galada.ttf");


        // Initializing Google SDK

        googleSDK();


        // Inflating View

        setContentView(R.layout.dashboard);


        // Initializing Local Storage

        session = new LocalStorage(getApplicationContext());


        // Setting Up Header Text

        header = (TextView) findViewById(R.id.headertext);


        // Applying Aaruush Font

        header.setTypeface(logo);


        // Setting Up View Pager

        viewpager = (ViewPager) findViewById(R.id.viewpager);

        setUpViewPager(viewpager);


        // Setting Up Tabbed Activity

        tablayout = (TabLayout) findViewById(R.id.tablayout);

        tablayout.setupWithViewPager(viewpager);


        // Selecting Middle Tab (Timeline) At Start

        TabLayout.Tab tab = tablayout.getTabAt(1);

        tab.select();


        // Setting Up Drawer Layout

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);


        // Initializing Toolbar

        toolbar = (Toolbar) findViewById(R.id.toolbar);


        // Initializing Navigation Drawer

        navigationDrawer();

    }


    public void setUpViewPager(ViewPager set) {

        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new Hub(), "Hub");

        adapter.addFragment(new Timeline(), "Timeline");

        adapter.addFragment(new Events(), "Events");

        adapter.addFragment(new Workshops(), "WShops");

        set.setAdapter(adapter);

    }


    private void googleSDK() {

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        gapiclient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, (GoogleApiClient.OnConnectionFailedListener) this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

    }


    public void navigationDrawer() {

        nview = (NavigationView) findViewById(R.id.navigation_view);

        nview.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                int id = menuItem.getItemId();

                switch (id) {

                    case R.id.stockmarket:

                        stockmarket = new Intent(Dashboard.this, StockLogin.class);

                        startActivity(stockmarket);

                        drawerLayout.closeDrawers();

                        break;


                    case R.id.settings:

                        Toast.makeText(Dashboard.this, "In Development", Toast.LENGTH_LONG).show();

                        drawerLayout.closeDrawers();

                        break;


                    case R.id.logout:

                        // Logout Of Facebook

                        manager.getInstance().logOut();


                        // Logout Of Google

                        Auth.GoogleSignInApi.signOut(gapiclient);


                        // Logout Of Session

                        session.logoutUser();


                        // Go The The Main Login

                        mainlogin = new Intent(Dashboard.this, MainLogin.class);

                        startActivity(mainlogin);

                        finish();

                        drawerLayout.closeDrawers();

                        break;

                }

                return true;
            }

        });

        View header = nview.getHeaderView(0);

        name = (TextView) header.findViewById(R.id.name);

        name.setText(getResources().getString(R.string.app_name));

        dtoogle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.draweropen, R.string.drawerclose) {

            @Override
            public void onDrawerClosed(View v) {
                super.onDrawerClosed(v);
            }

            @Override
            public void onDrawerOpened(View v) {
                super.onDrawerOpened(v);
            }
        };

        drawerLayout.addDrawerListener(dtoogle);

        dtoogle.syncState();

    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }


    @Override
    public void onClick(View view) {


    }


}
