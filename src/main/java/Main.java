import edu.berkeley.nlp.lm.NgramLanguageModel;
import edu.berkeley.nlp.lm.StupidBackoffLm;

import static edu.berkeley.nlp.lm.io.LmReaders.readGoogleLmBinary;

public class Main {
    public static void main(String [] args) {
        final NgramLanguageModel lm = loadLanguageModel();

    }

    public static NgramLanguageModel loadLanguageModel() {
        System.out.println("Loading Language Model (~1 minute)");
        long startTime = System.currentTimeMillis();
        //  Google Books binaries for English from http://tomato.banatao.berkeley.edu:8080/berkeleylm_binaries/
        final StupidBackoffLm<String> lm = readGoogleLmBinary("/Users/drufener/steno/src/main/resources/eng.blm", "/Users/drufener/steno/src/main/resources/vocab_cs");
        long endTime = System.currentTimeMillis();
        System.out.println("Loaded Language Model Time (ms): " + (endTime - startTime));
        return lm;
    }
}
