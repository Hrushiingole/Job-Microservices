package com.example.jobms.job;

import com.example.jobms.job.Dto.JobWithCompanyDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobService jobService;




    @GetMapping
    public ResponseEntity<List<JobWithCompanyDto>> findAll() {
        List<JobWithCompanyDto> jobList=jobService.findAll();
        return new ResponseEntity<>(jobList, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<String > createJobs(@RequestBody Job job){
        jobService.createJobs(job);
        return new ResponseEntity<>("Job created successfully", HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobWithCompanyDto> getJobById(@PathVariable Long id) {
        JobWithCompanyDto job=jobService.getJobById(id);
        if(job!=null){
            return new ResponseEntity<>(jobService.getJobById(id), HttpStatus.OK);
        }
       return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable Long id){
        boolean delete=jobService.deleteJobById(id);
        if (delete){
            return new ResponseEntity<>("job deleted successfully",HttpStatus.OK);
        }

        return new ResponseEntity<>("job not found",HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String > updateJob(@PathVariable Long id,@RequestBody Job job) {
        JobWithCompanyDto prevJob=jobService.getJobById(id);
        if(prevJob==null){
            return new ResponseEntity<>("job not found",HttpStatus.NOT_FOUND);
        }
        Job pJob=new Job();
        pJob.setId(prevJob.getId());
        pJob.setDescription(prevJob.getDescription());
        pJob.setLocation(prevJob.getLocation());
        pJob.setMaxSalary(prevJob.getMaxSalary());
        pJob.setMinSalary(prevJob.getMinSalary());
        pJob.setTitle(prevJob.getTitle());
        pJob.setCompanyId(prevJob.getCompany().getId());
       jobService.updateJob(pJob,job);
        return new ResponseEntity<>("Job updated successfully",HttpStatus.OK);
    }




}
//    GET /jobs : get all jobs
//    GET /jobs/{id} : get job by id
//    POST /jobs : create new job
//    DELETE /jobs/{id} : delete job by id
//    PUT /jobs/{id} : update job by id

//    exmaple API URLS:
//   GET  http://localhost:8080/jobs
//    GET http://localhost:8080/jobs/1
//    POST http://localhost:8080/jobs
//    DELETE http://localhost:8080/jobs/1
//    PUT http://localhost:8080/jobs/1