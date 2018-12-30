package steno;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


public class NextWordPredictorTest {

    @Test
    public void sortByLikelihoodDescending() {
        ImmutableList<String> context = ImmutableList.of("I", "went");
        ImmutableSet<String> candidateNextWords = ImmutableSet.of("of", "the", "to", "store", "over");
        List<String> results = NextWordPredictor.sortByLikelihoodDescending(candidateNextWords, context);
        assertThat(results).containsExactly("to", "over", "the", "of", "store");

        context = ImmutableList.of("cross", "my");
        candidateNextWords = ImmutableSet.of("the", "path", "heart");
        results = NextWordPredictor.sortByLikelihoodDescending(candidateNextWords, context);
        assertThat(results).containsExactly("path", "heart", "the");
    }
}