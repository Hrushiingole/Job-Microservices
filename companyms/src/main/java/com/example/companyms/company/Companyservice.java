package com.example.companyms.company;

import com.example.companyms.company.dto.ReviewMessage;

import java.util.List;

public interface Companyservice {
    Company createCompany(Company company);
    List<Company> getAllCompanies();

    Boolean updateCompany(Company company,Long id);
    Boolean deleteCompany(Long id);

    Company getCompanyById(Long id);

    void updateCompanyRating(ReviewMessage reviewMessage);
}
