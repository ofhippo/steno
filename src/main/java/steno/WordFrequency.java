package steno;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class WordFrequency {
    private static Long SUM_OF_ALL_FREQUENCY_COUNTS = 588124220187L; // awk -F '\t' '{print $2}' src/main/resources/all_words_by_freq.txt | awk '{s+=$1} END {print s}'\
    public static Map<String, Double> DICTIONARY = loadDictionary();

    private static Map<String, Double> loadDictionary() {
        Map<String, Double> dictionary = new HashMap<>(133030);
        try{
            // http://www.speech.cs.cmu.edu/cgi-bin/cmudict
            final BufferedReader buf = new BufferedReader(new FileReader("/Users/drufener/steno/src/main/resources/all_words_by_freq.txt"));
            String lineJustRead;

            while (true) {
                lineJustRead = buf.readLine();
                if (lineJustRead == null) {
                    break;
                } else {
                    final String[] wordAndFrequencyCount = lineJustRead.split("\t");
                    final String word = wordAndFrequencyCount[0];
                    final double frequencyCount = Long.valueOf(wordAndFrequencyCount[1]);
                    dictionary.put(word, frequencyCount / SUM_OF_ALL_FREQUENCY_COUNTS);
                }
            }

            buf.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dictionary;
    }
}
