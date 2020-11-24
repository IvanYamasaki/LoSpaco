package com.ivangy.lospaco.model;

public class Comments {

    public String commentText, nameClient;
    public float starRating;

    public Comments(String commentText, String nameClient, float starRating) {
        this.commentText = commentText;
        this.nameClient = nameClient;
        this.starRating = starRating;
    }

    public String getCommentText() {
        return commentText;
    }

    public String getNameClient() {
        return nameClient;
    }

    public float getStarRating() {
        return starRating;
    }
}
