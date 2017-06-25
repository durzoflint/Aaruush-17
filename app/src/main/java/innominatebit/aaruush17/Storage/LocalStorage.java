package innominatebit.aaruush17.Storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;

public class LocalStorage {

    SharedPreferences pref;

    SharedPreferences.Editor editor;

    Context _context;

    int PRIVATE_MODE = 0;

    public static final String PREF_NAME = "Aaruush Pref";

    public static final String METHOD = "Method";

    public static final String PROFILEID = "q";

    public static final String PROFILELINK = "w";

    public static final String PICTURE = "e";

    public static final String FIRSTNAME = "r";

    public static final String LASTNAME = "t";

    public static final String EMAIL = "y";

    public static final String FIREBASETOKEN = "f";


    public LocalStorage(Context context) {

        this._context = context;

        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);

        editor = pref.edit();

    }


    public void loginSession(String method, String profileid, String profilelink, String picture, String firstname, String lastname, String email) {

        editor.putString(METHOD, method);

        editor.putString(PROFILEID, profileid);

        editor.putString(PROFILELINK, profilelink);

        editor.putString(PICTURE, picture);

        editor.putString(FIRSTNAME, firstname);

        editor.putString(LASTNAME, lastname);

        editor.putString(EMAIL, email);

        editor.commit();

    }


    public void firebaseToken(String token) {

        editor.putString(FIREBASETOKEN, token);

        editor.commit();

    }


    public void logoutUser() {

        editor.clear();

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        firebaseToken(refreshedToken);

        editor.commit();

    }


    public HashMap<String, String> getProfileDetails() {

        HashMap<String, String> name = new HashMap<>();

        name.put(METHOD, pref.getString(METHOD, null));

        name.put(PROFILEID, pref.getString(PROFILEID, null));

        name.put(PROFILELINK, pref.getString(PROFILELINK, null));

        name.put(PICTURE, pref.getString(PICTURE, null));

        name.put(FIRSTNAME, pref.getString(FIRSTNAME, null));

        name.put(LASTNAME, pref.getString(LASTNAME, null));

        name.put(EMAIL, pref.getString(EMAIL, null));

        return name;

    }


}