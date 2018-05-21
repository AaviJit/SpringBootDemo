package org.avijit.Service;

import org.avijit.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepo extends JpaRepository<Student,Integer> {

    public boolean existsByRoll(String roll);
    public boolean existsByRollAndPass(String roll, String pass);
}
