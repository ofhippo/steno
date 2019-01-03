package steno;

import org.apache.commons.collections4.queue.CircularFifoQueue;

import java.util.Arrays;

public class PerformanceStats {
    private static final int MAX_SCORE_TO_TRACK = 20;
    private static final int MISSED_WORD_STROKE_THRESHOLD = 10;
    private static final int MAX_MISSED_WORDS_TO_TRACK = 1000;

    private final int[] strokesHistogram = new int[MAX_SCORE_TO_TRACK];
    private final int[] rankHistogram = new int[MAX_SCORE_TO_TRACK];
    private double scoreSum = 0;
    private double frequencySum = 0;
    private double perfectSum = 0;
    private long count = 0;
    private CircularFifoQueue<String> missedWordsWithStrokes = new CircularFifoQueue<>(MAX_MISSED_WORDS_TO_TRACK);

    public void add(int strokes, int rank, double frequency, String word) {
        strokesHistogram[Math.min(MAX_SCORE_TO_TRACK - 1, strokes)]++;
        rankHistogram[Math.min(MAX_SCORE_TO_TRACK - 1, rank)]++;
        scoreSum += frequency * strokes;
        frequencySum += frequency;
        if (rank == 0) {
            perfectSum++;
        }
        count++;
        if (strokes > MISSED_WORD_STROKE_THRESHOLD) {
            missedWordsWithStrokes.add(word + ":" + String.valueOf(strokes));
        }
    }

    public double cost() {
        return scoreSum / frequencySum;
    }

    public CircularFifoQueue<String> getMissedWordsWithStrokes() {
        return missedWordsWithStrokes;
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
