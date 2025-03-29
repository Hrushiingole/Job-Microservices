package com.example.jobms.job.Dto;

import com.example.jobms.job.Job;
import com.example.jobms.job.external.Company;

public class JobWithCompanyDto {
    private Job job;
    private Company company;

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
