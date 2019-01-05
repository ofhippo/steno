package steno;

import org.junit.Test;

import java.util.Map;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static steno.Schemes.*;
import static steno.Texts.*;

public class KeyerTest {
    private static final int NUM_TRIALS_FOR_RANDOM = 1000;

    @Test
    public void scoreDictionaryWithoutContext() {
        Keyer keyer = new Keyer(new ArpabetCompressor(IDENTITY));
        assertThat(keyer.scoreDictionaryWithoutContext().cost()).isBetween(0.5, 1.0);

        keyer = new Keyer(new ArpabetCompressor(COLLAPSED_VOWELS));
        assertThat(keyer.scoreDictionaryWithoutContext().cost()).isBetween(1.0, 1.5);

        keyer = new Keyer(new ArpabetCompressor(DUMB_13_STATE));
        assertThat(keyer.scoreDictionaryWithoutContext().cost()).isBetween(1.5, 2.0);

        keyer = new Keyer(new ArpabetCompressor(DUMB_8_STATE));
        assertThat(keyer.scoreDictionaryWithoutContext().cost()).isBetween(2.0, 2.5);
    }

    @Test
    public void scoreText() {
        Keyer keyer = new Keyer(new ArpabetCompressor(IDENTITY));
        assertThat(keyer.scoreText(SHORT_TEXT).cost()).isEqualTo(0);
        assertThat(keyer.scoreText(SHORT_TEXT_HOMOPHONE).cost()).isEqualTo(0);
        assertThat(keyer.scoreText(SHORT_TEXT_HOMOPHONE_LITTLE_CONTEXT).cost()).isEqualTo(0);
        assertThat(keyer.scoreText(HOLMES_LONG_TEXT).cost()).isLessThan(1d);
        assertThat(keyer.scoreText(RED_RISING_LONG_TEXT).cost()).isLessThan(1d);
        assertThat(keyer.scoreText(BLOG_POST).cost()).isLessThan(0.5);

        keyer = new Keyer(new ArpabetCompressor(COLLAPSED_VOWELS));
        assertThat(keyer.scoreText(SHORT_TEXT).cost()).isLessThan(1d);
        assertThat(keyer.scoreText(SHORT_TEXT_HOMOPHONE).cost()).isLessThan(1d);
        assertThat(keyer.scoreText(SHORT_TEXT_HOMOPHONE_LITTLE_CONTEXT).cost()).isLessThan(1d);
        assertThat(keyer.scoreText(HOLMES_LONG_TEXT).cost()).isLessThan(1d);
        assertThat(keyer.scoreText(RED_RISING_LONG_TEXT).cost()).isLessThan(1d);
        assertThat(keyer.scoreText(BLOG_POST).cost()).isLessThan(1);

        keyer = new Keyer(new ArpabetCompressor(DUMB_13_STATE));
        assertThat(keyer.scoreText(SHORT_TEXT).cost()).isLessThan(1d);
        assertThat(keyer.scoreText(SHORT_TEXT_HOMOPHONE).cost()).isLessThan(1d);
        assertThat(keyer.scoreText(SHORT_TEXT_HOMOPHONE_LITTLE_CONTEXT).cost()).isLessThan(1.5);
        assertThat(keyer.scoreText(HOLMES_LONG_TEXT).cost()).isLessThan(1d);
        assertThat(keyer.scoreText(RED_RISING_LONG_TEXT).cost()).isLessThan(1.5);
        assertThat(keyer.scoreText(BLOG_POST).cost()).isLessThan(1);

        keyer = new Keyer(new ArpabetCompressor(DUMB_8_STATE));
        assertThat(keyer.scoreText(SHORT_TEXT).cost()).isLessThan(1.1);
        assertThat(keyer.scoreText(SHORT_TEXT_HOMOPHONE).cost()).isLessThan(1.5);
        assertThat(keyer.scoreText(SHORT_TEXT_HOMOPHONE_LITTLE_CONTEXT).cost()).isLessThan(2.1);
        assertThat(keyer.scoreText(RED_RISING_LONG_TEXT).cost()).isLessThan(2.0);
        assertThat(keyer.scoreText(HOLMES_LONG_TEXT).cost()).isLessThan(2.0);
        assertThat(keyer.scoreText(BLOG_POST).cost()).isLessThan(1.5);
    }

    @Test
    public void optimize() {
        final SchemeOptimizer optimizer = new SchemeOptimizer(2);
        final Map<Arpabet, Enum> best = optimizer.run(100);
        best.forEach((key, value) -> System.out.println("" + key.toString() + " : " + value.toString()));
        final Keyer keyer = new Keyer( new ArpabetCompressor(best));
        final PerformanceStats stats = keyer.scoreText(THE_EGG);
        System.out.println("strokes per word: " + String.valueOf(stats.cost()));
        System.out.println("perfect rank %: " + String.valueOf(stats.getPerfectPredictionPercentage()));
        System.out.println();
        stats.printStrokesHistogram();
        stats.printRankHistogram();
        System.out.println(String.join(" | ", stats.getMissedWordsWithStrokes()));
        System.out.println();
    }

    @Test
    public void scoreTextForRandom() {
        runTrialsAndPrintStats(39);
        for (int range = 2; range <= 9; range++) {
            runTrialsAndPrintStats(range);
        }
    }

    private void runTrialsAndPrintStats(int range) {
        Keyer keyer;
        double minCost = 1e9;
        PerformanceStats minStats = null;
        double sumCost = 0;
        double minCostPerfectPercentage = 0;
        double sumPerfectPercentage = 0;
        for (int i = 0; i < NUM_TRIALS_FOR_RANDOM; i++) {
            keyer = new Keyer( new ArpabetCompressor(Schemes.random(range)));
            final PerformanceStats stats = keyer.scoreText(THE_EGG);
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
        System.out.println("---RANGE " + String.valueOf(range) + "----");
        System.out.println("best strokes per word: " + String.valueOf(minCost));
        System.out.println("avg strokes per word: " + String.valueOf(sumCost / NUM_TRIALS_FOR_RANDOM));
        System.out.println("best perfect rank %: " + String.valueOf(minCostPerfectPercentage));
        System.out.println("avg perfect rank %: " + String.valueOf(sumPerfectPercentage / NUM_TRIALS_FOR_RANDOM));
        System.out.println();
        minStats.printStrokesHistogram();
        minStats.printRankHistogram();
        System.out.println(String.join(" | ", minStats.getMissedWordsWithStrokes()));
        System.out.println();

    }
}