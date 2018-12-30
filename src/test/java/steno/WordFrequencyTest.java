package steno;

import org.assertj.core.data.Percentage;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static steno.WordFrequency.DICTIONARY;

public class WordFrequencyTest {
    @Test
    public void testDictionary() {
        assertThat(DICTIONARY.get("the")).isBetween(0.01, 0.05);
        assertThat(DICTIONARY.get("dog")).isCloseTo(DICTIONARY.get("cat"), Percentage.withPercentage(100));
        assertThat(DICTIONARY.get("dog")).isNotCloseTo(DICTIONARY.get("bat"), Percentage.withPercentage(100));
    }
}