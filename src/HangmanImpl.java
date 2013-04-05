/*
 * HangmanImpl.java 	Sep 22, 2005
 */

import java.lang.ref.PhantomReference;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;

/**
 * Implements the Hangman remote interface to provide a Hangman remote object.
 * 
 * @author Christian Rubiales
 */
public class HangmanImpl extends UnicastRemoteObject implements Hangman {

    /**
     * Initialize the server.
     * 
     * @throws RemoteException
     */
    public HangmanImpl() throws RemoteException {
        super();
    }
    
    /**
     * @see Hangman#getRandomHangmanPhrase()
     */
    public String getRandomHangmanPhrase() throws RemoteException {
        int index = (int) (Math.random() * (phrases.length - 1));
        return phrases[index];
    }

    /**
     * Launch Hangman remote object.
     * 
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        System.out.println("Initializing Hangman...");
        
        // create remote object
        Hangman hangman = new HangmanImpl();
        
        // specify remote object name
        String serverObjectName = "rmi://localhost/Hangman";
        
        // bind Hangman remote object in RMI registry
        Naming.rebind(serverObjectName, hangman);
        
        System.out.println("Hangman phrases server is running.");
    }
    
    /**
     * The array of phrases for the Hangman clients.
     */
    private String[] phrases = {
        "A faithful friend is the medicine of life",
        "A friend is a person with whom I may be sincere",
        "A friend is a present you give yourself",
        "A friend is a single soul dwelling in two bodies",
        "A friend is one who knows us but loves us anyway",
        "A friend loves at all times",
        "A friend may well be reckoned the masterpiece of nature",
        "A good friend is my nearest relation",
        "A problem well stated is a problem half solved",
        "A ship in port is safe but that is not what ships are for",
        "An honest answer is the sign of true friendship",
        "Count your age with friends but not with years",
        "Do not ever take a fence down until you know why it was put up",
        "Doing more things faster is no substitute for doing the right things",
        "Every man passes his life in the search after friendship",
        "Excellence is not an event it is a habit",
        "Facts do not cease to exist because they are ignored",
        "Friends are the sunshine of life",
        "Friends show their love in times of trouble",
        "Friendship doubles our joy and divides our grief",
        "Friendship is essentially a partnership",
        "Friendship is Love without his wings",
        "Friendship multiplies the good of life and divides the evil",
        "Friendship needs no words",
        "Given enough eyeballs all bugs are shallow",
        "Half knowledge is worse than ignorance",
        "Hold a true friend with both your hands",
        "I hear and I forget I see and I remember I do and I understand",
        "I often quote myself It adds spice to my conversation",
        "If you cannot feed a hundred people then feed just one",
        "If you have always done it that way it is probably wrong",
        "If you judge people you have no time to love them",
        "It is easier to get forgiveness than permission",
        "It is not an optical illusion it just looks like one",
        "It should be possible to explain the laws of physics to a barmaid",
        "Judge a man by his questions rather than his answers",
        "Let your best be for your friend",
        "Love your enemies for they tell you your faults",
        "My friend is he who will tell me my faults in private",
        "Never tell everything you know",
        "No man is useless while he has a friend",
        "One loyal friend is worth ten thousand relatives",
        "One who makes no mistakes never makes anything",
        "Only the educated are free",
        "Plant a seed of friendship reap a bouquet of happiness",
        "Practice is the best of all instructors",
        "Seeking to know is too often learning to doubt",
        "Testing can show the presence of errors but not their absence",
        "The art of teaching is the art of assisting discovery",
        "The best mirror is an old friend",
        "The best way to have a good idea is to have lots of ideas",
        "The greatest mistake is to imagine that we never err",
        "The key to failure is to try to please everyone",
        "The only reward for love is the experience of loving",
        "The only way to have a friend is to be one",
        "The standard definition of AI is that which we do not understand",
        "There has never been set up a statue in honor of a critic",
        "There is no obfuscated Perl contest because it is pointless",
        "To teach is to learn again",
        "Try not to become a man of success but of value",
        "What we have to learn to do we learn by doing",
        "What we see depends mainly on what we look for",
        "Whatever our souls are made up his and mine are the same",
        "Where there is love there is life ",
        "Who finds a faithful friend finds a treasure",
        "Winning is not everything It is the only thing",
        "You are what you repeatedly do",
        "You should never watch laws and sausage being made",
        "Your friend is your needs answered"        
    };
}
