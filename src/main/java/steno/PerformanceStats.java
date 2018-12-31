package steno;

import org.apache.commons.collections4.queue.CircularFifoQueue;

import java.util.Arrays;

public class PerformanceStats {
    private static final int MAX_RANK_TO_TRACK = 20;
    private static final int MISSED_WORD_RANK_THRESHOLD = 8;
    private static final int MAX_MISSED_WORDS_TO_TRACK = 100;

    private final int[] rankHistogram = new int[MAX_RANK_TO_TRACK];
    private double scoreSum = 0;
    private double frequencySum = 0;
    private long count = 0;
    private CircularFifoQueue<String> missedWords = new CircularFifoQueue<>(MAX_MISSED_WORDS_TO_TRACK);

    public void add(int rank, double frequency, String word) {
        rankHistogram[Math.min(MAX_RANK_TO_TRACK-1, rank)]++;
        scoreSum += frequency * rank;
        frequencySum += frequency;
        count++;
        if (rank > MISSED_WORD_RANK_THRESHOLD) {
            missedWords.add(word);
        }
    }

    public double cost() {
        return scoreSum / frequencySum;
    }

    public int[] getRankHistogram() {
        return rankHistogram;
    }

    public CircularFifoQueue<String> getMissedWords() {
        return missedWords;
    }

    public double getPercentageWithRankAtOrBelow(int rankThreshold) {
        if (rankThreshold >= MAX_RANK_TO_TRACK) {
            throw new RuntimeException("Must be below max");
        }
        double sum = 0;
        for (int i = 0; i <= rankThreshold; i++) {
            sum += rankHistogram[i];
        }
        return sum / count;
    }

    //https://stackoverflow.com/questions/13106906/how-to-create-a-histogram-in-java
    public void printRankHistogram() {
        for (int range = 0; range < rankHistogram.length; range++) {
            if (rankHistogram[range] > 0) {
                String label = range + " : ";
                System.out.println(label + convertToStars(rankHistogram[range], Arrays.stream(rankHistogram).sum()));
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
