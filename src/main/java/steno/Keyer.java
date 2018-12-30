package steno;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.util.*;
import java.util.stream.Collectors;

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
        final List<Set<Arpabet>> possibleArpabets = compressor.decode(compressed);
        int numCombinations = 1;
        for (Set<Arpabet> possibleArpabet : possibleArpabets) {
            numCombinations *= possibleArpabet.size();
        }
        if (numCombinations > 1e5) {
            return MAX_RANK;
        }

        final Set<List<Arpabet>> possibleCombinations = permutations(possibleArpabets);
        final Set<Set<String>> possibleWordSets = possibleCombinations.stream().map(Arpabet::toPossibleWords).collect(Collectors.toSet());
        final Set<String> possibleWords = new HashSet<>();
        possibleWordSets.stream().filter(Objects::nonNull).forEach(possibleWords::addAll);
        final List<String> rankedWordsByLikelihood = NextWordPredictor.sortByLikelihoodDescending(possibleWords, context);
        final int rank = rankedWordsByLikelihood.indexOf(word);
        return rank >= 0 && rank < MAX_RANK ? rank : MAX_RANK; //TODO
    }

    //https://stackoverflow.com/questions/17192796/generate-all-combinations-from-multiple-lists
    public static <T> Set<List<T>> permutations(List<Set<T>> collections) {
        if (collections == null || collections.isEmpty()) {
            return new HashSet<>();
        } else {
            Set<List<T>> res = new HashSet<>();
            permutationsImpl(collections, res, 0, new LinkedList<T>());
            return res;
        }
    }

    private static <T> void permutationsImpl(List<Set<T>> ori, Set<List<T>> res, int d, List<T> current) {
        // if depth equals number of original collections, final reached, add and return
        if (d == ori.size()) {
            res.add(current);
            return;
        }

        // iterate from current collection and copy 'current' element N times, one for each element
        Collection<T> currentCollection = ori.get(d);
        for (T element : currentCollection) {
            List<T> copy = Lists.newLinkedList(current);
            copy.add(element);
            permutationsImpl(ori, res, d + 1, copy);
        }
    }
}
