package ch.bytecrowd.dokupository.tests.word;

import ch.bytecrowd.dokupository.models.enums.DocumentationLanguage;
import ch.bytecrowd.dokupository.models.enums.DocumentationType;
import ch.bytecrowd.dokupository.models.jpa.*;
import ch.bytecrowd.dokupository.tests.utils.DummyDataCreatorUtil;
import ch.bytecrowd.dokupository.gui.controllers.documentations.DocumentationGenerator;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class ChangelogFillerTest {

    private static final Logger LOG = LoggerFactory.getLogger(ChangelogFillerTest.class);

    @Test
    public void testDe() {

        final java.lang.String CHANGELOG_DE = "docx/Changelog - DE.docx";
        fillCHangelogForTest(CHANGELOG_DE, DocumentationLanguage.DE);

        assertThat("Reason for Test", "STRING", is("STRING"));
    }
    @Test
    public void testFr() {

        final java.lang.String CHANGELOG_FR = "docx/Changelog - FR.docx";
        fillCHangelogForTest(CHANGELOG_FR, DocumentationLanguage.FR);

        assertThat("Reason for Test", "STRING", is("STRING"));
    }

    private void fillCHangelogForTest(String templateResource, DocumentationLanguage lang) {
        try (final InputStream changeLogTemplateAsStream = ChangelogFillerTest.class.getClassLoader().getResourceAsStream(templateResource);
             final DataInputStream dataInputStream = new DataInputStream(changeLogTemplateAsStream);) {

            final WordTemplate wordTemplate = new WordTemplate();
            wordTemplate.setDocumentationType(DocumentationType.CHANGE_LOG);
            wordTemplate.setLanguage(lang);
            wordTemplate.setBytes(dataInputStream.readAllBytes());

            final Application dummyApplication = DummyDataCreatorUtil.createDummyApplication();
            final ApplicationVersion dummyApplicationVersion = DummyDataCreatorUtil.createDummyApplicationVersion(dummyApplication);

            final DocumentationNode dummyDocumentationNode = DummyDataCreatorUtil.createDummyDocumentationNode(dummyApplicationVersion, dummyApplicationVersion);
            dummyDocumentationNode.setTextDe("I am a Title");
            dummyDocumentationNode.setStyle("Heading 1");

            final PrintScreen dummyPrintScreen_PNG = DummyDataCreatorUtil.createDummyPrintScreenWithImageFromResources(dummyDocumentationNode, "img/PrintScreen.png");
            dummyPrintScreen_PNG.setFootnote("HELLO i am a Footnote");
            dummyPrintScreen_PNG.setLanguage(lang);
            final PrintScreen dummyPrintScreen_GIF = DummyDataCreatorUtil.createDummyPrintScreenWithImageFromResources(dummyDocumentationNode, "img/PrintScreen.gif");
            dummyPrintScreen_GIF.setLanguage(lang);
            final PrintScreen dummyPrintScreen_JPG = DummyDataCreatorUtil.createDummyPrintScreenWithImageFromResources(dummyDocumentationNode, "img/PrintScreen.jpg");
            dummyPrintScreen_JPG.setLanguage(lang);

            dummyDocumentationNode.getPrintscreens().add(dummyPrintScreen_PNG);
            dummyDocumentationNode.getPrintscreens().add(dummyPrintScreen_GIF);
            dummyDocumentationNode.getPrintscreens().add(dummyPrintScreen_JPG);

            final Map<String, String> map = DocumentationGenerator.fillMapWithPlaceholdeValues(wordTemplate, dummyApplicationVersion, dummyApplicationVersion);

            final File targetFile = new File(System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") + templateResource.replaceAll("docx/", ""));
            DocumentationType.CHANGE_LOG.generateDocumentation(Arrays.asList(dummyDocumentationNode), wordTemplate, map, targetFile);

        } catch (Exception e) {
            LOG.error("ChangelogFillerTest failed", e);
            fail(e.getMessage());
        }
    }
}