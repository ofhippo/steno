import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordToPhonemes {
    final private Map<String, List<String>> dictionary = new HashMap<>(133030);

    public WordToPhonemes() {
        loadDictionary();
    }

    public List<String> toPhonemes(String word) {
        return dictionary.get(word.toUpperCase());
    }

    private void loadDictionary() {
        try{
            final BufferedReader buf = new BufferedReader(new FileReader("/Users/drufener/steno/src/main/resources/text_to_phonemes.txt"));
            String lineJustRead;

            while (true) {
                lineJustRead = buf.readLine();
                if (lineJustRead == null) {
                    break;
                } else {
                    final String[] wordAndPhonemes = lineJustRead.split("\t");
                    dictionary.put(wordAndPhonemes[0], Arrays.asList(wordAndPhonemes[1].split(" ")));
                }
            }

            buf.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
