package innominatebit.aaruush17.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import innominatebit.aaruush17.R;


public class CustomGridViewAdapter extends BaseAdapter {

    private Context mContext;

    private final String[] gridViewString;

    private final int[] gridViewImageId;

    public CustomGridViewAdapter(Context context, String[] gridViewString, int[] gridViewImageId) {

        mContext = context;

        this.gridViewImageId = gridViewImageId;

        this.gridViewString = gridViewString;

    }

    @Override
    public int getCount() {

        return gridViewString.length;

    }

    @Override
    public Object getItem(int i) {

        return null;

    }

    @Override
    public long getItemId(int i) {

        return 0;

    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {

        View v;

        if (convertView == null)

        {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            v = new View(mContext);

            v = inflater.inflate(R.layout.customlist, null);

        } else {

            v = (View) convertView;

        }

        ImageView imageViewAndroid = (ImageView) v.findViewById(R.id.android_gridview_image);

        imageViewAndroid.setImageResource(gridViewImageId[i]);

        return v;

    }

}