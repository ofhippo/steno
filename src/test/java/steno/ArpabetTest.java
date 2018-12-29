package steno;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static steno.Arpabet.*;
public class ArpabetTest {

    @Test
    public void fromText() {
        assertThat(Arpabet.fromWord("hello")).containsExactly(HH, AH, L, OW);
        assertThat(Arpabet.fromWord("world")).containsExactly(W, ER, L, D);
        assertThat(Arpabet.fromWord("too")).containsExactly(T,  UW);
    }

    @Test
    public void toPossibleTexts() {
        assertThat(Arpabet.toPossibleWords(ImmutableList.of(HH, AH, L, OW))).isEqualTo(ImmutableSet.of("hello"));
        assertThat(Arpabet.toPossibleWords(ImmutableList.of(W, ER, L, D))).isEqualTo(ImmutableSet.of("world", "whirled"));
        assertThat(Arpabet.toPossibleWords(ImmutableList.of(T, UW))).isEqualTo(ImmutableSet.of("tu", "tew(2)", "too", "tue", "to", "two", "thuy"));
    }
}