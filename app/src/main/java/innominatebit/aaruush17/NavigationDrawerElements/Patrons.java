package innominatebit.aaruush17.NavigationDrawerElements;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import innominatebit.aaruush17.Dashboard;
import innominatebit.aaruush17.R;

public class Patrons extends AppCompatActivity {

    private Typeface logo;

    private TextView header;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initializing Fonts

        logo = Typeface.createFromAsset(getAssets(), "fonts/galada.ttf");


        // Inflating View

        setContentView(R.layout.activity_patrons);


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
