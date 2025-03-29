package com.example.reviewms.Reviews.impl;


import com.example.reviewms.Reviews.Review;
import com.example.reviewms.Reviews.ReviewRepository;
import com.example.reviewms.Reviews.ReviewService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class reviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;


    @Override
    public List<Review> getAllReviews(Long companyId) {
        List<Review> reviews=reviewRepository.findByCompanyId(companyId);
        return reviews;
    }

    @Override
    public Review createReview(Long companyId, Review review) {

        if (companyId!=null && review!=null){
            review.setCompanyId(companyId);
            return reviewRepository.save(review);
        }
        return null;

    }

    @Override
    public Review getReviewById(Long reviewId){
        Review review=reviewRepository.findById(reviewId).orElse(null);
        if(review==null) return null;
        return review;
    }

    @Override
    public boolean updateReview(Long reviewId, Review updatedReview) {
        Review review=reviewRepository.findById(reviewId).orElse(null);
        if (review!=null){
            review.setTitle(updatedReview.getTitle());
            review.setDescription(updatedReview.getDescription());
            review.setRating(updatedReview.getRating());
           review.setCompanyId(updatedReview.getCompanyId());
            reviewRepository.save(review);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteReview(Long reviewId) {
        Review review=reviewRepository.findById(reviewId).orElse(null);
        if(review==null) return false;
        reviewRepository.delete(review);
        return true;
    }
}
