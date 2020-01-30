package ch.bytecrowd.dokupository.utils;

import ch.bytecrowd.dokupository.models.enums.DocumentationLanguage;
import ch.bytecrowd.dokupository.models.enums.DocumentationType;
import ch.bytecrowd.dokupository.models.jpa.ApplicationVersion;
import ch.bytecrowd.dokupository.models.jpa.DocumentationNode;
import ch.bytecrowd.dokupository.models.jpa.WordTemplate;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlException;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class WordUtil {

    private static final Logger LOG = LoggerFactory.getLogger(WordTemplate.class);

    private static final Pattern VARIABLE_EXTRACTOR = Pattern.compile("\\$[A-Za-z_]+");

    private WordUtil() {
    }

    public static void fillNodes(List<DocumentationNode> documentationNodes, WordTemplate wordTemplate, Map<String, String> placeHoldersMap, File targetFile) throws IOException {

        final ByteArrayInputStream wordTemplateStream = new ByteArrayInputStream(wordTemplate.getBytes());
        final XWPFDocument doc = new XWPFDocument(wordTemplateStream);

        fillPlaceHoldersMap(placeHoldersMap, doc);

        documentationNodes.forEach(node -> fillNode(doc, node, wordTemplate.getLanguage()));

        if (wordTemplate.getDocumentationType().equals(DocumentationType.USER_DOCUMENTATION)) {
            LOG.debug("start fillVersionTable");
            try {
                fillTable(doc, documentationNodes, wordTemplate.getLanguage());
                LOG.debug("end fillVersionTable");
            } catch (XmlException e) {
                LOG.error("fillTable failed", e);
            }
        }


        doc.enforceUpdateFields();

        try (FileOutputStream out = new FileOutputStream(targetFile)) {
            doc.write(out);
        } catch (IOException e) {
            LOG.error("fillNodes failed", e);
        }
    }

    private static void fillPlaceHoldersMap(Map<String, String> map, XWPFDocument doc) {

        logPlageholders(map);

        doc.getParagraphsIterator().forEachRemaining(p -> {

            replaceByMap(map, p);
        });

        doc.getTables().forEach(t -> {
            t.getRows().forEach(r -> {
                r.getTableCells().forEach(c -> {
                    c.getParagraphs().forEach(p -> {
                        replaceByMap(map, p);
                    });
                });
            });
        });
    }

    private static void logPlageholders(Map<String, String> map) {

        if (LOG.isDebugEnabled()) {
            final String placeHoldersAvail = map.keySet().stream().map(key -> {
                return "\n" + key + ": " + map.get(key);
            }).collect(Collectors.joining());

            if (LOG.isDebugEnabled())
                LOG.debug("Available placeholders: " + placeHoldersAvail);
        }
    }

    private static void replaceByMap(Map<String, String> map, XWPFParagraph paragraph) {

        if (paragraph.getText().contains("$")) {


            final Matcher matcher = VARIABLE_EXTRACTOR.matcher(paragraph.getText());
            matcher.results().forEach(result -> {
                final String placeHolder = result.group();
                if (map.containsKey(placeHolder)) {


                    paragraph.getIRuns().forEach(run -> {


                        String newText = ((XWPFRun) run).getText(0);
                        newText = newText.replaceAll(placeHolder.replaceFirst("\\$", ""), map.get(placeHolder)).replaceFirst("\\$", "");

                        if (LOG.isDebugEnabled())
                            LOG.debug("placeholder mapped to Template: " + placeHolder + " value: " + newText);

                        ((XWPFRun) run).setText(newText, 0);

                    });
                } else {
                    LOG.warn("placeHolder: " + placeHolder + " not found");
                }
            });


        }
    }

    private static void fillNode(XWPFDocument doc, DocumentationNode documentationNode, DocumentationLanguage language) {

        if (LOG.isDebugEnabled())
            LOG.debug("filling node: " + documentationNode.getTextDe());

        final XWPFParagraph paragraphText = doc.createParagraph();
        paragraphText.setStyle(documentationNode.getStyle());

        switch (language) {
            case DE: addParagraphWithLineBreak(documentationNode.getTextDe(), paragraphText); break;
            case FR: addParagraphWithLineBreak(documentationNode.getTextFr(), paragraphText); break;
            default: throw new IllegalArgumentException("Language not supported: " + language);
        }


        appendPrintScreens(doc, documentationNode, language);
    }
    
    private static void addParagraphWithLineBreak(String text, XWPFParagraph paragraphText) {
    	
    	XWPFRun run = paragraphText.createRun();
    	
    	if (text != null && text.contains("\n")) {
            String[] lines = text.split("\n");
            run.setText(lines[0], 0); // set first line into XWPFRun
            for(int i=1;i<lines.length;i++){
                // add break and insert new text
                run.addBreak();
                run.setText(lines[i]);
            }
        } else {
            run.setText(text, 0);
        }
    }

    public static void appendPrintScreens(XWPFDocument doc, DocumentationNode documentationNode, DocumentationLanguage language) {

        if (LOG.isDebugEnabled())
            LOG.debug("adding Printscreens: " + documentationNode.getPrintscreens().size());
        documentationNode.getPrintscreens().stream().filter(p -> p.getLanguage().equals(language)).forEach(p -> {

            try (final ByteArrayInputStream imageStream = new ByteArrayInputStream(p.getData());) {

                Double width = p.getWidth();
                Double height = p.getHeight();
                Double scaling = 1.;
                if (height == null || width == null) {

                    final BufferedImage image = ImageUtil.toImage(p);
                    width = Double.valueOf(image.getWidth());
                    height = Double.valueOf(image.getHeight());
                    scaling = ImageUtil.getScalingForDocx(image);
                }
                final int pictureType = ImageUtil.guessPictureType(p);

                final XWPFParagraph paragraphImage = doc.createParagraph();
                final XWPFRun imageRun = paragraphImage.createRun();
                imageRun.addPicture(imageStream, pictureType, "IMAGE", Units.toEMU(width * scaling), Units.toEMU(height * scaling));

                if (p.getFootnote() != null) {
                    final XWPFParagraph paragraphText = doc.createParagraph();
                    paragraphText.createRun().setText(p.getFootnote());
                }

            } catch (InvalidFormatException | IOException e) {
                LOG.error("Appending PrintScreen failed for id:" + p.getId(), e);
            }
        });
    }

    public static void fillTable(XWPFDocument doc, List<DocumentationNode> documentationNodes, DocumentationLanguage lang) throws IOException, XmlException {

        final XWPFTable table = doc.getTableArray(1);

        final XWPFTableRow oldRow = table.getRow(1);

        final List<ApplicationVersion> versions = documentationNodes.stream().map(node -> node.getVersionBegin()).distinct().sorted((o1, o2) -> {
            return o1.getSortierung() == null ? 1 : o1.getSortierung().compareTo(o2.getSortierung());
        }).collect(Collectors.toList());


        final AtomicInteger j = new AtomicInteger(0);
        versions.forEach(version -> {
            XWPFTableRow newRow = null;
            LOG.debug("Processing Version(): " + version.getVersion());
            try {
                final CTRow ctrow = CTRow.Factory.parse(oldRow.getCtRow().newInputStream());
                newRow = new XWPFTableRow(ctrow, table);
                LOG.debug("newRow.getTableCells().size(): " + newRow.getTableCells().size());
                for (XWPFTableCell cell : newRow.getTableCells()) {
                    for (XWPFParagraph paragraph : cell.getParagraphs()) {
                        for (XWPFRun run : paragraph.getRuns()) {
                            switch (j.get() % 4) {
                                case (0):
                                    run.setText(version.getVersion());
                                    break;
                                case (1):
                                    run.setText(lang.equals(DocumentationLanguage.FR) ? version.getRemarkFr() : version.getRemarkDe());
                                    break;
                                case (2):
                                    if (version.getReleaseDate() != null) {
                                        run.setText(DateTimeFormatter.ofPattern("dd.MM.yyyy").format(version.getReleaseDate()));
                                    }
                                    break;
                                case (3):
                                    if (version.getUserResponsible() != null) {
                                        run.setText(version.getUserResponsible().getFirstname_Lastname());
                                    }
                                    break;
                            }
                            j.incrementAndGet();
                        }
                    }
                }
            } catch (XmlException | IOException e) {
                LOG.error("Fill Version in TableRow failed", e);
            }
            table.addRow(newRow);
        });

        table.removeRow(1);

    }
}
