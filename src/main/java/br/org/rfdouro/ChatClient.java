package br.org.rfdouro;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatClient extends Remote {
 void receiveMessage(String message) throws RemoteException;
}
