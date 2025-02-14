package com.project.project.controller;

import com.project.project.model.Review;
import com.project.project.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    @PostMapping
    public ResponseEntity<Review> addReview(@RequestBody Review review) {
        Review savedReview = reviewRepository.save(review);
        return ResponseEntity.ok(savedReview);
    }

    @GetMapping("/product/{productId}")
    public List<Review> getReviewsForProduct(@PathVariable Long productId) {
        return reviewRepository.findByProductId(productId);
    }

    @GetMapping("/user/{userId}")
    public List<Review> getReviewsByUser(@PathVariable Long userId) {
        return reviewRepository.findByUserId(userId);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<Review> updateReview(@PathVariable Long reviewId, @RequestBody Review review) {
        review.setId(reviewId);
        Review updatedReview = reviewRepository.save(review);
        return ResponseEntity.ok(updatedReview);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId) {
        reviewRepository.deleteById(reviewId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public List<Review> listReviews() {
        return reviewRepository.findAll();
    }

    @GetMapping("/top")
    public ResponseEntity<List<Review>> fetchTopRatedProducts() {
        List<Review> topRatedReviews = reviewRepository.findAll()
                .stream()
                .filter(review -> review.getRating() >= 4) // Assuming a rating of 4 and above is considered top-rated
                .collect(Collectors.toList());
        return ResponseEntity.ok(topRatedReviews);
    }

//    @PostMapping("/{reviewId}/like")
//    public ResponseEntity<String> likeReview(@PathVariable Long reviewId) {
//        Optional<Review> reviewOpt = reviewRepository.findById(reviewId);
//        if (reviewOpt.isPresent()) {
//            Review review = reviewOpt.get();
//            review.setLikes(review.getLikes() + 1); // Increment likes
//            reviewRepository.save(review);
//            return ResponseEntity.ok("Review liked");
//        }
//        return ResponseEntity.notFound().build();
//    }
//
//    @PostMapping("/{reviewId}/dislike")
//    public ResponseEntity<String> dislikeReview(@PathVariable Long reviewId) {
//        Optional<Review> reviewOpt = reviewRepository.findById(reviewId);
//        if (reviewOpt.isPresent()) {
//            Review review = reviewOpt.get();
//            review.setDislikes(review.getDislikes() + 1); // Increment dislikes
//            reviewRepository.save(review);
//            return ResponseEntity.ok("Review disliked");
//        }
//        return ResponseEntity.notFound().build();
//    }

    @GetMapping("/product/{productId}/average")
    public ResponseEntity<Double> getAverageRating(@PathVariable Long productId) {
        List<Review> reviews = reviewRepository.findByProductId(productId);
        if (reviews.isEmpty()) {
            return ResponseEntity.ok(0.0); // No reviews, average is 0
        }
        double averageRating = reviews.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0);
        return ResponseEntity.ok(averageRating);
    }
}