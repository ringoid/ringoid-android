package com.hbb20.model;

/**
 * Update every time new language is supported #languageSupport
 */
//add an entry for your language in attrs.xml's <attr name="language" format="enum"> enum.
//add here so that language can be set programmatically
public enum Language {
    ARABIC("ar"),
    BENGALI("bn"),
    CHINESE_SIMPLIFIED("zh"),
    CHINESE_TRADITIONAL("zh"),
    DUTCH("nl"),
    ENGLISH("en"),
    FARSI("fa"),
    FRENCH("fr"),
    GERMAN("de"),
    GUJARATI("gu"),
    HEBREW("iw"),
    HINDI("hi"),
    INDONESIA("in"),
    ITALIAN("it"),
    JAPANESE("ja"),
    KOREAN("ko"),
    POLISH("pl"),
    PORTUGUESE("pt"),
    PUNJABI("pa"),
    RUSSIAN("ru"),
    SLOVAK("sk"),
    SPANISH("es"),
    SWEDISH("sv"),
    TURKISH("tr"),
    UKRAINIAN("uk");

    String code;

    Language(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
