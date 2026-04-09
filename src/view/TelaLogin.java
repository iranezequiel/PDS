package view;

import controller.SupermercadoController;
import model.Usuario;
import javax.swing.*;
import java.awt.event.ActionEvent;

public class TelaLogin extends JFrame {
    private JTextField txtNome, txtCpf;
    private SupermercadoController controller = new SupermercadoController();

    public TelaLogin() {
        setTitle("Login do Sistema"); 
        setSize(320, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null); // Centraliza a janela na tela

        JLabel lblNome = new JLabel("Nome:"); lblNome.setBounds(30, 30, 80, 25); add(lblNome);
        txtNome = new JTextField(); txtNome.setBounds(90, 30, 160, 25); add(txtNome);

        JLabel lblCpf = new JLabel("CPF:"); lblCpf.setBounds(30, 65, 80, 25); add(lblCpf);
        txtCpf = new JTextField(); txtCpf.setBounds(90, 65, 160, 25); add(txtCpf);

        JButton btnLogin = new JButton("Entrar"); btnLogin.setBounds(30, 110, 100, 30); add(btnLogin);
        JButton btnCadastrar = new JButton("Cadastrar"); btnCadastrar.setBounds(150, 110, 100, 30); add(btnCadastrar);

        btnLogin.addActionListener((ActionEvent e) -> {
            Usuario u = controller.login(txtNome.getText(), txtCpf.getText());
            if (u != null) {
                if (u.isAdmin()) {
                    new TelaCadastroProdutos(u).setVisible(true); 
                } else {
                    new TelaCompra(u).setVisible(true); 
                }
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Usuário ou CPF incorretos!"); 
            }
        });

        btnCadastrar.addActionListener(e -> {
            new TelaCadastroUsuario().setVisible(true); 
            dispose();
        });
    }
}