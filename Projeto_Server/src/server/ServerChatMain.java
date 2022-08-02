/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

import Dados.Mensagem;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jorge Martins
 */
public class ServerChatMain {

    private static Vector<ServerChatThread> listClients = new Vector<ServerChatThread>();
    private static boolean verify, verify2;
    private static ObjectOutputStream output;
    private static ObjectInputStream input;
    private static SimpleDateFormat sdf;
    private static String date;

    public static void main(String[] args) {
        int port = 4000;
        String pattern = "HH:mm:ss";
        sdf = new SimpleDateFormat(pattern);
        try {
            ServerSocket serversocket = new ServerSocket(port);
            System.out.println("IP do Server: " + InetAddress.getLocalHost().getHostAddress() + ":" + port);
            while (true) {
                System.out.println("Mensagem ->Servidor à escuta");
                Socket socket = serversocket.accept();
                date = sdf.format(new Date());
                System.out.println("( " + date + " )Servidor recebeu um novo pedido de ligação");
                try {

                    output = new ObjectOutputStream(socket.getOutputStream());

                    input = new ObjectInputStream(socket.getInputStream());

                    Mensagem msg = (Mensagem) input.readObject();
                    date = sdf.format(new Date());

                    if (msg.getOperacao() == 0) { //a operação 0 é o numero de tentativa de conexão qualquer outro numero receberá uma mensagem nok
                        synchronized (listClients) {
                            verify = true;
                            verify2 = true;
                            if (listClients.isEmpty()) { //se tiver vazio também preciso de verificar se o nome esta a branco ou se usam a palavra Todos para o username
                                if (msg.getNome().equals("Todos")) {
                                    verify = false;
                                    System.out.println("( " + date + " )Tentativa de autenticar com o nome reservado Todos");

                                }
                                if (msg.getNome().isBlank()) {
                                    verify = false;
                                    System.out.println("( " + date + " )Tentativa de autenticar com o nome em branco");
                                }

                                if (verify) {
                                    System.out.println("( " + date + " ) Mensagem ->Servidor ip " + socket.getLocalAddress().getHostAddress() + ":" + socket.getLocalPort() + " conectado com um cliente ip " + socket.getInetAddress().getHostAddress() + ":" + socket.getPort());
                                    Mensagem snd = new Mensagem(3, "login ok", "conection started");

                                    output.writeObject(snd);
                                    output.flush();

                                    ServerChatThread client = new ServerChatThread(socket, listClients, output, input, msg);

                                    listClients.add(client);

                                    client.start();
                                } else {
                                    System.out.println("( " + date + " )Mensagem ->Tentativa de Cliente em ligar falhada");
                                    Mensagem snd = new Mensagem(4, "login nok", "refused connect"); //nem vai ler a mensagem apenas o numero de operação

                                    try {
                                        output.writeObject(snd);
                                        output.close();
                                        input.close();
                                        socket.close();
                                    } catch (IOException io) {
                                        System.out.println("( " + date + " )Ligação com cliente terminada antes do tempo");
                                    }
                                }

                            } else {

                                if (msg.getNome().equals("Todos")) {
                                    verify = false;
                                    System.out.println("( " + date + " )Tentativa de autenticar com o nome reservado Todos");

                                }
                                if (msg.getNome().isBlank()) {
                                    verify = false;
                                    System.out.println("( " + date + " )Tentativa de autenticar com o nome em branco");
                                }
                                if (verify) { //só deve utilizar este recurso se as outras duas opções não tiverem posto o verify a false para não consumir a mais

                                    Iterator itClient = listClients.iterator();
                                    while (itClient.hasNext()) {
                                        ServerChatThread client = (ServerChatThread) itClient.next();
                                        boolean verifyUsername = verifyUsername(client, msg);
                                        if (verifyUsername) {
                                            verify = false;
                                            verify2 = false; //para nao entrar desnecessáriamente na proxima condição

                                            System.out.println("( " + date + " )Mensagem ->Tentativa de Cliente em ligar falhada");
                                            Mensagem snd = new Mensagem(4, "login nok", "refused connect, login already exist"); //nem vai ler a mensagem apenas o numero de operação

                                            try {
                                                output.writeObject(snd);
                                                output.close();
                                                input.close();
                                                socket.close();

                                            } catch (IOException ex) {
                                                System.out.println("( " + date + " )Mensagem ->Tentativa de Cliente em ligar falhada, inesperadamente");
                                            } finally {
                                                break; //parar o ciclo
                                            }
                                        }

                                    }
                                }
                                if (verify2) { //caso tenha visto nomes iguais já enviou mensagem e cortou as ligações, codigo anterior
                                    if (verify) {
                                        System.out.println("( " + date + " )Mensagem ->Servidor ip " + socket.getLocalAddress().getHostAddress() + ":" + socket.getLocalPort() + " conectado com um cliente ip " + socket.getInetAddress().getHostAddress() + ":" + socket.getPort());
                                        ServerChatThread client2 = new ServerChatThread(socket, listClients, output, input, msg);

                                        listClients.add(client2);

                                        client2.start();
                                        Mensagem snd = new Mensagem(3, "login ok", "conection started");

                                        try {
                                            output.writeObject(snd);

                                        } catch (IOException ex) {
                                            System.out.println("( " + date + " )Cliente terminou a ligação inesperadamente");

                                        }
                                    } //caso tenha erro ele irá enviar esta mensagem para o cliente
                                    else {
                                        System.out.println("( " + date + " )Mensagem ->Tentativa de Cliente em ligar falhada");
                                        Mensagem snd = new Mensagem(4, "login nok", "refused connect"); //nem vai ler a mensagem apenas o numero de operação

                                        try {
                                            output.writeObject(snd);
                                            output.close();
                                            input.close();
                                            socket.close();
                                        } catch (IOException io) {
                                            System.out.println("( " + date + " )Ligação com cliente terminada antes do tempo");
                                        }
                                    }
                                }
                            }

                        }
                    } else {// caso o cliente tenha enviado uma mensagem com a operação errada irá receber esta mensagem
                        System.out.println("( " + date + " )Mensagem ->Tentativa de Cliente em ligar falhada");
                        Mensagem snd = new Mensagem(4, "login nok", "refused connect"); //nem vai ler a mensagem apenas o numero de operação

                        try {
                            output.writeObject(snd);
                            output.close();
                            input.close();
                            socket.close();
                        } catch (IOException io) {
                            System.out.println("( " + date + " )Ligação com cliente terminada antes do tempo");
                        }
                    }
                } catch (IOException ioe) {
                    System.out.println("( " + date + " )Após conexão, ligação com cliente falhou");
                }

            }
        } catch (Exception e) {
            System.out.println("( " + date + " )Erro:" + e);
        }
    }

    private static boolean verifyUsername(ServerChatThread client, Mensagem msg) { //algo para verificar os nomes, espaço não significa diferente
        int comp = 0;
        String auxNameForSplit = "";
        String[] auxName = client.getNameObj().split(" "); //primeiro dividir as strings
        String[] auxMsgNome = msg.getNome().split(" ");
        //este ciclo é para dividir e isolar os nomes usados na string sem espaços para o que esta guardado
        for (int i = 0; i < auxName.length; ++i) {
            auxName[i] = auxName[i].trim();
            if (!auxName[i].isBlank()) {
                auxNameForSplit = auxNameForSplit + auxName[i] + ",";
            }
        }
        String[] auxNameFinal = auxNameForSplit.split(",");
        auxNameForSplit = ""; //reniciar a variavel para reutilizar
        //este ciclo faz o mesmo que o anterior mas para o nome que o utilizador tentou inserir
        for (int i = 0; i < auxMsgNome.length; ++i) {
            auxMsgNome[i] = auxMsgNome[i].trim();
            if (!auxMsgNome[i].isBlank()) {
                auxNameForSplit = auxNameForSplit + auxMsgNome[i] + ",";
            }
        }
        String[] auxMsgNomeFinal = auxNameForSplit.split(",");

        if (auxNameFinal.length != auxMsgNomeFinal.length) { //se tem o mesmo tamanho podem ser nomes iguais, aqui ainda não sabemos quase nada
            return false;
        } else { //nesta fase eles têm o tamanho igual, usar a legnth de um ou outro dá no mesmo
            for (int i = 0; i < auxNameFinal.length; ++i) { //fazer o trim para ficar só com nomes e não espaços em branco, ter a certeza que não ficou nada
                auxNameFinal[i] = auxNameFinal[i].trim();
                auxMsgNomeFinal[i] = auxMsgNomeFinal[i].trim();
                if (auxNameFinal[i].equals(auxMsgNomeFinal[i])) {
                    ++comp; //apenas sera um nome igual de forma completa se este numero for igual ao tamanho das array de string
                }
            }
            if (comp == auxNameFinal.length) {
                return true; //significa que o nome é completamente iguals
            } else {
                return false; //indica que o nome não é completamente iguals
            }

        }
    }

}
