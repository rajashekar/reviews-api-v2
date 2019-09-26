package com.udacity.course3.reviews.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reviews")
public class Reviews {
    @Id
    private Integer id;
    private String username;
    private int rating;
    private String title;
    private String body;
    private Product product;
    private List<Comment> comments;

    public Reviews() {
    }

    public Reviews(Review review) {
        this.id = review.getId();
        this.username = review.getUsername();
        this.rating = review.getRating();
        this.title = review.getTitle();
        this.body = review.getBody();
        this.product = review.getProduct();
        this.comments = new ArrayList<>();
    }

    public void addComments(Comment comment) {
        comments.add(comment);
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getRating() {
        return this.rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return this.body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Comment> getComments() {
        return this.comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "{" + " id='" + getId() + "'" + ", username='" + getUsername() + "'" + ", rating='" + getRating() + "'"
                + ", title='" + getTitle() + "'" + ", body='" + getBody() + "'" + ", product='" + getProduct() + "'"
                + ", comments='" + getComments() + "'" + "}";
    }

}