package pl.novaris.resumebuilder.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.novaris.resumebuilder.dao.entity.Education;
import pl.novaris.resumebuilder.dao.entity.Resume;

@Controller
public class MainController {

    @RequestMapping(value = {"","/","/index"}, method = RequestMethod.GET)
    public String showIndex(){
        return "index";
    }

    @RequestMapping(value = "/fill", params = {"generate"}, method = RequestMethod.POST)
    public String generate() {
        return "docx4j";
    }

    @RequestMapping(value = "/fill", params = {"resumeList"}, method = RequestMethod.POST)
    public String listResumes() {
        return "list-resumes";
    }
}
