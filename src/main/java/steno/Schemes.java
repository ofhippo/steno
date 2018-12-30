package steno;

import com.google.common.collect.ImmutableList;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import static steno.Arpabet.*;

public class Schemes {
    private static final ImmutableList<Arpabet> VOWEL_SOUNDS = ImmutableList.of(AA, AE, AH, AO, AW, AY, EH, ER, EY, IH, IY, OW, OY, UH, UW);

    public static Map<Arpabet, Enum> IDENTITY = Arrays.stream(Arpabet.values()).collect(Collectors.toMap(a -> a, a -> a));
    public static Map<Arpabet, Enum> COLLAPSED_VOWELS = Arrays.stream(Arpabet.values()).collect(
            Collectors.toMap(a -> a, a -> VOWEL_SOUNDS.contains(a) ? ExtendedArpabet.VOWEL : a)
    );

    enum ExtendedArpabet {
        VOWEL
    }

}
