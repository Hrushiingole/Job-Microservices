package com.example.jobms.job;

import com.example.jobms.job.Dto.JobDto;

import java.util.List;

public interface JobService {
    List<JobDto> findAll();
    void createJobs(Job job);

    JobDto getJobById(Long id);
    boolean deleteJobById(Long id);
    void updateJob(Job prevJob,Job newJob);
}
