package innominatebit.aaruush17.NavigationDrawerElements;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import innominatebit.aaruush17.R;

import com.squareup.picasso.Picasso;

public class MeetTheDevTransition extends AppCompatActivity {
    private ImageView dp;
    private TextView namefield, collegefield, agefield, professionfield, expertisefield, appliedfield, bio;
    private String name, age, profession, college, expertise, applied, aboutme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflating View
        setContentView(R.layout.activity_meet_the_dev_transition);
        // Declaring All Datatypes
        dp = (ImageView) findViewById(R.id.picture);
        namefield = (TextView) findViewById(R.id.namefield);
        collegefield = (TextView) findViewById(R.id.collegefield);
        agefield = (TextView) findViewById(R.id.agefield);
        professionfield = (TextView) findViewById(R.id.professionfield);
        expertisefield = (TextView) findViewById(R.id.expertisefield);
        appliedfield = (TextView) findViewById(R.id.appliedfield);
        bio = (TextView) findViewById(R.id.bio);
        // Getting Data From Bundle
        Bundle bundle = getIntent().getExtras();
        name = bundle.getString("name");
        age = bundle.getString("age");
        profession = bundle.getString("profession");
        college = bundle.getString("college");
        expertise = bundle.getString("expertise");
        applied = bundle.getString("applied");
        aboutme = bundle.getString("aboutme");
        String activity = bundle.getString("Activity");
        // Setting Values
        namefield.setText(name);
        collegefield.setText(college);
        agefield.setText(age);
        professionfield.setText(profession);
        expertisefield.setText(expertise);
        appliedfield.setText(applied);
        bio.setText(aboutme);
        Bitmap bmp = (Bitmap) bundle.getParcelable("imagebitmap");
        dp.setImageBitmap(getRoundedCornerBitmap(bmp, 10));
        if (activity.equals("Team")) {
            ((TextView) findViewById(R.id.age)).setText("Designation");
            ((TextView) findViewById(R.id.profession)).setText("Email ID");
            final String number = collegefield.getText().toString();
            collegefield.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + number));
                    if (ActivityCompat.checkSelfPermission(MeetTheDevTransition.this,
                            Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(MeetTheDevTransition.this, "Permission Missing to Call.", Toast.LENGTH_LONG).show();
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    startActivity(callIntent);
                }
            });
        }
        if(expertise.equals(""))
        {
            (findViewById(R.id.expertise)).setVisibility(View.GONE);
            expertisefield.setVisibility(View.GONE);
        }
        if(aboutme.equals(""))
            (findViewById(R.id.cardview2)).setVisibility(View.GONE);
    }
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }
}