package innominatebit.aaruush17.StockMarket;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
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

import innominatebit.aaruush17.R;
import innominatebit.aaruush17.Storage.LocalStorage;

public class StockMarket extends AppCompatActivity
{
    private String emailID;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_market);
        Bundle bundle = getIntent().getExtras();
        emailID = bundle.getString("emailID");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("SRM Stock Market");
    }
    @Override
    protected void onStart(){
        super.onStart();

        new FetchStockData().execute();

        new MyData().execute("http://srmvdpauditorium.in/SRMStockMarket/getUserData.php?emailID="+emailID);

        doInteraction();

    }
    void doInteraction() {
        LinearLayout data=(LinearLayout)findViewById(R.id.data);
        for(int i=0;i<data.getChildCount();i++)
        {
            final LinearLayout child=(LinearLayout) data.getChildAt(i);
            child.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    TextView viewname=(TextView) child.getChildAt(0);
                    final String name=viewname.getText().toString();
                    TextView viewValuePerShare=(TextView) child.getChildAt(1);
                    final String valuePerShare=viewValuePerShare.getText().toString();
                    final Double value=Double.parseDouble(valuePerShare);
                    TextView viewSharesOwned=(TextView) child.getChildAt(2);
                    final String sharesOwned=viewSharesOwned.getText().toString();
                    final Double myShares=Double.parseDouble(sharesOwned);
                    String buyMore="Buy More";
                    if(myShares==0.0)
                        buyMore="Buy";
                    AlertDialog.Builder stockDialog = new AlertDialog.Builder(new ContextThemeWrapper(StockMarket.this, R.style.MyDialogTheme))
                            .setMessage("\nValue per Share : "+valuePerShare+"\nShares Owned by You: "+sharesOwned)
                            .setIcon(android.R.drawable.ic_menu_agenda).setTitle(name);
                    switch (myShares+"")
                    {
                        default:
                            stockDialog.setNegativeButton("Sell", new DialogInterface.OnClickListener()
                                    {
                                        public void onClick(DialogInterface dialog, int which)
                                        {
                                            LayoutInflater inflater = LayoutInflater.from(StockMarket.this);
                                            final View stockDetails= inflater.inflate(R.layout.stock_details, null);
                                            new AlertDialog.Builder(new ContextThemeWrapper(StockMarket.this, R.style.MyDialogTheme))
                                                    .setTitle("Sell Stocks for "+name)
                                                    .setView(stockDetails)
                                                    .setMessage("\nValue per Share : "+valuePerShare
                                                            +"\nShares Owned by You: "+sharesOwned
                                                            +"\n\nEnter the quantity of stocks you wish to sell.")
                                                    .setIcon(android.R.drawable.ic_menu_agenda)
                                                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener()
                                                    {
                                                        public void onClick(DialogInterface dialog, int which)
                                                        {
                                                            EditText stockQuantity=(EditText)stockDetails.findViewById(R.id.stockquantity);
                                                            String input=stockQuantity.getText().toString();
                                                            if(input.length()>0&&!(input.equals(".")))
                                                            {
                                                                double quantity=Double.parseDouble(input);
                                                                if(quantity<=myShares)
                                                                    new MyData().execute("http://srmvdpauditorium.in/SRMStockMarket/sellStocks.php?emailID="+emailID+"&stockName="+handleSpaces(name)+"&quantity="+quantity);
                                                                else
                                                                    Toast.makeText(StockMarket.this, "Entered number of shares cannot be more than the shares you own!", Toast.LENGTH_LONG).show();
                                                            }
                                                        }
                                                    })
                                                    .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener(){
                                                        public void onClick(DialogInterface dialog, int which){}})
                                                    .create().show();
                                        }
                                    })
                                    .setNeutralButton("Sell All", new DialogInterface.OnClickListener()
                                    {
                                        public void onClick(DialogInterface dialog, int which)
                                        {
                                            new MyData().execute("http://srmvdpauditorium.in/SRMStockMarket/sellStocks.php?emailID="+emailID+"&stockName="+handleSpaces(name)+"&quantity="+myShares);
                                        }
                                    });
                        case "0.0" :
                            stockDialog.setPositiveButton(buyMore, new DialogInterface.OnClickListener()
                            {
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    LayoutInflater inflater = LayoutInflater.from(StockMarket.this);
                                    final View stockDetails= inflater.inflate(R.layout.stock_details, null);
                                    new AlertDialog.Builder(new ContextThemeWrapper(StockMarket.this, R.style.MyDialogTheme)).setTitle("Buy Stocks for "+name)
                                            .setView(stockDetails)
                                            .setMessage("\nValue per Share : "+valuePerShare
                                                    +"\nShares Owned by You: "+sharesOwned
                                                    +"\n\nEnter the quantity of stocks you wish to buy.")
                                            .setIcon(android.R.drawable.ic_menu_agenda)
                                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener()
                                            {
                                                public void onClick(DialogInterface dialog, int which)
                                                {
                                                    EditText stockQuantity=(EditText)stockDetails.findViewById(R.id.stockquantity);
                                                    String input=stockQuantity.getText().toString();
                                                    if(input.length()>0&&!(input.equals(".")))
                                                    {
                                                        double quantity=Double.parseDouble(input);
                                                        TextView valueRemaning=(TextView)findViewById(R.id.valueremaning);
                                                        Double userCash=Double.parseDouble(valueRemaning.getText().toString().substring(17));
                                                        if(userCash>=quantity*value)
                                                            new MyData().execute("http://srmvdpauditorium.in/SRMStockMarket/buyStocks.php?emailID=" + emailID + "&stockName=" + handleSpaces(name)+ "&quantity=" + quantity);
                                                        else
                                                            Toast.makeText(StockMarket.this, "You do not have enough Cash to buy "+quantity+" stocks of "+name+"!", Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            })
                                            .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener(){
                                                public void onClick(DialogInterface dialog, int which){}})
                                            .create().show();
                                }
                            });
                    }
                    stockDialog.create().show();
                }
            });
        }
    }
    private class MyData extends AsyncTask<String,Void,Void>{
        String cashRemaining="Cash Remaining : ";
        HashMap<String,String> userShares;
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute(){
            progressDialog = ProgressDialog.show(StockMarket.this, "Please Wait!","Fetching Your Stock Details");
            super.onPreExecute();
            userShares=new HashMap<>();
        }
        @Override
        protected Void doInBackground(String... strings){
            URL url;
            HttpURLConnection urlConnection = null;
            try
            {
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                BufferedReader br=new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String data,webPage="";
                while ((data=br.readLine()) != null)
                    webPage=webPage+data;
                while(webPage.indexOf('<')!=-1)
                {
                    String key=webPage.substring(0,webPage.indexOf('<'));
                    webPage=webPage.substring(webPage.indexOf('<')+1);
                    String value=webPage.substring(0,webPage.indexOf('>'));
                    webPage=webPage.substring(webPage.indexOf('>')+1);
                    if(key.equals("Cash"))
                        cashRemaining=cashRemaining+value;
                    else
                        userShares.put(key,value);
                }
            }
            catch (IOException e)
            {
                Toast.makeText(StockMarket.this, "Oops! Some Error Occurred!", Toast.LENGTH_SHORT).show();
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
        protected void onPostExecute(Void aVoid){
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
    private class FetchStockData extends AsyncTask<Void,Void,Void>{
        ProgressDialog progressDialog;
        ArrayList<String> values;
        @Override
        protected void onPreExecute(){
            progressDialog = ProgressDialog.show(StockMarket.this, "Please Wait!","Fetching Latest Stock Data");
            super.onPreExecute();
            values=new ArrayList<>();
        }
        @Override
        protected Void doInBackground(Void... voids){
            URL url;
            HttpURLConnection urlConnection = null;
            try
            {
                url = new URL("http://srmvdpauditorium.in/SRMStockMarket/getStockData.php");
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStreamReader isw = new InputStreamReader(urlConnection.getInputStream());
                BufferedReader br=new BufferedReader(isw);
                String data,webPage="";
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
                Toast.makeText(StockMarket.this, "Oops! Some Error Occurred!", Toast.LENGTH_SHORT).show();
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
        protected void onPostExecute(Void aVoid){
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
            Toast.makeText(StockMarket.this, "Tap on a Stock to Interact", Toast.LENGTH_SHORT).show();
        }
    }
    String handleSpaces(String s){
        String x="";
        for(int i=0;i<s.length();i++)
        {
            char ch= s.charAt(i);
            if(ch==' ')
                x=x+"%20";
            else
                x=x+ch;
        }
        return x;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home)
            onBackPressed();
        if (id == R.id.leaderboard) {
            Intent intent = new Intent(StockMarket.this, LeaderboardActivity.class);
            intent.putExtra("emailID", emailID);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
}