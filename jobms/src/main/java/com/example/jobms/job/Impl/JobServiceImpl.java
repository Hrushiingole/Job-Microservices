package com.example.jobms.job.Impl;

import com.example.jobms.job.Dto.JobDto;
import com.example.jobms.job.Job;
import com.example.jobms.job.JobRepository;
import com.example.jobms.job.JobService;
import com.example.jobms.job.clients.CompanyClient;
import com.example.jobms.job.clients.ReviewClient;
import com.example.jobms.job.external.Company;
import com.example.jobms.job.external.Review;
import com.example.jobms.job.mapper.JobMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class JobServiceImpl  implements JobService {


//    private List<Job> jobs=new ArrayList<>();
@Autowired
private JobRepository jobsRepository;

@Autowired
RestTemplate restTemplate;
@Autowired
private CompanyClient companyClient;

@Autowired
private ReviewClient reviewClient;

int attempt=0;


    @Override
//    @CircuitBreaker(name = "companyBreaker", fallbackMethod = "companyBreakerFallback")
    @Retry(name = "companyBreaker", fallbackMethod = "companyBreakerFallback")
    public List<JobDto> findAll() {
        System.out.println("attempt"+attempt++);
        List<Job> jobs = jobsRepository.findAll();
        List<JobDto> jobDTOS = new ArrayList<>();

        for (Job job : jobs) {
            jobDTOS.add(convertToDto(job));
        }
        return jobDTOS;
    }

    public List<JobDto> companyBreakerFallback(Exception e) {
        List<JobDto> fallbackJobs = new ArrayList<>();
        JobDto fallbackJob = new JobDto();
        fallbackJob.setTitle("Data not found");
        fallbackJobs.add(fallbackJob);
        return fallbackJobs;
    }


    private JobDto convertToDto(Job job){
//            RestTemplate restTemplate=new RestTemplate();
//code for inter service communication using rest template
//            Company company=restTemplate.getForObject("http://COMPANY-SERVICE:8081/companies/"+job.getCompanyId(), Company.class);

//            ResponseEntity<List<Review>> reviews = restTemplate.exchange("http://REVIEW-SERVICE:8083/reviews?companyId=" + job.getCompanyId(),
//                    HttpMethod.GET,
//                    null,
//                    new ParameterizedTypeReference<List<Review>>() {
//                    });

        //code for inter service communication using feign client
        Company company=companyClient.getCompany(job.getCompanyId());
        List<Review> reviews=reviewClient.getReviews(job.getCompanyId());

            JobDto jobDto = JobMapper.mapToJobWithCompanyDto(job,company,reviews);
            return jobDto;

    }

    @Override
    public void createJobs(Job job) {
        jobsRepository.save(job);
    }

    @Override
    public JobDto getJobById(Long id) {
        Job job =jobsRepository.findById(id).orElse(null);
        return convertToDto(job);

    }

    @Override
    public boolean deleteJobById(Long id) {
        Job job=jobsRepository.findById(id).orElse(null);

        if (job==null){
            return false;
        }
        jobsRepository.delete(job);
        return true;
    }

    @Override
    public void updateJob(Job prevJob, Job newJob) {
        prevJob.setDescription(newJob.getDescription());
        prevJob.setLocation(newJob.getLocation());
        prevJob.setMaxSalary(newJob.getMaxSalary());
        prevJob.setMinSalary(newJob.getMinSalary());
        prevJob.setTitle(newJob.getTitle());
        prevJob.setCompanyId(newJob.getCompanyId());
        jobsRepository.save(prevJob);
    }


}
