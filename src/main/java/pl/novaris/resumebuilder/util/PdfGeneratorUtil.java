package pl.novaris.resumebuilder.util;

import com.lowagie.text.pdf.BaseFont;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.pdf.PDFEncryption;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.UUID;

@Component
public class PdfGeneratorUtil {

    private String pdfFilePath;

    @Autowired
    private TemplateEngine templateEngine;
    public void createPdf(String templateName, Map map) throws Exception{
        Assert.notNull(templateName,"Template name must not be empty.");
        Context ctx = new Context();
        if (map != null) {
            Iterator itMap = map.entrySet().iterator();
            while (itMap.hasNext()) {
                Map.Entry pair = (Map.Entry)itMap.next();
                ctx.setVariable(pair.getKey().toString(),pair.getValue());
            }
        }

        ResourceBundle resourceBundle = ResourceBundle.getBundle("configuration");
        pdfFilePath = resourceBundle.getString("pdf.path");

        String processedHtml = templateEngine.process(templateName, ctx);
        FileOutputStream os = null;
        String fileName = UUID.randomUUID().toString();
        try {
            final File outputFile = File.createTempFile(fileName, ".pdf", new File(pdfFilePath)); // pdfFilePath must be specified in configuration.properties file in C:\\path format
            os = new FileOutputStream(outputFile);

            ITextRenderer renderer = new ITextRenderer();
            renderer.getFontResolver().addFont("C:\\Windows\\Fonts\\ARIALUNI.TTF", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            renderer.setDocumentFromString(processedHtml);
            renderer.layout();
            renderer.createPDF(os, false);
            renderer.finishPDF();
            System.out.println("PDF created successfully");
        }
        finally {
            if (os != null){
                try {
                    os.close();
                } catch(IOException e){/*ignore*/}
            }
        }
    }
}
