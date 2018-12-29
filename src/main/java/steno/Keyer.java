package steno;

public class Keyer {
    private final ArpabetCompressor compressor;
    private final NextWordPredictor nextWordPredictor;

    public Keyer(ArpabetCompressor compressor, NextWordPredictor nextWordPredictor) {
        this.compressor = compressor;
        this.nextWordPredictor = nextWordPredictor;
    }

    //TODO
}
