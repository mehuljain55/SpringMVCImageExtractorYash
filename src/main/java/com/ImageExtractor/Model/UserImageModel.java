package com.ImageExtractor.Model;

import org.springframework.web.multipart.MultipartFile;

public class UserImageModel {

    private MultipartFile image;

    public UserImageModel(MultipartFile image) {
        this.image = image;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}