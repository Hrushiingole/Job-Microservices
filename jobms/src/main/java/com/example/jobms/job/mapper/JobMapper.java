package com.example.jobms.job.mapper;

import com.example.jobms.job.Dto.JobWithCompanyDto;
import com.example.jobms.job.Job;
import com.example.jobms.job.external.Company;

public class JobMapper {
    public static JobWithCompanyDto mapToJobWithCompanyDto(Job job,
                                                           Company company){
        JobWithCompanyDto jobWithCompanyDto=new JobWithCompanyDto();
        jobWithCompanyDto.setId(job.getId());
        jobWithCompanyDto.setTitle(job.getTitle());
        jobWithCompanyDto.setDescription(job.getDescription());
        jobWithCompanyDto.setMinSalary(job.getMinSalary());
        jobWithCompanyDto.setMaxSalary(job.getMaxSalary());
        jobWithCompanyDto.setLocation(job.getLocation());
        jobWithCompanyDto.setCompany(company);
        return jobWithCompanyDto;

    }
}
