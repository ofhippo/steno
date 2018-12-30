package steno;

import java.util.*;
import java.util.stream.Collectors;

public class ArpabetCompressor {
    private final Map<Arpabet, Enum> scheme;
    private Map<List<Enum>, Set<String>> compressedToPossibleWords;

    public ArpabetCompressor(Map<Arpabet, Enum> scheme) {
        this.scheme = scheme;
    }

    public List<Enum> encode(List<Arpabet> arpabets) {
        return arpabets.stream().map(scheme::get).collect(Collectors.toList());
    }

    public Set<String> decode(List<Enum> compressed) {
        if (compressedToPossibleWords == null) {
            loadCompressedToPossibleWords();
        }
        return compressedToPossibleWords.get(compressed);
    }

    private void loadCompressedToPossibleWords() {
        compressedToPossibleWords = new HashMap<>();
        final Map<String, List<Arpabet>> wordsToArpabets = Arpabet.getDictionary();
        for (Map.Entry<String, List<Arpabet>> wordToArpabets : wordsToArpabets.entrySet()) {
            final String word = wordToArpabets.getKey();
            final List<Arpabet> arpabets = wordToArpabets.getValue();
            final List<Enum> compressed = encode(arpabets);
            final Set<String> possibleWords = compressedToPossibleWords.computeIfAbsent(compressed, k -> new HashSet<>());
            possibleWords.add(word);
        }
    }
}
