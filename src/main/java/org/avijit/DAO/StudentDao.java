package org.avijit.DAO;


import org.avijit.Entity.Student;
import org.avijit.Service.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
@Transactional
public class StudentDao {
    @Autowired
    StudentRepo studentRepo;

    public void saveStudent(Student student)
    {
        studentRepo.save(student);
    }

    public List<Student> getStudents()
    {
        return studentRepo.findAll();
    }

    public void deleteStudent(int id)
    {
        studentRepo.deleteById(id);
    }
    public Student  getStudent(int id)
    {
        return studentRepo.getOne(id);
    }

    public boolean rollExist(String roll)
    {
        return studentRepo.existsByRoll(roll);
    }

    public boolean existsByRollAndPass(String roll, String pass)
    {
        return studentRepo.existsByRollAndPass(roll,pass);
    }
}
