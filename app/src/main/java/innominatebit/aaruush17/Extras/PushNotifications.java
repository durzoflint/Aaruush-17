package innominatebit.aaruush17.Extras;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import innominatebit.aaruush17.R;

public class PushNotifications extends AppCompatActivity {

    private Typeface logo;

    private TextView header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initializing Fonts

        logo = Typeface.createFromAsset(getAssets(), "fonts/galada.ttf");


        // Inflating View

        setContentView(R.layout.activity_push_notifications);


        // Setting Up Header Text

        header = (TextView) findViewById(R.id.headertext);


        // Applying Aaruush Font

        header.setTypeface(logo);

    }


}
