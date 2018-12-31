package steno;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PerformanceStats {
    private static final int MAX_RANK_TO_TRACK = 20;
    public static final int MISSED_WORD_RANK_THRESHOLD = 8;
    private final int[] rankHistogram = new int[MAX_RANK_TO_TRACK];
    private double scoreSum = 0;
    private long count = 0;
    private List<String> missedWords = new ArrayList<>();

    public void add(int rank, double frequency, String word) {
        rankHistogram[Math.min(MAX_RANK_TO_TRACK, rank)]++;
        scoreSum += frequency * rank;
        count++;
        if (rank > MISSED_WORD_RANK_THRESHOLD) {
            missedWords.add(word);
        }
    }

    public double score() {
        return scoreSum / count;
    }

    public int[] getRankHistogram() {
        return rankHistogram;
    }

    public List<String> getMissedWords() {
        return missedWords;
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