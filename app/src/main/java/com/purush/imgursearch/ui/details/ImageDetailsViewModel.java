package com.purush.imgursearch.ui.details;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.purush.imgursearch.data.repositories.CommentRepository;
import com.purush.imgursearch.data.source.local.entities.CommentEntity;
import com.purush.imgursearch.data.source.local.entities.ImageEntity;
import com.purush.imgursearch.data.source.local.entities.ImageWithComments;
import com.purush.imgursearch.data.source.remote.schema.Image;


public class ImageDetailsViewModel extends ViewModel {

    private final MutableLiveData<Image> selectedImageLiveData;
    private final CommentRepository commentRepository;

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

    public LiveData<ImageWithComments> getImageWithComment(String imageId) {
        return commentRepository.getCommentsForImage(imageId);
    }

    public void addCommentForImage(String comment, Image selectedImage) {

        ImageEntity imageEntity = new ImageEntity(selectedImage.getId(), selectedImage.getTitle());
        CommentEntity commentEntity = new CommentEntity(comment, selectedImage.getId());

        commentRepository.insertCommentToImage(imageEntity, commentEntity);
    }
}
