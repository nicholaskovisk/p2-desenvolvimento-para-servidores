package login;

import java.awt.*;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class TelaCadastro extends JFrame {
    private final JFrame telaAnterior;
    private final JTextField txtNome = new JTextField(18);
    private final JTextField txtUsuario = new JTextField(18);
    private final JPasswordField txtSenha = new JPasswordField(18);
    private final JPasswordField txtConfirmar = new JPasswordField(18);

    public TelaCadastro(JFrame telaAnterior) {
        this.telaAnterior = telaAnterior;
        setTitle("Cadastro de usuário");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(460, 370);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel p = new JPanel(new GridBagLayout());
        p.setBorder(new EmptyBorder(20, 35, 20, 35));
        p.setBackground(new Color(245, 247, 250));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 6, 8, 6);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0; c.gridy = 0; c.gridwidth = 2;
        JLabel titulo = new JLabel("Criar conta", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 23));
        p.add(titulo, c);
        c.gridwidth = 1; c.gridy++;
        adicionarCampo(p, c, "Nome:", txtNome);
        adicionarCampo(p, c, "Usuário:", txtUsuario);
        adicionarCampo(p, c, "Senha:", txtSenha);
        adicionarCampo(p, c, "Confirmar senha:", txtConfirmar);

        JButton cadastrar = new JButton("Cadastrar");
        JButton cancelar = new JButton("Cancelar");
        c.gridx = 0; c.gridy++; p.add(cadastrar, c);
        c.gridx = 1; p.add(cancelar, c);
        cadastrar.addActionListener(e -> cadastrar());
        cancelar.addActionListener(e -> voltar());
        setContentPane(p);
    }

    private void adicionarCampo(JPanel p, GridBagConstraints c, String rotulo, JComponent campo) {
        c.gridx = 0; c.gridy++; p.add(new JLabel(rotulo), c);
        c.gridx = 1; p.add(campo, c);
    }

    private void cadastrar() {
        String nome = txtNome.getText().trim();
        String usuario = txtUsuario.getText().trim();
        String senha = new String(txtSenha.getPassword());
        String confirmar = new String(txtConfirmar.getPassword());

        if (nome.isEmpty() || usuario.isEmpty() || senha.isEmpty() || confirmar.isEmpty()) {
            aviso("Preencha todos os campos.");
            return;
        }
        if (nome.length() > 30 || usuario.length() > 10) {
            aviso("O nome deve ter até 30 caracteres e o usuário até 10 caracteres.");
            return;
        }
        if (!senha.equals(confirmar)) {
            aviso("Os campos senha e confirmar senha precisam ser iguais.");
            txtSenha.setText("");
            txtConfirmar.setText("");
            return;
        }

        try {
            Usuario modelo = new Usuario();
            if (modelo.verificaUsuario(usuario)) {
                aviso("Este nome de usuário já está cadastrado.");
                return;
            }
            if (modelo.cadastraUsuario(usuario, nome, senha)) {
                JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso! Faça login.", "Cadastro", JOptionPane.INFORMATION_MESSAGE);
                voltar();
            } else {
                aviso("Não foi possível concluir o cadastro.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void aviso(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem, "Atenção", JOptionPane.WARNING_MESSAGE);
    }

    private void voltar() {
        dispose();
        if (telaAnterior != null) telaAnterior.setVisible(true);
    }
}
