package innominatebit.aaruush17.NavigationDrawerElements;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import innominatebit.aaruush17.R;
import com.squareup.picasso.Picasso;

public class MeetTheDevTransition extends AppCompatActivity {

    private ImageView dp;

    private TextView namefield;

    private TextView collegefield;

    private TextView agefield;

    private TextView professionfield;

    private TextView expertisefield;

    private TextView appliedfield;

    private TextView bio;

    private String name;

    private String age;

    private String profession;

    private String college;

    private String expertise;

    private String applied;

    private String aboutme;

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
