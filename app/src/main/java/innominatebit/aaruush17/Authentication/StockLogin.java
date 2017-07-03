package innominatebit.aaruush17.Authentication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import innominatebit.aaruush17.R;
import innominatebit.aaruush17.StockMarket.StockMarket;
import innominatebit.aaruush17.Storage.LocalStorage;


public class StockLogin extends AppCompatActivity {

    private LocalStorage session;

    private HashMap<String, String> profile;

    private String method;

    private String name;

    private String firstname;

    private String lastname;

    private String email;

    private Typeface logo;

    private Toolbar toolbar;

    private TextView header;

    private TextView namefield;

    private TextView emailfield;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_some);

        // Initializing Fonts

        logo = Typeface.createFromAsset(getAssets(), "fonts/galada.ttf");


        // Initializing Toolbar

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(null);


        // Adding Back Button

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // Setting Up TextView

        header = (TextView) findViewById(R.id.headertext);

        namefield = (TextView) findViewById(R.id.name);

        emailfield = (TextView) findViewById(R.id.email);


        // Applying Aaruush Font

        header.setTypeface(logo);


        session = new LocalStorage(getApplicationContext());

        profile = session.getProfileDetails();

        method = profile.get(LocalStorage.METHOD);

        checkLoginMethod();

        Button login=(Button)findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Toast.makeText(StockLogin.this, "Coming Soon", Toast.LENGTH_SHORT).show();

            }
        });

    }


    private void checkLoginMethod() {

        if (method.contains("Facebook")) {

            firstname = profile.get(LocalStorage.FIRSTNAME);

            lastname = profile.get(LocalStorage.LASTNAME);

            name = firstname + " " + lastname;

            email = profile.get(LocalStorage.EMAIL);

            namefield.setText(name);

            emailfield.setText(email);

            new AddUser().execute(name, email);

            Toast.makeText(StockLogin.this, "NAME: " + name + "\n" + "EMAIL: " + email, Toast.LENGTH_LONG).show();

        }

        else if (method.contains("Google")) {

            name = profile.get(LocalStorage.FIRSTNAME);

            email = profile.get(LocalStorage.EMAIL);

            namefield.setText(name);

            emailfield.setText(email);

            new AddUser().execute(name, email);

            Toast.makeText(StockLogin.this, "NAME: " + name + "\n" + "EMAIL: " + email, Toast.LENGTH_LONG).show();

        }

        else {

            Toast.makeText(StockLogin.this, "Error Login Credentials", Toast.LENGTH_LONG).show();

        }

    }


    private class AddUser extends AsyncTask<String,Void,Void>{
        String webPage="";
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute(){
            progressDialog = ProgressDialog.show(StockLogin.this, "Please Wait!","Registering for SRM Stock Market");
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
            String reply;
            switch (webPage) {
                case "registration successful":
                    reply = "Registration Success.";
                    break;
                case "already exists":
                    reply = "Your data already exists.";
                    break;
                default:
                    reply = "Registration Failed.";
                    break;
            }
            Toast.makeText(StockLogin.this, reply, Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
        }
    }
}