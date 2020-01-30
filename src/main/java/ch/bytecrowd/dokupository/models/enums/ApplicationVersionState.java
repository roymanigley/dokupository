package ch.bytecrowd.dokupository.models.enums;

import ch.bytecrowd.dokupository.utils.I18nUtil;

public enum ApplicationVersionState {

    /*In Arbeit, In Prüfung, Freigegeben*/
    IN_ARBEIT("label.versionstate.inarbeit"),
    IN_PRUEFUNG("label.versionstate.inpruefung"),
    ABGENOMMEN("label.versionstate.abgenommen");

    private String i18nKey;

    ApplicationVersionState(String i18nKey) {
        this.i18nKey = i18nKey;
    }

    public String toI18nString() {

        return I18nUtil.getI18nString(i18nKey);
    }

    public String toI18nString(DocumentationLanguage locale) {

        return I18nUtil.getI18nString(i18nKey, locale);
    }

}
