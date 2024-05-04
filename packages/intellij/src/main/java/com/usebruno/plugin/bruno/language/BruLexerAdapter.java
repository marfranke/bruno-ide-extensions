package com.usebruno.plugin.bruno.language;

import com.intellij.lexer.FlexAdapter;

public class BruLexerAdapter extends FlexAdapter {

    public BruLexerAdapter() {
        super(new BruLexer(null));
    }

}