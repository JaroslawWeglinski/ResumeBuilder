package pl.novaris.resumebuilder.service;

import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.ContentAccessor;
import org.docx4j.wml.Tbl;
import org.docx4j.wml.Tr;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface DocX4JResumeService {
    void replacePlaceholdersInTemplate(String templateName, String placeholder, String value, String target) throws IOException, Docx4JException;
    void addParagraphsToTemplate(String templateName, String placeholder, String textToAdd, ContentAccessor addTo);
    void addTablesToTemplate(String templateName, String[] placeholders, List<Map<String,String>> textToAdd, String target) throws IOException, Docx4JException, JAXBException;
    void addImageToTemplate(String templateName, String placeholder, File image, String target) throws Exception;
}
