package steno;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

public enum Arpabet {
/*
Phoneme Example Translation
------- ------- -----------
AA	odd     AA D
AE	at	AE T
AH	hut	HH AH T
AO	ought	AO T
AW	cow	K AW
AY	hide	HH AY D
B 	be	B IY
CH	cheese	CH IY Z
D 	dee	D IY
DH	thee	DH IY
EH	Ed	EH D
ER	hurt	HH ER T
EY	ate	EY T
F 	fee	F IY
G 	green	G R IY N
HH	he	HH IY
IH	it	IH T
IY	eat	IY T
JH	gee	JH IY
K 	key	K IY
L 	lee	L IY
M 	me	M IY
N 	knee	N IY
NG	ping	P IH NG
OW	oat	OW T
OY	toy	T OY
P 	pee	P IY
R 	read	R IY D
S 	sea	S IY
SH	she	SH IY
T 	tea	T IY
TH	theta	TH EY T AH
UH	hood	HH UH D
UW	two	T UW
V 	vee	V IY
W 	we	W IY
Y 	yield	Y IY L D
Z 	zee	Z IY
ZH	seizure	S IY ZH ER
*/
    HH, B, D, DH, F, G, K, L, M, N, P, R, S, UH, T, SH, V, W, Y, Z, IH, AA, UW, EH, AE, CH, AH, OW, OY, ER, AO, ZH, IY, EY, TH, AW, AY, NG, JH;

    private static Map<String, List<Arpabet>> dictionary = loadDictionary();
    private static Map<List<Arpabet>, Set<String>> reverseDictionary = buildReverseDictionary();

    private static Map<List<Arpabet>, Set<String>> buildReverseDictionary() {
        final Map<List<Arpabet>, Set<String>> inverted = new HashMap<>();
        for (Map.Entry<String, List<Arpabet>> entry : dictionary.entrySet()) {
            final String word = entry.getKey();
            final List<Arpabet> arpabets = entry.getValue();
            final Set<String> words = inverted.computeIfAbsent(arpabets, k -> new HashSet<>());
            words.add(word);
        }
        return inverted;
    }

    public static List<Arpabet> fromWord(String word) {
        //TODO: If not in dictionary, do something like http://www.speech.cs.cmu.edu/tools/lextool.html
        return dictionary.get(word.toLowerCase());
    }

    public static Set<String> toPossibleWords(List<Arpabet> arpabets) {
        return reverseDictionary.get(arpabets);
    }

    public static Map<String, List<Arpabet>> getDictionary() {
        return dictionary;
    }

    private static Map<String, List<Arpabet>> loadDictionary() {
        dictionary = new HashMap<>(133030);
        try{
            // http://www.speech.cs.cmu.edu/cgi-bin/cmudict
            final BufferedReader buf = new BufferedReader(new FileReader("/Users/drufener/steno/src/main/resources/text_to_phonemes.txt"));
            String lineJustRead;

            while (true) {
                lineJustRead = buf.readLine();
                if (lineJustRead == null) {
                    break;
                } else {
                    final String[] wordAndPhonemes = lineJustRead.split("\t");
                    final String word = wordAndPhonemes[0].toLowerCase();
                    final List<Arpabet> phonemes = Arrays.stream(wordAndPhonemes[1].split(" ")).map(Arpabet::valueOf).collect(Collectors.toList());
                    dictionary.put(word, phonemes);
                }
            }

            buf.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dictionary;
    }
}
