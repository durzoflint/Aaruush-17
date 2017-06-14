package innominatebit.aaruush17;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class StockMarket extends AppCompatActivity
{
    String emailID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_market);
        Bundle bundle = getIntent().getExtras();
        emailID = bundle.getString("emailID");
    }
    @Override
    protected void onStart() {
        super.onStart();
        new FetchStockData().execute();
        new MyData().execute();
    }
    class MyData extends AsyncTask<Void,Void,Void>
    {
        String webPage="";
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(StockMarket.this, "Please Wait!","Fetching Your Stock Details");
        }
        @Override
        protected Void doInBackground(Void... voids)
        {
            URL url;
            HttpURLConnection urlConnection = null;
            try
            {
                url = new URL("http://srmvdpauditorium.in/SRMStockMarket/getUserData.php?emailID="+emailID);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStreamReader isw = new InputStreamReader(urlConnection.getInputStream());
                BufferedReader br=new BufferedReader(isw);
                String data="";
                while ((data=br.readLine()) != null)
                    webPage=webPage+data;

                //TODO format user stock data

                /*values=new ArrayList<>();
                while(webPage.indexOf('<')!=-1)
                {
                    webPage=webPage.substring(webPage.indexOf('<')+1);
                    values.add(webPage.substring(0,webPage.indexOf('>')));
                }*/
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
            super.onPostExecute(aVoid);
            progressDialog.dismiss();

            //TODO store in a hashmap<key="Name of building" , value="ids of textview that needs to be updated">
            //TODO Update the textviews with the User's Shares

            Toast.makeText(StockMarket.this, webPage, Toast.LENGTH_SHORT).show();
        }

    }
    class FetchStockData extends AsyncTask<Void,Void,Void>
    {
        ProgressDialog progressDialog;
        ArrayList<String> values;
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(StockMarket.this, "Please Wait!","Fetching Latest Stock Data");
        }
        @Override
        protected Void doInBackground(Void... voids)
        {
            URL url;
            HttpURLConnection urlConnection = null;
            try
            {
                url = new URL("http://srmvdpauditorium.in/SRMStockMarket/getStockData.php");
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStreamReader isw = new InputStreamReader(urlConnection.getInputStream());
                BufferedReader br=new BufferedReader(isw);
                String data="",webPage="";
                while ((data=br.readLine()) != null)
                    webPage=webPage+data;
                values=new ArrayList<>();
                while(webPage.indexOf('<')!=-1)
                {
                    webPage=webPage.substring(webPage.indexOf('<')+1);
                    values.add(webPage.substring(0,webPage.indexOf('>')));
                }
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
            super.onPostExecute(aVoid);
            int c=0;
            ArrayList<Integer> ids=new ArrayList<>();
            ids.add(R.id.hightech);             ids.add(R.id.eee);              ids.add(R.id.civil);
            ids.add(R.id.placement);            ids.add(R.id.aaruush);          ids.add(R.id.aero);
            ids.add(R.id.backgate);             ids.add(R.id.ub);               ids.add(R.id.techpark);
            ids.add(R.id.archblock);            ids.add(R.id.bioblock);         ids.add(R.id.mba);
            ids.add(R.id.ghostel);              ids.add(R.id.bhostel);          ids.add(R.id.audi);
            ids.add(R.id.java);                 ids.add(R.id.hospital);         ids.add(R.id.vpt);
            ids.add(R.id.dental);               ids.add(R.id.pfhostel);
            while(c<20)
            {
                TextView t=(TextView)findViewById(ids.get(c));
                t.setText(values.get(c++));
            }
            progressDialog.dismiss();
        }
    }
}
