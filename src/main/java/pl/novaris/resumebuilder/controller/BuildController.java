package pl.novaris.resumebuilder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.novaris.resumebuilder.dao.entity.*;
import pl.novaris.resumebuilder.service.ResumeService;
import pl.novaris.resumebuilder.util.PdfGeneratorUtil;


@Controller
public class BuildController {

    @Autowired
    PdfGeneratorUtil pdfGeneratorUtil;

    @Autowired
    @Qualifier("resumeService")
    private ResumeService resumeService;

    @Autowired
    @Qualifier("resume")
    private Resume resume;

    @RequestMapping(value = {"/","/build"}, method = RequestMethod.GET)
    public String showIndex(Model model){
        model.addAttribute("resume", new Resume());
        return "index";
    }

    @RequestMapping(value = "/build", method = RequestMethod.POST)
    public String generatePdf(@ModelAttribute Resume newResume) {
        try {
            resumeService.addData("target",newResume.getTarget());
            resumeService.addData("education",newResume.getEducation());
            resumeService.addData("languages",newResume.getLanguages());
            resumeService.addData("certificates",newResume.getCertificates());
            resumeService.addData("hobbies",newResume.getHobbies());
            pdfGeneratorUtil.createPdf("cv", resumeService.getResumeData());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "result";
    }
}
