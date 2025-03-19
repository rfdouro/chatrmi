package br.org.rfdouro;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ChatServer implements Chat {
 private List<ChatClient> clients = new ArrayList<>();

 public ChatServer() {
 }

 @Override
 public void sendMessage(String message) throws RemoteException {
  System.out.println("Mensagem recebida: " + message);
  broadcastMessage(message);
 }

 @Override
 public void registerClient(ChatClient client) throws RemoteException {
  clients.add(client);
  System.out.println("Novo cliente registrado.");
 }

 private void broadcastMessage(String message) {
  for (ChatClient client : clients) {
   try {
    client.receiveMessage(message);
   } catch (RemoteException e) {
    System.err.println("Erro ao enviar mensagem para um cliente: " + e.getMessage());
   }
  }
 }

 public static void main(String[] args) {
  try {
   ChatServer server = new ChatServer();
   Chat stub = (Chat) UnicastRemoteObject.exportObject(server, 0);

   Registry registry = LocateRegistry.createRegistry(8080);
   registry.rebind("Chat", stub);

   System.out.println("Servidor de Chat RMI pronto!");
  } catch (Exception e) {
   System.err.println("Erro no servidor: " + e.toString());
   e.printStackTrace();
  }
 }
}
