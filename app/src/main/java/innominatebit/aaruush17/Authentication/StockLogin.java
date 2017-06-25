package innominatebit.aaruush17.Authentication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import innominatebit.aaruush17.R;
import innominatebit.aaruush17.StockMarket.StockMarket;

public class StockLogin extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_some);

        //TODO Fetch user email id and name then assign here
        final EditText name=(EditText)findViewById(R.id.name);
        final EditText enteredEmailID=(EditText)findViewById(R.id.enteredemailid);
        Button register=(Button)findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName=name.getText().toString();
                String emailID=enteredEmailID.getText().toString();
                if(userName.length()>0&&emailID.length()>0)
                    new AddUser().execute(userName, emailID);
                else
                    Toast.makeText(StockLogin.this, "Invalid Data", Toast.LENGTH_SHORT).show();
            }
        });
        Button login=(Button)findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText userID=(EditText)findViewById(R.id.loginmail);
                String userEmailID=userID.getText().toString();
                if(userEmailID.length()>0)
                {
                    Intent intent = new Intent(StockLogin.this, StockMarket.class);
                    intent.putExtra("emailID", userEmailID);
                    startActivity(intent);
                }
                else
                    Toast.makeText(StockLogin.this, "Invalid Data", Toast.LENGTH_SHORT).show();
            }
        });
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