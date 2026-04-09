package view;

import controller.SupermercadoController;
import model.Produto;
import model.Usuario;
import javax.swing.*;

public class TelaCadastroProdutos extends JFrame {
    private SupermercadoController controller = new SupermercadoController();
    private DefaultListModel<Produto> listModel = new DefaultListModel<>();
    private JList<Produto> listaUi = new JList<>(listModel);

    public TelaCadastroProdutos(Usuario admin) {
        setTitle("Gerenciar Produtos - Adm: " + admin.getNome());
        setSize(420, 450);
        setLayout(null);
        setLocationRelativeTo(null);

        JLabel lblNome = new JLabel("Produto:"); lblNome.setBounds(20, 20, 80, 25); add(lblNome);
        JTextField txtNome = new JTextField(); txtNome.setBounds(100, 20, 180, 25); add(txtNome);

        JLabel lblPreco = new JLabel("Preço (R$):"); lblPreco.setBounds(20, 55, 80, 25); add(lblPreco);
        JTextField txtPreco = new JTextField(); txtPreco.setBounds(100, 55, 180, 25); add(txtPreco);

        JLabel lblEstoque = new JLabel("Estoque:"); lblEstoque.setBounds(20, 90, 80, 25); add(lblEstoque);
        JTextField txtEstoque = new JTextField(); txtEstoque.setBounds(100, 90, 180, 25); add(txtEstoque);

        JButton btnSalvar = new JButton("Adicionar"); btnSalvar.setBounds(20, 130, 100, 30); add(btnSalvar);
        JButton btnRemover = new JButton("Remover"); btnRemover.setBounds(140, 130, 100, 30); add(btnRemover);
        JButton btnLogout = new JButton("Deslogar"); btnLogout.setBounds(260, 130, 100, 30); add(btnLogout); 
        
        JLabel lblLista = new JLabel("Lista de Produtos:"); lblLista.setBounds(20, 175, 150, 25); add(lblLista);
        JScrollPane scroll = new JScrollPane(listaUi); scroll.setBounds(20, 200, 350, 180); add(scroll);

        atualizarLista();

        btnSalvar.addActionListener(e -> {
            try {
                Produto p = new Produto();
                p.setNome(txtNome.getText());
                p.setPreco(Double.parseDouble(txtPreco.getText().replace(",", "."))); // Aceita vírgula ou ponto
                p.setEstoque(Integer.parseInt(txtEstoque.getText()));
                if(controller.cadastrarProduto(p)){
                    JOptionPane.showMessageDialog(this, "Produto Salvo!");
                    txtNome.setText(""); txtPreco.setText(""); txtEstoque.setText(""); // Limpa os campos
                    atualizarLista();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Verifique se o preço e o estoque são números válidos.");
            }
        });

        btnRemover.addActionListener(e -> {
            Produto selecionado = listaUi.getSelectedValue();
            if(selecionado != null && controller.removerProduto(selecionado.getId())){
                JOptionPane.showMessageDialog(this, "Produto Removido!"); 
                atualizarLista();
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um produto da lista primeiro.");
            }
        });

        btnLogout.addActionListener(e -> {
            new TelaLogin().setVisible(true);
            dispose();
        });
    }

    private void atualizarLista() {
        listModel.clear();
        for (Produto p : controller.listarProdutos()) { listModel.addElement(p); }
    }
}