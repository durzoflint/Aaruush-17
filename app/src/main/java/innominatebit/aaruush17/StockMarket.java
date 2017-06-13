package innominatebit.aaruush17;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

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
        new FetchStockData().execute();
    }
    class FetchStockData extends AsyncTask<Void,Void,Void>
    {
        ArrayList<String> values;
        String murl="http://srmvdpauditorium.in/SRMStockMarket/getStockData.php";
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
            super.onPostExecute(aVoid);
        }
    }
}
