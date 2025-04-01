package com.example.reviewms.Reviews;

import com.example.reviewms.Reviews.messaging.ReviewMessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReviewMessageProducer reviewMessageProducer;

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews(@RequestParam Long companyId) {
        List<Review> reviews = reviewService.getAllReviews(companyId);
        return ResponseEntity.ok(reviews);
    }
    @PostMapping
    public ResponseEntity<String> createReview(@RequestParam Long companyId, @RequestBody Review review) {
        Review createdReview = reviewService.createReview(companyId, review);
        if(createdReview==null){

            return new ResponseEntity<>("Company not found", HttpStatus.NOT_FOUND);
        }
        reviewMessageProducer.sendMessage(createdReview);
        return ResponseEntity.ok("Review created successfully");
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long reviewId) {
        Review review = reviewService.getReviewById(reviewId);
        if (review == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(review);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<String> updateReview(@PathVariable Long reviewId, @RequestBody Review review) {

        boolean isUpdated = reviewService.updateReview(reviewId, review);
        if(isUpdated==false){
            return new ResponseEntity<>("Review not found", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok("Review updated successfully");
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId) {
        boolean isDeleted = reviewService.deleteReview(reviewId);
        if(isDeleted==false){
            return new ResponseEntity<>("Review not found", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok("Review deleted successfully");
    }

    @GetMapping("/averageRating")
    public Double getAverageRating(@RequestParam Long companyId) {
        List<Review> reviews=reviewService.getAllReviews(companyId);
        return reviews.stream().mapToDouble(Review::getRating).average().orElse(0.0);
    }



}

//GET /companies/{companyId}/reviews
//POST /companies/{companyId}/reviews
//GET /companies/{companyId}/reviews/{reviewId}
//PUT /companies/{companyId}/reviews/{reviewId}
//DELETE /companies/{companyId}/reviews/{reviewId}