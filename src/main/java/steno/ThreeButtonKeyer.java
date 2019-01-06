package steno;

import java.util.List;

public class ThreeButtonKeyer implements Keyer {
    public static final int MAX_RANK_BEFORE_FALLBACK = 10;
    private Compressor compressor;
    public static final Alphabet[] ALPHABET = Alphabet.values();
    public static final String[] MORSE = new String[]{".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..",
            ".---", "-.-", ".-..", "--", "-.", "---", ".---.", "--.-", ".-.",
            "...", "-", "..-", "...-", ".--", "-..-", "-.--", "--..", ".----",
            "..---", "...--", "....-", ".....", "-....", "--...", "---..", "----.",
            "-----", "--..--", ".-.-.-", "..--.."};

    public ThreeButtonKeyer(Compressor compressor) {
        this.compressor = compressor;
    }

    @Override
    public int getMaxRankBeforeFallback() {
        return MAX_RANK_BEFORE_FALLBACK;
    }

    @Override
    public int strokesForFallback(String word) {
        return strokesForMorse(word) + 1;
    }

    @Override
    public int strokesToKey(List<Enum> compressed) {
        return compressed.size() + 1;
    }

    @Override
    public int strokesForRank(int rank) {
        if (rank < 0 || rank > MAX_RANK_BEFORE_FALLBACK) {
            return MAX_RANK_BEFORE_FALLBACK;
        } else {
            return (int) Math.ceil(rank / 3.0);
        }
    }

    @Override
    public Compressor getCompressor() {
        return compressor;
    }

    //https://stackoverflow.com/questions/29706653/morse-code-translatorsimple
    private static int strokesForMorse(String word) {
        int count = 0;
        List<Alphabet> letters = Alphabet.fromWord(word);
        for (Alphabet letter : letters) {
            for (int j = 0; j < ALPHABET.length; j++) {
                if (ALPHABET[j] == letter) {
                    count += MORSE[j].split("").length;
                }
            }
        }
        return count;
    }
}
