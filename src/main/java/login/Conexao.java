package login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Conexao {
    private static final String URL = valorEnv("DB_URL", "jdbc:mysql://localhost:3306/bd_login?useSSL=false&serverTimezone=America/Sao_Paulo&allowPublicKeyRetrieval=true");
    private static final String USUARIO = valorEnv("DB_USER", "root");
    private static final String SENHA = valorEnv("DB_PASSWORD", "");

    private Conexao() {}

    private static String valorEnv(String chave, String padrao) {
        String valor = System.getenv(chave);
        return valor == null ? padrao : valor;
    }

    public static Connection abrirConexao() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver do MySQL não encontrado. Verifique as dependências Maven.", e);
        }
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}
