package ch.bytecrowd.dokupository.utils;

import ch.bytecrowd.dokupository.models.enums.DocumentationLanguage;

import java.util.Locale;
import java.util.ResourceBundle;

public class I18nUtil {


    public static final ResourceBundle i18n_BUNDLE = ResourceBundle.getBundle("i18n/labels", Locale.GERMAN);
    public static final ResourceBundle i18n_BUNDLE_FR = ResourceBundle.getBundle("i18n/labels", Locale.FRENCH);

    private I18nUtil() {
    }

    public static String getI18nString(String key) {

        DocumentationLanguage defaultLang = (Locale.getDefault().toLanguageTag().toUpperCase().contains("FR") ? DocumentationLanguage.FR: DocumentationLanguage.DE);
        return getI18nString(key, defaultLang);
    }


    public static String getI18nString(String key, DocumentationLanguage language) {

        final ResourceBundle bundle = language.equals(DocumentationLanguage.FR) ? i18n_BUNDLE_FR : i18n_BUNDLE;
        if (bundle.containsKey(key))
            return bundle.getString(key);
        System.err.println("i18n key not found: " + key);
        return "??? " + key + " ???";
    }
}
