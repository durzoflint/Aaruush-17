package innominatebit.aaruush17;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_some);

        //TODO Fetch user email id and assign here
        final String emailID="aaaorabhinav@gmail.com";

        Button button=(Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SomeActivity.this, StockMarket.class);
                intent.putExtra("emailID", emailID);
                startActivity(intent);
            }
        });
    }
}