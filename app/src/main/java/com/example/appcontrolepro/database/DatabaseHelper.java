package com.example.appcontrolepro.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// essa classe é responsável por criar o banco de dados e salvar as informações
public class DatabaseHelper extends SQLiteOpenHelper {

    // nome do banco
    private static final String NOME_BANCO = "appcontrole.db";

    // versão do banco
    private static final int VERSAO = 1;

    // construtor
    public DatabaseHelper(Context context) {
        super(context, NOME_BANCO, null, VERSAO);
    }

    // esse método roda automaticamente quando o banco é criado pela primeira vez
    @Override
    public void onCreate(SQLiteDatabase db) {

        // tabela de usuários
        db.execSQL("CREATE TABLE usuarios (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nome TEXT, " +
                "email TEXT, " +
                "senha TEXT)");

        // tabela de times
        db.execSQL("CREATE TABLE times (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nome TEXT, " +
                "cidade TEXT, " +
                "estado TEXT)");

        // tabela de jogadores
        db.execSQL("CREATE TABLE jogadores (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nome TEXT, " +
                "numero INTEGER, " +
                "posicao TEXT)");

        // tabela de jogos
        db.execSQL("CREATE TABLE jogos (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "adversario TEXT, " +
                "data TEXT, " +
                "local TEXT)");

        // tabela financeira
        db.execSQL("CREATE TABLE financeiro (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "descricao TEXT, " +
                "valor REAL, " +
                "tipo TEXT)");
    }

    // esse método roda se a versão do banco mudar
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // apaga as tabelas antigas
        db.execSQL("DROP TABLE IF EXISTS usuarios");
        db.execSQL("DROP TABLE IF EXISTS times");
        db.execSQL("DROP TABLE IF EXISTS jogadores");
        db.execSQL("DROP TABLE IF EXISTS jogos");
        db.execSQL("DROP TABLE IF EXISTS financeiro");

        // cria tudo novamente
        onCreate(db);
    }

    // ==========================
    // USUÁRIOS
    // ==========================

    // salva usuário no banco
    public boolean cadastrarUsuario(String nome, String email, String senha){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("nome", nome);
        valores.put("email", email);
        valores.put("senha", senha);

        long resultado = db.insert("usuarios", null, valores);

        return resultado != -1;
    }

    // verifica login
    public boolean validarLogin(String email, String senha){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM usuarios WHERE email=? AND senha=?",
                new String[]{email, senha}
        );

        return cursor.moveToFirst();
    }

    // ==========================
    // TIMES
    // ==========================

    // salva time no banco
    public boolean cadastrarTime(String nome, String cidade, String estado){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("nome", nome);
        valores.put("cidade", cidade);
        valores.put("estado", estado);

        long resultado = db.insert("times", null, valores);

        return resultado != -1;
    }

    // busca lista de times
    public Cursor listarTimes(){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM times", null);
    }

    // ==========================
    // JOGADORES
    // ==========================

    // salva jogador
    public boolean cadastrarJogador(String nome, int numero, String posicao){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("nome", nome);
        valores.put("numero", numero);
        valores.put("posicao", posicao);

        long resultado = db.insert("jogadores", null, valores);

        return resultado != -1;
    }

    // lista jogadores
    public Cursor listarJogadores(){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM jogadores", null);
    }

    // ==========================
    // JOGOS
    // ==========================

    // salva jogo
    public boolean cadastrarJogo(String adversario, String data, String local){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("adversario", adversario);
        valores.put("data", data);
        valores.put("local", local);

        long resultado = db.insert("jogos", null, valores);

        return resultado != -1;
    }

    // lista jogos
    public Cursor listarJogos(){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM jogos", null);
    }

    // ==========================
    // FINANCEIRO
    // ==========================

    // salva lançamento financeiro
    public boolean salvarLancamento(String descricao, double valor, String tipo){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("descricao", descricao);
        valores.put("valor", valor);
        valores.put("tipo", tipo);

        long resultado = db.insert("financeiro", null, valores);

        return resultado != -1;
    }

    // lista lançamentos financeiros
    public Cursor listarFinanceiro(){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM financeiro", null);
    }
}