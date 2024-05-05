package com.usebruno.plugin.bruno.language.injection;

import com.intellij.lang.Language;
import com.intellij.lang.injection.general.Injection;
import com.intellij.lang.injection.general.LanguageInjectionContributor;
import com.intellij.lang.injection.general.SimpleInjection;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class BruInjector implements LanguageInjectionContributor {

    @Override
    public @Nullable Injection getInjection(@NotNull PsiElement context) {
        if (context instanceof TextBlockLanguageInjectionHostMixin textBlock) {
            Language injectableLanguage = textBlock.getInjectableLanguage();
            if (null != injectableLanguage) {
                return new SimpleInjection(injectableLanguage, "", "", null);
            }
        }
        return null;
    }
}