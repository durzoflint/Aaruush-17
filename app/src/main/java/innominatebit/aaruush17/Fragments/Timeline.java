package innominatebit.aaruush17.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.HashMap;

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

    private TextView username;

    private CircleImageView dp;

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

        Picasso.with(getContext()).load(profilepicture).into(dp);


        // Loading Data In TextView

        return view;

    }




}
