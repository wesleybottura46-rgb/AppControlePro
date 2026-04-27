// Arquivo principal do projeto
// Aqui ficam plugins usados pelos módulos do app

plugins {

    // Plugin padrão do Android
    alias(libs.plugins.android.application) apply false

    // ADICIONADO:
    // Plugin necessário para integração com Firebase
    id("com.google.gms.google-services") version "4.4.2" apply false
}