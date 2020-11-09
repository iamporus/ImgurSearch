package com.purush.imgursearch.ui.details;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.purush.imgursearch.data.repositories.CommentRepository;
import com.purush.imgursearch.data.source.local.entities.CommentEntity;
import com.purush.imgursearch.data.source.local.entities.ImageEntity;
import com.purush.imgursearch.data.source.local.entities.ImageWithComments;
import com.purush.imgursearch.data.source.remote.schema.Image;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


public class ImageDetailsViewModel extends ViewModel {

    private final MutableLiveData<Image> selectedImageLiveData;
    private final CommentRepository commentRepository;
    private final MutableLiveData<ImageWithComments> imageWithCommentsLiveData;

    public ImageDetailsViewModel(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
        selectedImageLiveData = new MutableLiveData<>();
        imageWithCommentsLiveData = new MutableLiveData<>();
    }

    public void setSelectedImage(Image selectedImage) {
        selectedImageLiveData.setValue(selectedImage);

        CompletableFuture<ImageWithComments> future =
                commentRepository.getCommentsForImageAsync(selectedImage.getId());
        try {

            imageWithCommentsLiveData.setValue(future.get());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public MutableLiveData<Image> getSelectedImage() {
        return selectedImageLiveData;
    }

    public LiveData<ImageWithComments> getImageWithComment() {
        return imageWithCommentsLiveData;
    }

    @SuppressWarnings("unused")
    public void addCommentForImage(String comment, Image selectedImage) {

        ImageEntity imageEntity = new ImageEntity(selectedImage.getId(), selectedImage.getTitle());
        CommentEntity commentEntity = new CommentEntity(comment, selectedImage.getId());

        commentRepository.insertCommentToImageAsync(imageEntity, commentEntity);

    }
}
