package steno;

import java.util.*;
import java.util.stream.Collectors;

public class ArpabetCompressor {
    private final Map<Enum, Set<Arpabet>> reversedScheme;
    private Map<Arpabet, Enum> scheme;

    public ArpabetCompressor(Map<Arpabet, Enum> scheme) {
        this.scheme = scheme;
        this.reversedScheme = invertScheme(scheme);
    }

    public List<Enum> encode(List<Arpabet> arpabets) {
        return arpabets.stream().map(scheme::get).collect(Collectors.toList());
    }

    public List<Set<Arpabet>> decode(List<Enum> compressed) {
        return compressed.stream().map(reversedScheme::get).collect(Collectors.toList());
    }

    private static Map<Enum, Set<Arpabet>> invertScheme(Map<Arpabet, Enum> scheme) {
        final Map<Enum, Set<Arpabet>> inverted = new HashMap<>();
        for (Map.Entry<Arpabet, Enum> entry : scheme.entrySet()) {
            final Arpabet arpabet = entry.getKey();
            final Enum compressedSymbol = entry.getValue();
            final Set<Arpabet> arpabets = inverted.computeIfAbsent(compressedSymbol, k -> new HashSet<>());
            arpabets.add(arpabet);
        }
        return inverted;
    }
}
