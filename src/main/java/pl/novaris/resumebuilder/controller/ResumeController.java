package pl.novaris.resumebuilder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.novaris.resumebuilder.dao.entity.Resume;
import pl.novaris.resumebuilder.service.ResumeService;

import java.util.List;

@Controller
@RequestMapping("/resume")
public class ResumeController {

    @Autowired
    @Qualifier("resumeService")
    private ResumeService resumeService;

    @GetMapping("/list")
    public String listResumes(Model theModel){

        List<Resume> theResumes = resumeService.getResumes();

        theModel.addAttribute("resumes", theResumes);

        return "list-resumes";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model theModel) {

        Resume theResume = new Resume();

        theModel.addAttribute("resume", theResume);

        return "docx4j";

    }

    @PostMapping("/saveResume")
    public String saveResume(@ModelAttribute("resume") Resume theResume){

        resumeService.saveResume(theResume);

        return "redirect:/resume/list";

    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("resumeId") int theId, Model theModel) {

        Resume theResume = resumeService.getResume(theId);

        theModel.addAttribute("resume", theResume);

        return "docx4j";

    }

    @GetMapping("/delete")
    public String deleteResume(@RequestParam("resumeId") int theId) {

        resumeService.deleteResume(theId);

        return "redirect:/resume/list";

    }

}
