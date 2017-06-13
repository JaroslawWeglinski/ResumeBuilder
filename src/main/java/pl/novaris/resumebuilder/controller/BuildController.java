package pl.novaris.resumebuilder.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.novaris.resumebuilder.entity.Resume;
import pl.novaris.resumebuilder.entity.repository.*;
import pl.novaris.resumebuilder.util.PdfGeneratorUtil;

import java.util.Optional;

@Controller
public class BuildController {

    @Autowired
    PdfGeneratorUtil pdfGeneratorUtil;

    @Autowired
    private ResumeRepository resumeRepository;

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
            resumeRepository.add("target",newResume.getTarget());
            resumeRepository.add("education",newResume.getEducation());
            resumeRepository.add("languages",newResume.getLanguages());
            resumeRepository.add("certificates",newResume.getCertificates());
            resumeRepository.add("hobbies",newResume.getHobbies());
            pdfGeneratorUtil.createPdf("cv", resumeRepository.getResumeData());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "result";
    }
}
