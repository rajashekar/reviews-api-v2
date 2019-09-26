package com.udacity.course3.reviews.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.entity.Review;
import com.udacity.course3.reviews.entity.Reviews;
import com.udacity.course3.reviews.repository.ProductRepository;
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
 * Spring REST controller for working with review entity.
 */
@RestController
public class ReviewsController {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ReviewApiRepository reviewApiRepository;

    /**
     * Creates a review for a product.
     *
     * @param productId The id of the product.
     * @return The created review or 404 if product id is not found.
     */
    @RequestMapping(value = "/reviews/products/{productId}", method = RequestMethod.POST)
    public ResponseEntity<?> createReviewForProduct(@PathVariable("productId") Integer productId,
            @RequestBody Review review) {
        Optional<Product> product = productRepository.findById(productId);
        if (!product.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        review.setProduct(product.get());

        // save to mysql
        Review mysqlReview = reviewRepository.save(review);
        // save to mongo
        Reviews mongoReview = reviewApiRepository.save(new Reviews(mysqlReview));
        return ResponseEntity.ok(mongoReview);
    }

    /**
     * Lists reviews by product.
     *
     * @param productId The id of the product.
     * @return The list of reviews.
     */
    @RequestMapping(value = "/reviews/products/{productId}", method = RequestMethod.GET)
    public ResponseEntity<List<?>> listReviewsForProduct(@PathVariable("productId") Integer productId) {
        // find in mysql
        List<Review> reviews = reviewRepository.findReviewsByProduct(new Product(productId));
        // find in mongo
        Iterable<Reviews> iterReviews = reviewApiRepository
                .findAllById(reviews.stream().map(r -> r.getId()).collect(Collectors.toList()));
        // convert to list
        List<Reviews> mongoReviews = StreamSupport.stream(iterReviews.spliterator(), false)
                .collect(Collectors.toList());
        return ResponseEntity.ok(mongoReviews);
    }
}