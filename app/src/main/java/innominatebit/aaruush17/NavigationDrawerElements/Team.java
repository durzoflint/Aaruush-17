package innominatebit.aaruush17.NavigationDrawerElements;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import innominatebit.aaruush17.Dashboard;
import innominatebit.aaruush17.R;

public class Team extends AppCompatActivity implements View.OnClickListener
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Team Aaruush");
        RelativeLayout data = (RelativeLayout) findViewById(R.id.data);
        for(int i=0;i<data.getChildCount();i++)
        {
            View v = data.getChildAt(i);
            v.setOnClickListener(this);
        }
    }
    @Override
    public void onClick(View view) {
        LinearLayout linearLayout = (LinearLayout) view;
        LinearLayout ll = (LinearLayout) linearLayout.getChildAt(0);
        LinearLayout containsDP = (LinearLayout) ll.getChildAt(0);
        ImageView dp = (ImageView)containsDP.getChildAt(0);
        LinearLayout containsData = (LinearLayout) ll.getChildAt(1);
        TextView name = (TextView)containsData.getChildAt(0);
        TextView age = (TextView)containsData.getChildAt(1);
        TextView proffesion = (TextView)containsData.getChildAt(2);
        TextView collage = (TextView)containsData.getChildAt(3);
        sendData(dp,name,age,proffesion,collage);
    }
    private void sendData(ImageView dp, TextView name, TextView age, TextView profession,TextView college){
        dp.buildDrawingCache();
        Bitmap image = dp.getDrawingCache();
        Intent intent = new Intent(Team.this, MeetTheDevTransition.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("imagebitmap", image);
        bundle.putString("name", name.getText().toString());
        bundle.putString("age", age.getText().toString());
        bundle.putString("profession", profession.getText().toString());
        bundle.putString("college", college.getText().toString());
        bundle.putString("expertise", "");
        bundle.putString("applied", "");
        bundle.putString("aboutme", "");
        bundle.putString("Activity","Team");
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