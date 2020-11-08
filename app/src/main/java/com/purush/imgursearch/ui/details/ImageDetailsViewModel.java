package com.purush.imgursearch.ui.details;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.purush.imgursearch.data.repositories.CommentRepository;
import com.purush.imgursearch.data.schema.Image;

public class ImageDetailsViewModel extends ViewModel {

    private MutableLiveData<Image> selectedImageLiveData;
    private CommentRepository commentRepository;

    public ImageDetailsViewModel(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
        selectedImageLiveData = new MutableLiveData<>();
    }

    public void setSelectedImage(Image selectedImage) {
        selectedImageLiveData.setValue(selectedImage);
    }

    public MutableLiveData<Image> getSelectedImage() {
        return selectedImageLiveData;
    }
}
