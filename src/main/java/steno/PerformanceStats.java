package steno;

import org.apache.commons.collections4.queue.CircularFifoQueue;

import java.util.Arrays;

public class PerformanceStats {
    private static final int MAX_RANK_TO_TRACK = 20;
    private static final int MISSED_WORD_STROKE_THRESHOLD = 10;
    private static final int MAX_MISSED_WORDS_TO_TRACK = 1000;

    private final int[] scoreHistogram = new int[MAX_RANK_TO_TRACK];
    private double scoreSum = 0;
    private double frequencySum = 0;
    private long count = 0;
    private CircularFifoQueue<String> missedWordsWithStrokes = new CircularFifoQueue<>(MAX_MISSED_WORDS_TO_TRACK);

    public void add(int strokes, double frequency, String word) {
        scoreHistogram[Math.min(MAX_RANK_TO_TRACK-1, strokes)]++;
        scoreSum += frequency * strokes;
        frequencySum += frequency;
        count++;
        if (strokes > MISSED_WORD_STROKE_THRESHOLD) {
            missedWordsWithStrokes.add(word + ":" + String.valueOf(strokes));
        }
    }

    public double cost() {
        return scoreSum / frequencySum;
    }

    public int[] getScoreHistogram() {
        return scoreHistogram;
    }

    public CircularFifoQueue<String> getMissedWordsWithStrokes() {
        return missedWordsWithStrokes;
    }

    public double getPercentageWithScoreAtOrBelow(int rankThreshold) {
        if (rankThreshold >= MAX_RANK_TO_TRACK) {
            throw new RuntimeException("Must be below max");
        }
        double sum = 0;
        for (int i = 0; i <= rankThreshold; i++) {
            sum += scoreHistogram[i];
        }
        return sum / count;
    }

    //https://stackoverflow.com/questions/13106906/how-to-create-a-histogram-in-java
    public void printRankHistogram() {
        for (int range = 0; range < scoreHistogram.length; range++) {
            if (scoreHistogram[range] > 0) {
                String label = range + " : ";
                System.out.println(label + convertToStars(scoreHistogram[range], Arrays.stream(scoreHistogram).sum()));
            }
        }
    }

    private String convertToStars(int num, long sum) {
        StringBuilder builder = new StringBuilder();
        for (int j = 0; j < Math.floor(100d*num/sum); j++) {
            builder.append('*');
        }
        return builder.toString();
    }
}
