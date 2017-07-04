package innominatebit.aaruush17.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import innominatebit.aaruush17.R;
import innominatebit.aaruush17.Storage.TinyDB;

public class Domains extends Fragment implements View.OnClickListener {

    public Domains() {
    }

    private View view;

    private int length;

    private int i;

    private LinearLayout favouritesarea;

    private LinearLayout fclickit;

    private LinearLayout fbuildify;

    private LinearLayout fadbees;

    private LinearLayout fdigitomania;

    private LinearLayout fsourcecode;

    private LinearLayout clickit;

    private LinearLayout buildify;

    private LinearLayout adbees;

    private LinearLayout digitomania;

    private LinearLayout sourcecode;

    private ImageView fclickitstar;

    private ImageView fbuildifystar;

    private ImageView fadbeesstar;

    private ImageView fdigitomaniastar;

    private ImageView fsourcecodestar;

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


        // Defining Variables

        clickitstar = (ImageView) view.findViewById(R.id.clickitstar);

        buildifystar = (ImageView) view.findViewById(R.id.buildifystar);

        adbeesstar = (ImageView) view.findViewById(R.id.adbeesstar);

        digitomaniastar = (ImageView) view.findViewById(R.id.digitomaniastar);

        sourcecodestar = (ImageView) view.findViewById(R.id.sourcecodestar);

        fclickitstar = (ImageView) view.findViewById(R.id.fclickitstar);

        fbuildifystar = (ImageView) view.findViewById(R.id.fbuildifystar);

        fadbeesstar = (ImageView) view.findViewById(R.id.fadbeesstar);

        fdigitomaniastar = (ImageView) view.findViewById(R.id.fdigitomaniastar);

        fsourcecodestar = (ImageView) view.findViewById(R.id.fsourcecodestar);

        favouritesarea = (LinearLayout) view.findViewById(R.id.favourites);

        fclickit = (LinearLayout) view.findViewById(R.id.fclickit);

        fbuildify = (LinearLayout) view.findViewById(R.id.fbuildify);

        fadbees = (LinearLayout) view.findViewById(R.id.fadbees);

        fdigitomania = (LinearLayout) view.findViewById(R.id.fdigitomania);

        fsourcecode = (LinearLayout) view.findViewById(R.id.fsourcecode);

        clickit = (LinearLayout) view.findViewById(R.id.clickit);

        buildify = (LinearLayout) view.findViewById(R.id.buildify);

        adbees = (LinearLayout) view.findViewById(R.id.adbees);

        digitomania = (LinearLayout) view.findViewById(R.id.digitomania);

        sourcecode = (LinearLayout) view.findViewById(R.id.sourcecode);


        // Initiating TinyDB

        db = new TinyDB(getContext());

        favourites = db.getListString("Favourites");

        if (favourites.isEmpty()) {

            db.putListString("Favourites", favourites);

            favouritesarea.setVisibility(View.GONE);

            Toast.makeText(getContext(), "Empty", Toast.LENGTH_LONG).show();

        } else {

            fclickit.setVisibility(View.GONE);

            fbuildify.setVisibility(View.GONE);

            fadbees.setVisibility(View.GONE);

            fdigitomania.setVisibility(View.GONE);

            fsourcecode.setVisibility(View.GONE);

            length = favourites.size();

            for (i = 0; i < length; i++) {

                if (favourites.get(i).contains("fclickit")) {

                    if (fclickit.getVisibility() != View.VISIBLE) {

                        fclickit.setVisibility(View.VISIBLE);

                        clickit.setVisibility(View.GONE);

                    }

                }

                if (favourites.get(i).contains("fbuildify")) {

                    if (fbuildify.getVisibility() != View.VISIBLE) {

                        fbuildify.setVisibility(View.VISIBLE);

                        buildify.setVisibility(View.GONE);

                    }

                }

                if (favourites.get(i).contains("fadbees")) {

                    if (fadbees.getVisibility() != View.VISIBLE) {

                        fadbees.setVisibility(View.VISIBLE);

                        adbees.setVisibility(View.GONE);

                    }

                }

                if (favourites.get(i).contains("fdigitomania")) {

                    if (fdigitomania.getVisibility() != View.VISIBLE) {

                        fdigitomania.setVisibility(View.VISIBLE);

                        digitomania.setVisibility(View.GONE);

                    }

                }

                if (favourites.get(i).contains("fsourcecode")) {

                    if (fsourcecode.getVisibility() != View.VISIBLE) {

                        fsourcecode.setVisibility(View.VISIBLE);

                        sourcecode.setVisibility(View.GONE);

                    }
                }

            }

        }


        // Adding On Click Listeners

        fclickitstar.setOnClickListener(this);

        fbuildifystar.setOnClickListener(this);

        fadbeesstar.setOnClickListener(this);

        fdigitomaniastar.setOnClickListener(this);

        fsourcecodestar.setOnClickListener(this);

        clickitstar.setOnClickListener(this);

        buildifystar.setOnClickListener(this);

        adbeesstar.setOnClickListener(this);

        digitomaniastar.setOnClickListener(this);

        sourcecodestar.setOnClickListener(this);

        return view;

    }


    private void favouriteCheck() {

        fclickit.setVisibility(View.GONE);

        fbuildify.setVisibility(View.GONE);

        fadbees.setVisibility(View.GONE);

        fdigitomania.setVisibility(View.GONE);

        fsourcecode.setVisibility(View.GONE);

        favourites = db.getListString("Favourites");

        length = favourites.size();

        for (i = 0; i < length; i++) {

            if (favourites.get(i).contains("fclickit")) {

                fclickit.setVisibility(View.VISIBLE);

                clickit.setVisibility(View.GONE);

            } else if (favourites.get(i).contains("fbuildify")) {

                fbuildify.setVisibility(View.VISIBLE);

                buildify.setVisibility(View.GONE);

            } else if (favourites.get(i).contains("fadbees")) {

                fadbees.setVisibility(View.VISIBLE);

                adbees.setVisibility(View.GONE);

            } else if (favourites.get(i).contains("fdigitomania")) {

                fdigitomania.setVisibility(View.VISIBLE);

                digitomania.setVisibility(View.GONE);

            } else if (favourites.get(i).contains("fsourcecode")) {

                fsourcecode.setVisibility(View.VISIBLE);

                sourcecode.setVisibility(View.GONE);

            } else {

                Toast.makeText(getContext(), "Error Including Favourites!", Toast.LENGTH_LONG).show();

            }

        }

    }


    @Override
    public void onClick(View view) {

        if (view == clickitstar) {

            favourites.add("fclickit");

            db.putListString("Favourites", favourites);

            favouritesarea.setVisibility(View.VISIBLE);

            favouriteCheck();

            fclickit.setVisibility(View.VISIBLE);

            clickit.setVisibility(View.GONE);

            Toast.makeText(getContext(), "Click It Added To Favourites!", Toast.LENGTH_LONG).show();

        } else if (view == buildifystar) {

            favourites.add("fbuildify");

            db.putListString("Favourites", favourites);

            favouritesarea.setVisibility(View.VISIBLE);

            favouriteCheck();

            fbuildify.setVisibility(View.VISIBLE);

            buildify.setVisibility(View.GONE);

            Toast.makeText(getContext(), "Buildify Added To Favourites!", Toast.LENGTH_LONG).show();


        } else if (view == adbeesstar) {

            favourites.add("fadbees");

            db.putListString("Favourites", favourites);

            favouritesarea.setVisibility(View.VISIBLE);

            favouriteCheck();

            fadbees.setVisibility(View.VISIBLE);

            adbees.setVisibility(View.GONE);

            Toast.makeText(getContext(), "Ad-Bees Added To Favourites!", Toast.LENGTH_LONG).show();


        } else if (view == digitomaniastar) {

            favourites.add("fdigitomania");

            db.putListString("Favourites", favourites);

            favouritesarea.setVisibility(View.VISIBLE);

            favouriteCheck();

            fdigitomania.setVisibility(View.VISIBLE);

            digitomania.setVisibility(View.GONE);

            Toast.makeText(getContext(), "Digito Mania Added To Favourites!", Toast.LENGTH_LONG).show();


        } else if (view == sourcecodestar) {

            favourites.add("fsourcecode");

            db.putListString("Favourites", favourites);

            favouritesarea.setVisibility(View.VISIBLE);

            favouriteCheck();

            fsourcecode.setVisibility(View.VISIBLE);

            sourcecode.setVisibility(View.GONE);

            Toast.makeText(getContext(), "Source Code 2.0 Added To Favourites!", Toast.LENGTH_LONG).show();

        } else if (view == fclickitstar) {

            favourites.remove("fclickit");

            db.putListString("Favourites", favourites);

            favouriteCheck();

            fclickit.setVisibility(View.GONE);

            clickit.setVisibility(View.VISIBLE);

            Toast.makeText(getContext(), "Click It Removed From Favourites!", Toast.LENGTH_LONG).show();

        } else if (view == fbuildifystar) {

            favourites.remove("fbuildify");

            db.putListString("Favourites", favourites);

            favouriteCheck();

            fbuildify.setVisibility(View.GONE);

            buildify.setVisibility(View.VISIBLE);

            Toast.makeText(getContext(), "Buildify Removed From Favourites!", Toast.LENGTH_LONG).show();


        } else if (view == fadbeesstar) {

            favourites.remove("fadbees");

            db.putListString("Favourites", favourites);

            favouriteCheck();

            fadbees.setVisibility(View.GONE);

            adbees.setVisibility(View.VISIBLE);

            Toast.makeText(getContext(), "Ad-Bees Removed From Favourites!", Toast.LENGTH_LONG).show();


        } else if (view == fdigitomaniastar) {

            favourites.remove("fdigitomania");

            db.putListString("Favourites", favourites);

            favouriteCheck();

            fdigitomania.setVisibility(View.GONE);

            digitomania.setVisibility(View.VISIBLE);

            Toast.makeText(getContext(), "Digito Mania Removed From Favourites!", Toast.LENGTH_LONG).show();


        } else if (view == fsourcecodestar) {

            favourites.remove("fsourcecode");

            db.putListString("Favourites", favourites);

            favouriteCheck();

            fsourcecode.setVisibility(View.GONE);

            sourcecode.setVisibility(View.VISIBLE);

            Toast.makeText(getContext(), "Source Code 2.0 Removed From Favourites!", Toast.LENGTH_LONG).show();

        }

    }

}
