package com.example.jobms.job;

import com.example.jobms.job.Dto.JobWithCompanyDto;

import java.util.List;

public interface JobService {
    List<JobWithCompanyDto> findAll();
    void createJobs(Job job);

    JobWithCompanyDto getJobById(Long id);
    boolean deleteJobById(Long id);
    void updateJob(Job prevJob,Job newJob);
}
