package steno;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static steno.Schemes.GENERIC_CLASS.*;

public class ThreeKeyWithChordingPrototype extends JFrame implements KeyListener, ActionListener {
    private static final Font SELECTION_FONT = new Font("Verdana", Font.BOLD, 48);
    private static final Font MESSAGE_FONT = new Font("Verdana", Font.PLAIN, 14);
    static final int MILLIS_DELTA_THRESHOLD_FOR_CHORDING = 30;
    private JTextArea displayArea;
    private JTextField typingArea;
    static final String newline = System.getProperty("line.separator");
    final Keyer keyer = new ThreeButtonKeyer(new AlphabetCompressor(Schemes.THREE_CLASS_ALPHABETIC_SPLIT));
    private List<String> rankedPossibleWords = null;
    private java.util.List<String> message = new ArrayList<>();
    private int currentIndex = 0;
    private List<Enum> currentWord = new ArrayList<>();
    private List<Enum> currentChord = new ArrayList<>();

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
        ThreeKeyWithChordingPrototype frame = new ThreeKeyWithChordingPrototype("ThreeKeyWithChordingPrototype");
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

    public ThreeKeyWithChordingPrototype(String name) {
        super(name);
    }


    /**
     * Handle the key typed event from the text field.
     */
    public void keyTyped(KeyEvent e) {
//        displayInfo(e, "KEY TYPED: ");
    }

    /**
     * Handle the key pressed event from the text field.
     */
    public void keyPressed(KeyEvent e) {
        typingArea.setText("");
    }

        /*
        Key	        Code
        left arrow	37
        up arrow	38
        right arrow	39
        down arrow	40
        i	73
        j	74
        k	75
        l	76
         */

    /**
     * Handle the key released event from the text field.
     */
    public void keyReleased(KeyEvent e) {
        final int keyCode = e.getKeyCode();
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
        switch (keyCode) {
            case 74:
            case 37:
                currentChord.add(CLASS_A);
                break;
            case 75:
            case 40:
                currentChord.add(CLASS_B);
                break;
            case 76:
            case 39:
                currentChord.add(CLASS_C);
                break;
        }
    }

    private void processCurrentChord() {
        System.out.println(Joiner.on(" ").join(currentChord));
        switch (currentChord.size()) {
            case 1:
                if (rankedPossibleWords != null) {
                    addWord();
                }
                currentWord.addAll(currentChord);
                break;
            case 2:
                if (currentChord.containsAll(ImmutableList.of(CLASS_A, CLASS_C))) {
                    currentIndex = 0;
                    currentWord = new ArrayList<>();
                    rankedPossibleWords = null;
                    printMessage();
                    break;
                } else if (currentChord.containsAll(ImmutableList.of(CLASS_A, CLASS_B))) {
                    nextLetter();
                } else if (currentChord.containsAll(ImmutableList.of(CLASS_B, CLASS_C))) {
                    selectLetter();
                } else {
                    throw new RuntimeException("Weird Chord!:" + Joiner.on(" ").join(currentChord));
                }
                break;
            case 3:
                if (rankedPossibleWords == null) {
                    processCurrentWord();
                } else {
                    currentIndex++;
                    printPossibleWordAtCurrentIndex();
                    break;
                }
                break;
        }
        currentChord = new ArrayList<>();
    }

    private void nextLetter() {
        if (rankedPossibleWords == null) {

        } else {

        }
    }

    private void selectLetter() {

    }

    private void print(Object o) {
        displayArea.append(String.valueOf(o) + newline);
    }

    private void processCurrentWord() {
        if (!currentWord.isEmpty()) {
            final Set<String> possibleWords = keyer.getCompressor().decode(currentWord);
            if (possibleWords == null) {
                handleError();
            } else {
                final java.util.List<String> context = message.subList(Math.max(0, message.size() - 3), message.size());
                final List<String> rankedPossibleWords = NextWordPredictor.sortByLikelihoodDescending(possibleWords, context);
                this.rankedPossibleWords = rankedPossibleWords;
                printPossibleWordAtCurrentIndex();
                currentWord = new ArrayList<>();
            }
        }
    }

    private void printPossibleWordAtCurrentIndex() {
        clearDisplay();
        displayArea.setFont(SELECTION_FONT);
        print(rankedPossibleWords.get(currentIndex % rankedPossibleWords.size()));
    }

    private void printMessage() {
        clearDisplay();
        displayArea.setFont(MESSAGE_FONT);

        print(Joiner.on(" ").join(message));
    }


    private void handleError() {
        currentIndex = 0;
        currentWord = new ArrayList<>();
        rankedPossibleWords = null;
        clearDisplay();
        displayArea.setFont(SELECTION_FONT);
        print("Error!");
    }

    private void addWord() {
        message.add(rankedPossibleWords.get(currentIndex));
        currentIndex = 0;
        rankedPossibleWords = null;
        printMessage();
    }

    private void clearDisplay() {
        displayArea.setText("");
    }

    /**
     * Handle the button click.
     */
    public void actionPerformed(ActionEvent e) {
        rankedPossibleWords = null;
        message = new ArrayList<>();
        currentIndex = 0;
        currentWord = new ArrayList<>();

        //Clear the text components.
        clearDisplay();
        typingArea.setText("");

        //Return the focus to the typing area.
        typingArea.requestFocusInWindow();
    }
}