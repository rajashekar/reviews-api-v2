package com.udacity.course3.reviews;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import com.udacity.course3.reviews.entity.Comment;
import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.entity.Review;
import com.udacity.course3.reviews.entity.Reviews;
import com.udacity.course3.reviews.repository.ReviewApiRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataMongoTest
public class ReviewsMongoTests {

    @Autowired
    private ReviewApiRepository reviewApiRepository;

    @Test
    public void review() {
        Review review = getReview();
        review.setProduct(getProduct());
        Reviews reviews = new Reviews(review);
        reviews.addComments(getComment());

        Reviews results = reviewApiRepository.save(reviews);
        Optional<Reviews> reviewsOpt = reviewApiRepository.findById(results.getId());
        assertThat(reviewsOpt.isPresent(), equalTo(Boolean.TRUE));
        assertThat(reviewsOpt.get().getTitle(), equalTo(reviews.getTitle()));
        assertThat(reviewsOpt.get().getComments().get(0).getUsername(),
                equalTo(reviews.getComments().get(0).getUsername()));
    }

    private Product getProduct() {
        Product product = new Product();
        product.setId(1);
        product.setName("iPhone");
        product.setDescription("Phone with i");
        product.setPrice(1000);
        return product;
    }

    private Review getReview() {
        Review review = new Review();
        review.setId(1);
        review.setTitle("Its just an iphone");
        review.setRating(5);
        review.setBody("Why i?");
        review.setUserName("Tyler Durden");
        return review;
    }

    private Comment getComment() {
        Comment comment = new Comment();
        comment.setId(1);
        comment.setUserName("Neo");
        comment.setBody("Follow white Rabbit");
        comment.setReview(getReview());
        return comment;
    }
}