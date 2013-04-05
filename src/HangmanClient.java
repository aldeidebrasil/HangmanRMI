/*
 * HangmanClient.java 	Sep 22, 2005
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.ConnectException;
import java.rmi.Naming;

/**
 * This class uses the Hangman remote object to be able to play.
 * 
 * @author Christian Rubiales
 */
public class HangmanClient {

    
    /**
     * The remote Hangman object implementation.
     */
    private Hangman hangman;
    
    /**
     * The input reader of the client.
     */
    private BufferedReader in;
    
    /**
     * The String to hold the letters used by the player for a single game.
     * This must be reinitialized to empty when a new game starts.
     */
    private String usedLetters = "";
    
    /**
     * Constructor for HangmanClient which takes the locaiton of the server.
     * 
     * @param server - The location of the Hangman remote object implementation.
     */
    public HangmanClient(String server) {
        
        // initialize the input reader
        in = new BufferedReader(new InputStreamReader(System.in));
        
        // connect to server
        try {
            
            // name of remote server object bound to rmi registry
            String remoteName = "rmi://" + server + "/Hangman";
            
            // lookup HangmanImpl remote object
            hangman = (Hangman) Naming.lookup(remoteName);
            
        } catch (Exception e) {
            System.err.println("Error establishing connection.");
            e.printStackTrace();
        }
    }
    
    /**
     * Gets the unique letters for a certain phrase.
     * 
     * @param phrase - The phrase to get the unique letters with.
     * @return the String of unique letters from the phrase.
     */
    public String getUniqueLetters(String phrase) {
        String ret = "";
        for (int i = 0; i < phrase.length(); i++) {
            char character = phrase.charAt(i);
            if (character != ' ') {
                boolean flag = false;
                for (int j = 0; j < ret.length(); j++) {
                    if (ret.charAt(j) == character) {
                        flag = true;
                        break;
                    }
                }
                if (!flag) ret += "" + character;
            }
        }

        return ret;
    }
    
    /**
     * Checks if an input is valid. If the input contains more than one 
     * character, only the first character is used. Spaces are ignored, but if
     * the input results in a blank line, then the input invalid. If the input
     * is resolves to a valid letter, it is checked against previous input
     * letters. If the input letter is previously used, it is invalid.
     * 
     * @param input - The String to check.
     * @return <code>true</code> if the input is valid, otherwise return
     *      <code>false</code>. 
     */
    public boolean verifyInput(String input) {
        char c = ' ';
        input = input.toUpperCase().trim(); // strip spaces from the input
        
        // fail if the input is blank
        if (input.length() >= 1) {
            c = input.charAt(0);
            
            // fail, if the first valid character is not a letter
            if (!("" + c).matches("[A-Z]")) {
                return false;
            }

            // fail if the letter is already used
            for (int i = 0; i < usedLetters.length(); i++) {
                if (usedLetters.charAt(i) == c) {
                    return false;
                }
            }
            
        } else {
            return false;
        }
        
        // the valid letter is already used, add to the usedLetters String
        usedLetters += c;
        
        return true;
    }
    
    /**
     * Decrypts the <code>phrase</code> encrypted with <code>encryptor</code>
     * using the <code>usedLetters</code> String.
     * 
     * @param phrase - The original phrase to decrypt using the usedLetters 
     *      String.
     * @param encryptor - The character used to encrypt the phrase with.
     * @return the decrypted String processed with the usedLetters String.
     */
    public String decryptPhrase(String phrase, char encryptor) {
        String ret = "";
        
        // initially the return String is encrypted all over
        for (int i = 0; i < phrase.length(); i++) {
            ret += (phrase.charAt(i) == ' ') ? ' ' : encryptor;
        }
        
        // decrypt the phrase using each of the characters of usedLetters
        for (int i = 0; i < usedLetters.length(); i++) {
            char c = usedLetters.charAt(i);
            for (int j = 0; j < phrase.length(); j++) {
                if (c == phrase.charAt(j)) {
                    char[] stringArray = ret.toCharArray();
                    stringArray[j] = c;
                    ret = new String(stringArray);
                }
            }
        }
        
        return ret;
    }
    
    /**
     * Asks the user if he or she wants to quit already. If the user enters "N"
     * or "n" then the application is stopped.
     */
    public void promptToQuit() {
        try {
            System.out.print("Do you want to play again? " +
                    "(Press 'N' or 'n' then <Enter> to end the game) :");
            String input = in.readLine();
            if (input.trim().toUpperCase().equals("N")) {
                System.exit(0);
            }
        } catch (IOException e) {
            promptToQuit();
        }
    }
    
    /**
     * Begin a single game with the specified phrase.
     * 
     * @param phrase - The phrase to play a Hangman game with.  
     */
    public void play(String phrase) {
        String letters = getUniqueLetters(phrase);
        int moves = (letters.length() < 21) 
                ? letters.length() + 5 
                : letters.length() + (26 - letters.length());
        
        int i = 0;
        for (i = moves; i > 0; i--) {
            System.out.println("\n");
            System.out.println("MOVES: " + i);
            System.out.println();
            System.out.println(decryptPhrase(phrase, '*'));
            
            // continually get input until valid
            String input = "";
            while (input.equals("")) {
                try {
                    System.out.print(">");
                    input = in.readLine();
                    if (verifyInput(input)) {
                        
                        // if the decrypted phrase is equal to the original
                        // phrase, then the player wins
                        if (decryptPhrase(phrase, '*').equals(phrase)) {
                            System.out.println("\n\nNice one!");
                            promptToQuit();
                        }
                    } else {
                        throw new IOException("Error in input.");
                    }
                } catch (IOException e) {
                    // input was invalid, try to prompt for input again
                    input = "";
                    continue;
                }
            }
        }
        
        // end of a single game, reinitialize usedLetters String
        usedLetters = "";

        // if all moves are used up, then the player loses
        if (i <= 0) {
            System.out.println("\n\nYou lose.");
            promptToQuit();
        }
    }
    
    /**
     * Print the welcome message and the game instructions.
     */
    public void printWelcome() {
        System.out.println("\n\nWelcome to Hangman using RMI!\n");
        System.out.println(
            "Due to Java limitations, character inputs should " +
            "be followed by <Enter>. If the input is greater than one " +
            "character, only the first valid character is used.\n\n");
    }
    
    /**
     * The Hangman game proper on the client side. 
     * 
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        HangmanClient client = null;
        
        // if no server IP address or host name specified, use "localhost"
        if (args.length == 0) {
            client = new HangmanClient("localhost");
        }
        
        // otherwise use specified host
        else {
            client = new HangmanClient(args[0]);
        }
        
        client.printWelcome();
        
        // play until <Control> + C is encountered
        for (;;) {
            String phrase = client.hangman.getRandomHangmanPhrase();
            client.play(phrase.toUpperCase());
        }
    }

}
