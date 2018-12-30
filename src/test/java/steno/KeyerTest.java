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
        assertThat(keyer.scoreText("He disappeared into his bedroom and returned in a few minutes in the character of an amiable and simple-minded Nonconformist clergyman. His broad black hat, his baggy trousers, his white tie, his sympathetic smile, and general look of peering and benevolent curiosity were such as Mr. John Hare alone could have equalled. It was not merely that Holmes changed his costume. His expression, his manner, his very soul seemed to vary with every fresh part that he assumed. The stage lost a fine actor, even as science lost an acute reasoner, when he became a specialist in crime.\n" +
                "It was a quarter past six when we left Baker Street, and it still wanted ten minutes to the hour when we found ourselves in Serpentine Avenue. It was already dusk, and the lamps were just being lighted as we paced up and down in front of Briony Lodge, waiting for the coming of its occupant. The house was just such as I had pictured it from Sherlock Holmes' succinct description, but the locality appeared to be less private than I expected. On the contrary, for a small street in a quiet neighbourhood, it was remarkably animated. There was a group of shabbily dressed men smoking and laughing in a corner, a scissors-grinder with his wheel, two guardsmen who were flirting with a nurse-girl, and several well-dressed young men who were lounging up and down with cigars in their mouths.")
        ).isLessThan(1d);
    }
}