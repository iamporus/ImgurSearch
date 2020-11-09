package com.purush.imgursearch.ui.details;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.purush.imgursearch.ImgurSearchApplication;
import com.purush.imgursearch.R;
import com.purush.imgursearch.data.repositories.CommentRepository;
import com.purush.imgursearch.data.repositories.ImageRepository;
import com.purush.imgursearch.data.source.remote.schema.Image;
import com.purush.imgursearch.ui.main.ViewModelFactory;

public class ImageDetailsActivity extends AppCompatActivity {

    public static String TAG = "ImageDetailsActivity";
    public static String EXTRA_SELECTED_IMAGE = "com.purush.imgursearch.ui.details.extra.selected_image";
    private ImageDetailsViewModel viewModel;

    public static void navigate(Activity activity, Image selectedImage) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_SELECTED_IMAGE, selectedImage);

        Intent intent = new Intent(activity, ImageDetailsActivity.class);
        intent.putExtras(args);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_details);

        //TODO: inject these via Dagger
        ImageRepository imageRepository =
                ((ImgurSearchApplication) getApplication()).getImageRepository();
        CommentRepository commentRepository =
                ((ImgurSearchApplication) getApplication()).getCommentRepository();

        ViewModelFactory viewModelFactory = new ViewModelFactory(imageRepository, commentRepository);
        viewModel = new ViewModelProvider(this, viewModelFactory).get(ImageDetailsViewModel.class);

        viewModel.getSelectedImage().observe(this, image -> {
            if (image != null) {
                showActionBarTitle(image.getTitle());
                showSelectedImage(image.getLink());
            }
        });

        viewModel.getImageWithComment().observe(this, imageWithComments -> {
            Log.e(TAG, "getImageWithComment(): " + imageWithComments);
        });

        //TODO: functionality to add comments

        if (getIntent() != null) {
            setupSelectedImage();
        } else {
            Log.e(TAG, "onCreate: selected image received as null");
        }
    }

    private void setupSelectedImage() {

        Intent intent = getIntent();
        Image selectedImage = (Image) intent.getSerializableExtra(EXTRA_SELECTED_IMAGE);
        if (selectedImage != null) {
            viewModel.setSelectedImage(selectedImage);
        } else {
            Log.e(TAG, "showSelectedImage: selected image found to be null");
        }
    }

    private void showSelectedImage(String link) {

        ImageView selectedImageView = findViewById(R.id.selectedImageView);
        Glide.with(this).load(link)
                .fitCenter()
                .override(Target.SIZE_ORIGINAL, selectedImageView.getHeight())
                .placeholder(new ColorDrawable(Color.GRAY))
                .error(new ColorDrawable(Color.RED))
                .into(selectedImageView);
    }

    private void showActionBarTitle(String title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}