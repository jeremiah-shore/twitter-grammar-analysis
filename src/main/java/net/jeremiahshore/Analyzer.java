package net.jeremiahshore;

import net.jeremiahshore.model.Tweet;

import java.io.IOException;
import java.util.*;

public class Analyzer {
    private List<Tweet> tweets;

    public Analyzer(List<Tweet> tweets) {
        this.tweets = tweets;
    }

    public void getWordUseCount() throws IOException {
        Map<String, Integer> wordUseMap = new HashMap<>();
        for(Tweet tweet : tweets) {
            String[] words = tweet.getFull_text().split(" ");
            for(String word : words) {
                word = word.toLowerCase();
                if(passesGrammarExclusionChecks(word)) {
                    //todo: determine if mention, count separately
                    wordUseMap.merge(word, 1, (a, b) -> a + b);
                }
            }
        }
        wordUseMap = sortMap(wordUseMap);
        wordUseMap.forEach((key, value) -> {
            System.out.printf("%s: %s%n", key, value);
        });
    }

    private boolean passesGrammarExclusionChecks(String word) throws IOException {
        return !Grammar.isAnyGrammarFragmentType(word)
                && !word.equals("");
    }

    private Map<String, Integer> sortMap(Map<String, Integer> map) {
        List<Map.Entry<String, Integer>> list = new LinkedList<>(map.entrySet());
        Collections.sort(list, Comparator.comparing(o -> (o.getValue())));
        Map<String, Integer> sortedMap = new LinkedHashMap<>();
        for(Map.Entry<String, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }
}
