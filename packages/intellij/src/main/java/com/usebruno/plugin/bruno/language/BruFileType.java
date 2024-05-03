package com.usebruno.plugin.bruno.language;

import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class BruFileType extends LanguageFileType {
    public static final BruFileType INSTANCE = new BruFileType();
    public static final String DEFAULT_EXTENSION = "bru";

    private BruFileType() {
        super(BruLanguage.INSTANCE);
    }


    @NotNull
    @Override
    public String getName() {
        return "Bru File";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Bru request file";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return DEFAULT_EXTENSION;
    }

    @Override
    public Icon getIcon() {
        return BruIcons.FILE_TYPE;
    }
}


