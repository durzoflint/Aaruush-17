package innominatebit.aaruush17.Firebase;

import android.util.Log;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import innominatebit.aaruush17.Storage.LocalStorage;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    private LocalStorage session;

    @Override
    public void onTokenRefresh() {

        session = new LocalStorage(getApplicationContext());

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        session.firebaseToken(refreshedToken);

        Log.d(TAG, "Refreshed Token: " + refreshedToken);

    }

}