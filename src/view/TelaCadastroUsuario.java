package view;

import controller.SupermercadoController;
import model.Usuario;
import javax.swing.*;

public class TelaCadastroUsuario extends JFrame {
    public TelaCadastroUsuario() {
        setTitle("Cadastro de Usuário");
        setSize(320, 230);
        setLayout(null);
        setLocationRelativeTo(null);

        JLabel lblNome = new JLabel("Nome:"); lblNome.setBounds(20, 20, 80, 25); add(lblNome);
        JTextField txtNome = new JTextField(); txtNome.setBounds(80, 20, 180, 25); add(txtNome);

        JLabel lblCpf = new JLabel("CPF:"); lblCpf.setBounds(20, 55, 80, 25); add(lblCpf);
        JTextField txtCpf = new JTextField(); txtCpf.setBounds(80, 55, 180, 25); add(txtCpf);

        JCheckBox chkAdmin = new JCheckBox("É Administrador?"); chkAdmin.setBounds(80, 90, 150, 25); add(chkAdmin);

        JButton btnSalvar = new JButton("Salvar"); btnSalvar.setBounds(40, 130, 100, 30); add(btnSalvar);
        JButton btnVoltar = new JButton("Voltar"); btnVoltar.setBounds(160, 130, 100, 30); add(btnVoltar);

        btnSalvar.addActionListener(e -> {
            Usuario u = new Usuario();
            u.setNome(txtNome.getText());
            u.setCpf(txtCpf.getText());
            u.setAdmin(chkAdmin.isSelected());

            if (new SupermercadoController().cadastrarUsuario(u)) {
                JOptionPane.showMessageDialog(this, "Cadastrado com sucesso!"); 
                new TelaLogin().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar.");
            }
        });

        btnVoltar.addActionListener(e -> {
            new TelaLogin().setVisible(true);
            dispose();
        });
    }
}