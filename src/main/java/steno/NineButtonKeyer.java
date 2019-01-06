package steno;

import java.util.List;

public class NineButtonKeyer implements Keyer {

    private final Compressor compressor;

    public NineButtonKeyer(Compressor compressor) {
        this.compressor = compressor;
    }

    @Override
    public int getMaxRankBeforeFallback() {
        return 12;
    }

    @Override
    public int strokesForFallback(String word) {
        return word.split("").length;
    }

    @Override
    public int strokesToKey(List<Enum> compressed) {
        return (int) Math.ceil(compressed.size() / 4.0);
    }

    @Override
    public int getStrokesForRank(int rank) {
        if (rank < -1 || rank > getMaxRankBeforeFallback()) {
            return (int) Math.ceil(getMaxRankBeforeFallback() / 16.0);
        }
        return (int) Math.ceil(rank / 16.0);
    }

    @Override
    public Compressor getCompressor() {
        return compressor;
    }
}
