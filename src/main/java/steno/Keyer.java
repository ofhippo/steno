package steno;

import com.google.common.collect.ImmutableList;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Keyer {
    public static final int MAX_RANK = 20;
    private final ArpabetCompressor compressor;

    public Keyer(ArpabetCompressor compressor) {
        this.compressor = compressor;
    }

    public double scoreDictionaryWithoutContext() {
        double score = 0;
        final Object[] entries = WordFrequency.DICTIONARY.entrySet().toArray();
        for (int i = 0; i < entries.length; i++) {
            if (i % 100 == 0) {
                System.out.println(String.valueOf(i / (double) entries.length));
            }
            Map.Entry<String, Double> wordAndFrequency = (Map.Entry<String, Double>) entries[i];
            final String word = wordAndFrequency.getKey();
            final int rank = predictionRankAfterEncodingAndDecoding(word, ImmutableList.of());
            score += wordAndFrequency.getValue() * rank;
        }
        return score;
    }

    public double scoreText(String text) {
        double score = 0;
        List<String> words = Arrays.asList(text.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+"));
        for (int i = 0; i < words.size(); i++) {
            final String word = words.get(i);
            final List<String> context = words.subList(Math.max(0, i - 4), i);
            final int rank = predictionRankAfterEncodingAndDecoding(word, context);
            score += rank;
        }
        return score / words.size();
    }

    private int predictionRankAfterEncodingAndDecoding(String word, List<String> context) {
        final List<Arpabet> trueArpabets = Arpabet.fromWord(word);
        if (trueArpabets == null) {
            return 0; //TODO
        }
        final List<Enum> compressed = compressor.encode(trueArpabets);
        final Set<String> possibleWords = compressor.decode(compressed);
        final List<String> rankedWordsByLikelihood = NextWordPredictor.sortByLikelihoodDescending(possibleWords, context);
        final int rank = rankedWordsByLikelihood.indexOf(word);
        return rank >= 0 && rank < MAX_RANK ? rank : MAX_RANK; //TODO
    }
}
