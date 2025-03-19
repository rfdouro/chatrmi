package br.org.rfdouro;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class ChatClientImpl implements ChatClient {
 private String name;

 public ChatClientImpl(String name) {
  this.name = name;
 }

 @Override
 public void receiveMessage(String message) throws RemoteException {
  System.out.println(message);
 }

 public static void main(String[] args) {
  try {
   Scanner scanner = new Scanner(System.in);
   System.out.print("Digite seu nome: ");
   String name = scanner.nextLine();

   ChatClientImpl client = new ChatClientImpl(name);
   ChatClient stub = (ChatClient) UnicastRemoteObject.exportObject(client, 0);

   Registry registry = LocateRegistry.getRegistry("localhost", 1099);
   Chat chatServer = (Chat) registry.lookup("Chat");

   chatServer.registerClient(stub);

   System.out.println("Conectado ao chat. Digite suas mensagens:");

   while (true) {
    String message = scanner.nextLine();
    chatServer.sendMessage(name + ": " + message);
   }
  } catch (Exception e) {
   System.err.println("Erro no cliente: " + e.toString());
   e.printStackTrace();
  }
 }
}
