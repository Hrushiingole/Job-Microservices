package com.example.companyms.company.messaging;

import com.example.companyms.company.Companyservice;
import com.example.companyms.company.dto.ReviewMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class ReviewMessageConsumer {

    private final Companyservice companyservice;

    public ReviewMessageConsumer(Companyservice companyservice) {
        this.companyservice = companyservice;
    }


    @RabbitListener(queues ="companyRatingQueue" )
    public void consumeMessage(ReviewMessage reviewMessage) {
        companyservice.updateCompanyRating(reviewMessage);
    }
}
