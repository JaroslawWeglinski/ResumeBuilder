package pl.novaris.resumebuilder.controller;

import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/docx4j")
public class DocX4JController {

    @RequestMapping("/create")
    public String createDocX() throws Docx4JException {

        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.createPackage();

        wordMLPackage.getMainDocumentPart()
                .addStyledParagraphOfText("Title", "Hello Maven Central");

        wordMLPackage.getMainDocumentPart().addParagraphOfText("from docx4j!");

        // Now save it
        wordMLPackage.save(new java.io.File(System.getProperty("user.dir") + "/helloMavenCentral.docx") );

        return "result";
    }
}
