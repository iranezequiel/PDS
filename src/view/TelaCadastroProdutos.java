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
        setSize(450, 520);
        setLayout(null);
        setLocationRelativeTo(null);

        JLabel lblNome = new JLabel("Produto:"); lblNome.setBounds(20, 20, 80, 25); add(lblNome);
        JTextField txtNome = new JTextField(); txtNome.setBounds(100, 20, 180, 25); add(txtNome);

        JLabel lblDescricao = new JLabel("Descrição:"); lblDescricao.setBounds(20, 55, 80, 25); add(lblDescricao);
        JTextField txtDescricao = new JTextField(); txtDescricao.setBounds(100, 55, 180, 25); add(txtDescricao);

        JLabel lblPreco = new JLabel("Preço (R$):"); lblPreco.setBounds(20, 90, 80, 25); add(lblPreco);
        JTextField txtPreco = new JTextField(); txtPreco.setBounds(100, 90, 180, 25); add(txtPreco);

        JLabel lblEstoque = new JLabel("Estoque:"); lblEstoque.setBounds(20, 125, 80, 25); add(lblEstoque);
        JTextField txtEstoque = new JTextField(); txtEstoque.setBounds(100, 125, 180, 25); add(txtEstoque);

        JButton btnSalvar = new JButton("Adicionar"); btnSalvar.setBounds(20, 170, 100, 30); add(btnSalvar);
        JButton btnEditar = new JButton("Editar"); btnEditar.setBounds(130, 170, 90, 30); add(btnEditar);
        JButton btnRemover = new JButton("Remover"); btnRemover.setBounds(230, 170, 90, 30); add(btnRemover);
        JButton btnLogout = new JButton("Deslogar"); btnLogout.setBounds(330, 170, 90, 30); add(btnLogout); 
        
        JLabel lblLista = new JLabel("Lista de Produtos:"); lblLista.setBounds(20, 215, 150, 25); add(lblLista);
        JScrollPane scroll = new JScrollPane(listaUi); scroll.setBounds(20, 240, 400, 220); add(scroll);

        atualizarLista();

      
        listaUi.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && listaUi.getSelectedValue() != null) {
                Produto p = listaUi.getSelectedValue();
                txtNome.setText(p.getNome());
                txtDescricao.setText(p.getDescricao());
                txtPreco.setText(String.valueOf(p.getPreco()));
                txtEstoque.setText(String.valueOf(p.getEstoque()));
            }
        });

        btnSalvar.addActionListener(e -> {
            try {
                Produto p = new Produto();
                p.setNome(txtNome.getText());
                p.setDescricao(txtDescricao.getText());
                p.setPreco(Double.parseDouble(txtPreco.getText().replace(",", ".")));
                p.setEstoque(Integer.parseInt(txtEstoque.getText()));
                if(controller.cadastrarProduto(p)){
                    JOptionPane.showMessageDialog(this, "Produto adicionado com sucesso!");
                    limparCampos(txtNome, txtDescricao, txtPreco, txtEstoque);
                    atualizarLista();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Verifique se o preço e o estoque são números válidos.", "Erro", JOptionPane.ERROR_MESSAGE); 
            }
        });

        btnEditar.addActionListener(e -> {
            try {
                Produto selecionado = listaUi.getSelectedValue();
                if (selecionado != null) {
                    selecionado.setNome(txtNome.getText());
                    selecionado.setDescricao(txtDescricao.getText());
                    selecionado.setPreco(Double.parseDouble(txtPreco.getText().replace(",", ".")));
                    selecionado.setEstoque(Integer.parseInt(txtEstoque.getText()));
                    
                    if (controller.editarProduto(selecionado)) {
                        JOptionPane.showMessageDialog(this, "Produto atualizado com sucesso!");
                        limparCampos(txtNome, txtDescricao, txtPreco, txtEstoque);
                        atualizarLista();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Selecione um produto da lista para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Verifique os dados informados.", "Erro", JOptionPane.ERROR_MESSAGE); 
            }
        });

        btnRemover.addActionListener(e -> {
            Produto selecionado = listaUi.getSelectedValue();
            if(selecionado != null && controller.removerProduto(selecionado.getId())){
                JOptionPane.showMessageDialog(this, "Produto Removido!"); 
                limparCampos(txtNome, txtDescricao, txtPreco, txtEstoque);
                atualizarLista();
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um produto da lista primeiro.", "Aviso", JOptionPane.WARNING_MESSAGE);
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

    private void limparCampos(JTextField... campos) {
        for (JTextField campo : campos) campo.setText("");
    }
}
