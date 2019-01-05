package steno;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Alphabet {
    A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z;

    public static List<Alphabet> fromWord(String word) {
        return Arrays.stream(word.split("")).map(c -> Alphabet.valueOf(c.toUpperCase())).collect(Collectors.toList());
    }
}
