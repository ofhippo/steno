package steno;

import com.google.common.collect.ImmutableList;

import java.util.*;
import java.util.stream.Collectors;

public class Keyer {
    private final ArpabetCompressor compressor;

    public Keyer(ArpabetCompressor compressor) {
        this.compressor = compressor;
    }

    public double scoreDictionaryWithoutContext() {
        double score = 0;
        for (Map.Entry<String, Double> wordAndFrequency : WordFrequency.DICTIONARY.entrySet()) {
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
            System.out.println("Could not find word: " + word);
            return 0; //TODO
        }
        final List<Enum> compressed = compressor.encode(trueArpabets);
        final List<Set<Arpabet>> possibleArpabets = compressor.decode(compressed);
        final Set<List<Arpabet>> possibleCombinations = getAllPossibleCombinations(possibleArpabets);
        final Set<Set<String>> possibleWordSets = possibleCombinations.stream().map(Arpabet::toPossibleWords).collect(Collectors.toSet());
        final Set<String> possibleWords = new HashSet<>();
        possibleWordSets.forEach(possibleWords::addAll);
        final List<String> rankedWordsByLikelihood = NextWordPredictor.sortByLikelihoodDescending(possibleWords, context);
        return rankedWordsByLikelihood.indexOf(word);
    }

    Set<List<Arpabet>> getAllPossibleCombinations(final List<Set<Arpabet>> possibleArpabets) {
        return getAllPossibleCombinations(new ArrayList<>(possibleArpabets), new HashSet<>());
    }

    private Set<List<Arpabet>> getAllPossibleCombinations(final List<Set<Arpabet>> possibleArpabets, final Set<List<Arpabet>> priorResults) {
        if (possibleArpabets.isEmpty()) {
            return priorResults;
        } else {
            final Set<Arpabet> firstArpabets = possibleArpabets.remove(0);
            final Set<List<Arpabet>> newResults = new HashSet<>();
            if (priorResults.isEmpty()) {
                newResults.addAll(firstArpabets.stream().map(arpabet -> {
                    final List<Arpabet> list = new ArrayList<>();
                    list.add(arpabet);
                    return list;
                }).collect(Collectors.toList()));
            } else {
                for (Arpabet arpabet : firstArpabets) {
                    newResults.addAll(priorResults.stream().map(priorResult -> {
                        final List<Arpabet> copyOfPriorResult = new ArrayList<>(priorResult);
                        copyOfPriorResult.add(arpabet);
                        return copyOfPriorResult;
                    }).collect(Collectors.toList()));
                }
            }
            return getAllPossibleCombinations(possibleArpabets, newResults);
        }
    }
}
