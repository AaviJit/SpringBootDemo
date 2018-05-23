package org.avijit.Service;

import org.avijit.DAO.StudentDao;
import org.avijit.Entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class StudentService {

    @Autowired
    StudentDao studentDao;

    public void saveStudent(Student student)
    {
        studentDao.saveStudent(student);
    }

    public List<Student> getStudents()
    {
        return studentDao.getStudents();
    }
    public void deleteStudent(int id)
    {
        studentDao.deleteStudent(id);
    }
    public Student  getStudent(int id)
    {
        return studentDao.getStudent(id);
    }
    public boolean rollExist(String roll)
    {
        return studentDao.rollExist(roll);
    }
    public boolean existsByRollAndPass(String roll, String pass)
    {
        return studentDao.existsByRollAndPass(roll,pass);
    }

}
