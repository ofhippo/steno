import org.junit.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


public class WordToPhonemesTest {

    @Test
    public void toPhonemes() {
        final WordToPhonemes wordToPhonemes = new WordToPhonemes();
        assertThat(wordToPhonemes.toPhonemes("hello")).containsExactly("HH", "AH", "L", "OW");
        assertThat(wordToPhonemes.toPhonemes("world")).containsExactly("W", "ER", "L", "D");
    }
}