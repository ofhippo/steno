package steno;

import com.google.common.collect.ImmutableList;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static steno.Arpabet.*;
import static steno.Schemes.ExtendedArpabet.VOWEL;

public class ArpabetCompressorTest {

    @Test
    public void withIdentity() {
        final ArpabetCompressor compressor = new ArpabetCompressor(Schemes.IDENTITY);
        final List<Enum> encoded = compressor.encode(ImmutableList.of(HH, AH, L, OW));
        assertThat(encoded).containsExactly(HH, AH, L, OW);
        assertThat(compressor.decode(encoded)).containsExactly("HELLO");
    }

    @Test
    public void withCustom() {
        final ArpabetCompressor compressor = new ArpabetCompressor(Schemes.COLLAPSED_VOWELS);
        final List<Enum> encoded = compressor.encode(ImmutableList.of(HH, AH, L, OW));
        assertThat(encoded).containsExactly(HH, VOWEL, L, VOWEL);
        assertThat(compressor.decode(encoded)).contains("HELLO", "HILLY", "HOLY", "HALO");
    }
}