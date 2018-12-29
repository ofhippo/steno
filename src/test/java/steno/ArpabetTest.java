package steno;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static steno.Arpabet.*;
public class ArpabetTest {

    @Test
    public void fromText() {
        assertThat(Arpabet.fromText("hello")).containsExactly(HH, AH, L, OW);
        assertThat(Arpabet.fromText("world")).containsExactly(W, ER, L, D);
        assertThat(Arpabet.fromText("too")).containsExactly(T,  UW);
    }

    @Test
    public void toPossibleTexts() {
        assertThat(Arpabet.toPossibleTexts(ImmutableList.of(HH, AH, L, OW))).isEqualTo(ImmutableSet.of("hello"));
        assertThat(Arpabet.toPossibleTexts(ImmutableList.of(W, ER, L, D))).isEqualTo(ImmutableSet.of("world", "whirled"));
        assertThat(Arpabet.toPossibleTexts(ImmutableList.of(T, UW))).isEqualTo(ImmutableSet.of("tu", "tew(2)", "too", "tue", "to", "two", "thuy"));
    }
}