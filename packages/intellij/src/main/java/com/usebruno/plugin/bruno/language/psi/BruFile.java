package com.usebruno.plugin.bruno.language.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import com.usebruno.plugin.bruno.language.BruFileType;
import com.usebruno.plugin.bruno.language.BruLanguage;
import org.jetbrains.annotations.NotNull;

public class BruFile extends PsiFileBase {

    public BruFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, BruLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return BruFileType.INSTANCE;
    }

    @Override
    public String toString() {
        return "Bru File";
    }

}