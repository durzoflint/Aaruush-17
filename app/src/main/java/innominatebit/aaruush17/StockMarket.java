package innominatebit.aaruush17;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class StockMarket extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_market);
        Bundle bundle = getIntent().getExtras();
        String emailID = bundle.getString("emailID");
    }
    @Override
    protected void onStart() {
        super.onStart();

    }
    class FetchData extends AsyncTask<Void,Void,Void>
    {
        String murl="http://srmvdpauditorium.in/aaaTestApp.php?paramUsername=aaa&paramPassword=123",webPage="";
        @Override
        protected Void doInBackground(Void... voids)
        {
            URL url;
            HttpURLConnection urlConnection = null;
            try
            {
                url = new URL(murl);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStreamReader isw = new InputStreamReader(urlConnection.getInputStream());
                BufferedReader br=new BufferedReader(isw);
                String data="";
                while ((data=br.readLine()) != null)
                    webPage=webPage+data+"\n";
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
        protected void onPostExecute(Void aVoid)
        {
            //Toast.makeText(SomeActivity.this, webPage, Toast.LENGTH_SHORT).show();
            super.onPostExecute(aVoid);
        }
    }
}
