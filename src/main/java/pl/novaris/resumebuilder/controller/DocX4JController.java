package pl.novaris.resumebuilder.controller;

import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.novaris.resumebuilder.dao.entity.Resume;
import pl.novaris.resumebuilder.service.DocX4JResumeService;
import pl.novaris.resumebuilder.service.ResumeService;
import pl.novaris.resumebuilder.service.impl.DocX4JResumeServiceImpl;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/docx4j")
public class DocX4JController {

    @Autowired
    @Qualifier("docX4JResumeService")
    private DocX4JResumeService docX4JResumeService;

    @Autowired
    @Qualifier("resume")
    private Resume resume;

    @RequestMapping(value = "/fill", method = RequestMethod.GET)
    public String fillTemplate(Model model) {

        model.addAttribute("resume", new Resume());

        return "docx4j";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createDocX(@ModelAttribute Resume newResume) throws Exception {

        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(new FileInputStream(new File("DOCX_TEMPLATE2.docx")));

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
        mappings.put("EXPERIENCE_TIME", newResume.getExperienceTime());
        mappings.put("EXPERIENCE_NAME", newResume.getExperienceName());
        mappings.put("EXPERIENCE_ROLE_NAME", newResume.getExperienceRoleName());
        mappings.put("EXPERIENCE_DESCRIPTION", newResume.getExperienceDescription());
        mappings.put("SKILL_NAME", newResume.getSkillName());
        mappings.put("SKILL_DESCRIPTION", newResume.getSkillDescription());
        mappings.put("LANGUAGE_NAME", newResume.getLanguageName());
        mappings.put("LANGUAGE_LEVEL", newResume.getLanguageLevel());
        mappings.put("CERTIFICATE", newResume.getCertificate());
        mappings.put("HOBBY", newResume.getHobby());

        wordMLPackage.getMainDocumentPart().variableReplace(mappings);

        Map<String, String> educationOne = new HashMap<String, String>();

        educationOne.put("EDUCATION_TIME", newResume.getEducationTimeOne());
        educationOne.put("UNIVERSITY_NAME", newResume.getUniversityNameOne());
        educationOne.put("UNIVERSITY_COURSE", newResume.getUniversityCourseOne());

        Map<String, String> educationTwo = new HashMap<String, String>();

        educationTwo.put("EDUCATION_TIME", newResume.getEducationTimeTwo());
        educationTwo.put("UNIVERSITY_NAME", newResume.getUniversityNameTwo());
        educationTwo.put("UNIVERSITY_COURSE", newResume.getUniversityCourseTwo());



        //wordMLPackage.getMainDocumentPart()
//                .addStyledParagraphOfText("Title", "Hello Maven Central");

        //wordMLPackage.getMainDocumentPart().addParagraphOfText("from docx4j!");

        // Now save it
        wordMLPackage.save(new java.io.File(System.getProperty("user.dir") + "/DOCX_FILE.docx"));

        docX4JResumeService.addTablesToTemplate("DOCX_FILE.docx", new String[]{"EDUCATION_TIME","UNIVERSITY_NAME",
                "UNIVERSITY_COURSE"}, Arrays.asList(educationOne,educationTwo),
                System.getProperty("user.dir") + "/DOCX_FILE.docx");

        docX4JResumeService.addImageToTemplate("DOCX_FILE.docx", "PICTURE", new File("C:\\GeneratorCV\\cv.jpg"), System.getProperty("user.dir") + "/DOCX_FILE.docx");

        //docX4JResumeService.replacePlaceholdersInTemplate("DOCX_TEMPLATE.docx", "${NAME}", "JAROSLAW", System.getProperty("user.dir") + "/DOCX_FILE.docx");

        return "result";
    }
}
