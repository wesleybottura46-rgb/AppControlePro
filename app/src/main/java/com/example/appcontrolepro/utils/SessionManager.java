package com.example.appcontrolepro.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.appcontrolepro.models.Usuario;

// =========================================================
// SESSION MANAGER (VERSÃO FINAL CORRIGIDA)
// =========================================================
// Responsável por salvar dados da sessão do usuário.
//
// ✔ Usa SharedPreferences (dados persistem)
// ✔ Evita bugs de navegação após logout
//
public class SessionManager {

    // =========================================================
    // NOME DO ARQUIVO DE ARMAZENAMENTO
    // =========================================================
    private static final String PREF_NAME = "APP_CONTROLE";

    // =========================================================
    // CHAVES
    // =========================================================
    private static final String KEY_TIME_ID = "timeId";

    // =========================================================
    // SALVAR TIME SELECIONADO
    // =========================================================
    public static void setTimeId(Context context, String id) {

        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        // 🔥 salva o id do time
        prefs.edit().putString(KEY_TIME_ID, id).apply();
    }

    // =========================================================
    // RECUPERAR TIME
    // =========================================================
    public static String getTimeId(Context context) {

        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        return prefs.getString(KEY_TIME_ID, null);
    }

    // =========================================================
    // VERIFICAR SE EXISTE TIME SALVO
    // =========================================================
    public static boolean temTimeSelecionado(Context context) {

        String timeId = getTimeId(context);

        // 🔥 proteção contra null e vazio
        return timeId != null && !timeId.trim().isEmpty();
    }

    // =========================================================
    // LIMPAR SESSÃO COMPLETA (LOGOUT)
    // =========================================================
    public static void limparSessao(Context context) {

        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        // 🔥 remove todos os dados salvos
        prefs.edit().clear().apply();

        // 🔥 limpa também usuário em memória
        usuarioLogado = null;
    }

    // =========================================================
    // USUÁRIO (APENAS EM MEMÓRIA)
    // =========================================================
    private static Usuario usuarioLogado;

    public static void setUsuarioLogado(Usuario usuario) {
        usuarioLogado = usuario;
    }

    public static Usuario getUsuarioLogado() {
        return usuarioLogado;
    }
}