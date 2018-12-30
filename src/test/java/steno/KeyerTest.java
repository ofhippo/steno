package steno;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static steno.Arpabet.*;
import static steno.Arpabet.IDENTITY_SCHEME;

public class KeyerTest {

    @Test
    public void scoreDictionaryWithoutContext() {
        final ArpabetCompressor compressor = new ArpabetCompressor(IDENTITY_SCHEME);
        final Keyer keyer = new Keyer(compressor);
        assertThat(keyer.scoreDictionaryWithoutContext()).isBetween(0d, 0.25);
    }

    @Test
    public void scoreText() {
        final ArpabetCompressor compressor = new ArpabetCompressor(IDENTITY_SCHEME);
        final Keyer keyer = new Keyer(compressor);
        assertThat(keyer.scoreText("Yes, I like to read stories by the science fiction author Isaac Asimov!")).isEqualTo(0);
    }

    @Test
    public void getAllPossibleCombinations() {
        final ArpabetCompressor compressor = new ArpabetCompressor(IDENTITY_SCHEME);
        final Keyer keyer = new Keyer(compressor);

        final List<Arpabet> arpabets = Arpabet.fromWord("cat");
        final List<Enum> compressed = compressor.encode(arpabets);
        final List<Set<Arpabet>> possibleArpabets = compressor.decode(compressed);

        Set<List<Arpabet>> allPossibleCombinations = keyer.getAllPossibleCombinations(possibleArpabets);
        assertThat(allPossibleCombinations).containsExactly(ImmutableList.of(K, AE, T));

        allPossibleCombinations = keyer.getAllPossibleCombinations(ImmutableList.of(
                ImmutableSet.of(L, M),
                ImmutableSet.of(M),
                ImmutableSet.of(P, R),
                ImmutableSet.of(L, S, N)
        ));
        assertThat(allPossibleCombinations).containsExactlyInAnyOrder(
                ImmutableList.of(L, M, P, L),
                ImmutableList.of(L, M, P, S),
                ImmutableList.of(L, M, P, N),
                ImmutableList.of(M, M, P, L),
                ImmutableList.of(M, M, P, S),
                ImmutableList.of(M, M, P, N),
                ImmutableList.of(L, M, R, L),
                ImmutableList.of(L, M, R, S),
                ImmutableList.of(L, M, R, N),
                ImmutableList.of(M, M, R, L),
                ImmutableList.of(M, M, R, S),
                ImmutableList.of(M, M, R, N)
        );
    }
}