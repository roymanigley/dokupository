package ch.bytecrowd.dokupository.gui.utils;

import ch.bytecrowd.dokupository.models.enums.*;
import ch.bytecrowd.dokupository.models.interfaces.Keyable;
import ch.bytecrowd.dokupository.models.jpa.Application;
import ch.bytecrowd.dokupository.models.jpa.ApplicationVersion;
import ch.bytecrowd.dokupository.models.jpa.User;
import ch.bytecrowd.dokupository.models.jpa.WordTemplate;
import ch.bytecrowd.dokupository.repositories.jpa.*;
import ch.bytecrowd.dokupository.services.jpa.GenericCrudService;
import javafx.util.StringConverter;
import javafx.util.converter.LocalDateStringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class ConverterFactory {

    private static final Logger LOG = LoggerFactory.getLogger(ConverterFactory.class);

    private ConverterFactory() {
    }


    public static StringConverter<Application> createApplicationConverter(GenericCrudService<Application> repository) {
        return new StringConverter<>() {

            @Override
            public String toString(Application application) {
                if (application == null || application.getId() == null)
                    return null;
                return application.getNameDe() + " id:" + application.getId().toString();
            }

            @Override
            public Application fromString(String string) {

                return fromStringToEntity(string, repository);
            }
        };
    }

    public static StringConverter<User> createUserConverter(GenericCrudService<User> repository) {
        return new StringConverter<>() {

            @Override
            public String toString(User user) {
                if (user == null || user.getId() == null)
                    return null;
                return user.getFirstname() + " " + user.getLastname() + " id:" + user.getId().toString();
            }

            @Override
            public User fromString(String string) {

                return fromStringToEntity(string, repository);
            }
        };
    }

    private static <T extends Keyable> T fromStringToEntity(String string, GenericCrudService<T> repository) {
        try {
            final String id = string.replaceAll("^.+id:", "");
            return repository.fetchById(Long.valueOf(id)).orElseThrow(() -> {
                throw new RuntimeException("JFXComboBox converter.fromString failed String: " + string);
            });
        } catch (NumberFormatException e) {
            LOG.warn("JFXComboBox converter.fromString failed String: " + string + " => " + e.getMessage());
            return null;
        }
    }

    public static StringConverter<WordTemplate> createWortTemplateConverter(GenericCrudService<WordTemplate> repository) {
        return new StringConverter<>() {

            @Override
            public String toString(WordTemplate template) {
                if (template == null || template.getId() == null)
                    return null;
                return template.getName() + " id:" + template.getId().toString();
            }

            @Override
            public WordTemplate fromString(String string) {

                return fromStringToEntity(string, repository);
            }
        };
    }

    public static StringConverter<ApplicationVersion> createApplicationVersionConverter(GenericCrudService<ApplicationVersion> repository) {
        return new StringConverter<>() {

            @Override
            public String toString(ApplicationVersion applicationVersion) {
                if (applicationVersion == null || applicationVersion.getId() == null)
                    return null;
                return applicationVersion.getVersion() + " (" + applicationVersion.getState().toI18nString() + ")" + " id:" + applicationVersion.getId().toString();
            }

            @Override
            public ApplicationVersion fromString(String string) {

                return fromStringToEntity(string, repository);
            }
        };
    }

    public static StringConverter<ApplicationType> createAppTypeConverter() {

        return new StringConverter<>() {
            @Override
            public String toString(ApplicationType appType) {
                return appType.toString();
            }

            @Override
            public ApplicationType fromString(String string) {

                return Arrays.stream(ApplicationType.values())
                        .filter(repoType -> repoType.toString().equals(string)).findAny()
                        .orElseThrow(() -> {
                            throw new RuntimeException("JFXComboBox converter.fromString for ApplicationType failed String: " + string);
                        });
            }
        };
    }

    public static StringConverter<RepoType> createRepoTypeConverter() {

        return new StringConverter<>() {
            @Override
            public String toString(RepoType repoType) {
                return repoType.toString();
            }

            @Override
            public RepoType fromString(String string) {

                return Arrays.stream(RepoType.values())
                        .filter(repoType -> repoType.toString().equals(string)).findAny()
                        .orElseThrow(() -> {
                            throw new RuntimeException("JFXComboBox converter.fromString for RepoType failed String: " + string);
                        });
            }
        };
    }

    public static StringConverter<DocumentationType> createDocumentationTypeConverter() {

        return new StringConverter<DocumentationType>() {
            @Override
            public String toString(DocumentationType documentationType) {
                return documentationType.toString();
            }

            @Override
            public DocumentationType fromString(String string) {

                return Arrays.stream(DocumentationType.values())
                        .filter(documentationType -> documentationType.toString().equals(string)).findAny()
                        .orElseThrow(() -> {
                            throw new RuntimeException("JFXComboBox converter.fromString for DocumentationType failed String: " + string);
                        });
            }
        };
    }

    public static LocalDateStringConverter createLocalDateConverter() {

        final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        return new LocalDateStringConverter() {

            @Override
            public String toString(LocalDate date) {
                return date != null ? DATE_FORMAT.format(date) : "";
            }

            @Override
            public LocalDate fromString(String string) {

                try {
                    return LocalDate.from(LocalDateTime.parse(string, DATE_FORMAT));
                } catch (Exception e) {
                    LOG.warn("LocalDateStringConverter converter.fromString failed String: " + string + " => " + e.getMessage());
                }
                return null;
            }
        };
    }

    public static StringConverter<DocumentationLanguage> createDocumentationLanguageConverter() {

        return new StringConverter<>() {
            @Override
            public String toString(DocumentationLanguage language) {
                return language.toString();
            }

            @Override
            public DocumentationLanguage fromString(String string) {

                return Arrays.stream(DocumentationLanguage.values())
                        .filter(language -> language.toString().equals(string)).findAny()
                        .orElseThrow(() -> {
                            throw new RuntimeException("JFXComboBox converter.fromString for DocumentationLanguage failed String: " + string);
                        });
            }
        };
    }

    public static StringConverter<ApplicationVersionState> createApplicationVersionStateConverter() {

        return new StringConverter<ApplicationVersionState>() {
            @Override
            public String toString(ApplicationVersionState state) {
                return state.toI18nString();
            }

            @Override
            public ApplicationVersionState fromString(String string) {

                return Arrays.stream(ApplicationVersionState.values())
                        .filter(state -> state.toI18nString().equals(string)).findAny()
                        .orElseThrow(() -> {
                            throw new RuntimeException("JFXComboBox converter.fromString for ApplicationVersionState failed String: " + string);
                        });
            }
        };
    }

    public static StringConverter<String> createStringConverter() {

        return new StringConverter<>() {
            @Override
            public String toString(String object) {
                return object.toString();
            }

            @Override
            public String fromString(String string) {
                return string;
            }
        };
    }
}
