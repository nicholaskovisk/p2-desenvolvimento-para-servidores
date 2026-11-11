package login;

import java.awt.*;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class TelaLogin extends JFrame {
    private final JTextField txtUsuario = new JTextField(18);
    private final JPasswordField txtSenha = new JPasswordField(18);

    public TelaLogin() {
        setTitle("Sistema de Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(430, 330);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBorder(new EmptyBorder(24, 35, 24, 35));
        painel.setBackground(new Color(245, 247, 250));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 6, 8, 6);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0; c.gridy = 0; c.gridwidth = 2;
        JLabel titulo = new JLabel("Bem-vindo", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 25));
        painel.add(titulo, c);

        c.gridwidth = 1; c.gridy++;
        painel.add(new JLabel("Usuário:"), c);
        c.gridx = 1; painel.add(txtUsuario, c);
        c.gridx = 0; c.gridy++;
        painel.add(new JLabel("Senha:"), c);
        c.gridx = 1; painel.add(txtSenha, c);

        JButton entrar = botao("Entrar", new Color(35, 95, 170));
        JButton cadastrar = botao("Cadastrar", new Color(70, 125, 90));
        c.gridx = 0; c.gridy++; painel.add(entrar, c);
        c.gridx = 1; painel.add(cadastrar, c);
        entrar.addActionListener(e -> entrar());
        cadastrar.addActionListener(e -> {
            new TelaCadastro(this).setVisible(true);
            setVisible(false);
        });
        setContentPane(painel);
        getRootPane().setDefaultButton(entrar);
    }

    private JButton botao(String texto, Color cor) {
        JButton b = new JButton(texto);
        b.setBackground(cor);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        return b;
    }

    private void entrar() {
        String usuario = txtUsuario.getText().trim();
        String senha = new String(txtSenha.getPassword());
        if (usuario.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe usuário e senha.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            Usuario modelo = new Usuario();
            if (modelo.verificaUsuario(usuario, senha)) {
                new TelaInicio(modelo.getUsuario(), modelo.getNome()).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Usuário ou senha incorretos.", "Falha no login", JOptionPane.ERROR_MESSAGE);
                txtSenha.setText("");
                txtSenha.requestFocus();
            }
        } catch (SQLException ex) {
            erroBanco(ex);
        }
    }

    private void erroBanco(SQLException ex) {
        JOptionPane.showMessageDialog(this,
                "Não foi possível acessar o banco de dados. Confira se o MySQL está ativo e se a conexão está configurada.\n\nDetalhes: " + ex.getMessage(),
                "Erro de conexão", JOptionPane.ERROR_MESSAGE);
    }
}
