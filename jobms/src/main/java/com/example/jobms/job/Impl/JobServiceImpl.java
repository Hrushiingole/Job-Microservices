package com.example.jobms.job.Impl;

import com.example.jobms.job.Dto.JobWithCompanyDto;
import com.example.jobms.job.Job;
import com.example.jobms.job.JobRepository;
import com.example.jobms.job.JobService;
import com.example.jobms.job.external.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class JobServiceImpl  implements JobService {


//    private List<Job> jobs=new ArrayList<>();
@Autowired
private JobRepository jobsRepository;


    @Override
    public List<JobWithCompanyDto> findAll() {
        List<Job> jobs = jobsRepository.findAll();
        List<JobWithCompanyDto> jobWithCompanyDTOs=new ArrayList<>();

        for(Job job:jobs){
            jobWithCompanyDTOs.add(convertToDto(job));
        }



        //using rest template for inter service communication
//        RestTemplate restTemplate=new RestTemplate();
//        Company company=restTemplate.getForObject("http://localhost:8081/companies/1", Company.class);
//        System.out.println("COMPANY NAME: "+company.getTitle());
//        System.out.println("COMPANY ID: "+company.getId());
        return jobWithCompanyDTOs;
    }
    private JobWithCompanyDto convertToDto(Job job){
            RestTemplate restTemplate=new RestTemplate();
            Company company=restTemplate.getForObject("http://localhost:8081/companies/"+job.getCompanyId(), Company.class);
            JobWithCompanyDto jobWithCompanyDto=new JobWithCompanyDto();
            jobWithCompanyDto.setJob(job);
            jobWithCompanyDto.setCompany(company);
            return jobWithCompanyDto;

    }

    @Override
    public void createJobs(Job job) {
        jobsRepository.save(job);
    }

    @Override
    public Job getJobById(Long id) {
        Job job =jobsRepository.findById(id).orElse(null);
        return job;
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
