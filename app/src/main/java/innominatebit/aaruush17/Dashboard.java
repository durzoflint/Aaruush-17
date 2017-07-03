package innominatebit.aaruush17;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import innominatebit.aaruush17.Adapters.ViewPagerAdapter;
import innominatebit.aaruush17.Authentication.MainLogin;
import innominatebit.aaruush17.Fragments.Domains;
import innominatebit.aaruush17.Fragments.Hub;
import innominatebit.aaruush17.Fragments.Timeline;
import innominatebit.aaruush17.Fragments.Workshops;
import innominatebit.aaruush17.Extras.PushNotifications;
import innominatebit.aaruush17.NavigationDrawerElements.AboutUs;
import innominatebit.aaruush17.NavigationDrawerElements.Highlights;
import innominatebit.aaruush17.NavigationDrawerElements.MeetTheDevs;
import innominatebit.aaruush17.NavigationDrawerElements.Patrons;
import innominatebit.aaruush17.NavigationDrawerElements.Sponsors;
import innominatebit.aaruush17.NavigationDrawerElements.Team;
import innominatebit.aaruush17.StockMarket.StockMarket;
import innominatebit.aaruush17.Storage.LocalStorage;

public class Dashboard extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    private String email;
    private Typeface logo;
    private LocalStorage session;
    private NavigationView nview;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle dtoogle;
    private Intent sponsors;
    private Intent highlights;
    private Intent aboutus;
    private Intent patrons;
    private Intent team;
    private Intent meetthedevs;
    private Intent mainlogin;
    private Intent notifications;
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

        viewpager.setOffscreenPageLimit(69);

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

        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(null);


        // Initializing Navigation Drawer

        navigationDrawer();

    }
    public void setUpViewPager(ViewPager set) {

        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new Hub(), "Hub");

        adapter.addFragment(new Timeline(), "Timeline");

        adapter.addFragment(new Domains(), "Domains");

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
                    case R.id.sponsors:
                        sponsors = new Intent(Dashboard.this, Sponsors.class);
                        startActivity(sponsors);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.stockmarket:
                        HashMap<String, String> profile1;
                        String method1;
                        LocalStorage session1 = new LocalStorage(getApplicationContext());
                        profile1 = session1.getProfileDetails();
                        method1 = profile1.get(LocalStorage.METHOD);
                        String name1;
                        if (method1.contains("Facebook"))
                        {
                            String firstname1 = profile1.get(LocalStorage.FIRSTNAME);
                            String lastname1 = profile1.get(LocalStorage.LASTNAME);
                            name1 = firstname1 + " " + lastname1;
                            email = profile1.get(LocalStorage.EMAIL);
                            new AddUser().execute(name1, email);
                        }
                        else if (method1.contains("Google"))
                        {
                            name1 = profile1.get(LocalStorage.FIRSTNAME);
                            email = profile1.get(LocalStorage.EMAIL);
                            new AddUser().execute(name1, email);
                        }
                        else
                        {
                            Toast.makeText(Dashboard.this, "Error Login Credentials", Toast.LENGTH_LONG).show();
                        }
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.highlights:
                        highlights = new Intent(Dashboard.this, Highlights.class);
                        startActivity(highlights);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.aboutus:
                        aboutus = new Intent(Dashboard.this, AboutUs.class);
                        startActivity(aboutus);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.patrons:
                        patrons = new Intent(Dashboard.this, Patrons.class);
                        startActivity(patrons);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.team:
                        team = new Intent(Dashboard.this, Team.class);
                        startActivity(team);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.developers:
                        meetthedevs = new Intent(Dashboard.this, MeetTheDevs.class);
                        startActivity(meetthedevs);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.logout:
                        drawerLayout.closeDrawers();
                        new AlertDialog.Builder(new ContextThemeWrapper(Dashboard.this, R.style.MyDialogTheme))
                                .setTitle("Really Logout?")
                                .setMessage("\nAre you sure you want to logout?")
                                .setIcon(android.R.drawable.ic_dialog_info)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                                {
                                    public void onClick(DialogInterface dialog, int which)
                                    {
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
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener(){
                                    public void onClick(DialogInterface dialog, int which){}})
                                .create().show();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bell, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.bell) {
            notifications = new Intent(Dashboard.this, PushNotifications.class);
            startActivity(notifications);
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {}
    @Override
    public void onClick(View view) {
    }
    private class AddUser extends AsyncTask<String,Void,Void> {
        String webPage="";
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute(){
            progressDialog = ProgressDialog.show(Dashboard.this, "Please Wait!","Registering for SRM Stock Market");
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(String... strings) {
            URL url;
            HttpURLConnection urlConnection = null;
            try
            {
                String x="";
                for(int i=0;i<strings[0].length();i++)
                {
                    char ch= strings[0].charAt(i);
                    if(ch==' ')
                        x=x+"%20";
                    else
                        x=x+ch;
                }
                url = new URL("http://srmvdpauditorium.in/SRMStockMarket/registerUser.php?name="+x+"&emailID="+strings[1]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStreamReader isw = new InputStreamReader(urlConnection.getInputStream());
                BufferedReader br=new BufferedReader(isw);
                String data;
                while ((data=br.readLine()) != null)
                    webPage=webPage+data;
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            finally
            {
                if (urlConnection != null)
                    urlConnection.disconnect();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Intent i = new Intent(Dashboard.this, StockMarket.class);
            i.putExtra("emailID",email);
            switch (webPage) {
                case "registration successful":
                case "already exists":
                    startActivity(i);
                    break;
                default:
                    Toast.makeText(Dashboard.this, "Error while logging into Stock Market. Please logout and try again.", Toast.LENGTH_LONG).show();
                    break;
            }
            progressDialog.dismiss();
        }
    }
}