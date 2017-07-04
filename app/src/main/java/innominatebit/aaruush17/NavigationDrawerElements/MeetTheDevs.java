package innominatebit.aaruush17.NavigationDrawerElements;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import innominatebit.aaruush17.Dashboard;
import innominatebit.aaruush17.R;

public class MeetTheDevs extends AppCompatActivity implements View.OnClickListener {
    private Typeface logo;
    private TextView header;
    private Toolbar toolbar;
    private LinearLayout app1,app2,app3,app4,app5,app6;
    private ImageView dp1,dp2,dp3,dp4,dp5,dp6;
    private TextView name1,name2,name3,name4,name5,name6;
    private TextView age1,age2,age3,age4,age5,age6;
    private TextView profession1,profession2,profession3,profession4,profession5,profession6;
    private TextView college1,college2,college3,college4,college5,college6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initializing Fonts
        logo = Typeface.createFromAsset(getAssets(), "fonts/galada.ttf");
        // Inflating View
        setContentView(R.layout.activity_meet_the_devs);
        // Initializing Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        // Adding Back Button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Setting Up Header Text
        header = (TextView) findViewById(R.id.headertext);
        // Applying Aaruush Font
        header.setTypeface(logo);
        // Declaring LinearLayouts
        app1 = (LinearLayout) findViewById(R.id.app1);
        app2 = (LinearLayout) findViewById(R.id.app2);
        app3 = (LinearLayout) findViewById(R.id.app3);
        app4 = (LinearLayout) findViewById(R.id.app4);
        app5 = (LinearLayout) findViewById(R.id.app5);
        app6 = (LinearLayout) findViewById(R.id.app6);
        // Setting OnClick Listeners
        app1.setOnClickListener(this);
        app2.setOnClickListener(this);
        app3.setOnClickListener(this);
        app4.setOnClickListener(this);
        app5.setOnClickListener(this);
        app6.setOnClickListener(this);
        // Declaring Images
        dp1 = (ImageView) findViewById(R.id.dp1);
        dp2 = (ImageView) findViewById(R.id.dp2);
        dp3 = (ImageView) findViewById(R.id.dp3);
        dp4 = (ImageView) findViewById(R.id.dp4);
        dp5 = (ImageView) findViewById(R.id.dp5);
        dp6 = (ImageView) findViewById(R.id.dp6);
        // Declaring Names
        name1 = (TextView) findViewById(R.id.name1);
        name2 = (TextView) findViewById(R.id.name2);
        name3 = (TextView) findViewById(R.id.name3);
        name4 = (TextView) findViewById(R.id.name4);
        name5 = (TextView) findViewById(R.id.name5);
        name6 = (TextView) findViewById(R.id.name6);
        // Declaring Ages
        age1 = (TextView) findViewById(R.id.age1);
        age2 = (TextView) findViewById(R.id.age2);
        age3 = (TextView) findViewById(R.id.age3);
        age4 = (TextView) findViewById(R.id.age4);
        age5 = (TextView) findViewById(R.id.age5);
        age6 = (TextView) findViewById(R.id.age6);
        // Declaring Professions
        profession1 = (TextView) findViewById(R.id.profession1);
        profession2 = (TextView) findViewById(R.id.profession2);
        profession3 = (TextView) findViewById(R.id.profession3);
        profession4 = (TextView) findViewById(R.id.profession4);
        profession5 = (TextView) findViewById(R.id.profession5);
        profession6 = (TextView) findViewById(R.id.profession6);
        // Declaring Colleges
        college1 = (TextView) findViewById(R.id.college1);
        college2 = (TextView) findViewById(R.id.college2);
        college3 = (TextView) findViewById(R.id.college3);
        college4 = (TextView) findViewById(R.id.college4);
        college5 = (TextView) findViewById(R.id.college5);
        college6 = (TextView) findViewById(R.id.college6);
    }
    private void sendData(ImageView dp, TextView name, TextView age, TextView profession,
                          TextView college, String expertise, String applied, String bio) {
        dp.buildDrawingCache();
        Bitmap image = dp.getDrawingCache();
        Intent intent = new Intent(MeetTheDevs.this, MeetTheDevTransition.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("imagebitmap", image);
        bundle.putString("name", name.getText().toString());
        bundle.putString("age", age.getText().toString());
        bundle.putString("profession", profession.getText().toString());
        bundle.putString("college", college.getText().toString());
        bundle.putString("expertise", expertise);
        bundle.putString("applied", applied);
        bundle.putString("aboutme", bio);
        bundle.putString("Activity","MeetTheDevs");
        intent.putExtras(bundle);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, dp, getString(R.string.picturetransition));
            startActivity(intent, options.toBundle());
        }
        else {
            startActivity(intent);
        }
    }
    @Override
    public void onClick(View view) {
        if (view == app1) {
            sendData(dp1, name1, age1, profession1, college1, "3rd Year", "API & Mining", "A techy travelling engineer who wants to talk to the world with his photographs and paragraphs.");
        }
        if (view == app2) {
            sendData(dp2, name2, age2, profession2, college2, "2nd Year", "Android Development", "Github > Pornhub. \n \nAbsolutely Amazing What One Can Build With Few Lines Of Code.");
        }
        if (view == app3) {
            sendData(dp3, name3, age3, profession3, college3, "3rd Year", "Android Development", "I simply love coding;");
        }
        if (view == app4) {
            sendData(dp4, name4, age4, profession4, college4, "2nd Year", "UI/UX Designer", "Capturing world through lens, one picture at a time.");
        }
        if (view == app5) {
            sendData(dp5, name5, age5, profession5, college5, "3rd Year", "Realtime Backend", "It's complicated to describe me. Just ask the people who know me. Although, they'll say I am a robo.");
        }
        if (view == app6) {
            sendData(dp6, name6, age6, profession6, college6, "3rd Year", "JAVA Core & Python", "If you are reading this for the nth time , then you are stuck in a recursive loop. Break it soon :D");
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}