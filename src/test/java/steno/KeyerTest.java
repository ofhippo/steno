package steno;

import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static steno.Schemes.COLLAPSED_VOWELS;
import static steno.Schemes.IDENTITY;

public class KeyerTest {

    @Test
    public void scoreDictionaryWithoutContext() {
        ArpabetCompressor compressor = new ArpabetCompressor(IDENTITY);
        Keyer keyer = new Keyer(compressor);
        assertThat(keyer.scoreDictionaryWithoutContext()).isLessThan(0.25);

        compressor = new ArpabetCompressor(COLLAPSED_VOWELS);
        keyer = new Keyer(compressor);
        assertThat(keyer.scoreDictionaryWithoutContext()).isBetween(0.25, 1d);
    }

    @Test
    public void scoreText() {
        ArpabetCompressor compressor = new ArpabetCompressor(IDENTITY);
        Keyer keyer = new Keyer(compressor);
        assertThat(keyer.scoreText("Yes, I like to read stories by the science fiction author Isaac Asimov!")).isEqualTo(0);
        assertThat(keyer.scoreText("I need to buy a clarinet reed")).isEqualTo(0);
        assertThat(keyer.scoreText("The reeds blow in the wind")).isEqualTo(0);

        compressor = new ArpabetCompressor(COLLAPSED_VOWELS);
        keyer = new Keyer(compressor);
        assertThat(keyer.scoreText("Yes, I like to read stories by the science fiction author Isaac Asimov!")).isLessThan(1d);
        assertThat(keyer.scoreText("I need to buy a clarinet reed")).isLessThan(1d);
        assertThat(keyer.scoreText("The reeds blow in the wind")).isLessThan(1d);
    }
}