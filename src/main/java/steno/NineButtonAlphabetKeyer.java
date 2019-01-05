package steno;

import java.util.List;

public class NineButtonAlphabetKeyer implements Keyer {

    private final Compressor compressor;

    public NineButtonAlphabetKeyer(Compressor compressor) {
        this.compressor = compressor;
    }

    @Override
    public int penaltyForExceedingMaxRank(String word, List<Enum> compressed) {
        return 5 + compressed.size();
    }

    @Override
    public int getMaxRankBeforeFallback() {
        return 12;
    }

    @Override
    public int strokesWhenDecodeFails(List<Enum> compressed) {
        return 1 + compressed.size();
    }

    @Override
    public int strokesToKey(List<Enum> compressed) {
        return (int) Math.ceil(compressed.size() / 4.0);
    }

    @Override
    public Compressor getCompressor() {
        return compressor;
    }
}
