package br.org.rfdouro;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Chat extends Remote {
 void sendMessage(String message) throws RemoteException;

 void registerClient(ChatClient client) throws RemoteException;
}