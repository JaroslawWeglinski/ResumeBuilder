package pl.novaris.resumebuilder.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.novaris.resumebuilder.util.PdfGeneratorUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/build")
public class BuildController {

    @Autowired
    PdfGeneratorUtil pdfGeneratorUtil;

    static private Map<String,String> data = new HashMap<>();

    @RequestMapping("/personalData")
    public void addPersonalData(Optional<String> maybePersonalData) throws Exception {
        String personalData = maybePersonalData.filter(StringUtils::isNotBlank).orElse("unknown");

        Map<String,String> data = new HashMap<>();
        data.put("personalData",personalData);

    }

    @RequestMapping("/education")
    public void addEducation(Optional<String> maybeEducation) throws Exception {
        String education = maybeEducation.filter(StringUtils::isNotBlank).orElse("unknown");

        Map<String,String> data = new HashMap<>();
        data.put("education",education);

    }

    @RequestMapping("/generate")
    public void generatePdf() {
        try {
            pdfGeneratorUtil.createPdf("test", data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
