package com.example.companyms.company.impl;


import com.example.companyms.company.Company;
import com.example.companyms.company.Companyservice;
import com.example.companyms.company.Repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CompanyServiceImpl implements Companyservice {

    @Autowired
    private CompanyRepository companyRepository;

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
}
