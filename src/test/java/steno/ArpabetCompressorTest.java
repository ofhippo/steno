package steno;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static steno.Arpabet.*;

public class ArpabetCompressorTest {

    @Test
    public void withIdentity() {
        final Map<Arpabet, Enum> identityScheme = Arrays.stream(Arpabet.values()).collect(Collectors.toMap(a -> a, a -> a));
        final ArpabetCompressor compressor = new ArpabetCompressor(identityScheme);
        final List<Enum> encoded = compressor.encode(ImmutableList.of(HH, AH, L, OW));
        assertThat(encoded).containsExactly(HH, AH, L, OW);
        assertThat(compressor.decode(encoded)).containsExactly(
                ImmutableSet.of(HH), ImmutableSet.of(AH), ImmutableSet.of(L), ImmutableSet.of(OW));
    }

    @Test
    public void withCustom() {
        final Map<Arpabet, Enum> scheme = ImmutableMap.of(
                HH, Mybet.A,
                AH, Mybet.A,
                L, Mybet.B,
                OW, Mybet.B
        );
        final ArpabetCompressor compressor = new ArpabetCompressor(scheme);
        final List<Enum> encoded = compressor.encode(ImmutableList.of(HH, AH, L, OW));
        assertThat(encoded).containsExactly(Mybet.A, Mybet.A, Mybet.B, Mybet.B);
        assertThat(compressor.decode(encoded)).containsExactly(
                ImmutableSet.of(HH, AH), ImmutableSet.of(AH, HH), ImmutableSet.of(L, OW), ImmutableSet.of(OW, L));
    }

    enum Mybet {
        A, B
    }
}