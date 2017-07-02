package innominatebit.aaruush17.Fragments;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Image;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.TweetUtils;
import com.twitter.sdk.android.tweetui.TweetView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import innominatebit.aaruush17.R;

public class Hub extends Fragment {

    public Hub() {
    }

    private LinearLayout myLayout;

    private ImageView twitter;

    private TextView loading;

    private View view;

    private Boolean LOADED = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_hub, container, false);

        Twitter.initialize(getContext());

        myLayout = (LinearLayout) view.findViewById(R.id.my_tweet_layout);

        twitter = (ImageView) view.findViewById(R.id.twitter);

        loading = (TextView) view.findViewById(R.id.loadingtext);

        if (!LOADED) {

            new FetchTweets().execute();

        }

        return view;

    }


    private class FetchTweets extends AsyncTask<Void, Void, Void> {

        String webPage = "";

        @Override
        protected void onPreExecute() {

            super.onPreExecute();

        }


        @Override
        protected Void doInBackground(Void... voids) {

            URL url;

            HttpURLConnection urlConnection = null;

            try {

                url = new URL("http://srmvdpauditorium.in/tjson/x/");

                urlConnection = (HttpURLConnection) url.openConnection();

                BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                String data;

                while ((data = br.readLine()) != null)

                    webPage = webPage + data;

            } catch (IOException e) {

                e.printStackTrace();

            } finally {

                if (urlConnection != null) {

                    urlConnection.disconnect();

                }

                return null;

            }

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            LOADED = true;

            try {

                JSONObject json = new JSONObject(webPage);

                JSONArray statuses = json.getJSONArray("statuses");

                for (int size = statuses.length(), i = 0; i < size; i++) {

                    JSONObject temp = statuses.getJSONObject(i);

                    final long key = temp.getLong("id");

                    TweetUtils.loadTweet(key, new Callback<Tweet>() {
                        @Override
                        public void success(Result<Tweet> result) {

                            twitter.setVisibility(View.GONE);

                            loading.setVisibility(View.GONE);

                            CardView card = new CardView(getContext());

                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                            );

                            params.setMargins(8, 4, 8, 4);

                            card.setLayoutParams(params);

                            card.setRadius(9);

                            card.setContentPadding(8, 8, 8, 8);

                            TweetView tweetView = new TweetView(getContext(), result.data);

                            tweetView.setTweetActionsEnabled(true);

                            card.addView(tweetView);

                            myLayout.addView(card);

                        }


                        @Override
                        public void failure(TwitterException exception) {

                            Toast.makeText(getContext(), "Failed To Load " + key, Toast.LENGTH_LONG).show();

                        }

                    });

                }

            } catch (JSONException e) {

                e.printStackTrace();

                Toast.makeText(getContext(), "Error Occured While Fetching. Please Check You Internet Connection.", Toast.LENGTH_LONG).show();

            }

        }

    }

}