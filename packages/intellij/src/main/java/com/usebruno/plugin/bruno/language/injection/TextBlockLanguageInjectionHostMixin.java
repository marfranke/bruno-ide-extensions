package com.usebruno.plugin.bruno.language.injection;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.lang.Language;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.LiteralTextEscaper;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLanguageInjectionHost;
import com.intellij.psi.impl.source.tree.LeafPsiElement;
import com.usebruno.plugin.bruno.language.psi.BruTextTag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class TextBlockLanguageInjectionHostMixin extends ASTWrapperPsiElement implements PsiLanguageInjectionHost {
    protected TextBlockLanguageInjectionHostMixin(ASTNode node) {
        super(node);
    }

    @Nullable
    public Language getInjectableLanguage() {
        PsiElement parent = getParent();
        if (parent instanceof BruTextTag) {
            PsiElement tagElement = parent.getFirstChild();
            if (tagElement instanceof LeafPsiElement) {
                return switch (tagElement.getText()) {
                    case "body:graphql" -> Language.findLanguageByID("GraphQL");
                    case "script:pre-request", "script:post-response", "tests" ->
                            Language.findLanguageByID("JavaScript");
                    case "body:json", "body:graphql:vars" -> Language.findLanguageByID("JSON");
                    case "docs" -> Language.findLanguageByID("Markdown");
                    case "body:xml" -> Language.findLanguageByID("XML");
                    default -> Language.findLanguageByID("TEXT");
                };
            }
        }
        return null;
    }

    @Override
    public boolean isValidHost() {
        return true;
    }

    @Override
    public PsiLanguageInjectionHost updateText(@NotNull String text) {
        // TextBlockManipulator handles updates
        return this;
    }

    @NotNull
    @Override
    public LiteralTextEscaper<TextBlockLanguageInjectionHostMixin> createLiteralTextEscaper() {
        return new LiteralTextEscaper<>(this) {
            @Override
            public boolean decode(@NotNull final TextRange rangeInsideHost, @NotNull StringBuilder outChars) {
                outChars.append(myHost.getText(), rangeInsideHost.getStartOffset(), rangeInsideHost.getEndOffset());
                return true;
            }

            @Override
            public int getOffsetInHost(int offsetInDecoded, @NotNull final TextRange rangeInsideHost) {
                int offset = offsetInDecoded + rangeInsideHost.getStartOffset();
                if (offset < rangeInsideHost.getStartOffset()) offset = rangeInsideHost.getStartOffset();
                if (offset > rangeInsideHost.getEndOffset()) offset = rangeInsideHost.getEndOffset();
                return offset;
            }

            @Override
            public boolean isOneLine() {
                return false;
            }
        };
    }
}
