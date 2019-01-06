package steno;

import javafx.util.Pair;
import org.apache.commons.collections4.queue.CircularFifoQueue;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

public class PerformanceStats {
    private static final int MAX_SCORE_TO_TRACK = 20;
    private static final int MISSED_WORD_RANK_THRESHOLD = 3;
    private static final int MAX_MISSED_WORDS_TO_TRACK = 1000;

    private final int[] strokesHistogram = new int[MAX_SCORE_TO_TRACK];
    private final int[] rankHistogram = new int[MAX_SCORE_TO_TRACK];
    private double scoreSum = 0;
    private double weightSum = 0;
    private double perfectSum = 0;
    private long count = 0;
    private CircularFifoQueue<Pair<String, Integer>> missedWordsWithRank = new CircularFifoQueue<>(MAX_MISSED_WORDS_TO_TRACK);
    private CircularFifoQueue<Pair<String, Integer>> missedWordsWithStrokes = new CircularFifoQueue<>(MAX_MISSED_WORDS_TO_TRACK);

    public void add(int strokes, int rank, double weight, String word) {
        strokesHistogram[Math.min(MAX_SCORE_TO_TRACK - 1, strokes)]++;
        rankHistogram[Math.min(MAX_SCORE_TO_TRACK - 1, rank)]++;
        scoreSum += weight * strokes;
        weightSum += weight;
        if (rank == 0) {
            perfectSum++;
        }
        count++;
        if (rank > MISSED_WORD_RANK_THRESHOLD) {
            missedWordsWithRank.add(new Pair(word, rank));
            missedWordsWithStrokes.add(new Pair(word, strokes));
        }
    }

    public double cost() {
        return scoreSum / weightSum;
    }

    public void printMissingWords() {
        System.out.println();
        System.out.println("missed words with rank");
        System.out.println(String.join(" | ", missedWordsWithRank.stream()
                .sorted(Comparator.comparingInt(wordAndRank -> ((Pair<String, Integer>) wordAndRank).getValue()).reversed())
                .map(x -> x.getKey() + ":" + x.getValue())
                .collect(Collectors.toList())));
        System.out.println();
        System.out.println("missed words with strokes");
        System.out.println(String.join(" | ", missedWordsWithStrokes.stream()
                .sorted(Comparator.comparingInt(wordAndStrokes -> ((Pair<String, Integer>) wordAndStrokes).getValue()).reversed())
                .map(x -> x.getKey() + ":" + x.getValue())
                .collect(Collectors.toList())));
        System.out.println();
    }

    public double getPercentageWithScoreAtOrBelow(int rankThreshold) {
        if (rankThreshold >= MAX_SCORE_TO_TRACK) {
            throw new RuntimeException("Must be below max");
        }
        double sum = 0;
        for (int i = 0; i <= rankThreshold; i++) {
            sum += strokesHistogram[i];
        }
        return sum / count;
    }

    public double getPerfectPredictionPercentage() {
        return perfectSum / count;
    }

    public void printStrokesHistogram() {
        System.out.println("  STROKES (including disambiguation)");
        printHistogram(strokesHistogram);
    }

    public void printRankHistogram() {
        System.out.println("  RANK");
        printHistogram(rankHistogram);
    }

    //https://stackoverflow.com/questions/13106906/how-to-create-a-histogram-in-java
    private static void printHistogram(int[] histogram) {
        for (int range = 0; range < histogram.length; range++) {
            if (histogram[range] > 0) {
                String label = range + " : ";
                System.out.println(label + convertToStars(histogram[range], Arrays.stream(histogram).sum()));
            }
        }
        System.out.println();
    }

    private static String convertToStars(int num, long sum) {
        StringBuilder builder = new StringBuilder();
        for (int j = 0; j < Math.floor(100d*num/sum); j++) {
            builder.append('*');
        }
        return builder.toString();
    }
}
