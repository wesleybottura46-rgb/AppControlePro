package com.example.appcontrolepro.utils;

import com.example.appcontrolepro.models.Usuario;

// Classe responsável por guardar o usuário logado na sessão
public class SessionManager {

    // Variável estática que armazena o usuário atual
    private static Usuario usuarioLogado;

    // Define o usuário logado
    public static void setUsuarioLogado(Usuario usuario){
        usuarioLogado = usuario;
    }

    // Retorna o usuário logado
    public static Usuario getUsuarioLogado(){
        return usuarioLogado;
    }
}