package innominatebit.aaruush17;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class LeaderboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        Bundle bundle = getIntent().getExtras();
        String emailID = bundle.getString("emailID");
        new LoadLeaderboard().execute(emailID);
    }
    private class LoadLeaderboard extends AsyncTask<String,Void,Void>{
        ArrayList<String> ranks;
        ArrayList<String> names;
        ArrayList<String> worth;
        String webPage="";
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(LeaderboardActivity.this, "Please Wait!","Loading Leaderboard");
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(String... strings) {
            URL url;
            HttpURLConnection urlConnection = null;
            try
            {
                url = new URL("http://srmvdpauditorium.in/SRMStockMarket/leaderBoard.php?emailID="+strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStreamReader isw = new InputStreamReader(urlConnection.getInputStream());
                BufferedReader br=new BufferedReader(isw);
                String data;
                while ((data=br.readLine()) != null)
                    webPage=webPage+data;
                ranks = new ArrayList<>();
                names=new ArrayList<>();
                worth=new ArrayList<>();
                while(webPage.contains(":"))
                {
                    int in=webPage.indexOf(':');
                    ranks.add(webPage.substring(0,in));
                    webPage=webPage.substring(in+1);
                    in=webPage.indexOf(':');
                    names.add(webPage.substring(0,in));
                    webPage=webPage.substring(in+1);
                    in=webPage.indexOf(':');
                    worth.add(webPage.substring(0,in));
                    webPage=webPage.substring(in+1);
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
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Context context=LeaderboardActivity.this;
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout
                    .LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            LinearLayout linearLayout1=(LinearLayout) findViewById(R.id.linearLayout);
            TextView tv1=new TextView(context);
            layoutParams.weight = 1;
            tv1.setLayoutParams(layoutParams);
            tv1.setText("Rank");
            tv1.setPadding(8,0,0,0);
            tv1.setTextSize(20);
            linearLayout1.addView(tv1);
            TextView tv2=new TextView(context);
            layoutParams.weight = 10;
            tv2.setLayoutParams(layoutParams);
            tv2.setText("Name");
            tv2.setTextSize(20);
            linearLayout1.addView(tv2);
            TextView tv3=new TextView(context);
            layoutParams.weight = 4;
            tv3.setLayoutParams(layoutParams);
            tv3.setText("Worth");
            tv3.setGravity(Gravity.CENTER_HORIZONTAL);
            tv3.setTextSize(20);
            linearLayout1.addView(tv3);
            for(int i=0;i<ranks.size();i++)
            {
                LinearLayout linearLayout=new LinearLayout(context);
                if(!ranks.get(i).equals("."))
                    linearLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.border));
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                linearLayout.setPadding(0,8,0,8);
                LinearLayout data=(LinearLayout)findViewById(R.id.ranks);
                TextView tv11=new TextView(context);
                layoutParams.weight = 1;
                tv11.setLayoutParams(layoutParams);
                tv11.setText(ranks.get(i));
                tv11.setPadding(8,0,0,0);
                tv11.setTextSize(16);
                linearLayout.addView(tv11);
                TextView tv22=new TextView(context);
                layoutParams.weight = 10;
                tv22.setLayoutParams(layoutParams);
                tv22.setText(names.get(i));
                tv22.setTextSize(16);
                linearLayout.addView(tv22);
                TextView tv33=new TextView(context);
                layoutParams.weight = 4;
                tv33.setLayoutParams(layoutParams);
                tv33.setText(worth.get(i));
                tv33.setGravity(Gravity.CENTER_HORIZONTAL);
                tv33.setTextSize(16);
                linearLayout.addView(tv33);
                data.addView(linearLayout);
            }
            progressDialog.dismiss();
        }
    }
}