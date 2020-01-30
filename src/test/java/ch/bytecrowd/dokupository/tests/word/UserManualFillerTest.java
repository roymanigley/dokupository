package ch.bytecrowd.dokupository.tests.word;

import ch.bytecrowd.dokupository.gui.controllers.documentations.DocumentationGenerator;
import ch.bytecrowd.dokupository.models.enums.ApplicationVersionState;
import ch.bytecrowd.dokupository.models.enums.DocumentationLanguage;
import ch.bytecrowd.dokupository.models.enums.DocumentationType;
import ch.bytecrowd.dokupository.models.jpa.*;
import ch.bytecrowd.dokupository.tests.utils.DummyDataCreatorUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class UserManualFillerTest {

    private static final Logger LOG = LoggerFactory.getLogger(UserManualFillerTest.class);

    @Test
    public void testDE() {

        final java.lang.String DOKUMENTATION_DE = "docx/Benutzerdoku - DE.docx";
        fillUserdocumentationForTest(DOKUMENTATION_DE, DocumentationLanguage.DE);

        assertThat("Reason for Test", "STRING", is("STRING"));
    }

    @Test
    public void testFR() {

        final java.lang.String DOKUMENTATION_FR = "docx/Benutzerdoku - FR.docx";
        fillUserdocumentationForTest(DOKUMENTATION_FR, DocumentationLanguage.FR);

        assertThat("Reason for Test", "STRING", is("STRING"));
    }

    private void fillUserdocumentationForTest(String templateResource, DocumentationLanguage lang) {
        try (final InputStream userManualTemplateAsStream = UserManualFillerTest.class.getClassLoader().getResourceAsStream(templateResource);
             final DataInputStream dataInputStream = new DataInputStream(userManualTemplateAsStream);) {

            final WordTemplate wordTemplate = new WordTemplate();
            wordTemplate.setLanguage(lang);
            wordTemplate.setDocumentationType(DocumentationType.USER_DOCUMENTATION);
            wordTemplate.setBytes(dataInputStream.readAllBytes());

            final Application dummyApplication = DummyDataCreatorUtil.createDummyApplication();
            final ApplicationVersion dummyApplicationVersion_V_1 = DummyDataCreatorUtil.createDummyApplicationVersion("V 1.0", dummyApplication);
            dummyApplicationVersion_V_1.setReleaseDate(LocalDate.now());
            final ApplicationVersion dummyApplicationVersion_V_2 = DummyDataCreatorUtil.createDummyApplicationVersion("V 2.0", dummyApplication);

            final DocumentationNode dummyDocumentationNode_V_1 = DummyDataCreatorUtil.createDummyDocumentationNode(dummyApplicationVersion_V_1, null);
            dummyDocumentationNode_V_1.setTextDe("I am a Title V 1 [DE]");
            dummyDocumentationNode_V_1.setTextFr("I am a Title V 1 [FR]");
            dummyDocumentationNode_V_1.setStyle("Heading 1");

            final DocumentationNode dummyDocumentationNode_V_2 = DummyDataCreatorUtil.createDummyDocumentationNode(dummyApplicationVersion_V_2, null);
            dummyDocumentationNode_V_2.setTextDe("I am a Title V 2 [DE]");
            dummyDocumentationNode_V_2.setTextDe("I am a Title V 2 [FR]");
            dummyDocumentationNode_V_2.setStyle("Heading 1");

            final PrintScreen dummyPrintScreen_PNG = DummyDataCreatorUtil.createDummyPrintScreenWithImageFromResources(dummyDocumentationNode_V_1, "img/PrintScreen.png");
            dummyPrintScreen_PNG.setFootnote("HELLO i am a Footnote");
            dummyPrintScreen_PNG.setLanguage(lang);
            final PrintScreen dummyPrintScreen_GIF = DummyDataCreatorUtil.createDummyPrintScreenWithImageFromResources(dummyDocumentationNode_V_1, "img/PrintScreen.gif");
            dummyPrintScreen_GIF.setLanguage(lang);
            final PrintScreen dummyPrintScreen_JPG = DummyDataCreatorUtil.createDummyPrintScreenWithImageFromResources(dummyDocumentationNode_V_2, "img/PrintScreen.jpg");
            dummyPrintScreen_JPG.setLanguage(lang);

            dummyDocumentationNode_V_1.getPrintscreens().add(dummyPrintScreen_PNG);
            dummyDocumentationNode_V_1.getPrintscreens().add(dummyPrintScreen_GIF);
            dummyDocumentationNode_V_1.getPrintscreens().add(dummyPrintScreen_JPG);


            final Map<String, String> map = DocumentationGenerator.fillMapWithPlaceholdeValues(wordTemplate, dummyApplicationVersion_V_1, dummyApplicationVersion_V_2);
            /*
            final HashMap<String, String> map = new HashMap<>();
            map.put("$APP_NAME", "GinaPortal");
            map.put("$VERSION_BEGIN", dummyApplicationVersion_V_1.getVersion());
            map.put("$VERSION_END", dummyApplicationVersion_V_2.getVersion());
            map.put("$APP_NAME", "DummyAPP");
            map.put("$APP_RESPONSIBLE", "John Doe");
            map.put("$APP_RESPONSIBLE_ACRONYM", "JD");


            map.put("$VERSION_END_STATE", ApplicationVersionState.ABGENOMMEN.toI18nString());
            map.put("$VERSION_END_RESPONSIBLE", "John Doe");
            map.put("$VERSION_END_RESPONSIBLE_ACRONYM", "JD");
            map.put("$VERSION_END_TESTER", "Joe Tester");
            map.put("$VERSION_END_TESTER_ACRONYM", "JT");

            map.put("$CURRENT_USER", "Le Admin");
            map.put("$CURRENT_USER_ACRONYM", "LA");*/


            final File targetFile = new File(System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") + templateResource.replaceAll("docx/", ""));

            DocumentationType.USER_DOCUMENTATION.generateDocumentation(Arrays.asList(dummyDocumentationNode_V_1, dummyDocumentationNode_V_2), wordTemplate, map, targetFile);

        } catch (IOException e) {
            LOG.error("ChangelogFillerTest failed", e);
            fail(e.getMessage());
        }
    }
}