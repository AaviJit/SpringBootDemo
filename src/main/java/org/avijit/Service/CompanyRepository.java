package org.avijit.Service;

import org.avijit.Entity.CompanyDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository  extends JpaRepository<CompanyDetails, Integer>{
    public boolean existsByCode(String code);
}
