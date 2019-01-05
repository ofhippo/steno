package steno;

import java.util.List;

public interface Keyer {
    int penaltyForExceedingMaxRank(String word, List<Enum> compressed);

    int getMaxRankBeforeFallback();

    int strokesWhenDecodeFails(List<Enum> compressed);

    int strokesToKey(List<Enum> compressed);

    Compressor getCompressor();
}
