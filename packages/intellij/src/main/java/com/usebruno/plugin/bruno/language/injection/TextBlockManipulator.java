package com.usebruno.plugin.bruno.language.injection;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.AbstractElementManipulator;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import com.intellij.util.IncorrectOperationException;
import com.usebruno.plugin.bruno.language.BruFileType;
import com.usebruno.plugin.bruno.language.psi.BruTextBlock;
import com.usebruno.plugin.bruno.language.psi.impl.BruTagImpl;
import com.usebruno.plugin.bruno.language.psi.impl.BruTextBlockImpl;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class TextBlockManipulator extends AbstractElementManipulator<BruTextBlockImpl> {

    @Override
    public BruTextBlockImpl handleContentChange(@NotNull BruTextBlockImpl element, @NotNull TextRange range, String newContent) throws IncorrectOperationException {
        assert new TextRange(0, element.getTextLength()).contains(range);

        final String originalContent = element.getText();
        final TextRange elementRange = getRangeInElement(element);
        final String replacement =
                originalContent.substring(elementRange.getStartOffset(), range.getStartOffset()) +
                        newContent +
                        originalContent.substring(range.getEndOffset(), elementRange.getEndOffset());
        final PsiFileFactory psiFileFactory = PsiFileFactory.getInstance(element.getProject());
        PsiFile file = psiFileFactory.createFileFromText(
                "dummy." + BruFileType.INSTANCE.getDefaultExtension(),
                BruFileType.INSTANCE,
                "docs {\n" + replacement + "}"
        );
        BruTextBlock textBlock = Objects.requireNonNull(((BruTagImpl) file.getFirstChild()).getTextTag()).getTextBlock();
        return (BruTextBlockImpl) element.replace(textBlock);
    }

    @NotNull
    @Override
    public TextRange getRangeInElement(@NotNull BruTextBlockImpl element) {
        final String content = element.getText();
        return new TextRange(0, content.length());
    }
}