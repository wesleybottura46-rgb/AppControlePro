package com.example.appcontrolepro.utils;

import com.example.appcontrolepro.models.Usuario;

// guarda usuário logado
public class SessionManager {

    private static Usuario usuarioLogado;

    public static void setUsuarioLogado(Usuario usuario){
        usuarioLogado = usuario;
    }

    public static Usuario getUsuarioLogado(){
        return usuarioLogado;
    }
}