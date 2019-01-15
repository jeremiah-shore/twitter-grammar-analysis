package net.jeremiahshore;

import java.io.IOException;

public enum Grammar {
    ARTICLES("articles.txt"),
    CONJUNCTIONS("conjunctions.txt"),
    DEMONSTRATIVES("demonstratives.txt"),
    PREPOSITIONS("prepositions.txt"),
    PRONOUNS("pronouns.txt"),
    CONTRACTIONS("contractions.txt"),
    VERB("verbs.txt"),
    PUNCTUATION("punctuation.txt"),
    DETERMINERS("determiners.txt"),
    ADVERBS("adverbs.txt");
    //TODO: if needed, implement: multi-word subordinating conjunctions, conjunctive adverbs, possessive/reflexive/intensive pronouns, indefinite pronouns, gendered contractions

    private static final String ROOT_FOLDER = "grammar/";
    private final String filename;

    Grammar(String filename) {
        this.filename = ROOT_FOLDER + filename;
    }

    public static boolean isGrammarFragment(Grammar type, String word) throws IOException {
        //todo: ignore case
        return DataHelper.readAllLinesFromFile(type.filename).contains(word);
    }

    public static boolean isAnyGrammarFragmentType(String word) throws IOException {
        boolean result = false;
        for(Grammar type : Grammar.values()) {
            result = result || isGrammarFragment(type, word);
        }
        return  result;
    }
}
