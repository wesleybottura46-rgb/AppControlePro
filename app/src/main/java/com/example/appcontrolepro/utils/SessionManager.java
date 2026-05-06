// DEFINE O PACOTE DO ARQUIVO
package com.example.appcontrolepro.utils;

// ======================================================
// IMPORTAÇÕES
// ======================================================

// IMPORTA CONTEXT
import android.content.Context;

// IMPORTA SHARED PREFERENCES
import android.content.SharedPreferences;

// IMPORTA MODEL USUARIO
import com.example.appcontrolepro.models.Usuario;

// ======================================================
// SESSION MANAGER
// ======================================================
//
// ESSA CLASSE CONTROLA:
//
// ✔ SESSÃO DO USUÁRIO
// ✔ TIME SELECIONADO
// ✔ LOGIN
// ✔ LOGOUT
// ✔ DADOS SALVOS NO CELULAR
//
// ======================================================
//
// O QUE É SHARED PREFERENCES?
//
// É UMA MEMÓRIA PEQUENA
// DO CELULAR PARA SALVAR:
//
// ✔ LOGIN
// ✔ CONFIGURAÇÕES
// ✔ IDS
// ✔ DADOS SIMPLES
//
// ======================================================

// CRIA CLASSE
public class SessionManager {

    // ======================================================
    // NOME DO ARQUIVO
    // ======================================================
    //
    // NOME DO ARQUIVO SALVO NO CELULAR
    //
    // ======================================================

    // NOME DO ARQUIVO
    private static final String PREF_NAME =
            "APP_CONTROLE";

    // ======================================================
    // CHAVES
    // ======================================================
    //
    // CHAVES SÃO NOMES INTERNOS
    // DOS DADOS SALVOS
    //
    // ======================================================

    // CHAVE DO TIME
    private static final String KEY_TIME_ID =
            "timeId";

    // ======================================================
    // SALVAR TIME
    // ======================================================
    //
    // SALVA O ID DO TIME
    //
    // ======================================================

    public static void setTimeId(

            Context context,

            String id
    ) {

        // ==================================================
        // ABRE SHARED PREFERENCES
        // ==================================================

        SharedPreferences prefs =

                context.getSharedPreferences(

                        PREF_NAME,

                        Context.MODE_PRIVATE
                );

        // ==================================================
        // SALVA ID DO TIME
        // ==================================================

        prefs.edit()

                .putString(

                        KEY_TIME_ID,

                        id
                )

                .apply();
    }

    // ======================================================
    // PEGAR TIME
    // ======================================================
    //
    // RETORNA O ID DO TIME
    //
    // ======================================================

    public static String getTimeId(

            Context context
    ) {

        // ==================================================
        // ABRE SHARED PREFERENCES
        // ==================================================

        SharedPreferences prefs =

                context.getSharedPreferences(

                        PREF_NAME,

                        Context.MODE_PRIVATE
                );

        // ==================================================
        // RETORNA TIME ID
        // ==================================================

        return prefs.getString(

                KEY_TIME_ID,

                null
        );
    }

    // ======================================================
    // VERIFICAR TIME
    // ======================================================
    //
    // VERIFICA SE EXISTE
    // UM TIME SELECIONADO
    //
    // ======================================================

    public static boolean temTimeSelecionado(

            Context context
    ) {

        // ==================================================
        // PEGA TIME ID
        // ==================================================

        String timeId =
                getTimeId(context);

        // ==================================================
        // VERIFICA SE NÃO É NULO
        // ==================================================

        return

                timeId != null

                        &&

                        !timeId.trim().isEmpty();
    }

    // ======================================================
    // LIMPAR SESSÃO
    // ======================================================
    //
    // USADO NO:
    //
    // ✔ LOGOUT
    // ✔ TROCAR TIME
    // ✔ EXCLUIR CONTA
    //
    // ======================================================

    public static void limparSessao(

            Context context
    ) {

        // ==================================================
        // ABRE SHARED PREFERENCES
        // ==================================================

        SharedPreferences prefs =

                context.getSharedPreferences(

                        PREF_NAME,

                        Context.MODE_PRIVATE
                );

        // ==================================================
        // REMOVE TODOS OS DADOS
        // ==================================================

        prefs.edit()

                .clear()

                .apply();

        // ==================================================
        // LIMPA USUÁRIO EM MEMÓRIA
        // ==================================================

        usuarioLogado = null;
    }

    // ======================================================
    // USUÁRIO LOGADO
    // ======================================================
    //
    // ESSE USUÁRIO FICA APENAS
    // NA MEMÓRIA DO APP
    //
    // NÃO FICA SALVO NO CELULAR
    //
    // ======================================================

    // USUÁRIO EM MEMÓRIA
    private static Usuario usuarioLogado;

    // ======================================================
    // SALVAR USUÁRIO
    // ======================================================
    //
    // GUARDA USUÁRIO NA MEMÓRIA
    //
    // ======================================================

    public static void setUsuarioLogado(

            Usuario usuario
    ) {

        // SALVA USUÁRIO
        usuarioLogado = usuario;
    }

    // ======================================================
    // PEGAR USUÁRIO
    // ======================================================
    //
    // RETORNA USUÁRIO DA MEMÓRIA
    //
    // ======================================================

    public static Usuario getUsuarioLogado() {

        // RETORNA USUÁRIO
        return usuarioLogado;
    }
}