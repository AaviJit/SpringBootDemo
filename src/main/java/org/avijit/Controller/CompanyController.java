package org.avijit.Controller;

import org.avijit.Entity.CompanyDetails;
import org.avijit.Service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Controller
@RequestMapping("/company")
public class CompanyController {

    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    @Autowired
    CompanyService companyService;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("companyDetails", new CompanyDetails());
        model.addAttribute("url", "/register");
        return "/company/form";
    }


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String doRegistration(@Valid @ModelAttribute("companyDetails") CompanyDetails companyDetails, BindingResult result, Model model) {

        if (companyDetails.getId() == null && companyService.existsByCode(companyDetails.getCode())) {
            model.addAttribute("exist", "exist");
            return "/company/form";
        } else {
            try {
                companyDetails.setExpireDate(LocalDate.parse(companyDetails.getExpireDate_str(), formatter));
                companyService.saveCompany(companyDetails);
            } catch (DateTimeParseException n) {
                companyDetails.setExpireDate(null);
            }
            return "redirect:/company/companyList";
        }
    }

    @RequestMapping(value = "/companyList")
    public String companyList(Model model) {
        List<CompanyDetails> companyList = companyService.companyList();
        model.addAttribute("companyList", companyList);
        return "/company/companyList";
    }

    @RequestMapping(value = "/deleteCompany", method = RequestMethod.GET)
    public String deleteCompany(@RequestParam(name = "id") int id) {
        companyService.deleteCompany(id);
        return "redirect:/company/companyList";
    }


    @RequestMapping(value = "/editCompany", method = RequestMethod.GET)
    public String editCompany(@RequestParam(name = "id") int id, Model model) {
        CompanyDetails company = companyService.getCompany(id);
        model.addAttribute("url", "/editCompany");
        model.addAttribute("companyDetails", company);
        return "/company/form";
    }

    @RequestMapping(value = "/editCompany", method = RequestMethod.POST)
    public String editCompany(@Valid @ModelAttribute("companyDetails") CompanyDetails companyDetails, Model model) {

        CompanyDetails temp = companyService.getCompany(companyDetails.getId());

        if (!temp.getCode().equals(companyDetails.getCode()) && companyService.existsByCode(companyDetails.getCode())) {
            model.addAttribute("exist", "exist");
            model.addAttribute("url", "/editCompany");
            return "/company/form";
        } else {
            try {
                temp.setExpireDate(LocalDate.parse(companyDetails.getExpireDate_str(), formatter));
                temp.setCompanyName(companyDetails.getCompanyName());
                temp.setBranchName(companyDetails.getBranchName());
                temp.setCode(companyDetails.getCode());
                temp.setValid(companyDetails.isValid());
                companyService.saveCompany(temp);
            } catch (DateTimeParseException n) {
                temp.setExpireDate(null);
            }
            return "redirect:/company/companyList";
        }
    }
}
