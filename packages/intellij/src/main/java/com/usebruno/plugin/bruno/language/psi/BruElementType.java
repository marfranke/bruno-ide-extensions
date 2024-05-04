package com.usebruno.plugin.bruno.language.psi;

import com.intellij.psi.tree.IElementType;
import com.usebruno.plugin.bruno.language.BruLanguage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class BruElementType extends IElementType {

    public BruElementType(@NotNull @NonNls String debugName) {
        super(debugName, BruLanguage.INSTANCE);
    }

}