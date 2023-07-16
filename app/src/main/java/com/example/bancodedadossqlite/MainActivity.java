package com.example.bancodedadossqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {

            // Criar banco de dados
            SQLiteDatabase bancoDados = openOrCreateDatabase("app", MODE_PRIVATE, null);

            // Criar tabela
            bancoDados.execSQL("CREATE TABLE IF NOT EXISTS pessoas (nome VARCHAR, idade INT(3))");

            // Inserir dados
            //bancoDados.execSQL("INSERT INTO pessoas(nome, idade) VALUES('Mariana', 18)");
            //bancoDados.execSQL("INSERT INTO pessoas(nome, idade) VALUES('Pedro', 50)");

            // Consulta SQL - exemplo aplicando filtros
            /*String consulta = "SELECT nome, idade FROM pessoas WHERE nome='Maria' AND idade=36";*/
            /*String consulta = "SELECT nome, idade FROM pessoas WHERE idade >= 30 OR idade = 18";*/
            /*String consulta = "SELECT nome, idade FROM pessoas WHERE idade IN(18, 35)";*/
            /*String consulta = "SELECT nome, idade FROM pessoas WHERE idade BETWEEN 18 AND 35";*/
            /*String consulta = "SELECT nome, idade FROM pessoas WHERE nome LIKE '%Mari%' ";*/
            String consulta = "SELECT nome, idade FROM pessoas ORDER BY nome DESC LIMIT 4";

            // Recuperar pessoas
           Cursor cursor = bancoDados.rawQuery(consulta, null);


           // Indices na tabela
            int indiceNome = cursor.getColumnIndex("nome");
            int indiceIdade = cursor.getColumnIndex("idade");

            // Inicia o cursor na primeira posição (primeiro registro) do conjunto de resultados da consulta
            cursor.moveToFirst();

            while(cursor != null){ // Verificar se há registros retornados

                // Captura os valores nome e idade registrados no banco de dados
                String nome = cursor.getString(indiceNome);
                String idade = cursor.getString(indiceIdade);

                Log.i("Resultado - nome ", nome + " - idade: " + idade);

               // Move o cursor para a próxima posição (próximo registro) no conjunto de resultados da consulta.
               cursor.moveToNext();
           }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

/**
 * Anotação - Criando os primeiros comandos para o banco de dados SQLite
 *
 * A classe SQLiteDatabase é uma parte fundamental da API de banco de dados do Android. Ela é usada para criar e
 * gerenciar bancos de dados SQLite no contexto de um aplicativo Android. O SQLite é um banco de dados leve, embutido
 * e de código aberto que é amplamente utilizado em aplicativos móveis, incluindo o Android.
 *
 * A classe SQLiteDatabase fornece métodos para executar operações básicas de banco de dados, como criar, abrir, fechar,
 * executar consultas, inserir, atualizar e excluir dados. Ela encapsula a funcionalidade do SQLite e oferece uma
 * interface conveniente para interagir com o banco de dados.
 *
 * Aqui estão alguns dos métodos importantes fornecidos pela classe SQLiteDatabase:
 *
 * openOrCreateDatabase: Este método é usado para abrir ou criar um banco de dados. Ele retorna uma instância de
 *                       SQLiteDatabase que pode ser usada para executar operações no banco de dados.
 *
 * execSQL: Este método é usado para executar instruções SQL que não retornam dados, como criar tabelas, inserir dados
 * ou atualizar registros. Você pode passar uma instrução SQL como uma string para esse método.
 *
 * query: Este método é usado para executar consultas no banco de dados. Ele retorna um objeto Cursor que permite
 * percorrer os resultados da consulta. Você precisa fornecer os parâmetros necessários, como a tabela a ser consultada,
 * as colunas a serem retornadas, cláusulas WHERE opcionais etc.
 *
 * insert e update: Esses métodos são usados para inserir e atualizar registros no banco de dados, respectivamente.
 * Eles retornam o identificador do registro inserido ou o número de registros afetados pela atualização.
 *
 * delete: Este método é usado para excluir registros de uma tabela. Ele retorna o número de registros excluídos.
 *
 * close: Este método é usado para fechar o banco de dados. É importante sempre chamar esse método quando você terminar
 * de usar o banco de dados para liberar recursos.
 *
 * ------------- // ----------------
 *
 * A classe Cursor é uma classe fundamental na API de banco de dados do Android. Ela é usada para representar o conjunto
 * de resultados de uma consulta feita em um banco de dados SQLite. O Cursor permite percorrer os registros retornados
 * pela consulta e acessar os valores das colunas em cada registro.
 *
 * Aqui estão algumas características importantes da classe Cursor:
 *
 * Navegação: O Cursor permite percorrer os registros da consulta de forma sequencial. Inicialmente, o cursor está
 * posicionado antes do primeiro registro, e você pode usar o método moveToNext() para avançar para o próximo registro.
 * Você também pode usar os métodos moveToFirst(), moveToLast(), moveToPosition() e outros para posicionar o cursor em
 * um registro específico.
 *
 * Acesso aos dados: O Cursor fornece vários métodos para acessar os valores das colunas em um registro. Você pode usar
 * métodos como getInt(), getString(), getDouble(), getBlob(), entre outros, para recuperar os valores das colunas com
 * base no seu tipo de dado.
 *
 * Colunas e índices: O Cursor mantém informações sobre as colunas retornadas pela consulta, incluindo seus nomes e
 * índices. Você pode usar os métodos getColumnIndex() e getColumnIndexOrThrow() para obter o índice de uma coluna com
 * base no nome dela. Além disso, você pode usar métodos como getColumnName() e getColumnCount() para obter informações
 * sobre as colunas no Cursor.
 *
 * Gerenciamento de recursos: O Cursor requer recursos do sistema para acessar os dados do banco de dados. Portanto, é
 * importante fechá-lo adequadamente quando você terminar de usá-lo. Para liberar os recursos, você pode chamar o método
 * close() no Cursor.
 *
 * ------------- // ----------------
 *
 * O método rawQuery() é usado para executar uma consulta SQL direta em um banco de dados SQLite no Android. Ele
 * pertence à classe SQLiteDatabase e permite que você execute consultas personalizadas que não são tratadas pelas
 * conveniências fornecidas pelos métodos de alto nível, como query().
 *
 * Aqui está a assinatura do método rawQuery():
 * public Cursor rawQuery(String sql, String[] selectionArgs)
 *
 * sql: É uma string contendo a instrução SQL que você deseja executar. Essa instrução SQL pode ser qualquer consulta
 * válida, como SELECT, INSERT, UPDATE, DELETE etc. Por exemplo, "SELECT nome, idade FROM pessoas".
 *
 * selectionArgs (opcional): É um array de strings que pode ser usado para substituir placeholders na consulta SQL.
 * Você pode usar o valor null se a consulta não tiver placeholders. Os placeholders são representados como ? na
 * consulta SQL e são substituídos pelos valores fornecidos nos selectionArgs. Isso ajuda a evitar problemas de
 * segurança, como ataques de injeção de SQL.
 *
 * O método rawQuery() retorna um objeto Cursor que contém os resultados da consulta. O Cursor permite percorrer os
 * registros retornados pela consulta e acessar os valores das colunas em cada registro.
 *
 * É importante ressaltar que o método rawQuery() é útil quando você precisa de flexibilidade para escrever consultas
 * personalizadas. No entanto, também é importante ter cuidado ao utilizar consultas "raw" para evitar possíveis
 * problemas de segurança, como ataques de injeção de SQL. Sempre valide e sanitize corretamente os dados inseridos na
 * consulta para evitar riscos de segurança.
 *
 * ------------- // ----------------
 *
 * O método moveToFirst() é usado no contexto de um objeto Cursor para mover o cursor para a primeira posição do
 * conjunto de resultados da consulta.
 *
 * cursor.moveToFirst() está sendo chamado para verificar se há registros retornados pela consulta representada pelo
 * objeto cursor.
 *
 * Aqui está o que acontece quando cursor.moveToFirst() é executado:
 *
 * Se a consulta retornar um ou mais registros, o cursor será movido para a primeira posição (primeiro registro) do
 * conjunto de resultados e o método retornará true.
 *
 * Se a consulta não retornar nenhum registro, ou seja, o conjunto de resultados estiver vazio, o cursor não será
 * movido e o método retornará false.
 *
 * Em geral, após chamar moveToFirst(), é comum usar uma estrutura de repetição, como do-while ou while, em combinação
 * com moveToNext(), para percorrer os registros subsequentes do conjunto de resultados.
 *
 * ------------- // ----------------
 *
 * O método moveToNext() é usado em um objeto Cursor para mover o cursor para a próxima posição (próximo registro) no
 * conjunto de resultados da consulta.
 *
 * O cursor.moveToNext() está sendo chamado para mover o cursor para o próximo registro do conjunto de resultados, se
 * houver. Essa chamada é geralmente usada em combinação com um loop, como um while ou do-while, para percorrer todos
 * os registros retornados pela consulta.
 *
 * Aqui está o que acontece quando cursor.moveToNext() é executado:
 *
 * Se houver um próximo registro no conjunto de resultados, o cursor será movido para a próxima posição e o método
 * retornará true.
 *
 * Se o cursor já estiver no último registro ou se o conjunto de resultados estiver vazio, o cursor não será movido e
 * o método retornará false.
 *
 * É importante usar moveToNext() juntamente com uma verificação de condição, como em um loop, para garantir que você
 * esteja movendo o cursor apenas quando há um próximo registro a ser acessado.
 *
 * ------------- // ----------------
 *
 * Apenas para Didática - Placeholders na consulta SQL
 *
 * Placeholders são marcadores especiais usados em consultas SQL para indicar que um valor será fornecido posteriormente.
 * Eles são representados pelo caractere ? na consulta SQL.
 *
 * Os placeholders são usados principalmente para fornecer valores dinâmicos em consultas parametrizadas, onde os
 * valores são passados separadamente da consulta em si. Em vez de incluir diretamente os valores na consulta, você
 * pode usar placeholders para indicar onde os valores devem ser inseridos.
 *
 * A principal vantagem dos placeholders é a prevenção de ataques de injeção de SQL. Quando os valores são fornecidos
 * separadamente dos placeholders, o mecanismo de banco de dados trata automaticamente a inserção correta e a formatação
 * adequada dos valores na consulta. Isso evita que caracteres especiais ou maliciosos sejam interpretados erroneamente
 * como parte da consulta SQL.
 *
 * Além disso, o uso de placeholders também melhora a reutilização da consulta. Você pode executar a mesma consulta
 * várias vezes, fornecendo diferentes valores para os placeholders, em vez de reescrever a consulta inteira com
 * valores diferentes.
 *
 * Aqui está um exemplo de como usar placeholders em uma consulta SQL:
 *
 * ```
 * String nome = "João";
 * int idade = 25;
 *
 * String consulta = "SELECT * FROM pessoas WHERE nome = ? AND idade > ?";
 * Cursor cursor = bancoDados.rawQuery(consulta, new String[] { nome, String.valueOf(idade) });
 * ```
 * Neste exemplo, a consulta SQL contém dois placeholders, representados pelos ?. Os valores correspondentes aos
 * placeholders são fornecidos no método rawQuery() como um array de strings new String[] { nome, String.valueOf(idade) }.
 * Os valores serão substituídos nos placeholders na ordem em que são fornecidos.
 *
 * Os placeholders são uma prática recomendada para evitar problemas de segurança e garantir a correta formatação e
 * tratamento dos valores na consulta SQL.
 * */