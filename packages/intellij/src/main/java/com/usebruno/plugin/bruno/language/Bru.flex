package com.usebruno.plugin.bruno.language;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.TokenType;
import com.usebruno.plugin.bruno.language.psi.BruTypes;

%%

%class BruLexer
%implements FlexLexer
%unicode
%function advance
%type IElementType
%eof{  return;
%eof}


BEGIN_OF_BLOCK=\{
END_OF_BLOCK=\R}

NON_CLRF_WHITESPACE=[ \t\f]
WHITESPACE=\s
CRLF=\R

SEPARATOR=:

KEY_BEGIN_CHAR=[^\r\n \t\f:]
KEY_CHAR=[^\r\n:]
VALUE_CHAR=[^\r\n]

// Dictionary Block Tags
TAGS_META=meta
TAGS_HTTP=get|post|put|delete|patch|options|head|connect|trace
TAGS_HEADERS=headers
TAGS_QUERY=query
TAGS_VARS=vars|vars:pre-request|vars:post-request
TAGS_AUTH=auth|auth:awsv4|auth:basic|auth:bearer|auth:digest|auth:oauth2
TAGS_ASSERT=assert
TAGS_BODY_DICTIONARY=body:form-urlencoded|body:multipart-form

// Text Block Tags
TAGS_BODY_TEXT=body|body:json|body:text|body:xml|body:sparql|body:graphql|body:graphql:vars
TAGS_SCRIPT=script:pre-request|script:post-response
TAGS_TEST=tests
TAGS_DOCS=docs

%state YYINITIAL
%state WAITING_FOR_DICTIONARY
%state WAITING_FOR_DICTIONARY_KEY
%state WAITING_FOR_DICTIONARY_SEPARATOR
%state WAITING_FOR_DICTIONARY_VALUE
%state WAITING_FOR_TEXT
%state WAITING_FOR_TEXT_LINE

%%

<YYINITIAL> {TAGS_META}                                     { yybegin(WAITING_FOR_DICTIONARY); return BruTypes.TAG_META; }
<YYINITIAL> {TAGS_HTTP}                                     { yybegin(WAITING_FOR_DICTIONARY); return BruTypes.TAG_HTTP; }
<YYINITIAL> {TAGS_HEADERS}                                  { yybegin(WAITING_FOR_DICTIONARY); return BruTypes.TAG_HEADERS; }
<YYINITIAL> {TAGS_QUERY}                                    { yybegin(WAITING_FOR_DICTIONARY); return BruTypes.TAG_QUERY; }
<YYINITIAL> {TAGS_VARS}                                     { yybegin(WAITING_FOR_DICTIONARY); return BruTypes.TAG_VARS; }
<YYINITIAL> {TAGS_AUTH}                                     { yybegin(WAITING_FOR_DICTIONARY); return BruTypes.TAG_AUTH; }
<YYINITIAL> {TAGS_ASSERT}                                   { yybegin(WAITING_FOR_DICTIONARY); return BruTypes.TAG_ASSERT; }
<YYINITIAL> {TAGS_BODY_DICTIONARY}                          { yybegin(WAITING_FOR_DICTIONARY); return BruTypes.TAG_BODY_DICTIONARY; }

<YYINITIAL> {TAGS_BODY_TEXT}                                { yybegin(WAITING_FOR_TEXT); return BruTypes.TAG_BODY_TEXT; }
<YYINITIAL> {TAGS_SCRIPT}                                   { yybegin(WAITING_FOR_TEXT); return BruTypes.TAG_SCRIPT; }
<YYINITIAL> {TAGS_TEST}                                     { yybegin(WAITING_FOR_TEXT); return BruTypes.TAG_TEST; }
<YYINITIAL> {TAGS_DOCS}                                     { yybegin(WAITING_FOR_TEXT); return BruTypes.TAG_DOCS; }

<YYINITIAL> {WHITESPACE}+                                   { yybegin(YYINITIAL); return TokenType.WHITE_SPACE; }

<WAITING_FOR_DICTIONARY> {BEGIN_OF_BLOCK}                   { yybegin(WAITING_FOR_DICTIONARY_KEY); return BruTypes.BLOCK_BEGIN; }
<WAITING_FOR_DICTIONARY> {NON_CLRF_WHITESPACE}+             { yybegin(WAITING_FOR_DICTIONARY); return TokenType.WHITE_SPACE; }

<WAITING_FOR_DICTIONARY_KEY> {END_OF_BLOCK}                 { yybegin(YYINITIAL); return BruTypes.BLOCK_END; }
<WAITING_FOR_DICTIONARY_KEY> {NON_CLRF_WHITESPACE}+         { yybegin(WAITING_FOR_DICTIONARY_KEY); return TokenType.WHITE_SPACE; }
<WAITING_FOR_DICTIONARY_KEY> {CRLF}                         { yybegin(WAITING_FOR_DICTIONARY_KEY); return TokenType.WHITE_SPACE; }
<WAITING_FOR_DICTIONARY_KEY> {KEY_BEGIN_CHAR}{KEY_CHAR}*    { yybegin(WAITING_FOR_DICTIONARY_SEPARATOR); return BruTypes.DICTIONARY_KEY; }

<WAITING_FOR_DICTIONARY_SEPARATOR> {NON_CLRF_WHITESPACE}*   { yybegin(WAITING_FOR_DICTIONARY_SEPARATOR); return TokenType.WHITE_SPACE; }
<WAITING_FOR_DICTIONARY_SEPARATOR> {SEPARATOR}              { yybegin(WAITING_FOR_DICTIONARY_VALUE); return BruTypes.SEPARATOR; }

<WAITING_FOR_DICTIONARY_VALUE> {VALUE_CHAR}*                { yybegin(WAITING_FOR_DICTIONARY_KEY); return BruTypes.DICTIONARY_VALUE; }

<WAITING_FOR_TEXT> {BEGIN_OF_BLOCK}                         { yybegin(WAITING_FOR_TEXT_LINE); return BruTypes.BLOCK_BEGIN; }
<WAITING_FOR_TEXT> {NON_CLRF_WHITESPACE}+                   { yybegin(WAITING_FOR_TEXT); return TokenType.WHITE_SPACE; }

<WAITING_FOR_TEXT_LINE> {END_OF_BLOCK}                      { yybegin(YYINITIAL); return BruTypes.BLOCK_END; }
<WAITING_FOR_TEXT_LINE> {VALUE_CHAR}*                       { yybegin(WAITING_FOR_TEXT_LINE); return BruTypes.TEXT_LINE; }
<WAITING_FOR_TEXT_LINE> {CRLF}                              { yybegin(WAITING_FOR_TEXT_LINE); return TokenType.WHITE_SPACE; }

[^]                                                         { return TokenType.BAD_CHARACTER; }