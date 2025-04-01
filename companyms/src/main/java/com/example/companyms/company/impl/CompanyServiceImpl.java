package com.example.companyms.company.impl;


import com.example.companyms.company.Company;
import com.example.companyms.company.Companyservice;
import com.example.companyms.company.Repository.CompanyRepository;
import com.example.companyms.company.clients.ReviewClient;
import com.example.companyms.company.dto.ReviewMessage;
import jakarta.ws.rs.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
@Service
public class CompanyServiceImpl implements Companyservice {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ReviewClient reviewClient;

    @Override
    public Company createCompany(Company company) {
        return companyRepository.save(company);
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public Boolean updateCompany(Company company,Long id) {
        Company prevCompany=companyRepository.findById(id).orElse(null);
        if(prevCompany==null) return false;

        prevCompany.setTitle(company.getTitle());
        prevCompany.setDescription(company.getDescription());

        companyRepository.save(prevCompany);

        return true;
    }

    @Override
    public Boolean deleteCompany(Long id) {
        Company company=companyRepository.findById(id).orElse(null);
        if (company==null) return false;
        companyRepository.delete(company);
        return true;
    }

    @Override
    public Company getCompanyById(Long id) {
        return companyRepository.findById(id).orElse(null);
    }

    @Override
    public void updateCompanyRating(ReviewMessage reviewMessage) {
        System.out.println(reviewMessage.getDescription());
        Company company=companyRepository.findById(reviewMessage.getCompanyId()).orElse(null);
        if (company==null) {
            throw new NotFoundException("Company not found"+reviewMessage.getCompanyId());
        }
        double averageRating=reviewClient.getAverageRating(reviewMessage.getCompanyId());
        // Round to 2 decimal places
        BigDecimal roundedRating = BigDecimal.valueOf(averageRating).setScale(2, RoundingMode.HALF_UP);

        company.setRating(roundedRating.doubleValue());
        companyRepository.save(company);
    }
}
