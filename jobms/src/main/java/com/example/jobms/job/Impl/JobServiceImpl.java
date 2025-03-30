package com.example.jobms.job.Impl;

import com.example.jobms.job.Dto.JobDto;
import com.example.jobms.job.Job;
import com.example.jobms.job.JobRepository;
import com.example.jobms.job.JobService;
import com.example.jobms.job.external.Company;
import com.example.jobms.job.external.Review;
import com.example.jobms.job.mapper.JobMapper;
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


    @Override
    public List<JobDto> findAll() {
        List<Job> jobs = jobsRepository.findAll();
        List<JobDto> jobDTOS =new ArrayList<>();

        for(Job job:jobs){
            jobDTOS.add(convertToDto(job));
        }



        //using rest template for inter service communication
//        RestTemplate restTemplate=new RestTemplate();
//        Company company=restTemplate.getForObject("http://localhost:8081/companies/1", Company.class);
//        System.out.println("COMPANY NAME: "+company.getTitle());
//        System.out.println("COMPANY ID: "+company.getId());
        return jobDTOS;
    }
    private JobDto convertToDto(Job job){
//            RestTemplate restTemplate=new RestTemplate();

            Company company=restTemplate.getForObject("http://COMPANY-SERVICE:8081/companies/"+job.getCompanyId(), Company.class);


            ResponseEntity<List<Review>> reviews = restTemplate.exchange("http://REVIEW-SERVICE:8083/reviews?companyId=" + job.getCompanyId(),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Review>>() {
                    });

            List<Review> reviewList = reviews.getBody();
            JobDto jobDto = JobMapper.mapToJobWithCompanyDto(job,company,reviewList);

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
