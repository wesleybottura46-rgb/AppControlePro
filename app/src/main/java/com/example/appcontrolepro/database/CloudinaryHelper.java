package com.example.appcontrolepro.utils;

// ======================================================
// IMPORTAÇÕES
// ======================================================

import android.content.Context;

import com.cloudinary.android.MediaManager;

import java.util.HashMap;
import java.util.Map;

// ======================================================
// CLOUDINARY HELPER
// ======================================================
// RESPONSÁVEL POR INICIAR O CLOUDINARY
//
// O Cloudinary será usado para:
//
// ✔ Upload do escudo do time
// ✔ Upload do escudo adversário
// ✔ Futuras imagens do app
//
// NÃO substitui o Firebase.
// Apenas substitui o Firebase Storage.
// ======================================================

public class CloudinaryHelper {

    // ======================================================
    // CONTROLE PARA NÃO INICIAR 2X
    // ======================================================

    private static boolean iniciado = false;

    // ======================================================
    // INICIAR CLOUDINARY
    // ======================================================

    public static void init(Context context){

        // ======================================================
        // EVITA DUPLICAR
        // ======================================================

        if(iniciado){
            return;
        }

        // ======================================================
        // MAPA CONFIGURAÇÕES
        // ======================================================

        Map<String,String> config =
                new HashMap<>();

        // ======================================================
        // CLOUD NAME
        // ======================================================

        config.put(
                "cloud_name",
                "dmdmc7qfd"
        );

        // ======================================================
        // API KEY
        // ======================================================

        config.put(
                "api_key",
                "791174872376272"
        );

        // ======================================================
        // API SECRET
        // ======================================================

        config.put(
                "api_secret",
                "4z3kkQQXZumwclLPmTk395gw8y4"
        );

        // ======================================================
        // INICIA CLOUDINARY
        // ======================================================

        MediaManager.init(
                context,
                config
        );

        // ======================================================
        // MARCA COMO INICIADO
        // ======================================================

        iniciado = true;
    }
}