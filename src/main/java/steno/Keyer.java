package steno;

import com.google.common.collect.ImmutableList;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Keyer {
    private final ArpabetCompressor compressor;

    public Keyer(ArpabetCompressor compressor) {
        this.compressor = compressor;
    }

    public PerformanceStats scoreDictionaryWithoutContext() {
        final PerformanceStats performanceStats = new PerformanceStats();
        final Object[] entries = WordFrequency.DICTIONARY.entrySet().toArray();
        for (int i = 0; i < entries.length; i++) {
            if (i % 1000 == 0) {
                System.out.println(String.valueOf(i / (double) entries.length));
            }
            Map.Entry<String, Double> wordAndFrequency = (Map.Entry<String, Double>) entries[i];
            String word = wordAndFrequency.getKey();
            List<Arpabet> trueArpabets = Arpabet.fromWord(word);
            int strokes = -1;
            if (trueArpabets == null) {
                word = word.replaceAll("'", "");
                trueArpabets = Arpabet.fromWord(word);
                if (trueArpabets == null) {
                    strokes = word.length() * 5; //TODO
                }
            }
            int rank = 100;
            if (strokes > 0) {
                final List<Enum> compressed = compressor.encode(trueArpabets);
                final Set<String> possibleWords = compressor.decode(compressed);
                final List<String> rankedWordsByLikelihood = NextWordPredictor.sortByLikelihoodDescending(possibleWords, ImmutableList.of());
                rank = rankedWordsByLikelihood.indexOf(word);
                if (rank < 0) {
                    throw new RuntimeException("Missing word " + word);
                }
                strokes = getStrokes(compressed, rank);
            }
            performanceStats.add(strokes, rank, wordAndFrequency.getValue(), word);
        }
        return performanceStats;
    }

    public PerformanceStats scoreText(String text) {
        final PerformanceStats performanceStats = new PerformanceStats();
        final List<String> words = Arrays.asList(scrub(text).toLowerCase().split("\\s+"));
        for (int i = 0; i < words.size(); i++) {
            String word = words.get(i);
            final List<String> context = words.subList(Math.max(0, i - 4), i);
            List<Arpabet> trueArpabets = Arpabet.fromWord(word);

            int strokes = -1;
            if (trueArpabets == null) {
                word = word.replaceAll("'", "");
                trueArpabets = Arpabet.fromWord(word);
                if (trueArpabets == null) {
                    strokes = word.length() * 5; //TODO
                }
            }
            int rank = 100;
            if (strokes < 0) {
                final List<Enum> compressed = compressor.encode(trueArpabets);
                final Set<String> possibleWords = compressor.decode(compressed);
                final List<String> rankedWordsByLikelihood = NextWordPredictor.sortByLikelihoodDescending(possibleWords, context);
                rank = rankedWordsByLikelihood.indexOf(word);
                if (rank < 0) {
                    throw new RuntimeException("Missing word " + word);
                }
                strokes = getStrokes(compressed, rank);
            }
            performanceStats.add(strokes, rank, 1, word);
        }
        return performanceStats;
    }

    private String scrub(String text) {
        //TODO: Smarter handling of ' and -
        return text.replaceAll("-", " ")
                .replaceAll("’", "'")
                .replaceAll("[^a-zA-Z \n']", "");
    }

    private static int getStrokes(List<Enum> compressed, int rank) {
        return compressed.size() + 1 + (int) Math.ceil(rank / 3.0); // compressed + space + (1 of 3 or new 3)
    }
}
