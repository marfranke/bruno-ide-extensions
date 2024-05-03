package com.usebruno.plugin.bruno.language;

import com.intellij.lang.Language;

public class BruLanguage extends Language {

    public static final BruLanguage INSTANCE = new BruLanguage();

    private BruLanguage() {
        super("Bru");
    }

}