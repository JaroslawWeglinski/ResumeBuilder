package pl.novaris.resumebuilder.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.novaris.resumebuilder.config.HibernateConfig;
import pl.novaris.resumebuilder.util.PdfGeneratorUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    PdfGeneratorUtil pdfGeneratorUtil;

    @RequestMapping({"", "/{person}"})
    public String greetTest(@PathVariable(name = "person", required = false) Optional<String> maybePerson) throws Exception {
        String person = maybePerson.filter(StringUtils::isNotBlank).orElse("unknown person");

        Map<String,String> data = new HashMap<>();
        data.put("name",person);
        try {
            pdfGeneratorUtil.createPdf("test", data);
            HibernateConfig hbConf = new HibernateConfig();
            hbConf.connectToDb();
        }catch(Exception e) {e.printStackTrace();}
        return String.format("Hello, %s!", person);
    }
}
