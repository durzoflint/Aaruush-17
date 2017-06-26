package innominatebit.aaruush17.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import innominatebit.aaruush17.R;

public class Domains extends Fragment {

    public Domains() {}

    private View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_domains, container, false);

        return view;
    }

}
