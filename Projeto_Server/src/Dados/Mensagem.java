/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dados;

import java.io.Serializable;

/**
 *
 * @author Jorge Martins
 */
public class Mensagem implements Serializable{
    
 private int operacao; // ver observação a) abaixo
 private String nome; // o nome do utilizador
 private String texto; // o texto da mensagem
 private String nomePrivado; //quando envia em privado
 
 
 public Mensagem(int aOperacao, String aNome, String aTexto){
     operacao = aOperacao;
     nome = aNome;
     texto = aTexto;
 
 } // constructor 
 
 public Mensagem(){
 
 
 } // constructor 

    public int getOperacao() {
        return operacao;
    }

    public void setOperacao(int operacao) {
        this.operacao = operacao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
    
    public void setNomePrivado(String nomePriv){
        this.nomePrivado = nomePriv;
    }
    
    public String getNomePrivado(){
        return this.nomePrivado;
    }
    
    
 
    
    
}
