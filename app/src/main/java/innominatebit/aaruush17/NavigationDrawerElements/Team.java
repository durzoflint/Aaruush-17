package innominatebit.aaruush17.NavigationDrawerElements;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import innominatebit.aaruush17.Adapters.CustomGridViewAdapter;
import innominatebit.aaruush17.Dashboard;
import innominatebit.aaruush17.R;

public class Team extends AppCompatActivity {

    private Typeface logo;

    private TextView header;

    private Toolbar toolbar;

    private GridView androidGridView;

    private CustomGridViewAdapter cgrid;

    String[] gridViewString = {

            "Pranav", "Radhika", "Ranshu", "Poojith", "Pavan", "Nishanth", "Nishant", "Naveen",

            "Manish", "Kaushal", "Kalaivani", "Haroon", "Gautham", "Dakhsita", "Ashrita", "Arjun",

            "Aneek", "Amans", "Amank", "Abhiprae"

    };

    int[] gridViewImageId = {

            R.drawable.pranav, R.drawable.radhika, R.drawable.ranshu,

            R.drawable.poojith, R.drawable.pavan, R.drawable.nishanthk,

            R.drawable.nishanth, R.drawable.naveen, R.drawable.manish, R.drawable.kaushal, R.drawable.kalaivani,

            R.drawable.haroon, R.drawable.gautham, R.drawable.dakshita,

            R.drawable.ashrita, R.drawable.arjun, R.drawable.aneek, R.drawable.amans, R.drawable.amank,

            R.drawable.abhiprae

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initializing Fonts

        logo = Typeface.createFromAsset(getAssets(), "fonts/galada.ttf");


        // Inflating View

        setContentView(R.layout.activity_team);


        // Setting Up Grid View

        gridSetup();


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


    private void gridSetup() {

        cgrid = new CustomGridViewAdapter(Team.this, gridViewString, gridViewImageId);

        androidGridView = (GridView) findViewById(R.id.grid);

        androidGridView.setAdapter(cgrid);

        androidGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {

                Toast.makeText(Team.this, "Team: " + gridViewString[+i], Toast.LENGTH_LONG).show();

            }

        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:

                Intent intent = new Intent(this, Dashboard.class);

                startActivity(intent);

                finish();

                return true;

            default:

                return super.onOptionsItemSelected(item);

        }

    }


}
