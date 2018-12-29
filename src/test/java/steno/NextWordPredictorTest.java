package steno;

import com.google.common.collect.ImmutableList;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


public class NextWordPredictorTest {

    @Test
    public void sortByLikelihoodDescending() {
        ImmutableList<String> context = ImmutableList.of("I", "went");
        ImmutableList<String> candidateNextWords = ImmutableList.of("of", "the", "to", "store", "over");
        List<String> results = NextWordPredictor.sortByLikelihoodDescending(context, candidateNextWords);
        assertThat(results).containsExactly("to", "over", "the", "of", "store");

        context = ImmutableList.of("cross", "my");
        candidateNextWords = ImmutableList.of("the", "path", "heart");
        results = NextWordPredictor.sortByLikelihoodDescending(context, candidateNextWords);
        assertThat(results).containsExactly("path", "heart", "the");
    }
}