package com.usebruno.plugin.bruno.language;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.HighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import com.usebruno.plugin.bruno.language.psi.BruTypes;
import org.jetbrains.annotations.NotNull;

import static com.intellij.openapi.editor.DefaultLanguageHighlighterColors.*;
import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

public class BruSyntaxHighlighter extends SyntaxHighlighterBase {

    public static final TextAttributesKey TAG_ASSERT = createTextAttributesKey("BRU_TAG_ASSERT", KEYWORD);
    public static final TextAttributesKey TAG_AUTH = createTextAttributesKey("BRU_TAG_AUTH", KEYWORD);
    public static final TextAttributesKey TAG_BODY = createTextAttributesKey("BRU_TAG_BODY", KEYWORD);
    public static final TextAttributesKey TAG_DOCS = createTextAttributesKey("BRU_TAG_DOCS", KEYWORD);
    public static final TextAttributesKey TAG_HEADERS = createTextAttributesKey("BRU_TAG_HEADERS", KEYWORD);
    public static final TextAttributesKey TAG_HTTP = createTextAttributesKey("BRU_TAG_HTTP", KEYWORD);
    public static final TextAttributesKey TAG_META = createTextAttributesKey("BRU_TAG_META", KEYWORD);
    public static final TextAttributesKey TAG_QUERY = createTextAttributesKey("BRU_TAG_QUERY", KEYWORD);
    public static final TextAttributesKey TAG_SCRIPT = createTextAttributesKey("BRU_TAG_SCRIPT", KEYWORD);
    public static final TextAttributesKey TAG_TEST = createTextAttributesKey("BRU_TAG_TEST", KEYWORD);
    public static final TextAttributesKey TAG_VARS = createTextAttributesKey("BRU_TAG_VARS", KEYWORD);
    public static final TextAttributesKey BLOCK_BRACKETS = createTextAttributesKey("BRU_BLOCK_BRACKETS", BRACKETS);
    public static final TextAttributesKey DICTIONARY_KEY = createTextAttributesKey("BRU_DICTIONARY_KEY", INSTANCE_FIELD);
    public static final TextAttributesKey SEPARATOR = createTextAttributesKey("BRU_SEPARATOR", OPERATION_SIGN);
    public static final TextAttributesKey DICTIONARY_VALUE = createTextAttributesKey("BRU_DICTIONARY_VALUE", STRING);
    public static final TextAttributesKey TEXT_LINE = createTextAttributesKey("BRU_TEXT_LINE", STRING);
    public static final TextAttributesKey BAD_CHARACTER = createTextAttributesKey("BRU_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER);

    @NotNull
    @Override
    public Lexer getHighlightingLexer() {
        return new BruLexerAdapter();
    }

    @Override
    public TextAttributesKey @NotNull [] getTokenHighlights(IElementType tokenType) {
        if (tokenType.equals(BruTypes.TAG_ASSERT)) {
            return new TextAttributesKey[]{TAG_ASSERT};
        }
        if (tokenType.equals(BruTypes.TAG_AUTH)) {
            return new TextAttributesKey[]{TAG_AUTH};
        }
        if (tokenType.equals(BruTypes.TAG_BODY_DICTIONARY) || tokenType.equals(BruTypes.TAG_BODY_TEXT)) {
            return new TextAttributesKey[]{TAG_BODY};
        }
        if (tokenType.equals(BruTypes.TAG_DOCS)) {
            return new TextAttributesKey[]{TAG_DOCS};
        }
        if (tokenType.equals(BruTypes.TAG_HEADERS)) {
            return new TextAttributesKey[]{TAG_HEADERS};
        }
        if (tokenType.equals(BruTypes.TAG_HTTP)) {
            return new TextAttributesKey[]{TAG_HTTP};
        }
        if (tokenType.equals(BruTypes.TAG_META)) {
            return new TextAttributesKey[]{TAG_META};
        }
        if (tokenType.equals(BruTypes.TAG_QUERY)) {
            return new TextAttributesKey[]{TAG_QUERY};
        }
        if (tokenType.equals(BruTypes.TAG_SCRIPT)) {
            return new TextAttributesKey[]{TAG_SCRIPT};
        }
        if (tokenType.equals(BruTypes.TAG_TEST)) {
            return new TextAttributesKey[]{TAG_TEST};
        }
        if (tokenType.equals(BruTypes.TAG_VARS)) {
            return new TextAttributesKey[]{TAG_VARS};
        }
        if (tokenType.equals(BruTypes.BLOCK_BEGIN) || tokenType.equals(BruTypes.BLOCK_END)) {
            return new TextAttributesKey[]{BLOCK_BRACKETS};
        }
        if (tokenType.equals(BruTypes.DICTIONARY_KEY)) {
            return new TextAttributesKey[]{DICTIONARY_KEY};
        }
        if (tokenType.equals(BruTypes.SEPARATOR)) {
            return new TextAttributesKey[]{SEPARATOR};
        }
        if (tokenType.equals(BruTypes.DICTIONARY_VALUE)) {
            return new TextAttributesKey[]{DICTIONARY_VALUE};
        }
        if (tokenType.equals(BruTypes.TEXT_LINE)) {
            return new TextAttributesKey[]{TEXT_LINE};
        }
        if (tokenType.equals(TokenType.BAD_CHARACTER)) {
            return new TextAttributesKey[]{BAD_CHARACTER};
        }
        return new TextAttributesKey[0];
    }

}