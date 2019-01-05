package steno;

import java.util.List;
import java.util.Set;

public interface Compressor {
    List<Enum> encode(String word);
    Set<String> decode(List<Enum> compressed);
}
