package com.purush.imgursearch.ui.details;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.purush.imgursearch.ImgurSearchApplication;
import com.purush.imgursearch.R;
import com.purush.imgursearch.data.source.local.entities.CommentEntity;
import com.purush.imgursearch.data.source.remote.schema.Image;
import com.purush.imgursearch.ui.main.ViewModelFactory;

import javax.inject.Inject;

public class ImageDetailsActivity extends AppCompatActivity {

    public static String TAG = "ImageDetailsActivity";
    public static String EXTRA_SELECTED_IMAGE = "com.purush.imgursearch.ui.details.extra.selected_image";
    private Image selectedImage;

    @Inject
    ViewModelFactory viewModelFactory;

    private ImageDetailsViewModel viewModel;

    public static void navigate(Activity activity, Image selectedImage) {
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_SELECTED_IMAGE, selectedImage);

        Intent intent = new Intent(activity, ImageDetailsActivity.class);
        intent.putExtras(args);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ((ImgurSearchApplication) getApplication()).getAppComponent().inject(this);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_image_details);

        viewModel = new ViewModelProvider(this, viewModelFactory).get(ImageDetailsViewModel.class);

        if (getIntent() != null) {

            Intent intent = getIntent();
            selectedImage = (Image) intent.getParcelableExtra(EXTRA_SELECTED_IMAGE);

            if (selectedImage != null) {
                viewModel.setSelectedImage(selectedImage);
            } else {
                Log.e(TAG, "showSelectedImage: selected image found to be null");
            }
        } else {
            Log.e(TAG, "onCreate: selected image received as null");
        }

        viewModel.getSelectedImage().observe(this, image -> {
            if (image != null) {
                showActionBarTitle(image.getTitle());
                showSelectedImage(image.getLink());
            }
        });

        viewModel.getImageWithComment(selectedImage.getId()).observe(this, imageWithComments -> {

            TextView commentsBoxTextView = findViewById(R.id.commentsTextView);
            if (imageWithComments != null && imageWithComments.getComments().size() > 0) {
                StringBuilder stringBuilder = new StringBuilder();
                for (CommentEntity comment : imageWithComments.getComments()) {
                    stringBuilder.append(" - ").append(comment.getComment()).append("\n");
                }
                commentsBoxTextView.setText(stringBuilder.toString());
            } else {
                commentsBoxTextView.setText(R.string.no_comments);
            }
        });

        Button addCommentButton = findViewById(R.id.addCommentButton);
        addCommentButton.setOnClickListener(v -> {

            EditText editTextBox = findViewById(R.id.commentEditText);
            String comment = editTextBox.getText().toString();
            if (comment.length() > 0) {
                viewModel.addCommentForImage(comment, selectedImage);
                editTextBox.setText("");
                hideKeyboard(editTextBox);
            }
        });

    }

    private void hideKeyboard(EditText editTextBox) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm != null)
            imm.hideSoftInputFromWindow(editTextBox.getWindowToken(), 0);
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