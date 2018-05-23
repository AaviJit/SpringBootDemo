package org.avijit.Service;

import org.avijit.Entity.CompanyDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CompanyService {
    @Autowired
    CompanyRepository companyRepository;

    public void saveCompany(CompanyDetails companyDetails)
    {
        companyRepository.save(companyDetails);
    }
    public boolean existsByCode(String code)
    {
        return companyRepository.existsByCode(code);
    }
    public List<CompanyDetails> companyList()
    {
        return companyRepository.findAll();
    }

    public void deleteCompany(int id)
    {
        companyRepository.deleteById(id);
    }

    public CompanyDetails getCompany(int id)
    {
        return companyRepository.getOne(id);
    }


}
