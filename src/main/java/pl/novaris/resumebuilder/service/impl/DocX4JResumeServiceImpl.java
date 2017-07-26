package pl.novaris.resumebuilder.service.impl;

import org.apache.commons.lang.StringUtils;
import org.docx4j.XmlUtils;
import org.docx4j.dml.wordprocessingDrawing.Inline;
import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage;
import org.docx4j.wml.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pl.novaris.resumebuilder.service.DocX4JResumeService;
import pl.novaris.resumebuilder.service.ResumeService;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import java.io.*;
import java.util.*;

@Service
@Qualifier("docX4JResumeService")
public class DocX4JResumeServiceImpl implements DocX4JResumeService {

    private WordprocessingMLPackage getTemplate(String name) throws Docx4JException, FileNotFoundException{
        WordprocessingMLPackage template = WordprocessingMLPackage.load(new FileInputStream(new File(name)));
        return template;
    }

    private List<Object> getAllElementFromObject(Object obj, Class<?> toSearch){
        List<Object> result = new ArrayList<>();
        if (obj instanceof JAXBElement) obj = ((JAXBElement<?>) obj).getValue();
        if (obj.getClass().equals(toSearch)) result.add(obj);
        else if (obj instanceof ContentAccessor) {
            List<?> children = ((ContentAccessor) obj).getContent();
            for (Object child : children) {
                result.addAll(getAllElementFromObject(child, toSearch));
            }
        }
        return result;

    }

    private void replacePlaceholder(WordprocessingMLPackage template, String placeholder, String value) {
        List<Object> texts = getAllElementFromObject(template.getMainDocumentPart(), Text.class);

        for (Object text : texts) {
            Text textElement = (Text) text;
            if (textElement.getValue().equals(placeholder)) {
                textElement.setValue(value);
            }
        }
    }

    private void writeDocxToStream(WordprocessingMLPackage template, String target) throws IOException, Docx4JException {
        File f = new File(target);
        template.save(f);
    }

    public static void searchAndReplace(List<Object> texts, Map<String, String> values){

        // -- scan all expressions
        // Will later contain all the expressions used though not used at the moment
        List<String> els = new ArrayList<String>();

        StringBuilder sb = new StringBuilder();
        int PASS = 0;
        int PREPARE = 1;
        int READ = 2;
        int mode = PASS;

        // to nullify
        List<int[]> toNullify = new ArrayList<int[]>();
        int[] currentNullifyProps = new int[4];

        // Do scan of els and immediately insert value
        for(int i = 0; i<texts.size(); i++){
            Object text = texts.get(i);
            Text textElement = (Text) text;
            String newVal = "";
            String v = textElement.getValue();
//          System.out.println("text: "+v);
            StringBuilder textSofar = new StringBuilder();
            int extra = 0;
            char[] vchars = v.toCharArray();
            for(int col = 0; col<vchars.length; col++){
                char c = vchars[col];
                textSofar.append(c);
                switch(c){
                    case '$': {
                        mode=PREPARE;
                        sb.append(c);
//                  extra = 0;
                    } break;
                    case '{': {
                        if(mode==PREPARE){
                            sb.append(c);
                            mode=READ;
                            currentNullifyProps[0]=i;
                            currentNullifyProps[1]=col+extra-1;
                            System.out.println("extra-- "+extra);
                        } else {
                            if(mode==READ){
                                // consecutive opening curl found. just read it
                                // but supposedly throw error
                                sb = new StringBuilder();
                                mode=PASS;
                            }
                        }
                    } break;
                    case '}': {
                        if(mode==READ){
                            mode=PASS;
                            sb.append(c);
                            els.add(sb.toString());
                            newVal +=textSofar.toString()
                                    +(null==values.get(sb.toString())?sb.toString():values.get(sb.toString()));
                            textSofar = new StringBuilder();
                            currentNullifyProps[2]=i;
                            currentNullifyProps[3]=col+extra;
                            toNullify.add(currentNullifyProps);
                            currentNullifyProps = new int[4];
                            extra += sb.toString().length();
                            sb = new StringBuilder();
                        } else if(mode==PREPARE){
                            mode = PASS;
                            sb = new StringBuilder();
                        }
                    }
                    default: {
                        if(mode==READ) sb.append(c);
                        else if(mode==PREPARE){
                            mode=PASS;
                            sb = new StringBuilder();
                        }
                    }
                }
            }
            newVal +=textSofar.toString();
            textElement.setValue(newVal);
        }

        // remove original expressions
        if(toNullify.size()>0)
            for(int i = 0; i<texts.size(); i++){
                if(toNullify.size()==0) break;
                currentNullifyProps = toNullify.get(0);
                Object text = texts.get(i);
                Text textElement = (Text) text;
                String v = textElement.getValue();
                StringBuilder nvalSB = new StringBuilder();
                char[] textChars = v.toCharArray();
                for(int j = 0; j<textChars.length; j++){
                    char c = textChars[j];
                    if(null==currentNullifyProps) {
                        nvalSB.append(c);
                        continue;
                    }
                    // I know 100000 is too much!!! And so what???
                    int floor = currentNullifyProps[0]*100000+currentNullifyProps[1];
                    int ceil = currentNullifyProps[2]*100000+currentNullifyProps[3];
                    int head = i*100000+j;
                    if(!(head>=floor && head<=ceil)){
                        nvalSB.append(c);
                    }

                    if(j>currentNullifyProps[3] && i>=currentNullifyProps[2]){
                        toNullify.remove(0);
                        if(toNullify.size()==0) {
                            currentNullifyProps = null;
                            continue;
                        }
                        currentNullifyProps = toNullify.get(0);
                    }
                }
                textElement.setValue(nvalSB.toString());
            }
    }

    private void replaceParagraph(String placeholder, String textToAdd, WordprocessingMLPackage template, ContentAccessor addTo) {
        // 1. get the paragraph
        List<Object> paragraphs = getAllElementFromObject(template.getMainDocumentPart(), P.class);

        P toReplace = null;
        for (Object p : paragraphs) {
            List<Object> texts = getAllElementFromObject(p, Text.class);
            for (Object t : texts) {
                Text content = (Text) t;
                if (content.getValue().equals(placeholder)) {
                    toReplace = (P) p;
                    break;
                }
            }
        }

        // we now have the paragraph that contains our placeholder: toReplace
        // 2. split into seperate lines
        String as[] = StringUtils.splitPreserveAllTokens(textToAdd, '\n');

        for (int i = 0; i < as.length; i++) {
            String ptext = as[i];

            // 3. copy the found paragraph to keep styling correct
            P copy = (P) XmlUtils.deepCopy(toReplace);

            // replace the text elements from the copy
            List texts = getAllElementFromObject(copy, Text.class);
            if (texts.size() > 0) {
                Text textToReplace = (Text) texts.get(0);
                textToReplace.setValue(ptext);
            }

            // add the paragraph to the document
            addTo.getContent().add(copy);
        }

        // 4. remove the original one
        ((ContentAccessor)toReplace.getParent()).getContent().remove(toReplace);

    }

    private Tbl getTemplateTable(List<Object> tables, String templateKey) throws Docx4JException, JAXBException {
        for (Iterator<Object> iterator = tables.iterator(); iterator.hasNext();) {
            Object tbl = iterator.next();
            List<?> textElements = getAllElementFromObject(tbl, Text.class);
            for (Object text : textElements) {
                Text textElement = (Text) text;
                System.out.println(textElement.getValue());
                if (textElement.getValue() != null && textElement.getValue().equals(templateKey))
                    return (Tbl) tbl;
            }
        }
        return null;
    }

    private void addRowToTable(Tbl reviewTable, Tr templateRow, Map<String, String> replacements) {
        Tr workingRow = (Tr) XmlUtils.deepCopy(templateRow);
        List textElements = getAllElementFromObject(workingRow, Text.class);
        for (Object object : textElements) {
            Text text = (Text) object;
            String replacementValue = (String) replacements.get(text.getValue());
            if (replacementValue != null)
                text.setValue(replacementValue);
        }

        reviewTable.getContent().add(workingRow);
    }

    private void replaceTable(String[] placeholders, List<Map<String, String>> textToAdd,
                              WordprocessingMLPackage template) throws Docx4JException, JAXBException {
        List<Object> tables = getAllElementFromObject(template.getMainDocumentPart(), Tbl.class);

        // 1. find the table
        Tbl tempTable = getTemplateTable(tables, placeholders[0]);
        List<Object> rows = getAllElementFromObject(tempTable, Tr.class);

        // first row is without header
        if (rows.size() == 1) {
            // this is our template row
            Tr templateRow = (Tr) rows.get(0);

            for (Map<String, String> replacements : textToAdd) {
                // 2 and 3 are done in this method
                addRowToTable(tempTable, templateRow, replacements);
            }

            // 4. remove the template row
            tempTable.getContent().remove(templateRow);
        }
    }

    private static P addInlineImageToParagraph(Inline inline) {
        // Now add the in-line image to a paragraph
        ObjectFactory factory = new ObjectFactory();
        P paragraph = factory.createP();
        R run = factory.createR();
        paragraph.getContent().add(run);
        Drawing drawing = factory.createDrawing();
        run.getContent().add(drawing);
        drawing.getAnchorOrInline().add(inline);
        return paragraph;
    }


    private static Inline createInlineImage(File file, WordprocessingMLPackage template) throws Exception {
        byte[] bytes = convertImageToByteArray(file);

        BinaryPartAbstractImage imagePart =
                BinaryPartAbstractImage.createImagePart(template, bytes);

        int docPrId = 1;
        int cNvPrId = 2;

        return imagePart.createImageInline("Filename hint",
                "Alternative text", docPrId, cNvPrId, false);
    }


    private static byte[] convertImageToByteArray(File file)
            throws FileNotFoundException, IOException {
        InputStream is = new FileInputStream(file);
        long length = file.length();
        // You cannot create an array using a long, it needs to be an int.
        if (length > Integer.MAX_VALUE) {
            System.out.println("File too large!!");
        }
        byte[] bytes = new byte[(int)length];
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }
        // Ensure all the bytes have been read
        if (offset < bytes.length) {
            System.out.println("Could not completely read file "+file.getName());
        }
        is.close();
        return bytes;
    }


    @Override
    public void replacePlaceholdersInTemplate(String templateName, String placeholder, String value, String target) throws IOException, Docx4JException {

        WordprocessingMLPackage template = getTemplate(templateName);

        List<Object> texts = getAllElementFromObject(
                template.getMainDocumentPart(), Text.class);

        searchAndReplace(texts, new HashMap<String, String>(){
            {
                this.put("${NAME}", "JAROSLAW");
                this.put("${SURNAME}", "WEGLINSKI");
            }
            @Override
            public String get(Object key) {
                // TODO Auto-generated method stub
                return super.get(key);
            }
        });


        //replacePlaceholder(template, placeholder, value);
        writeDocxToStream(template, target);
    }

    @Override
    public void addParagraphsToTemplate(String templateName, String placeholder, String textToAdd, ContentAccessor addTo) {

    }

    @Override
    public void addTablesToTemplate(String templateName, String[] placeholders, List<Map<String, String>> textToAdd, String target) throws IOException, Docx4JException, JAXBException {
        WordprocessingMLPackage template = getTemplate(templateName);
        replaceTable(placeholders, textToAdd, template);
        writeDocxToStream(template,target);
    }

    @Override
    public void addImageToTemplate(String templateName, String placeholder, File image, String target) throws Exception {
        WordprocessingMLPackage template = getTemplate(templateName);
        ObjectFactory factory = Context.getWmlObjectFactory();

        List elemetns = getAllElementFromObject(template.getMainDocumentPart(), Tbl.class);

        for(Object obj : elemetns){
            if(obj instanceof Tbl){
                Tbl table = (Tbl) obj;
                List rows = getAllElementFromObject(table, Tr.class);
                for(Object trObj : rows){
                    Tr tr = (Tr) trObj;
                    List cols = getAllElementFromObject(tr, Tc.class);
                    for(Object tcObj : cols){
                        Tc tc = (Tc) tcObj;
                        List texts = getAllElementFromObject(tc, Text.class);
                        for(Object textObj : texts){
                            Text text = (Text) textObj;
                            if(text.getValue().equalsIgnoreCase("PICTURE")){
                                P paragraphWithImage = addInlineImageToParagraph(createInlineImage(image, template));
                                PPr paragraphProperties = factory.createPPr();
                                Jc justification = factory.createJc();
                                justification.setVal(JcEnumeration.CENTER);
                                paragraphProperties.setJc(justification);
                                paragraphWithImage.setPPr(paragraphProperties);
                                tc.getContent().remove(0);

                                tc.getContent().add(paragraphWithImage);
                            }
                        }
                        System.out.println("here");
                    }
                }
                System.out.println("here");
            }
        }

        writeDocxToStream(template, target);
    }
}
