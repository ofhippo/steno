package steno;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public enum Arpabet {
    HH, B, D, DH, F, G, K, L, M, N, P, R, S, UH, T, SH, V, W, Y, Z, IH, AA, UW, EH, AE, CH, AH, OW, OY, ER, AO, ZH, IY, EY, TH, AW, AY, NG, JH;

    private static Map<String, List<Arpabet>> dictionary = loadDictionary();

    public static List<Arpabet> fromText(String word) {
        //TODO: If not in dictionary, do something like http://www.speech.cs.cmu.edu/tools/lextool.html
        return dictionary.get(word.toUpperCase());
    }

    private static Map<String, List<Arpabet>> loadDictionary() {
        dictionary = new HashMap<>(133030);
        try{
            // http://www.speech.cs.cmu.edu/cgi-bin/cmudict
            final BufferedReader buf = new BufferedReader(new FileReader("/Users/drufener/steno/src/main/resources/text_to_phonemes.txt"));
            String lineJustRead;

            while (true) {
                lineJustRead = buf.readLine();
                if (lineJustRead == null) {
                    break;
                } else {
                    final String[] wordAndPhonemes = lineJustRead.split("\t");
                    final String word = wordAndPhonemes[0];
                    final List<Arpabet> phonemes = Arrays.stream(wordAndPhonemes[1].split(" ")).map(Arpabet::valueOf).collect(Collectors.toList());
                    dictionary.put(word, phonemes);
                }
            }

            buf.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dictionary;
    }
}
