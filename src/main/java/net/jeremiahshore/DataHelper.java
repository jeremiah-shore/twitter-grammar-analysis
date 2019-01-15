package net.jeremiahshore;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataHelper {

    public static List<String> readAllLinesFromFile(String filename) throws IOException {
        String filepath = Grammar.class.getClassLoader().getResource(filename).getPath();
        BufferedReader br = new BufferedReader(new FileReader(new File(filepath)));

        List<String> wordList = new ArrayList<>();
        String word;
        while((word = br.readLine()) != null) {
            wordList.add(word);
        }

        return wordList;
    }
}
