package innominatebit.aaruush17.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;
import innominatebit.aaruush17.R;
import innominatebit.aaruush17.Storage.LocalStorage;

public class Timeline extends Fragment {
    public Timeline() {}
    private View view;
    private LocalStorage session;
    private String profileid;
    private String profilelink;
    private String profilepicture;
    private String firstname;
    private String lastname;
    private String email;
    private TextView username, timer;
    private CircleImageView dp;
    LinearLayout timelinedata;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflating View
        view = inflater.inflate(R.layout.fragment_timeline, container, false);
        // Initializing Local Storage And Importing Profile Details
        session = new LocalStorage(getContext());
        HashMap<String, String> details = session.getProfileDetails();
        profileid = details.get(LocalStorage.PROFILEID);
        profilelink = details.get(LocalStorage.PROFILELINK);
        profilepicture = details.get(LocalStorage.PICTURE);
        firstname = details.get(LocalStorage.FIRSTNAME);
        lastname = details.get(LocalStorage.LASTNAME);
        email = details.get(LocalStorage.EMAIL);
        // Variable Declarations
        dp = (CircleImageView) view.findViewById(R.id.profile_image);
        username = (TextView) view.findViewById(R.id.username);
        // Loading Images And Values
        username.setText(firstname + " " + lastname);
        if (!Objects.equals(profilepicture, "")) {
            Picasso.with(getContext()).load(profilepicture).into(dp);
        }
        // Loading Data In TextView
        timer = (TextView)view.findViewById(R.id.timer);
        timelinedata = (LinearLayout)view.findViewById(R.id.timelinedata);
        timelinedata.setVisibility(View.GONE);
        new CountDown().execute();
        return view;
    }
    private class CountDown extends AsyncTask<Void,Void,Void> {
        long days,hours,minutes,seconds;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Date curDate = new Date();
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            try {
                Date date = format.parse("15/09/2017 00:00:00");
                long diffInMillisec =date.getTime()- curDate.getTime();
                long diffInSec = TimeUnit.MILLISECONDS.toSeconds(diffInMillisec);
                seconds = diffInSec % 60;
                diffInSec/= 60;
                minutes = diffInSec % 60;
                diffInSec /= 60;
                hours = diffInSec % 24;
                diffInSec /= 24;
                days = diffInSec;
                if(diffInMillisec>0)
                    timer.setText(days+" Days, "+hours+" Hours, "+minutes+" Minutes and "+seconds+" Seconds left");
                else
                    cancel(true);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(isCancelled())
                return null;
            return null;
        }
        @Override
        protected void onCancelled(Void aVoid) {
            super.onCancelled(aVoid);
            timer.setVisibility(View.GONE);
            timelinedata.setVisibility(View.VISIBLE);
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            CountDown countDown=new CountDown();
            countDown.execute();
        }
    }
}