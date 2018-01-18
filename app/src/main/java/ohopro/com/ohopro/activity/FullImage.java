package ohopro.com.ohopro.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import ohopro.com.ohopro.R;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by home on 1/4/2018.
 */

public class FullImage extends AppCompatActivity {
    ImageView view;
    PhotoViewAttacher pAttacher;
    String imageurl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullimage);
        view=(ImageView)findViewById(R.id.fullimage);
        Bundle bundle = getIntent().getExtras();
        imageurl=bundle.getString("image");
        Glide.with(this).load(imageurl)
                .crossFade()
                .thumbnail(0.5f)
                .error(R.mipmap.logo)      // optional
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(view);
        pAttacher = new PhotoViewAttacher(view);
        pAttacher.update();


    }
}
