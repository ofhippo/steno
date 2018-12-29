package steno;

import org.junit.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static steno.Arpabet.*;
public class ArpabetTest {

    @Test
    public void fromText() {
        assertThat(Arpabet.fromText("hello")).containsExactly(HH, AH, L, OW);
        assertThat(Arpabet.fromText("world")).containsExactly(W, ER, L, D);
    }
}