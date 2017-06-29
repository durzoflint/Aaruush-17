package innominatebit.aaruush17.Authentication;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import innominatebit.aaruush17.Dashboard;
import innominatebit.aaruush17.R;
import innominatebit.aaruush17.Storage.LocalStorage;

public class MainLogin extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    private Typeface logo;

    private TextView logotext;

    private Button facebooklogin;

    private Button googlelogin;

    private LocalStorage session;

    private Intent dashboard;

    // Google Declarations

    private GoogleSignInOptions gso;

    private GoogleApiClient gapiclient;

    private GoogleSignInAccount account;

    private OptionalPendingResult<GoogleSignInResult> googletoken;

    private GoogleSignInResult result;

    private Intent gsignin;

    private String gpprofileid;

    private String gpprofilepicture;

    private String gpname;

    private String gpemail;


    // Facebook Declarations

    private CallbackManager call;

    private GraphRequest request;

    private AccessToken facebooktoken;

    private Profile profile;

    private Bundle parameters;

    private String fbprofileid;

    private String fbprofilelink;

    private String fbprofilepicture;

    private String fbfirstname;

    private String fblastname;

    private String fbemail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Splash Screen Setup

        setTheme(R.style.AppThemeNoBar);

        super.onCreate(savedInstanceState);


        // Initializing Fonts

        logo = Typeface.createFromAsset(getAssets(), "fonts/galada.ttf");


        // Initializing Facebook And Google SDK

        facebookSDK();

        googleSDK();


        // Checking Existing Logins

        facebooktoken = AccessToken.getCurrentAccessToken();

        googletoken = Auth.GoogleSignInApi.silentSignIn(gapiclient);

        if (facebooktoken != null || googletoken.isDone()) {

            dashboard = new Intent(MainLogin.this, Dashboard.class);

            startActivity(dashboard);

            finish();

        }


        // Inflating View

        setContentView(R.layout.activity_login);


        // Initialaing Local Storage

        session = new LocalStorage(getApplicationContext());


        // Variable Declarations

        logotext = (TextView) findViewById(R.id.logotext);

        facebooklogin = (Button) findViewById(R.id.facebooklogin);

        googlelogin = (Button) findViewById(R.id.googlelogin);


        // Setting OnClick Listeners On The Buttons

        facebooklogin.setOnClickListener(this);

        googlelogin.setOnClickListener(this);


        // Applying Aaruush Font

        logotext.setTypeface(logo);


    }


    private void facebookSDK() {

        call = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(call, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {

                request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {

                        try {

                            // Getting Facebook User Credentials From The Graph Request JSON

                            fbfirstname = response.getJSONObject().getString("first_name");

                            fblastname = response.getJSONObject().getString("last_name");

                            fbemail = response.getJSONObject().getString("email");


                            // Calling For Profile Information From The Resultant GraphQL

                            profile = Profile.getCurrentProfile();

                            fbprofileid = profile.getId();

                            fbprofilelink = profile.getLinkUri().toString();


                            // Generating 200 X 200 Profile Picture For Internal App Uses

                            fbprofilepicture = Profile.getCurrentProfile().getProfilePictureUri(200, 200).toString();


                            // Saving Facebook Details In Local Storage

                            session.loginSession("Facebook", fbprofileid, fbprofilelink, fbprofilepicture, fbfirstname, fblastname, fbemail);


                            // Moving To The Dashboard

                            dashboard = new Intent(MainLogin.this, Dashboard.class);

                            startActivity(dashboard);

                            finish();


                        } catch (JSONException e) {

                            e.printStackTrace();

                        }

                    }

                });

                parameters = new Bundle();

                parameters.putString("fields", "id, email, first_name, last_name");

                request.setParameters(parameters);

                request.executeAsync();

            }

            @Override
            public void onCancel() {

                Toast.makeText(MainLogin.this, "Login Cancelled!", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onError(FacebookException exception) {

                Toast.makeText(MainLogin.this, exception.getMessage(), Toast.LENGTH_LONG).show();

            }

        });

    }


    private void googleSDK() {

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        gapiclient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, (GoogleApiClient.OnConnectionFailedListener) this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

    }


    private void googleDetails(GoogleSignInResult result) {

        if (result.isSuccess()) {

            account = result.getSignInAccount();

            // Getting Google User Credentials

            gpprofileid = account.getId();

            gpname = account.getDisplayName();

            gpemail = account.getEmail();


            // Generating Profile Picture For Internal App Uses

            if (gpprofilepicture != "") {

                gpprofilepicture = account.getPhotoUrl().toString();

            }

            else {

                gpprofilepicture = "";

            }


            // Saving Google Details In Local Storage

            session.loginSession("Google", gpprofileid, "", gpprofilepicture, gpname, "", gpemail);


            // Moving To The Dashboard

            dashboard = new Intent(MainLogin.this, Dashboard.class);

            startActivity(dashboard);

            finish();

        } else {

            Toast.makeText(MainLogin.this, "Error", Toast.LENGTH_LONG).show();

        }

    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {}


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        call.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0601) {

            result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            googleDetails(result);

        }

    }


    @Override
    public void onClick(View view) {

        if (view == facebooklogin) {

            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("user_friends", "email", "public_profile"));

        } else if (view == googlelogin) {

            gsignin = Auth.GoogleSignInApi.getSignInIntent(gapiclient);

            startActivityForResult(gsignin, 0601);

        }

    }


}
