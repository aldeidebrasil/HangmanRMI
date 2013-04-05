/*
 * Hangman.java 	Sep 22, 2005
 */

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Hangman interface that declares methods used by a Hangman client.
 * 
 * @author Christian Rubiales
 */
public interface Hangman extends Remote {
    
    /**
     * Obtain a random phrase to be used by the Hangman client from the server.
     * 
     * @return the random phrase.
     * @throws RemoteException
     */
    public String getRandomHangmanPhrase() throws RemoteException;
    
}
