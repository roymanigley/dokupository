package ch.bytecrowd.dokupository.tests.utils;

import ch.bytecrowd.dokupository.models.enums.ApplicationType;
import ch.bytecrowd.dokupository.models.enums.DocumentationLanguage;
import ch.bytecrowd.dokupository.models.enums.DocumentationType;
import ch.bytecrowd.dokupository.models.enums.RepoType;
import ch.bytecrowd.dokupository.models.jpa.*;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * fills only notnull fields
 */
public class DummyDataCreatorUtil {

    public static Application createDummyApplication() {

        final Application app = new Application();
        app.setNameDe(RandomUtil.randomString());
        app.setNameFr(RandomUtil.randomString());
        app.setBranch(RandomUtil.randomString());
        app.setToken(RandomUtil.randomString());
        app.setRepositoryUrl(RandomUtil.randomString());
        app.setRepoType(RepoType.GOGS);
        app.setAppType(ApplicationType.MODUL);
        return app;
    }

    public static ApplicationVersion createDummyApplicationVersion(Application dummyApp) {

        String dummyVersion = RandomUtil.randomPositiveInteger() + "." + RandomUtil.randomPositiveInteger();
        return createDummyApplicationVersion(dummyVersion, dummyApp);
    }

    public static ApplicationVersion createDummyApplicationVersion(String dummyVersion, Application dummyApp) {

        final ApplicationVersion appVersion = new ApplicationVersion();
        appVersion.setVersion(dummyVersion);
        appVersion.setApplication(dummyApp);
        return appVersion;
    }

    public static User createDummyUser() {

        final User user = new User();
        user.setFirstname(RandomUtil.randomString());
        user.setLastname(RandomUtil.randomString());
        user.setUsername(RandomUtil.randomString());
        user.setPassword(RandomUtil.randomString());
        user.setAcronym(RandomUtil.randomString());
        user.setActive(Boolean.TRUE);
        return user;
    }

    public static Commit createDummyCommit(Application app) {

        final Commit commit = new Commit();
        commit.setApplication(app);
        commit.setHash(RandomUtil.randomString());
        commit.setIgnoreForDocumentation(Boolean.FALSE);
        return commit;
    }

    public static DocumentationNode createDummyDocumentationNode(ApplicationVersion versionBegin, ApplicationVersion versionEnd) {

        return createDummyDocumentationNode(versionBegin, versionEnd, RandomUtil.randomDocumentationType());
    }

    public static DocumentationNode createDummyDocumentationNode(ApplicationVersion versionBegin, ApplicationVersion versionEnd, DocumentationType documentationType) {

        final DocumentationNode documentationNode = new DocumentationNode();
        documentationNode.setVersionBegin(versionBegin);
        documentationNode.setVersionEnd(versionEnd);
        documentationNode.setDocumentationType(documentationType);
        return documentationNode;
    }

    public static WordTemplate createDummyWordTemplate() {

        final DocumentationType documentationType = RandomUtil.randomDocumentationType();
        return createDummyWordTemplate(documentationType);
    }

    public static WordTemplate createDummyWordTemplate(DocumentationType documentationType) {

        final WordTemplate wordTemplate = new WordTemplate();
        wordTemplate.setName(RandomUtil.randomString());
        wordTemplate.setDocumentationType(documentationType);
        wordTemplate.setBytes(RandomUtil.randomBytes(1024));
        wordTemplate.setLanguage(DocumentationLanguage.DE);
        return wordTemplate;
    }

    public static PrintScreen createDummyPrintScreen(DocumentationNode documentationNode) {

        final PrintScreen printScreen = new PrintScreen();
        printScreen.setDocumentationNode(documentationNode);
        printScreen.setData(RandomUtil.randomBytes(1024));
        printScreen.setLanguage(DocumentationLanguage.DE);
        return printScreen;
    }

    public static PrintScreen createDummyPrintScreenWithImageFromResources(DocumentationNode documentationNode, String resourceName) throws IOException {

        final PrintScreen printScreen = createDummyPrintScreen(documentationNode);
        try (final InputStream imageStream = DummyDataCreatorUtil.class.getClassLoader().getResourceAsStream(resourceName);
             final DataInputStream dataInputStream = new DataInputStream(imageStream);){
            final byte[] imageBytes = dataInputStream.readAllBytes();
            printScreen.setData(imageBytes);
        } catch (Exception e) {
            throw e;
        }
        return printScreen;
    }
}