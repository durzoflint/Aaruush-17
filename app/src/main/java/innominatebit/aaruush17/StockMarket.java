package innominatebit.aaruush17;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StockMarket extends AppCompatActivity
{

    //TODO Remove ids of individual linear layouts inside 'Data' if
        //TODO they have not been used anywhere after StockMarket Application is completed

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
        setOnClickListeners();
    }
    void setOnClickListeners()
    {
        LinearLayout data=(LinearLayout)findViewById(R.id.data);
        for(int i=0;i<data.getChildCount();i++)
        {
            final LinearLayout child=(LinearLayout) data.getChildAt(i);
            child.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    TextView viewname=(TextView) child.getChildAt(0);
                    String name=viewname.getText().toString();
                    TextView viewValuePerShare=(TextView) child.getChildAt(1);
                    String valuePerShare=viewValuePerShare.getText().toString();
                    TextView viewSharesOwned=(TextView) child.getChildAt(2);
                    String sharedOwned=viewSharesOwned.getText().toString();

                    //TODO create a dialog box which allows the user to buy new or sell his shares

                }
            });
        }
    }
    class MyData extends AsyncTask<Void,Void,Void>
    {
        String cashRemaining="Cash Remaining : ";
        HashMap<String,String> userShares;
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute()
        {
            progressDialog = ProgressDialog.show(StockMarket.this, "Please Wait!","Fetching Your Stock Details");
            super.onPreExecute();
            userShares=new HashMap<>();
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
                BufferedReader br=new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String data="",webPage="";
                while ((data=br.readLine()) != null)
                    webPage=webPage+data;
                while(webPage.indexOf('<')!=-1)
                {
                    String key=webPage.substring(0,webPage.indexOf('<'));
                    webPage=webPage.substring(webPage.indexOf('<')+1);
                    String value=webPage.substring(0,webPage.indexOf('>'));
                    webPage=webPage.substring(webPage.indexOf('>')+1);
                    if(key.equals("Cost"))
                        cashRemaining=cashRemaining+value;
                    else
                        userShares.put(key,value);
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
            progressDialog.dismiss();
            HashMap<String,Integer> textviews=new HashMap<>();
            textviews.put("High Tech",R.id.sharehightech);            textviews.put("EEE",R.id.shareeee);
            textviews.put("Civil",R.id.sharecivil);            textviews.put("Placement",R.id.shareplacement);
            textviews.put("Aaruush",R.id.shareaaruush);            textviews.put("Aero",R.id.shareaero);
            textviews.put("Back Gate",R.id.sharebackgate);            textviews.put("UB",R.id.shareub);
            textviews.put("Tech Park",R.id.sharetechpark);            textviews.put("Arch Block",R.id.sharearchblock);
            textviews.put("Bio Block",R.id.sharebioblock);            textviews.put("MBA",R.id.sharemba);
            textviews.put("G Hostel",R.id.shareghostel);            textviews.put("B Hostel",R.id.sharebhostel);
            textviews.put("Audi",R.id.shareaudi);            textviews.put("Java",R.id.shareghostel);
            textviews.put("Hospital",R.id.sharehospital);            textviews.put("VPT",R.id.sharevpt);
            textviews.put("Dental",R.id.sharedental);            textviews.put("PF Hostel",R.id.sharepfhostel);
            for(Map.Entry<String,String> e:userShares.entrySet())
            {
                String key = e.getKey();
                String value = e.getValue();
                int id=textviews.get(key);
                TextView tv=(TextView)findViewById(id);
                tv.setText(value);
            }
            TextView valueRemaning=(TextView)findViewById(R.id.valueremaning);
            valueRemaning.setText(cashRemaining);
        }

    }
    class FetchStockData extends AsyncTask<Void,Void,Void>
    {
        ProgressDialog progressDialog;
        ArrayList<String> values;
        @Override
        protected void onPreExecute()
        {
            progressDialog = ProgressDialog.show(StockMarket.this, "Please Wait!","Fetching Latest Stock Data");
            super.onPreExecute();
            values=new ArrayList<>();
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
            Toast.makeText(StockMarket.this, "Tap on any Stock for a Detailed View", Toast.LENGTH_SHORT).show();
        }
    }
}