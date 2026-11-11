package login;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HexFormat;

/**
 * Entidade e operações de persistência dos usuários.
 * O MD5 foi mantido para reproduzir o exercício das notas de aula.
 */
public class Usuario {
    private String usuario;
    private String nome;
    private String senha;

    public Usuario() {}

    public Usuario(String usuario, String nome, String senha) {
        setUsuario(usuario);
        setNome(nome);
        setSenha(senha);
    }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario == null ? "" : usuario.trim(); }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome == null ? "" : nome.trim(); }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha == null ? "" : senha; }

    private static String md5(String texto) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(texto.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Não foi possível gerar o resumo MD5.", e);
        }
    }

    // Sobrecarga: verifica usuário e senha (login).
    public boolean verificaUsuario(String usuario, String senha) throws SQLException {
        String sql = "SELECT usuario, nome FROM usuario WHERE usuario = ? AND senha = ?";
        try (Connection con = Conexao.abrirConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, usuario.trim());
            ps.setString(2, md5(senha));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    this.usuario = rs.getString("usuario");
                    this.nome = rs.getString("nome");
                    return true;
                }
                return false;
            }
        }
    }

    // Sobrecarga: verifica somente se o nome de usuário já existe.
    public boolean verificaUsuario(String usuario) throws SQLException {
        String sql = "SELECT 1 FROM usuario WHERE usuario = ?";
        try (Connection con = Conexao.abrirConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, usuario.trim());
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public boolean cadastraUsuario(String usuario, String nome, String senha) throws SQLException {
        String sql = "INSERT INTO usuario (usuario, nome, senha) VALUES (?, ?, ?)";
        try (Connection con = Conexao.abrirConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, usuario.trim());
            ps.setString(2, nome.trim());
            ps.setString(3, md5(senha));
            return ps.executeUpdate() == 1;
        }
    }

    public boolean alteraUsuario(String usuarioAtual, String novoUsuario,
                                 String novoNome, String novaSenha) throws SQLException {
        boolean trocarSenha = novaSenha != null && !novaSenha.isBlank();
        String sql = trocarSenha
                ? "UPDATE usuario SET usuario = ?, nome = ?, senha = ? WHERE usuario = ?"
                : "UPDATE usuario SET usuario = ?, nome = ? WHERE usuario = ?";
        try (Connection con = Conexao.abrirConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, novoUsuario.trim());
            ps.setString(2, novoNome.trim());
            if (trocarSenha) {
                ps.setString(3, md5(novaSenha));
                ps.setString(4, usuarioAtual);
            } else {
                ps.setString(3, usuarioAtual);
            }
            boolean alterou = ps.executeUpdate() == 1;
            if (alterou) {
                this.usuario = novoUsuario.trim();
                this.nome = novoNome.trim();
            }
            return alterou;
        }
    }

    public boolean excluiUsuario(String usuario) throws SQLException {
        String sql = "DELETE FROM usuario WHERE usuario = ?";
        try (Connection con = Conexao.abrirConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, usuario);
            return ps.executeUpdate() == 1;
        }
    }
}
