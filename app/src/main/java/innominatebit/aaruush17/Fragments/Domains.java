package innominatebit.aaruush17.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import innominatebit.aaruush17.R;
import innominatebit.aaruush17.Storage.TinyDB;

public class Domains extends Fragment implements View.OnClickListener {

    public Domains() {}

    private View view;

    private int length;

    private int i;

    private ImageView clickitstar;

    private ImageView buildifystar;

    private ImageView adbeesstar;

    private ImageView digitomaniastar;

    private ImageView sourcecodestar;

    private ArrayList<String> favourites;

    private TinyDB db;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_domains, container, false);


        // Initiating TinyDB

        db = new TinyDB(getContext());

        favourites = db.getListString("Favourites");

        if (favourites.isEmpty()) {

            db.putListString("Favourites", favourites);

            Toast.makeText(getContext(), "Empty List", Toast.LENGTH_LONG).show();

        }

        else {

            length = favourites.size();

            for (i = 0; i<length; i++) {


            }

        }


        // Defining Variables

        clickitstar = (ImageView) view.findViewById(R.id.clickitstar);

        buildifystar = (ImageView) view.findViewById(R.id.buildifystar);

        adbeesstar = (ImageView) view.findViewById(R.id.adbeesstar);

        digitomaniastar = (ImageView) view.findViewById(R.id.digitomaniastar);

        sourcecodestar = (ImageView) view.findViewById(R.id.sourcecodestar);


        // Adding On Click Listeners

        clickitstar.setOnClickListener(this);

        buildifystar.setOnClickListener(this);

        adbeesstar.setOnClickListener(this);

        digitomaniastar.setOnClickListener(this);

        sourcecodestar.setOnClickListener(this);

        return view;

    }



    @Override
    public void onClick(View view) {

        if (view == clickitstar) {

            favourites.add("Click It");

            db.putListString("Favourites", favourites);

            Toast.makeText(getContext(), "Click It Added To Favourites!", Toast.LENGTH_LONG).show();

        }

        else if (view == buildifystar) {

            favourites.add("Buildify");

            db.putListString("Favourites", favourites);

            Toast.makeText(getContext(), "Buildify Added To Favourites!", Toast.LENGTH_LONG).show();


        }

        else if (view == adbeesstar) {

            favourites.add("Ad-Bees");

            db.putListString("Favourites", favourites);

            Toast.makeText(getContext(), "Ad-Bees Added To Favourites!", Toast.LENGTH_LONG).show();


        }

        else if (view == digitomaniastar) {

            favourites.add("Digito Mania");

            db.putListString("Favourites", favourites);

            Toast.makeText(getContext(), "Digito Mania Added To Favourites!", Toast.LENGTH_LONG).show();


        }

        else if (view == sourcecodestar) {

            favourites.add("Source Code 2.0");

            db.putListString("Favourites", favourites);

            Toast.makeText(getContext(), "Source Code 2.0 Added To Favourites!", Toast.LENGTH_LONG).show();


        }

    }


}
