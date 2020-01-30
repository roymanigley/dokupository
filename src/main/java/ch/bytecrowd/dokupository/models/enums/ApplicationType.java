package ch.bytecrowd.dokupository.models.enums;

import ch.bytecrowd.dokupository.utils.I18nUtil;

public enum ApplicationType {

    /*In Arbeit, In Prüfung, Freigegeben*/
    MODUL("label.apptype.modul"),
    PROZESS("label.apptype.prozess");

    private String i18nKey;

    ApplicationType(String i18nKey) {
        this.i18nKey = i18nKey;
    }

    public String toI18nString() {

        return I18nUtil.getI18nString(i18nKey);
    }

    public String toI18nString(DocumentationLanguage locale) {

        return I18nUtil.getI18nString(i18nKey, locale);
    }

}
