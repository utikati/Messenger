/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Cliente;


import Dados.Mensagem;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.InetAddress;

import java.net.Socket;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author Jorge Martins
 */
public class ClientChat extends javax.swing.JFrame{
    
    private String ip;
    private int port;
    InetAddress in;
    Socket socket;
    ObjectInputStream input;
    ObjectOutputStream output;
    
    byte[] send;
    byte buf[] = new byte[1000];
    boolean flag = true;
    Thread thread;
    Mensagem msgSend = new Mensagem();
    Mensagem msgReceive = new Mensagem();
    DefaultComboBoxModel<String> combo;
    
    String elementClient = new String();
    private boolean warningClientLogOut;
    
    

    /**
     * Creates new form Chat
     */
    public ClientChat() {
        initComponents();
        this.sendButton.setEnabled(false);
        this.logoutButton.setEnabled(false);
        this.clientsPrivate.setEnabled(false);
        combo = new DefaultComboBoxModel<>();
        combo.addElement("Todos");
        clientsPrivate.setModel(combo);
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        ipTextField = new javax.swing.JTextField();
        portoTextField = new javax.swing.JTextField();
        loginButton = new javax.swing.JButton();
        nomeTextField = new javax.swing.JTextField();
        logoutButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        sendTextArea = new javax.swing.JTextArea();
        jScrollPane1 = new javax.swing.JScrollPane();
        chatTextArea = new javax.swing.JTextArea();
        sendButton = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        clientsArea = new javax.swing.JTextArea();
        clientsPrivate = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Projeto Cliente");
        setResizable(false);

        ipTextField.setText("localhost");
        ipTextField.setToolTipText("ip do servidor");
        ipTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ipTextFieldActionPerformed(evt);
            }
        });

        portoTextField.setText("4000");
        portoTextField.setToolTipText("porto do servidor");
        portoTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                portoTextFieldActionPerformed(evt);
            }
        });

        loginButton.setText("Login");
        loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginButtonActionPerformed(evt);
            }
        });

        nomeTextField.setToolTipText("nome de utilizador ");
        nomeTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nomeTextFieldActionPerformed(evt);
            }
        });

        logoutButton.setText("Logout");
        logoutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("IP:");

        jLabel2.setText("Porto:");

        jLabel3.setText("Nome:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ipTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                    .addComponent(nomeTextField))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(portoTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addComponent(loginButton))
                    .addComponent(logoutButton, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ipTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(portoTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(loginButton)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nomeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(logoutButton)
                    .addComponent(jLabel3))
                .addGap(228, 228, 228))
        );

        sendTextArea.setColumns(20);
        sendTextArea.setRows(5);
        sendTextArea.setToolTipText("insira a mensagem que quer enviar");
        jScrollPane2.setViewportView(sendTextArea);

        chatTextArea.setEditable(false);
        chatTextArea.setColumns(20);
        chatTextArea.setRows(5);
        jScrollPane1.setViewportView(chatTextArea);

        sendButton.setToolTipText("enviar mensagem");
        sendButton.setLabel("Enviar");
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });

        clientsArea.setEditable(false);
        clientsArea.setColumns(20);
        clientsArea.setRows(5);
        clientsArea.setToolTipText("Lista de Users Online");
        jScrollPane3.setViewportView(clientsArea);

        clientsPrivate.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        clientsPrivate.setToolTipText("Escolha a Opção de Envio");
        clientsPrivate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clientsPrivateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 477, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(sendButton, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                            .addComponent(clientsPrivate, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                    .addComponent(jScrollPane3))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(clientsPrivate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(sendButton, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(8, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void setChatTexFunction(String str){
        this.chatTextArea.setText(str);
    }
    
    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginButtonActionPerformed
        
        if(this.nomeTextField.getText().equals("") || this.nomeTextField.getText().isBlank()){
            JOptionPane.showMessageDialog(this,"Login Vazio" ,"Alerta" , JOptionPane.WARNING_MESSAGE);
        }if(this.nomeTextField.getText().equals("Todos")){
            JOptionPane.showMessageDialog(this,"O Username Todos é um nome reservado do Sistema" ,"Alerta" , JOptionPane.WARNING_MESSAGE);
        }else{
            this.ip = ipTextField.getText();
            this.flag = true; // passar o booleano para true por segurança
            try{
                this.port = Integer.parseInt(this.portoTextField.getText());

                try {
                    JOptionPane.showMessageDialog(this,"Após 20 segundos sem conectar o login é cancelado" ,"Alerta" , JOptionPane.WARNING_MESSAGE);
                    this.socket = new Socket(ip, port); //ip e porto de conexão ao servidor
                    
                    if(socket != null){
                        output = new ObjectOutputStream(socket.getOutputStream()); //enviar mensagem de servidor
                        msgSend = new Mensagem(0, this.nomeTextField.getText(), ""); //envio de primeira mensagem a indicar login na sessao ao servidor após se conectar com o mesmo
                        output.writeObject(msgSend);

                        output.flush(); //para que seja forçado a enviar
                       
                        input = new ObjectInputStream(socket.getInputStream()); //receber mensagem de servidor
                        //não há prob de bloquear aqui porque está a conectar
                        msgReceive = (Mensagem) input.readObject(); 

                        if(msgReceive.getOperacao() == 4){
                            if(msgReceive.getTexto().equals("refused connect, login already exist")){
                                JOptionPane.showMessageDialog(this,"Username já em uso, utilize outro Username" ,"Alerta" , JOptionPane.WARNING_MESSAGE);
                            }else{
                                JOptionPane.showMessageDialog(this,"Servidor não aceitou a ligação" ,"Alerta" , JOptionPane.WARNING_MESSAGE);
                            }
                            output.close();
                            input.close();
                            this.socket.close();
                        }else{
                            thread = new Thread(() -> { //apenas vai servir para receber após o login estiver ok

                            while (flag) {
                                try {

                                msgReceive = (Mensagem) input.readObject();
                                
                                if(msgReceive.getOperacao() != 5){ //todas as mensagens que vão ser printadas no chat, operação 5 é a lista de clientes 
                                    this.chatTextArea.append(msgReceive.getTexto()+"\n");
                                    
                                }else{
                                    this.sendButton.setEnabled(false); //motivo de segurança para não enviar para todos sem querer
                                    this.clientsArea.setText("");
                                    this.clientsArea.append(msgReceive.getTexto());
                                    String selected = (String) combo.getSelectedItem(); //Para ter aquele que foi inserido
                                    
                                    this.combo.removeAllElements();
                                    this.combo.addElement("Todos");
                                    String [] str = msgReceive.getTexto().split("\n");
                                    warningClientLogOut = true; // e uma flag para verificar se por acaso o que tinha selecionado não deu log out
                                    for(int i = 0; i < str.length; ++i){
                                        if(!str[i].equals(nomeTextField.getText())){
                                            this.combo.addElement(str[i]);
                                            if(str[i].equals(selected)){
                                                this.combo.setSelectedItem(str[i]);
                                                warningClientLogOut = false; 
                                            }
                                        }
                                    }
                                    
                                    if(warningClientLogOut && !selected.equals("Todos")){ //caso não existe ele como está a true vai dar aviso ao utilizador da saída do outro para quem ia enviar mensagem privada
                                        JOptionPane.showMessageDialog(this,"O utilizador que tinha marcado para mensagem privada saiu do chat!!" ,"Alerta" , JOptionPane.WARNING_MESSAGE);
                                    }
                                    this.sendButton.setEnabled(true);
                                }
                                }catch(IOException ion){ 
                                    if(flag){
                                        JOptionPane.showMessageDialog(this,"Erro - Falha na comunicação" ,"Alerta" , JOptionPane.WARNING_MESSAGE);
                                        this.sendButton.setEnabled(true);
                                    }else{
                                        JOptionPane.showMessageDialog(this,"Logout Realizado com Sucesso" ,"Alerta" , JOptionPane.WARNING_MESSAGE);
                                        this.sendButton.setEnabled(true);
                                    }
                                } 
                                catch(Exception e){
                                    if(flag){
                                        JOptionPane.showMessageDialog(this,"Erro - Poderá não receber algumas mensagens" ,"Alerta" , JOptionPane.WARNING_MESSAGE);
                                        this.sendButton.setEnabled(true);
                                    }else{
                                        JOptionPane.showMessageDialog(this,"Logout Realizado com Sucesso" ,"Alerta" , JOptionPane.WARNING_MESSAGE);
                                        this.sendButton.setEnabled(true);
                                    }
                                }
                            }
                            });
                            thread.start();

                            this.chatTextArea.setText("A sua sessão está ativa utilizador/a "+this.nomeTextField.getText()+"\n");
                            this.nomeTextField.setEnabled(false);
                            this.loginButton.setEnabled(false);
                            this.sendButton.setEnabled(true);
                            this.logoutButton.setEnabled(true);
                            this.clientsPrivate.setEnabled(true);
                            this.ipTextField.setEnabled(false);
                            this.portoTextField.setEnabled(false);
                        }

                    
                    }else{
                        JOptionPane.showMessageDialog(this,"Erro sem ligação ao servidor" ,"Alerta" , JOptionPane.WARNING_MESSAGE);
                    }
                }
                catch (Exception e) { 
                    JOptionPane.showMessageDialog(this,"Erro algo correu mal" ,"Alerta" , JOptionPane.WARNING_MESSAGE);
                }
            }catch(NumberFormatException nfe){
                JOptionPane.showMessageDialog(this,"Erro não é Inteiro" ,"Alerta" , JOptionPane.WARNING_MESSAGE);
            }


                }
        
    }//GEN-LAST:event_loginButtonActionPerformed

    private void ipTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ipTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ipTextFieldActionPerformed

    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendButtonActionPerformed
            
        try{  
            if(elementClient.equals("Todos")){
                msgSend = new Mensagem(1, this.nomeTextField.getText(), this.sendTextArea.getText());
                output.writeObject(msgSend);
                output.flush();
                output.reset();
                this.sendTextArea.setText("");
            }else{
                msgSend = new Mensagem(6, this.nomeTextField.getText(), this.sendTextArea.getText());
                msgSend.setNomePrivado(this.elementClient);
                output.writeObject(msgSend);
                output.flush();
                output.reset();
                this.sendTextArea.setText("");
            }
                
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(this,"Erro algo correu mal" ,"Alerta" , JOptionPane.WARNING_MESSAGE);
                
            }finally{
            this.sendTextArea.setText(""); 
        }
            
    }//GEN-LAST:event_sendButtonActionPerformed

    private void portoTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_portoTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_portoTextFieldActionPerformed

    private void logoutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutButtonActionPerformed
        this.flag = false;
        if(socket != null){
            try {
                output.writeObject(new Mensagem(2, this.nomeTextField.getText(), ""));
                output.flush();
                output.reset();
                output.close();
                input.close();
                socket.close();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,"Ligação Encerrada" ,"Alerta" , JOptionPane.WARNING_MESSAGE);
            }finally{
                this.chatTextArea.setText("");
                this.nomeTextField.setText("");
                this.portoTextField.setText("");
                this.ipTextField.setText("");
                this.sendTextArea.setText("");
                this.clientsArea.setText("");
                this.nomeTextField.setEnabled(true);
                this.loginButton.setEnabled(true);
                this.sendButton.setEnabled(false);
                this.logoutButton.setEnabled(false);
                this.clientsPrivate.setEditable(false);
                this.ipTextField.setEnabled(true);
                this.portoTextField.setEnabled(true);
            } 
        }else{
            JOptionPane.showMessageDialog(this,"Erro algo correu mal a fechar a ligação" ,"Alerta" , JOptionPane.WARNING_MESSAGE);
            this.chatTextArea.setText("");
                this.nomeTextField.setText("");
                this.portoTextField.setText("");
                this.ipTextField.setText("");
                this.sendTextArea.setText("");
                this.clientsArea.setText("");
                this.nomeTextField.setEnabled(true);
                this.loginButton.setEnabled(true);
                this.sendButton.setEnabled(false);
                this.logoutButton.setEnabled(false);
                this.clientsPrivate.setEditable(false);
                this.ipTextField.setEnabled(true);
                this.portoTextField.setEnabled(true);
        }
        
    }//GEN-LAST:event_logoutButtonActionPerformed

    private void nomeTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nomeTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nomeTextFieldActionPerformed

    private void clientsPrivateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clientsPrivateActionPerformed
        this.elementClient = (String) this.clientsPrivate.getSelectedItem();
    }//GEN-LAST:event_clientsPrivateActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ClientChat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClientChat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClientChat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClientChat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClientChat().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea chatTextArea;
    private javax.swing.JTextArea clientsArea;
    private javax.swing.JComboBox<String> clientsPrivate;
    private javax.swing.JTextField ipTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton loginButton;
    private javax.swing.JButton logoutButton;
    private javax.swing.JTextField nomeTextField;
    private javax.swing.JTextField portoTextField;
    private javax.swing.JButton sendButton;
    private javax.swing.JTextArea sendTextArea;
    // End of variables declaration//GEN-END:variables

    
}
