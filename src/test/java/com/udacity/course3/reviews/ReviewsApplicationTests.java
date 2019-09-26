package com.udacity.course3.reviews;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.Optional;

import com.udacity.course3.reviews.entity.Comment;
import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.entity.Review;
import com.udacity.course3.reviews.repository.CommentRepository;
import com.udacity.course3.reviews.repository.ProductRepository;
import com.udacity.course3.reviews.repository.ReviewRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ReviewsApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private CommentRepository commentRepository;

	@Test
	public void product() throws Exception {
		Product product = getProduct();
		productRepository.save(product);

		Optional<Product> optional = productRepository.findById(product.getId());
		assertThat(optional.isPresent(), equalTo(Boolean.TRUE));
		assertThat(optional.get().getName(), equalTo(product.getName()));
	}

	@Test
	public void review() throws Exception {
		Product product = getProduct();
		productRepository.save(product);

		Review review = getReview();
		review.setProduct(product);
		reviewRepository.save(review);

		Optional<Review> optional = reviewRepository.findById(review.getId());
		assertThat(optional.isPresent(), equalTo(Boolean.TRUE));
		assertThat(optional.get().getTitle(), equalTo(review.getTitle()));

		List<Review> reviews = reviewRepository.findReviewsByProduct(product);
		assertThat(reviews.get(0).getProduct().getName(), equalTo(review.getProduct().getName()));
	}

	@Test
	public void comment() throws Exception {
		Product product = getProduct();
		productRepository.save(product);

		Review review = getReview();
		review.setProduct(product);
		reviewRepository.save(review);

		Comment comment = getComment();
		comment.setReview(review);
		commentRepository.save(comment);

		Optional<Comment> optional = commentRepository.findById(comment.getId());
		assertThat(optional.isPresent(), equalTo(Boolean.TRUE));
		assertThat(optional.get().getBody(), equalTo(comment.getBody()));

		List<Comment> comments = commentRepository.findCommentsByReview(review);
		assertThat(comments.get(0).getReview().getTitle(), equalTo(comment.getReview().getTitle()));
		assertThat(comments.get(0).getReview().getProduct().getName(),
				equalTo(comment.getReview().getProduct().getName()));

	}

	private Product getProduct() {
		Product product = new Product();
		product.setName("iPhone");
		product.setDescription("Phone with i");
		product.setPrice(1000);
		return product;
	}

	private Review getReview() {
		Review review = new Review();
		review.setTitle("Its just an iphone");
		review.setRating(5);
		review.setBody("Why i?");
		review.setUserName("Tyler Durden");
		return review;
	}

	private Comment getComment() {
		Comment comment = new Comment();
		comment.setUserName("Neo");
		comment.setBody("Follow white Rabbit");
		comment.setReview(getReview());
		return comment;
	}

}