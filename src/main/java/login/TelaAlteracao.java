package login;

import java.awt.*;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class TelaAlteracao extends JFrame {
    private final String usuarioAtual;
    private final JTextField txtNome = new JTextField(18);
    private final JTextField txtUsuario = new JTextField(18);
    private final JPasswordField txtSenha = new JPasswordField(18);
    private final JPasswordField txtConfirmar = new JPasswordField(18);

    public TelaAlteracao(String usuarioAtual, String nomeAtual) {
        this.usuarioAtual = usuarioAtual;
        setTitle("Alterar dados");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(470, 390);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel p = new JPanel(new GridBagLayout());
        p.setBorder(new EmptyBorder(20, 35, 20, 35));
        p.setBackground(new Color(245, 247, 250));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 6, 8, 6);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0; c.gridy = 0; c.gridwidth = 2;
        JLabel titulo = new JLabel("Atualizar cadastro", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 23));
        p.add(titulo, c);
        c.gridwidth = 1;
        adicionarCampo(p, c, "Nome:", txtNome);
        adicionarCampo(p, c, "Usuário:", txtUsuario);
        adicionarCampo(p, c, "Nova senha:", txtSenha);
        adicionarCampo(p, c, "Confirmar senha:", txtConfirmar);
        txtNome.setText(nomeAtual);
        txtUsuario.setText(usuarioAtual);

        c.gridx = 0; c.gridy++;
        JButton salvar = new JButton("Alterar");
        JButton cancelar = new JButton("Cancelar");
        p.add(salvar, c);
        c.gridx = 1; p.add(cancelar, c);
        salvar.addActionListener(e -> alterar());
        cancelar.addActionListener(e -> {
            new TelaInicio(usuarioAtual, nomeAtual).setVisible(true);
            dispose();
        });
        setContentPane(p);
    }

    private void adicionarCampo(JPanel p, GridBagConstraints c, String rotulo, JComponent campo) {
        c.gridx = 0; c.gridy++; p.add(new JLabel(rotulo), c);
        c.gridx = 1; p.add(campo, c);
    }

    private void alterar() {
        String nome = txtNome.getText().trim();
        String novoUsuario = txtUsuario.getText().trim();
        String senha = new String(txtSenha.getPassword());
        String confirmar = new String(txtConfirmar.getPassword());

        if (nome.isEmpty() || novoUsuario.isEmpty()) {
            aviso("Nome e usuário precisam ser informados.");
            return;
        }
        if (nome.length() > 30 || novoUsuario.length() > 10) {
            aviso("O nome deve ter até 30 caracteres e o usuário até 10 caracteres.");
            return;
        }
        if (!senha.isEmpty() && !senha.equals(confirmar)) {
            aviso("As senhas informadas não são iguais.");
            return;
        }
        if (senha.isEmpty() && !confirmar.isEmpty()) {
            aviso("Para manter a senha atual, deixe os dois campos de senha vazios.");
            return;
        }

        try {
            Usuario modelo = new Usuario();
            if (!novoUsuario.equals(usuarioAtual) && modelo.verificaUsuario(novoUsuario)) {
                aviso("O novo nome de usuário já está em uso.");
                return;
            }
            if (modelo.alteraUsuario(usuarioAtual, novoUsuario, nome, senha)) {
                JOptionPane.showMessageDialog(this, "Dados alterados com sucesso. Faça login novamente.");
                new TelaLogin().setVisible(true);
                dispose();
            } else {
                aviso("Não foi possível alterar os dados.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao alterar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void aviso(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem, "Atenção", JOptionPane.WARNING_MESSAGE);
    }
}
