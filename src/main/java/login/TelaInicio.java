package login;

import java.awt.*;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class TelaInicio extends JFrame {
    private final String usuario;
    private final String nome;

    public TelaInicio(String usuario, String nome) {
        this.usuario = usuario;
        this.nome = nome;
        setTitle("Início");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(460, 310);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel p = new JPanel(new GridBagLayout());
        p.setBorder(new EmptyBorder(25, 35, 25, 35));
        p.setBackground(new Color(245, 247, 250));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 8, 10, 8);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0; c.gridy = 0;
        JLabel boasVindas = new JLabel("Olá, " + nome + "!", SwingConstants.CENTER);
        boasVindas.setFont(new Font("SansSerif", Font.BOLD, 25));
        p.add(boasVindas, c);
        c.gridy++;
        JLabel info = new JLabel("Você está conectado como: " + usuario, SwingConstants.CENTER);
        p.add(info, c);
        JButton alterar = new JButton("Alterar dados");
        JButton excluir = new JButton("Excluir usuário");
        JButton sair = new JButton("Voltar ao login");
        c.gridy++; p.add(alterar, c);
        c.gridy++; p.add(excluir, c);
        c.gridy++; p.add(sair, c);

        alterar.addActionListener(e -> {
            new TelaAlteracao(usuario, nome).setVisible(true);
            dispose();
        });
        excluir.addActionListener(e -> excluir());
        sair.addActionListener(e -> {
            new TelaLogin().setVisible(true);
            dispose();
        });
        setContentPane(p);
    }

    private void excluir() {
        int opcao = JOptionPane.showConfirmDialog(this,
                "Tem certeza de que deseja excluir definitivamente seu usuário?",
                "Confirmar exclusão", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (opcao != JOptionPane.YES_OPTION) return;
        try {
            if (new Usuario().excluiUsuario(usuario)) {
                JOptionPane.showMessageDialog(this, "Usuário excluído. Você será direcionado ao login.");
                new TelaLogin().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Nenhum usuário foi excluído.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
