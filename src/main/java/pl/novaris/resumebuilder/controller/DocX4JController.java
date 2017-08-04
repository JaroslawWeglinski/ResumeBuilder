package pl.novaris.resumebuilder.controller;

import org.docx4j.Docx4J;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.novaris.resumebuilder.dao.entity.Education;
import pl.novaris.resumebuilder.dao.entity.Experience;
import pl.novaris.resumebuilder.dao.entity.Resume;
import pl.novaris.resumebuilder.service.DocX4JResumeService;
import pl.novaris.resumebuilder.service.ResumeService;
import pl.novaris.resumebuilder.service.impl.DocX4JResumeServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/docx4j")
public class DocX4JController {

    @Autowired
    @Qualifier("docX4JResumeService")
    private DocX4JResumeService docX4JResumeService;

    @Autowired
    @Qualifier("resume")
    private Resume resume;

    private ResourceBundle resourceBundle = ResourceBundle.getBundle("configuration");

    private String templateName = resourceBundle.getString("template.name");;
    private String docxFileName = resourceBundle.getString("docx.file.name");;
    private String imageSourcePath = resourceBundle.getString("image.source.path");;
    private String pdfDestinationPath = resourceBundle.getString("pdf.destination.path");;



    @RequestMapping(value = "/fill", method = RequestMethod.GET)
    public String fillTemplate(Model model) {

        model.addAttribute("resume", new Resume());

        return "docx4j";
    }

    @RequestMapping(value = "/fill", params = {"addEducation"})
    public String addEducation(Resume resume, BindingResult bindingResult){
        resume.getEducations().add(new Education());
        return "docx4j";
    }

    @RequestMapping(value = "/fill", params = {"removeEducation"})
    public String removeEducation(Resume resume, BindingResult bindingResult, HttpServletRequest req) {
        Integer eduId = Integer.valueOf(req.getParameter("removeEducation"));
        resume.getEducations().remove(eduId.intValue());
        return "docx4j";
    }

    @RequestMapping(value = "/fill", params = {"addExperience"})
    public String addExperience(Resume resume, BindingResult bindingResult){
        resume.getExperiences().add(new Experience());
        return "docx4j";
    }

    @RequestMapping(value = "/fill", params = {"removeExperience"})
    public String removeExperience(Resume resume, BindingResult bindingResult, HttpServletRequest req) {
        Integer expId = Integer.valueOf(req.getParameter("removeExperience"));
        resume.getExperiences().remove(expId.intValue());
        return "docx4j";
    }

    @RequestMapping(value = "/fill", params = "save", method = RequestMethod.POST)
    public String createDocX(@ModelAttribute Resume newResume) throws Exception {

        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(new FileInputStream(new File(templateName)));

        HashMap<String, String> mappings = new HashMap<>();

        mappings.put("NAME", newResume.getName());
        mappings.put("SURNAME", newResume.getSurname());

        mappings.put("BIRTHDAY","Data urodzenia");
        mappings.put("LOCATION","Miejsce zamieszkania");
        mappings.put("EMAIL","Adres e-mail");
        mappings.put("PHONE","Numer telefonu");
        //mappings.put("PICTURE","");
        mappings.put("TARGET","Cel zawodowy");
        mappings.put("EDUCATION","Wykształcenie");
        mappings.put("EXPERIENCE","Doświadczenie");
        mappings.put("SKILLS","Umiejętności");
        mappings.put("LANGUAGES","Języki");
        mappings.put("CERTIFICATES","Certyfikaty");
        mappings.put("HOBBIES","Zainteresowania");
        mappings.put("FOOTER","Wyrażam zgodę na przetwarzanie moich danych osobowych dla potrzeb niezbędnych do"
                      + " realizacji procesu rekrutacji zgodnie z Ustawą z dn. 29.08.97 roku o Ochronie Danych "
                      + "Osobowych Dz. Ust. nr 133 poz.883.");

        mappings.put("BIRTHDAY_VAL",newResume.getBirthday());
        mappings.put("LOCATION_VAL1", newResume.getLocationOne());
        mappings.put("LOCATION_VAL2", newResume.getLocationTwo());
        mappings.put("EMAIL_VAL", newResume.getEmail());
        mappings.put("PHONE_VAL", newResume.getPhone());
        mappings.put("TARGET_VAL", newResume.getTarget());
        mappings.put("SKILL_NAME", newResume.getSkillName());
        mappings.put("SKILL_DESCRIPTION", newResume.getSkillDescription());
        mappings.put("LANGUAGE_NAME", newResume.getLanguageName());
        mappings.put("LANGUAGE_LEVEL", newResume.getLanguageLevel());
        mappings.put("CERTIFICATE", newResume.getCertificate());
        mappings.put("HOBBY", newResume.getHobby());

        wordMLPackage.getMainDocumentPart().variableReplace(mappings);



        //wordMLPackage.getMainDocumentPart()
//                .addStyledParagraphOfText("Title", "Hello Maven Central");

        //wordMLPackage.getMainDocumentPart().addParagraphOfText("from docx4j!");

        // Now save it
        wordMLPackage.save(new java.io.File(System.getProperty("user.dir") + "/" + docxFileName));

        List<Map<String,String>> theEducationList = new ArrayList<>();

        for (Education education : newResume.getEducations()){

            Map<String, String> theEducation = new HashMap<>();

            theEducation.put("EDUCATION_TIME", education.getTime());
            theEducation.put("UNIVERSITY_NAME", education.getUniversityName());
            theEducation.put("UNIVERSITY_COURSE", education.getUniversityCourse());

            theEducationList.add(theEducation);
        }

        docX4JResumeService.addTablesToTemplate(docxFileName, new String[]{"EDUCATION_TIME","UNIVERSITY_NAME",
                        "UNIVERSITY_COURSE"}, theEducationList,
                System.getProperty("user.dir") + "/" + docxFileName);

        List<Map<String,String>> theExperienceList = new ArrayList<>();

        for (Experience experience : newResume.getExperiences()){

            Map<String, String> theExperience = new HashMap<>();

            theExperience.put("EXPERIENCE_TIME", experience.getTime());
            theExperience.put("EXPERIENCE_NAME", experience.getName());
            theExperience.put("EXPERIENCE_ROLE_NAME", experience.getRoleName());
            theExperience.put("EXPERIENCE_DESCRIPTION", experience.getDescription());

            theExperienceList.add(theExperience);
        }

        docX4JResumeService.addTablesToTemplate(docxFileName, new String[]{"EXPERIENCE_TIME","EXPERIENCE_NAME",
                        "EXPERIENCE_ROLE_NAME", "EXPERIENCE_DESCRIPTION"}, theExperienceList,
                System.getProperty("user.dir") + "/" + docxFileName);

        docX4JResumeService.addImageToTemplate(docxFileName, "PICTURE", new File(imageSourcePath), System.getProperty("user.dir") + "/" + docxFileName);

        //docX4JResumeService.replacePlaceholdersInTemplate("DOCX_TEMPLATE.docx", "${NAME}", "JAROSLAW", System.getProperty("user.dir") + "/DOCX_FILE.docx");

        docX4JResumeService.convertDocxToPDF(docxFileName, pdfDestinationPath);

        System.out.println("Done.");

        return "result";
    }
}
