package com.example.jobms.job.mapper;

import com.example.jobms.job.Dto.JobDto;
import com.example.jobms.job.Job;
import com.example.jobms.job.external.Company;
import com.example.jobms.job.external.Review;

import java.util.List;

public class JobMapper {
    public static JobDto mapToJobWithCompanyDto(Job job,
                                                Company company, List<Review> reviews){
        JobDto jobDto =new JobDto();
        jobDto.setId(job.getId());
        jobDto.setTitle(job.getTitle());
        jobDto.setDescription(job.getDescription());
        jobDto.setMinSalary(job.getMinSalary());
        jobDto.setMaxSalary(job.getMaxSalary());
        jobDto.setLocation(job.getLocation());
        jobDto.setCompany(company);
        jobDto.setReview(reviews);
        return jobDto;

    }
}
