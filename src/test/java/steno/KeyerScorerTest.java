package steno;

import org.junit.Test;

import java.util.Map;

import static steno.Texts.THE_EGG;

public class KeyerScorerTest {
    private static final int NUM_TRIALS_FOR_RANDOM = 100;

// TODO: Convert to use KeyerScorer
//    @Test
//    public void scoreDictionaryWithoutContext() {
//        NineButtonArpabetKeyer keyer = new NineButtonArpabetKeyer(new ArpabetCompressor(IDENTITY));
//        assertThat(keyer.scoreDictionaryWithoutContext().cost()).isBetween(0.5, 1.0);
//
//        keyer = new NineButtonArpabetKeyer(new ArpabetCompressor(COLLAPSED_VOWELS));
//        assertThat(keyer.scoreDictionaryWithoutContext().cost()).isBetween(1.0, 1.5);
//
//        keyer = new NineButtonArpabetKeyer(new ArpabetCompressor(DUMB_13_STATE));
//        assertThat(keyer.scoreDictionaryWithoutContext().cost()).isBetween(1.5, 2.0);
//
//        keyer = new NineButtonArpabetKeyer(new ArpabetCompressor(DUMB_8_STATE));
//        assertThat(keyer.scoreDictionaryWithoutContext().cost()).isBetween(2.0, 2.5);
//    }
//
//    @Test
//    public void scoreText() {
//        NineButtonArpabetKeyer keyer = new NineButtonArpabetKeyer(new ArpabetCompressor(IDENTITY));
//        assertThat(keyer.scoreText(SHORT_TEXT).cost()).isEqualTo(0);
//        assertThat(keyer.scoreText(SHORT_TEXT_HOMOPHONE).cost()).isEqualTo(0);
//        assertThat(keyer.scoreText(SHORT_TEXT_HOMOPHONE_LITTLE_CONTEXT).cost()).isEqualTo(0);
//        assertThat(keyer.scoreText(HOLMES_LONG_TEXT).cost()).isLessThan(1d);
//        assertThat(keyer.scoreText(RED_RISING_LONG_TEXT).cost()).isLessThan(1d);
//        assertThat(keyer.scoreText(BLOG_POST).cost()).isLessThan(0.5);
//
//        keyer = new NineButtonArpabetKeyer(new ArpabetCompressor(COLLAPSED_VOWELS));
//        assertThat(keyer.scoreText(SHORT_TEXT).cost()).isLessThan(1d);
//        assertThat(keyer.scoreText(SHORT_TEXT_HOMOPHONE).cost()).isLessThan(1d);
//        assertThat(keyer.scoreText(SHORT_TEXT_HOMOPHONE_LITTLE_CONTEXT).cost()).isLessThan(1d);
//        assertThat(keyer.scoreText(HOLMES_LONG_TEXT).cost()).isLessThan(1d);
//        assertThat(keyer.scoreText(RED_RISING_LONG_TEXT).cost()).isLessThan(1d);
//        assertThat(keyer.scoreText(BLOG_POST).cost()).isLessThan(1);
//
//        keyer = new NineButtonArpabetKeyer(new ArpabetCompressor(DUMB_13_STATE));
//        assertThat(keyer.scoreText(SHORT_TEXT).cost()).isLessThan(1d);
//        assertThat(keyer.scoreText(SHORT_TEXT_HOMOPHONE).cost()).isLessThan(1d);
//        assertThat(keyer.scoreText(SHORT_TEXT_HOMOPHONE_LITTLE_CONTEXT).cost()).isLessThan(1.5);
//        assertThat(keyer.scoreText(HOLMES_LONG_TEXT).cost()).isLessThan(1d);
//        assertThat(keyer.scoreText(RED_RISING_LONG_TEXT).cost()).isLessThan(1.5);
//        assertThat(keyer.scoreText(BLOG_POST).cost()).isLessThan(1);
//
//        keyer = new NineButtonArpabetKeyer(new ArpabetCompressor(DUMB_8_STATE));
//        assertThat(keyer.scoreText(SHORT_TEXT).cost()).isLessThan(1.1);
//        assertThat(keyer.scoreText(SHORT_TEXT_HOMOPHONE).cost()).isLessThan(1.5);
//        assertThat(keyer.scoreText(SHORT_TEXT_HOMOPHONE_LITTLE_CONTEXT).cost()).isLessThan(2.1);
//        assertThat(keyer.scoreText(RED_RISING_LONG_TEXT).cost()).isLessThan(2.0);
//        assertThat(keyer.scoreText(HOLMES_LONG_TEXT).cost()).isLessThan(2.0);
//        assertThat(keyer.scoreText(BLOG_POST).cost()).isLessThan(1.5);
//    }

    @Test
    public void optimize() {
        final ArpabetSchemeOptimizer optimizer = new ArpabetSchemeOptimizer(2);
        final Map<Arpabet, Enum> best = optimizer.run(100);
        best.forEach((key, value) -> System.out.println("" + key.toString() + " : " + value.toString()));
        final NineButtonKeyer keyer = new NineButtonKeyer(new ArpabetCompressor(best));
        final PerformanceStats stats = KeyerScorer.scoreText(THE_EGG, keyer);
        System.out.println("strokes per word: " + String.valueOf(stats.cost()));
        System.out.println("perfect rank %: " + String.valueOf(stats.getPerfectPredictionPercentage()));
        System.out.println();
        stats.printStrokesHistogram();
        stats.printRankHistogram();
        stats.printMissingWords();
    }

    @Test
    public void scoreTextForRandom() {
        runTrialsAndScoreRandom(26);
        for (int range = 2; range <= 9; range++) {
            runTrialsAndScoreRandom(range);
        }
    }

    @Test
    public void test2ClassAlphabeticSplit() {
        score(Schemes.TWO_CLASS_ALPHABETIC_SPLIT);
    }

    @Test
    public void test3ClassAlphabeticSplit() {
        score(Schemes.THREE_CLASS_ALPHABETIC_SPLIT);
    }

    @Test
    public void testRandom3StateAlphabetic() {
        runTrialsAndScoreRandom(3);
    }

    private void score(Map<Alphabet, Enum> scheme) {
        score(scheme, null);
    }

    private void runTrialsAndScoreRandom(int range) {
        score(null, range);
    }

    private void score(Map<Alphabet, Enum> scheme, Integer rangeForRandom) {
        ThreeButtonKeyer keyer;
        double minCost = 1e9;
        PerformanceStats minStats = null;
        double sumCost = 0;
        double minCostPerfectPercentage = 0;
        double sumPerfectPercentage = 0;
        int numTrials = scheme == null ? NUM_TRIALS_FOR_RANDOM : 1;
        for (int i = 0; i < numTrials; i++) {
            if (scheme != null) {
                keyer = new ThreeButtonKeyer(new AlphabetCompressor(scheme));
            } else {
                keyer = new ThreeButtonKeyer(new AlphabetCompressor(Schemes.randomAlphabetScheme(rangeForRandom)));
            }
            final PerformanceStats stats = KeyerScorer.scoreText(THE_EGG, keyer);
            final double cost = stats.cost();
            final double predictionPercentage = stats.getPerfectPredictionPercentage();

            sumCost += cost;
            sumPerfectPercentage += predictionPercentage;

            if (cost < minCost) {
                minCost = cost;
                minCostPerfectPercentage = predictionPercentage;
                minStats = stats;
            }
        }
        System.out.println("---RANGE " + String.valueOf(scheme) + "----");
        System.out.println("best strokes per word: " + String.valueOf(minCost));
        System.out.println("best perfect rank %: " + String.valueOf(minCostPerfectPercentage));
        if (numTrials > 1) {
            System.out.println("avg strokes per word: " + String.valueOf(sumCost / NUM_TRIALS_FOR_RANDOM));
            System.out.println("avg perfect rank %: " + String.valueOf(sumPerfectPercentage / NUM_TRIALS_FOR_RANDOM));
        }
        System.out.println();
        minStats.printStrokesHistogram();
        minStats.printRankHistogram();
        minStats.printMissingWords();

    }
}