
/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package steno;

/*
 * NineKeyPrototype
 */

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static steno.Schemes.GENERIC_CLASS.CLASS_A;
import static steno.Schemes.GENERIC_CLASS.CLASS_B;

public class NineKeyPrototype extends JFrame implements KeyListener, ActionListener {
    //TODO: I'm sure there's a fancier way to do these index conversions
    public static final int ABORT_CODE = 100;

    static final Map<Integer, String> INDEX_TO_CLASS_STRING = ImmutableMap.<Integer, String>builder()
            .put(0, "_      ")
            .put(1, "^      ")
            .put(2, "_ _    ")
            .put(3, "_ ^    ")
            .put(4, "^ _    ")
            .put(5, "^ ^    ")
            .put(6, "_ _ _  ")
            .put(7, "_ _ ^  ")
            .put(8, "_ ^ _  ")
            .put(9, "^ _ _  ")
            .put(10, "^ _ ^ ")
            .put(11, "^ ^ _ ")
            .put(12, "^ ^ ^ ")
            .put(13, "_ _ _ _")
            .put(14, "_ _ _ ^")
            .put(ABORT_CODE, "^ ^ ^ ^")
            .build();

    static final Map<List<Schemes.GENERIC_CLASS>, Integer> CLASSES_TO_INDEX = ImmutableMap.<List<Schemes.GENERIC_CLASS>, Integer>builder()
            .put(ImmutableList.of(CLASS_A), 0)
            .put(ImmutableList.of(CLASS_B), 1)
            .put(ImmutableList.of(CLASS_A, CLASS_A), 2)
            .put(ImmutableList.of(CLASS_A, CLASS_B), 3)
            .put(ImmutableList.of(CLASS_B, CLASS_A), 4)
            .put(ImmutableList.of(CLASS_B, CLASS_B), 5)
            .put(ImmutableList.of(CLASS_A, CLASS_A, CLASS_A), 6)
            .put(ImmutableList.of(CLASS_A, CLASS_A, CLASS_B), 7)
            .put(ImmutableList.of(CLASS_A, CLASS_B, CLASS_A), 8)
            .put(ImmutableList.of(CLASS_B, CLASS_A, CLASS_A), 9)
            .put(ImmutableList.of(CLASS_B, CLASS_A, CLASS_B), 10)
            .put(ImmutableList.of(CLASS_B, CLASS_B, CLASS_A), 11)
            .put(ImmutableList.of(CLASS_B, CLASS_B, CLASS_B), 12)
            .put(ImmutableList.of(CLASS_A, CLASS_A, CLASS_A, CLASS_A), 13)
            .put(ImmutableList.of(CLASS_A, CLASS_A, CLASS_A, CLASS_B), 14)
            .put(ImmutableList.of(CLASS_B, CLASS_B, CLASS_B, CLASS_B), ABORT_CODE)
            .build();
    public static final int MAX_SELECTION_CHOICES = 5;

    JTextArea displayArea;
    JTextField typingArea;
    static final String newline = System.getProperty("line.separator");
    static final int MILLIS_DELTA_THRESHOLD_FOR_CHORDING = 100;
    List<Integer> currentChord = new ArrayList<>();
    List<Enum> currentWord = new ArrayList<>();
    final Keyer keyer = new NineButtonKeyer(new AlphabetCompressor(Schemes.TWO_CLASS_ALPHABETIC_SPLIT));
    List<String> rankedPossibleWords = null;
    private List<String> message = new ArrayList<>();
    private List<Schemes.GENERIC_CLASS> selectionChord;

    public static void main(String[] args) {
        /* Use an appropriate Look and Feel */
        try {
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        /* Turn off metal's use of bold fonts */
        UIManager.put("swing.boldMetal", Boolean.FALSE);

        //Schedule a job for event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        NineKeyPrototype frame = new NineKeyPrototype("NineKeyPrototype");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set up the content pane.
        frame.addComponentsToPane();


        //Display the window.
        frame.pack();
        frame.setVisible(true);
        frame.setPreferredSize(new Dimension(800, 800));
    }

    private void addComponentsToPane() {

        JButton button = new JButton("Clear");
        button.addActionListener(this);

        typingArea = new JTextField(20);
        typingArea.addKeyListener(this);

        //Uncomment this if you wish to turn off focus
        //traversal.  The focus subsystem consumes
        //focus traversal keys, such as Tab and Shift Tab.
        //If you uncomment the following line of code, this
        //disables focus traversal and the Tab events will
        //become available to the key event listener.
        //typingArea.setFocusTraversalKeysEnabled(false);

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setPreferredSize(new Dimension(375, 125));

        getContentPane().add(typingArea, BorderLayout.PAGE_START);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(button, BorderLayout.PAGE_END);
    }

    public NineKeyPrototype(String name) {
        super(name);
    }


    /** Handle the key typed event from the text field. */
    public void keyTyped(KeyEvent e) {
//        displayInfo(e, "KEY TYPED: ");
    }

    /** Handle the key pressed event from the text field. */
    public void keyPressed(KeyEvent e) {
//        displayInfo(e, "KEY PRESSED: ");
    }

    /** Handle the key released event from the text field. */
    public void keyReleased(KeyEvent e) {
        final int keyCode = e.getKeyCode();
        if (keyCode == 32) {
            if (rankedPossibleWords == null) {
                processCurrentChord();
                processCurrentWord();
            } else {
                if (rankedPossibleWords.size() <= MAX_SELECTION_CHOICES) {
                    rankedPossibleWords = null;
                } else {
                    rankedPossibleWords = rankedPossibleWords.subList(MAX_SELECTION_CHOICES, rankedPossibleWords.size());
                    printRankedPossibleWords();
                }
            }
        } else {
            if (currentChord.isEmpty()) {
                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                processCurrentChord();
                            }
                        },
                        MILLIS_DELTA_THRESHOLD_FOR_CHORDING
                );
            }
            currentChord.add(keyCode);
        }
    }

    private void print(Object o) {
        displayArea.append(String.valueOf(o) + newline);
    }

    private void processCurrentWord() {
        if (!currentWord.isEmpty()) {
            final Set<String> possibleWords = keyer.getCompressor().decode(currentWord);
            final List<String> context = message.subList(Math.max(0, message.size() - 3), message.size());
            final List<String> rankedPossibleWords = NextWordPredictor.sortByLikelihoodDescending(possibleWords, context);
            this.rankedPossibleWords = rankedPossibleWords;
            printRankedPossibleWords();
            currentWord = new ArrayList<>();
        }
    }

    private void printRankedPossibleWords() {
        clearDisplay();
        for (int i = 0; i < Math.min(rankedPossibleWords.size(), MAX_SELECTION_CHOICES); i++) {
            print(indexToClassString(i) + ": " + rankedPossibleWords.get(i));
        }
        print(indexToClassString(ABORT_CODE) + ": <ABORT>");
    }

    private void processCurrentChord() {
        final List<Schemes.GENERIC_CLASS> results = new ArrayList<>();

        if (currentChord.contains(72)) {
            results.add(CLASS_A);
        } else if (currentChord.contains(89)) {
            results.add(CLASS_B);
        }

        if (currentChord.contains(74)) {
            results.add(CLASS_A);
        } else if (currentChord.contains(85)) {
            results.add(CLASS_B);
        }

        if (currentChord.contains(75)) {
            results.add(CLASS_A);
        } else if (currentChord.contains(73)) {
            results.add(CLASS_B);
        }

        if (currentChord.contains(76)) {
            results.add(CLASS_A);
        } else if (currentChord.contains(79)) {
            results.add(CLASS_B);
        }

        if (rankedPossibleWords != null) {
            selectionChord = results;
            if (selectionChord != null && !selectionChord.isEmpty()) {
                processWordSelectionChord();
            }
        } else {
            currentWord.addAll(results);
        }
        currentChord = new ArrayList<>();
    }

    private void processWordSelectionChord() {
        final int selectedWordIndex = classChordToIndex(selectionChord);
        if (selectedWordIndex != ABORT_CODE) {
            final String selectedWord = rankedPossibleWords.get(selectedWordIndex);
            message.add(selectedWord);
        }
        printMessage();
        rankedPossibleWords = null;
    }

    private String indexToClassString(int i) {
        return INDEX_TO_CLASS_STRING.get(i);
    }

    private int classChordToIndex(List<Schemes.GENERIC_CLASS> selectionChord) {
        return CLASSES_TO_INDEX.get(selectionChord);
    }


    private void printMessage() {
        clearDisplay();
        print(Joiner.on(" ").join(message));
    }

    private void clearDisplay() {
        displayArea.setText("");
    }

    /** Handle the button click. */
    public void actionPerformed(ActionEvent e) {
        message = new ArrayList<>();
        rankedPossibleWords = null;

        //Clear the text components.
        clearDisplay();
        typingArea.setText("");

        //Return the focus to the typing area.
        typingArea.requestFocusInWindow();
    }
}
