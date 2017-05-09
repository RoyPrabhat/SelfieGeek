package com.roy.selfiegeek.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import com.roy.selfiegeek.R;
import com.roy.selfiegeek.adapter.GridViewAdapter;

/**
 * The following class is a work in progress
 * @author prabhat.roy.
 */

public class UploadedActivity extends AppCompatActivity {
    private GridView gridView;
    private GridViewAdapter gridAdapter;
    private Cursor cursor;
    private int columnIndex;
    public static final String TAG = CameraActivity.class.getSimpleName();

    /* TODO Download the images and videos uploaded to dropbox and show thumbnails of the same in a grid view as a art of this activity */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploaded);

        gridView = (GridView) findViewById(R.id.gridView);
 //       gridAdapter = new GridViewAdapter(this, R.layout.grid_item_layout, getData());
//        gridView.setAdapter(gridAdapter);
    }

    // Prepare some dummy data for gridview
  /*  private ArrayList<ImageItem> getData() {
        final ArrayList<ImageItem> imageItems = new ArrayList<>();
        TypedArray imgs = getResources().obtainTypedArray(R.array.image_ids);
        for (int i = 0; i < imgs.length(); i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));
            imageItems.add(new ImageItem(bitmap, "Image#" + i));
        }
        return imageItems;
    }
*/
}
