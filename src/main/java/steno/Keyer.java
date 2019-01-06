package steno;

import java.util.List;

public interface Keyer {
    int strokesToKey(List<Enum> compressed);

    int strokesForRank(int rank);

    int getMaxRankBeforeFallback();

    int strokesForFallback(String word);

    Compressor getCompressor();

}
