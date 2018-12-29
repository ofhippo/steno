package steno;

import edu.berkeley.nlp.lm.NgramLanguageModel;
import edu.berkeley.nlp.lm.StupidBackoffLm;
import javafx.util.Pair;

import java.util.*;
import java.util.stream.Collectors;

import static edu.berkeley.nlp.lm.io.LmReaders.readGoogleLmBinary;

public class NextWordPredictor {
    private static final NgramLanguageModel<String> languageModel = loadLanguageModel();

    private static NgramLanguageModel<String> loadLanguageModel() {
        System.out.println("Loading Language Model (~1 minute)");
        long startTime = System.currentTimeMillis();
        //  Google Books binaries for English from http://tomato.banatao.berkeley.edu:8080/berkeleylm_binaries/
        final StupidBackoffLm<String> lm = readGoogleLmBinary("/Users/drufener/steno/src/main/resources/eng.blm", "/Users/drufener/steno/src/main/resources/vocab_cs");
        long endTime = System.currentTimeMillis();
        System.out.println("Loaded Language Model Time (ms): " + (endTime - startTime));
        return lm;
    }

    public static List<String> sortByLikelihoodDescending(final List<String> context, List<String> candidates) {
        return candidates.stream().map(candidate -> {
            final List<String> candidateNgram = new ArrayList<>(context);
            candidateNgram.add(candidate);
            return new Pair<>(candidate, languageModel.getLogProb(candidateNgram));
        }).sorted(Comparator.comparingDouble((Pair p) -> (double) (float) p.getValue()).reversed())
                .map((Pair p) -> (String) p.getKey())
                .collect(Collectors.toList());
    }
}
