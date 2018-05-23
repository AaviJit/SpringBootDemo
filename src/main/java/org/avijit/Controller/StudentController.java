package org.avijit.Controller;


import org.avijit.Entity.Student;
import org.avijit.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Controller
public class StudentController {

    @Autowired
    StudentService studentService;

    @RequestMapping(value = "/logForm", method = RequestMethod.GET)
    public String gotoHome() {
        return "Login";
    }

    @RequestMapping(value = "/loginCheck", method = RequestMethod.POST)
    public String checkLogin(@RequestParam String roll, @RequestParam String pass, Model model) {
        if (studentService.existsByRollAndPass(roll, pass)) {
            return "Welcome";
        } else {
            model.addAttribute("logError", "logError");
            return "Login";
        }
    }


    @RequestMapping(value = "/registration")
    public String registration(Model model) {
        model.addAttribute(new Student());
        return "Registration";
    }

    @RequestMapping(value = "/getStudents")
    public String getStudents(Model model) {
        List<Student> studentList = studentService.getStudents();
        model.addAttribute(studentList);
        return "StudentList";
    }

    @RequestMapping(value = "/deleteStudent", method = RequestMethod.GET)
    public String deleteStudent(@RequestParam(name = "id") int id) {
        studentService.deleteStudent(id);
        return "redirect:/getStudents";
    }

    @RequestMapping(value = "/editStudent/{id}", method = RequestMethod.GET)
    public String editStudent(@PathVariable("id") int id, Model model) {
        Student student = studentService.getStudent(id);
        model.addAttribute("student", student);
        return "StudentList";
    }


    @RequestMapping(value = "/demo")
    public String demoRegistration(Model model) {
        model.addAttribute(new Student());
        return "DemoRegistration";
    }

    @RequestMapping(value = "/doRegistration", method = RequestMethod.POST)
    public String doRegistration(@Valid @ModelAttribute("student") Student student, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("hasError", true);
            return "DemoRegistration";
        } else {
            if (student.getId() == null && studentService.rollExist(student.getRoll())) {
                model.addAttribute("existRoll", "existRoll");
                model.addAttribute("hasError", true);
                return "DemoRegistration";
            } else {
                //(student.getId() == null && !studentService.rollExist(student.getRoll())) {

                studentService.saveStudent(student);
                return "Welcome";
            }

        }
    }


    @RequestMapping(value = "/doUpdate", method = RequestMethod.POST)
    String updateStudent(@Valid  @ModelAttribute("student")Student student, Model model) {
        Student student1 = studentService.getStudent(student.getId());
        if (student1.getId() != null && !student1.getRoll().equals(student.getRoll()) && studentService.rollExist(student.getRoll())) {
            model.addAttribute("hasError", "hasError");
            model.addAttribute("existRoll", "existRoll");
            return "StudentList";
        } else {
            student1.setFirstName(student.getFirstName());
            student1.setLastName(student.getLastName());
            student1.setRoll(student.getRoll());
            student1.setAge(student.getAge());
            student1.setPass(student.getPass());
            studentService.saveStudent(student1);
            return "redirect:/getStudents";
        }
    }

}
