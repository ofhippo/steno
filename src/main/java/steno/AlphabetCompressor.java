package steno;

import java.util.*;
import java.util.stream.Collectors;

public class AlphabetCompressor implements Compressor {
    private Map<Alphabet, Enum> scheme;
    private Map<List<Enum>, Set<String>> compressedToPossibleWords;

    public AlphabetCompressor(Map<Alphabet, Enum> scheme) {
        this.scheme = scheme;
    }

    @Override
    public List<Enum> encode(String word) {
        return encode(Alphabet.fromWord(word.replaceAll("[^a-z]", "")));
    }

    private List<Enum> encode (List<Alphabet> letters) {
        return letters.stream().map(scheme::get).collect(Collectors.toList());
    }

    @Override
    public Set<String> decode(List<Enum> compressed) {
        if (compressedToPossibleWords == null) {
            loadCompressedToPossibleWords();
        }
        return compressedToPossibleWords.get(compressed);
    }

    private void loadCompressedToPossibleWords() {
        compressedToPossibleWords = new HashMap<>();
        final Set<String> wordsDictionary = Arpabet.getDictionary().keySet();
        for (String word : wordsDictionary) {
            final List<Alphabet> letters = Alphabet.fromWord(word.replaceAll("[^a-z]", ""));
            final List<Enum> compressed = encode(letters);
            final Set<String> possibleWords = compressedToPossibleWords.computeIfAbsent(compressed, k -> new HashSet<>());
            possibleWords.add(word);
        }
    }
}
