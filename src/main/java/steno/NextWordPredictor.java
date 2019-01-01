package steno;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import edu.berkeley.nlp.lm.NgramLanguageModel;
import edu.berkeley.nlp.lm.StupidBackoffLm;
import javafx.util.Pair;

import java.util.*;
import java.util.stream.Collectors;

import static edu.berkeley.nlp.lm.io.LmReaders.readGoogleLmBinary;

public class NextWordPredictor {
    private static final NgramLanguageModel<String> languageModel = loadLanguageModel();
    private static final int LOG_PROB_WHEN_MODEL_FAILS = -1000;
    private static Map<String, List<String>> CONTRACTIONS = loadContractions();

    private static NgramLanguageModel<String> loadLanguageModel() {
        System.out.println("Loading Language Model (~1 minute)");
        long startTime = System.currentTimeMillis();
        //  Google Books binaries for English from http://tomato.banatao.berkeley.edu:8080/berkeleylm_binaries/
        final StupidBackoffLm<String> lm = readGoogleLmBinary("/Users/drufener/steno/src/main/resources/eng.blm", "/Users/drufener/steno/src/main/resources/vocab_cs");
        long endTime = System.currentTimeMillis();
        System.out.println("Loaded Language Model Time (ms): " + (endTime - startTime));
        return lm;
    }

    public static List<String> sortByLikelihoodDescending(final Set<String> candidates, final List<String> context) {
        return candidates.stream().map(candidate -> {
            final List<String> candidateNgram = new ArrayList<>();
            context.stream().map(NextWordPredictor::uncontractIfNeeded).forEach(candidateNgram::addAll);
            candidateNgram.addAll(uncontractIfNeeded(candidate));

            float logProb;
            if (Float.isNaN(getLogProb(ImmutableList.of(candidateNgram.get(candidateNgram.size() - 1))))) {
                logProb = LOG_PROB_WHEN_MODEL_FAILS;
            } else {
                logProb = getLogProb(candidateNgram);
                while (Float.isNaN(logProb)) {
                    if (!candidateNgram.isEmpty()) {
                        candidateNgram.remove(0);
                        logProb = getLogProb(candidateNgram);
                    } else {
                        logProb = LOG_PROB_WHEN_MODEL_FAILS;
                    }
                }
            }
            return new Pair<>(candidate,  logProb);
        }).sorted(Comparator.comparingDouble((Pair p) -> (double) (float) p.getValue()).reversed())
                .map((Pair p) -> (String) p.getKey())
                .collect(Collectors.toList());
    }

    private static float getLogProb(List<String> candidateNgram) {
        float logProb;
        try {
            logProb = languageModel.getLogProb(candidateNgram);
        } catch (ArrayIndexOutOfBoundsException e) {
            // Sometimes happens with long ngrams due to contraction expansion,  but no obvious way to predict when
            logProb = Float.NaN;
        }
        return logProb;
    }

    private static List<String> uncontractIfNeeded(String word) {
        if (!word.contains("'")) {
            return ImmutableList.of(word);
        } else {
            final List<String> knownContraction = CONTRACTIONS.get(word);
            if (knownContraction != null) {
                return knownContraction;
            }
            if (Float.isNaN(languageModel.getLogProb(ImmutableList.of(word)))) {
                return ImmutableList.of(word.replace("'",  ""));
            } else {
                return ImmutableList.of(word);
            }
        }
    }

    private static Map<String, List<String>> loadContractions() {
        return new ImmutableMap.Builder<String,  List<String>>()
                .put("i'm",  ImmutableList.of("i",  "am"))
                .put("you're", ImmutableList.of("you",  "are"))
                .put("we're", ImmutableList.of("we",  "are"))
                .put("we've", ImmutableList.of("we",  "have"))
                .put("they've", ImmutableList.of("they",  "have"))
                .put("could've", ImmutableList.of("could",  "have"))
                .put("would've", ImmutableList.of("would",  "have"))
                .put("should've", ImmutableList.of("should",  "have"))
                .put("might've", ImmutableList.of("might",  "have"))
                .put("who've", ImmutableList.of("who",  "have"))
                .put("there've", ImmutableList.of("there",  "have"))
                .put("he's", ImmutableList.of("he",  "is"))
                .put("she's", ImmutableList.of("she",  "is"))
                .put("it's", ImmutableList.of("it",  "is"))
                .put("what's", ImmutableList.of("what",  "is"))
                .put("that's", ImmutableList.of("that",  "is"))
                .put("who's", ImmutableList.of("who",  "is"))
                .put("there's", ImmutableList.of("there",  "is"))
                .put("here's", ImmutableList.of("here",  "is"))
                .put("i'll", ImmutableList.of("i",  "will"))
                .put("you'll", ImmutableList.of("you",  "will"))
                .put("she'll", ImmutableList.of("she",  "will"))
                .put("he'll", ImmutableList.of("he",  "will"))
                .put("it'll", ImmutableList.of("it",  "will"))
                .put("we'll", ImmutableList.of("we",  "will"))
                .put("they'll", ImmutableList.of("they",  "will"))
                .put("that'll", ImmutableList.of("that",  "will"))
                .put("there'll", ImmutableList.of("there",  "will"))
                .put("this'll", ImmutableList.of("this",  "will"))
                .put("what'll", ImmutableList.of("what",  "will"))
                .put("who'll", ImmutableList.of("who",  "will"))
                .put("i'd", ImmutableList.of("i",  "would"))
                .put("you'd", ImmutableList.of("you",  "would"))
                .put("he'd", ImmutableList.of("he",  "would"))
                .put("she'd", ImmutableList.of("she",  "would"))
                .put("we'd", ImmutableList.of("we",  "would"))
                .put("they'd", ImmutableList.of("they",  "would"))
                .put("it'd", ImmutableList.of("it",  "would"))
                .put("there'd", ImmutableList.of("there",  "would"))
                .put("what'd", ImmutableList.of("what",  "would"))
                .put("who'd", ImmutableList.of("who",  "would"))
                .put("that'd", ImmutableList.of("that",  "would"))
                .put("can't", ImmutableList.of("can",  "not"))
                .put("don't", ImmutableList.of("do",  "not"))
                .put("isn't", ImmutableList.of("is",  "not"))
                .put("won't", ImmutableList.of("will",  "not"))
                .put("shouldn't", ImmutableList.of("should",  "not"))
                .put("couldn't", ImmutableList.of("could",  "not"))
                .put("wouldn't", ImmutableList.of("would",  "not"))
                .put("aren't", ImmutableList.of("are",  "not"))
                .put("doesn't", ImmutableList.of("does",  "not"))
                .put("wasn't", ImmutableList.of("was",  "not"))
                .put("weren't", ImmutableList.of("were",  "not"))
                .put("hasn't", ImmutableList.of("has",  "not"))
                .put("haven't", ImmutableList.of("have",  "not"))
                .put("hadn't", ImmutableList.of("had",  "not"))
                .put("mustn't", ImmutableList.of("must",  "not"))
                .put("didn't", ImmutableList.of("did",  "not"))
                .put("mightn't", ImmutableList.of("might",  "not"))
                .put("needn't", ImmutableList.of("need",  "not"))
                .build();
    }
}
