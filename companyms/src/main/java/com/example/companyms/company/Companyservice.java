package com.example.companyms.company;

import java.util.List;

public interface Companyservice {
    Company createCompany(Company company);
    List<Company> getAllCompanies();

    Boolean updateCompany(Company company,Long id);
    Boolean deleteCompany(Long id);

    Company getCompanyById(Long id);
}
