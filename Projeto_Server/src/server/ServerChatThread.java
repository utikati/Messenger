/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;


import Dados.Mensagem;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
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
public class ServerChatThread extends Thread implements Serializable{
    
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket socketClient;
    private Mensagem sendMsg;
    private Mensagem receiveMsg;
    private static Vector<ServerChatThread> listClients = new Vector<ServerChatThread>();
    private String name;
    private boolean flag, flag2;
    private String msg = ""; //usada para enviar a lista de clientes no socket como mensagem
    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    private String date;
    
    
    public ServerChatThread(Socket clientSocket, Vector<ServerChatThread>listClients, ObjectOutputStream output, ObjectInputStream input, Mensagem msg) { // enviar a referencia do array
        this.name = msg.getNome();
        this.socketClient = clientSocket;
        this.listClients = listClients;
        this.output = output;
        this.input = input;
    }

    @Override
    public void run() {
        date = sdf.format(new Date());
        sendMsg = new Mensagem(1, "Cliente Logado", "Servidor ( "+date+" ) -> Cliente "+name+" entrou no chat");
        oneForAll(sendMsg);
        listOfClients();
        flag = true;
        flag2 = true; //vai ser usado para o logout
        try{
        while(flag){
            receiveMsg = (Mensagem) input.readObject();
            date = sdf.format(new Date());
            
            switch(receiveMsg.getOperacao()){
                case 1: 
                    String ipText = receiveMsg.getNome()+"( "+date+" ): "+receiveMsg.getTexto();
                    receiveMsg.setTexto(ipText); //envia-se a mensagem final para todos os clientes, assim eles só precisam de printar
                    oneForAll(receiveMsg);
                    
  
                    break;
                case 2: flag = false; System.out.println("( "+date+" )Sistema terminou ligação com cliente "+name);
                        synchronized(listClients){
                            listClients.remove(this);
                        }
                        sendMsg = new Mensagem(1, "Logout Cliente", "Servidor ( "+date+" ) -> Cliente "+name+" realizou logout");
                        
                        oneForAll(sendMsg); //ambos os casos de exception ele fica sem ligação
                        listOfClients();
                        flag2=false; //neste momento já enviou uma mensagem mesmo que dê exception que pode acontecer devido ao sync não envia duas
                        output.close();
                        input.close();
                        this.socketClient.close(); 
                        break;
                case 6: privateSendMsg(receiveMsg); break;
                default: //segurança para envio de mensagens estranhas.
                    try {
                        synchronized(listClients){    
                            listClients.remove(this);
                        }
                        oneForAll(new Mensagem(1, "Logout Cliente", "Servidor ( "+date+" ) -> Cliente "+name+" realizou logout"));
                        listOfClients();
                            flag2=false;
                        output.close();
                        input.close();
                        this.socketClient.close();
                    } catch (IOException ex) {
                        date = sdf.format(new Date());
                        System.out.println("( "+date+" )Sistema terminou ligação inesperadamente");
                    } break;
                
            }
            
        }
        }catch(IOException io){
            date = sdf.format(new Date());
            System.out.println("( "+date+" )Sistema terminou ligação com cliente "+name);
            flag = false;
        }
        catch(Exception e){
            e.printStackTrace();
        }finally{
            try {
                synchronized(listClients){
                    listClients.remove(this);
                }
                if(flag2){
                    oneForAll(new Mensagem(1, "Logout Cliente", "Servidor ( "+date+" ) -> Cliente "+name+" realizou logout")); //ambos os casos de exception ele fica sem ligação
                    listOfClients();
                }
                output.close();
                input.close();
                this.socketClient.close();
                
            } catch (IOException ex) {
                Logger.getLogger(ServerChatThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        
    } 
    
    private void oneForAll(Mensagem msg){ //mensagens de clientes para todos no chat
        
        synchronized(listClients){
        listClients.forEach((client) -> {
                        try {  
                            client.output.writeObject(msg); 
                            client.output.flush();
                        } catch (IOException ex) {
                            if(flag)
                                System.out.println("( "+date+" )Falha no envio no broadcast de mensagem");
                            else
                                System.out.println("( "+date+" )Foi enviada mensagem de aviso de saída de cliente");
                        }
                        
        }); 
        }
    }
    
    private void listOfClients(){
        synchronized(listClients){
            this.msg = "";
            listClients.forEach((client)->{
                this.msg = msg + client.getNameObj() + "\n";
            });
            
            Mensagem message = new Mensagem();
            message.setOperacao(5);
            message.setTexto(this.msg);
            oneForAll(message);
        }
    }
    
    private void privateSendMsg(Mensagem msg){
        String texto = msg.getTexto();
        
        synchronized(listClients){ 
            
            Iterator client = listClients.iterator();
            
            ServerChatThread aux;
            int count = 0;
            
            while(client.hasNext()){
                aux = (ServerChatThread) client.next();
                try {
                            if(msg.getNomePrivado().equals(aux.getNameObj())){
                                String ipText = msg.getNome()+" ( "+date+" )(Privado) : "+texto;
                                msg.setTexto(ipText); //envia-se a mensagem final para todos os clientes, assim eles só precisam de printar
                                aux.output.writeObject(msg); 
                                aux.output.flush();
                                count++;
                            }else{
                                if(msg.getNome().equals(aux.getNameObj())){
                                    String ipText2 = msg.getNome()+" ( "+date+" )(Privado para "+msg.getNomePrivado()+"): "+texto;
                                    msg.setTexto(ipText2); //envia-se a mensagem final para todos os clientes, assim eles só precisam de printar
                                    aux.output.writeObject(msg); 
                                    aux.output.flush();
                                    count++;
                                }
                            }
                            if(count >=2){ //para não correr infinitamente o ciclo
                                break;
                            }
                        } catch (IOException ex) {
                            if(flag)
                                System.out.println("( "+date+" )Falha no envio de mensagem");
                            else
                                System.out.println("( "+date+" )Foi enviada mensagem de aviso de saída de cliente");
                        }
                
            }

        }
        
    }
    
    
    public String getNameObj(){
        return this.name;
    }
    
   
    public void setNameobj(String name){
        this.name = name;
    }
    
    
    
    
}
