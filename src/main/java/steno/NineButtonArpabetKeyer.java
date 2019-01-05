package steno;

import java.util.List;
import java.util.Map;

public class NineButtonArpabetKeyer implements Keyer {
    private final Compressor compressor;

    public NineButtonArpabetKeyer(Map<Arpabet, Enum> scheme) {
        this(new ArpabetCompressor(scheme));
    }

    public NineButtonArpabetKeyer(Compressor compressor) {
        this.compressor = compressor;
    }

    public int penaltyForExceedingMaxRank(String word, List<Enum> compressed) {
        return 5 + compressed.size();
    }

    public int getMaxRankBeforeFallback() {
        return 12;
    }

    public int strokesWhenDecodeFails(List<Enum> compressed) {
        return 1 + compressed.size();
    }

    public int strokesToKey(List<Enum> compressed) {
        return (int) Math.ceil(compressed.size() / 4.0);
    }

    @Override
    public Compressor getCompressor() {
        return compressor;
    }

}
