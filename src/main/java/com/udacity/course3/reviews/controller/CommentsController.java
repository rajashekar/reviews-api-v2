package com.udacity.course3.reviews.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.udacity.course3.reviews.entity.Comment;
import com.udacity.course3.reviews.entity.Review;
import com.udacity.course3.reviews.entity.Reviews;
import com.udacity.course3.reviews.repository.CommentRepository;
import com.udacity.course3.reviews.repository.ReviewApiRepository;
import com.udacity.course3.reviews.repository.ReviewRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Spring REST controller for working with comment entity.
 */
@RestController
@RequestMapping("/comments")
public class CommentsController {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ReviewApiRepository reviewApiRepository;

    /**
     * Creates a comment for a review.
     *
     * @param reviewId The id of the review.
     */
    @RequestMapping(value = "/reviews/{reviewId}", method = RequestMethod.POST)
    public ResponseEntity<?> createCommentForReview(@PathVariable("reviewId") Integer reviewId,
            @RequestBody Comment comment) {
        // find review in mysql
        Optional<Review> review = reviewRepository.findById(reviewId);
        if (!review.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        comment.setReview(review.get());
        // save in mysql
        Comment mysqlComment = commentRepository.save(comment);

        // find review in mongo
        Optional<Reviews> mongoReviewOpt = reviewApiRepository.findById(reviewId);
        if (!mongoReviewOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Reviews mongoReview = mongoReviewOpt.get();
        mongoReview.addComments(mysqlComment);
        // save in mongo
        return ResponseEntity.ok(reviewApiRepository.save(mongoReview));
    }

    /**
     * List comments for a review.
     *
     * 2. Check for existence of review. 3. If review not found, return NOT_FOUND.
     * 4. If found, return list of comments.
     *
     * @param reviewId The id of the review.
     */
    @RequestMapping(value = "/reviews/{reviewId}", method = RequestMethod.GET)
    public List<?> listCommentsForReview(@PathVariable("reviewId") Integer reviewId) {
        List<Comment> comments = commentRepository.findCommentsByReview(new Review(reviewId));
        Optional<Reviews> mongoReviewOpt = reviewApiRepository.findById(reviewId);
        if (!mongoReviewOpt.isPresent()) {
            return new ArrayList<>();
        }
        Reviews mongoReview = mongoReviewOpt.get();

        return mongoReview.getComments();
    }
}