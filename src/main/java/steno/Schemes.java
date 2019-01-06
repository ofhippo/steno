package steno;

import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

import static steno.Alphabet.*;
import static steno.Schemes.ExtendedArpabet.CLASS_B;
import static steno.Schemes.ExtendedArpabet.CLASS_A;
import static steno.Schemes.ExtendedArpabet.CLASS_C;

public class Schemes {
//    private static final ImmutableList<Arpabet> VOWEL_SOUNDS = ImmutableList.of(AA, AE, AH, AO, AW, AY, EH, ER, EY, IH, IY, OW, OY, UH, UW);
//
//    public static Map<Arpabet, Enum> IDENTITY = Arrays.stream(Arpabet.values()).collect(Collectors.toMap(a -> a, a -> a));
//    public static Map<Arpabet, Enum> COLLAPSED_VOWELS = Arrays.stream(Arpabet.values()).collect(
//            Collectors.toMap(a -> a, a -> VOWEL_SOUNDS.contains(a) ? ExtendedArpabet.CLASS_A : a)
//    );
//    public static Map<Arpabet, Enum> COLLAPSED_VOWELS_AND_CONSONANTS = Arrays.stream(Arpabet.values()).collect(
//            Collectors.toMap(a -> a, a -> VOWEL_SOUNDS.contains(a) ? ExtendedArpabet.CLASS_A : ExtendedArpabet.CLASS_B)
//    );
//
//    public static Map<Arpabet, Enum> DUMB_13_STATE = new HashMap<>(COLLAPSED_VOWELS);
//    static {
//        DUMB_13_STATE.putAll(new ImmutableMap.Builder<Arpabet, Enum>()
//                .put(HH, B)
//                .put(D, DH)
//                .put(F, G)
//                .put(K, L)
//                .put(M, N)
//                .put(P, R)
//                .put(S, T)
//                .put(SH, V)
//                .put(W, Y)
//                .put(Z, CH)
//                .put(ZH, TH)
//                .put(NG, JH)
//                .build());
//    }
//
//    public static Map<Arpabet, Enum> DUMB_8_STATE = new HashMap<>(COLLAPSED_VOWELS);
//    static {
//        DUMB_8_STATE.putAll(new ImmutableMap.Builder<Arpabet, Enum>()
//                .put(HH, DH)
//                .put(B, DH)
//                .put(D, DH)
//                .put(G, L)
//                .put(F, L)
//                .put(K, L)
//                .put(M, N)
//                .put(R, N)
//                .put(P, T)
//                .put(S, T)
//                .put(V, Y)
//                .put(SH, Y)
//                .put(W, Y)
//                .put(CH, TH)
//                .put(Z, TH)
//                .put(ZH, TH)
//                .put(NG, JH)
//                .build());
//    }
//
//    public static Map<Arpabet, Enum> SMARTER_8_STATE = new HashMap<>(COLLAPSED_VOWELS);
//    static {
//        DUMB_8_STATE.putAll(new ImmutableMap.Builder<Arpabet, Enum>()
//                .put(HH, DH)
//                .put(B, DH)
//                .put(D, DH)
//                .put(G, L)
//                .put(F, L)
//                .put(K, L)
//                .put(M, N)
//                .put(R, N)
//                .put(P, T)
//                .put(S, T)
//                .put(V, Y)
//                .put(SH, Y)
//                .put(W, Y)
//                .put(CH, TH)
//                .put(Z, TH)
//                .put(ZH, TH)
//                .put(NG, JH)
//                .build());
//    }

    public static Map<Alphabet, Enum> VOWELS_RH = (new ImmutableMap.Builder<Alphabet, Enum>()
            .put(A, CLASS_A)
            .put(E, CLASS_A)
            .put(I, CLASS_A)
            .put(O, CLASS_A)
            .put(U, CLASS_A)
            .put(R, CLASS_A)
            .put(H, CLASS_A)
            .put(N, CLASS_B)
            .put(B, CLASS_B)
            .put(C, CLASS_B)
            .put(D, CLASS_B)
            .put(F, CLASS_B)
            .put(G, CLASS_B)
            .put(J, CLASS_B)
            .put(K, CLASS_B)
            .put(L, CLASS_B)
            .put(M, CLASS_B)
            .put(P, CLASS_B)
            .put(Q, CLASS_B)
            .put(S, CLASS_B)
            .put(T, CLASS_B)
            .put(Y, CLASS_B)
            .put(V, CLASS_B)
            .put(W, CLASS_B)
            .put(X, CLASS_B)
            .put(Z, CLASS_B)
            .build());

    public static Map<Alphabet, Enum> TWO_CLASS_ALPHABETIC_SPLIT = (new ImmutableMap.Builder<Alphabet, Enum>()
            .put(A, CLASS_A)
            .put(B, CLASS_A)
            .put(C, CLASS_A)
            .put(D, CLASS_A)
            .put(E, CLASS_A)
            .put(F, CLASS_A)
            .put(G, CLASS_A)
            .put(H, CLASS_A)
            .put(I, CLASS_A)
            .put(J, CLASS_A)
            .put(K, CLASS_A)
            .put(L, CLASS_A)
            .put(M, CLASS_B)
            .put(N, CLASS_B)
            .put(O, CLASS_B)
            .put(P, CLASS_B)
            .put(Q, CLASS_B)
            .put(R, CLASS_B)
            .put(S, CLASS_B)
            .put(T, CLASS_B)
            .put(U, CLASS_B)
            .put(V, CLASS_B)
            .put(W, CLASS_B)
            .put(X, CLASS_B)
            .put(Y, CLASS_B)
            .put(Z, CLASS_B)
            .build());

    public static Map<Alphabet, Enum> THREE_CLASS_ALPHABETIC_SPLIT = (new ImmutableMap.Builder<Alphabet, Enum>()
            .put(A, CLASS_A)
            .put(B, CLASS_A)
            .put(C, CLASS_A)
            .put(D, CLASS_A)
            .put(E, CLASS_A)
            .put(F, CLASS_A)
            .put(G, CLASS_A)
            .put(H, CLASS_B)
            .put(I, CLASS_B)
            .put(J, CLASS_B)
            .put(K, CLASS_B)
            .put(L, CLASS_B)
            .put(M, CLASS_B)
            .put(N, CLASS_B)
            .put(O, CLASS_B)
            .put(P, CLASS_C)
            .put(Q, CLASS_C)
            .put(R, CLASS_C)
            .put(S, CLASS_C)
            .put(T, CLASS_C)
            .put(U, CLASS_C)
            .put(V, CLASS_C)
            .put(W, CLASS_C)
            .put(X, CLASS_C)
            .put(Y, CLASS_C)
            .put(Z, CLASS_C)
            .build());


    public static Map<Arpabet, Enum> randomArpabetScheme(int range) {
        if (range > Arpabet.values().length) {
            throw new IllegalArgumentException("Range too large");
        }
        final Arpabet[] arpabets = Arpabet.values();
        final Map<Arpabet, Enum> results = new HashMap<>();
        for (Arpabet arpabet : arpabets) {
            results.put(arpabet, arpabets[(int) Math.floor(Math.random() * range)]);
        }
        return results;
    }

    public static Map<Alphabet, Enum> randomAlphabetScheme(int range) {
        if (range > values().length) {
            throw new IllegalArgumentException("Range too large");
        }
        final Alphabet[] letters = Alphabet.values();
        final Map<Alphabet, Enum> results = new HashMap<>();
        for (Alphabet letter : letters) {
            results.put(letter, letters[(int) Math.floor(Math.random() * range)]);
        }
        return results;
    }


    enum ExtendedArpabet {
        CLASS_A,
        CLASS_B,
        CLASS_C,
    }

}
