package com.udacity.course3.reviews.repository;

import java.util.List;

import com.udacity.course3.reviews.entity.Comment;
import com.udacity.course3.reviews.entity.Review;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findCommentsByReview(Review review);
}