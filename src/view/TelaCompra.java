package view;

import controller.SupermercadoController;
import model.Produto;
import model.Usuario;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class TelaCompra extends JFrame {
    private SupermercadoController controller = new SupermercadoController();
    private List<Produto> carrinho = new ArrayList<>();
    private DefaultListModel<Produto> modelDisponiveis = new DefaultListModel<>();
    private DefaultListModel<Produto> modelCarrinho = new DefaultListModel<>();
    private JList<Produto> listDisponiveis = new JList<>(modelDisponiveis);
    private JList<Produto> listCarrinho = new JList<>(modelCarrinho);
    private JLabel lblTotal = new JLabel("Total a Pagar: R$ 0.00");
    private double totalPago = 0.0;

    public TelaCompra(Usuario cliente) {
        setTitle("Supermercado - Cliente: " + cliente.getNome());
        setSize(550, 480);
        setLayout(null);
        setLocationRelativeTo(null);

        JLabel lblDisp = new JLabel("Produtos Disponíveis:"); lblDisp.setBounds(20, 15, 150, 25); add(lblDisp);
        JScrollPane scrollDisp = new JScrollPane(listDisponiveis); scrollDisp.setBounds(20, 40, 200, 200); add(scrollDisp);

        JLabel lblCar = new JLabel("Seu Carrinho:"); lblCar.setBounds(300, 15, 150, 25); add(lblCar);
        JScrollPane scrollCarrinho = new JScrollPane(listCarrinho); scrollCarrinho.setBounds(300, 40, 200, 200); add(scrollCarrinho);

        JButton btnDetalhes = new JButton("Ver Detalhes do Produto"); btnDetalhes.setBounds(20, 250, 200, 30); add(btnDetalhes); 
        JButton btnAdd = new JButton("Adicionar ao Carrinho >>"); btnAdd.setBounds(20, 290, 200, 30); add(btnAdd); 
        
        JButton btnRemover = new JButton("<< Remover do Carrinho"); btnRemover.setBounds(300, 250, 200, 30); add(btnRemover); 
        
        lblTotal.setBounds(300, 300, 200, 25); lblTotal.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14)); add(lblTotal); 

        JButton btnLogout = new JButton("Deslogar"); btnLogout.setBounds(20, 360, 120, 35); add(btnLogout); 
        JButton btnNota = new JButton("Finalizar e Emitir Nota Fiscal"); btnNota.setBounds(300, 360, 200, 35); add(btnNota); 

        carregarProdutos();

        btnDetalhes.addActionListener(e -> {
            Produto p = listDisponiveis.getSelectedValue();
            if(p != null) {
                JOptionPane.showMessageDialog(this, "Detalhes do Produto:\n\nNome: " + p.getNome() + "\nEstoque Atual: " + p.getEstoque() + "\nPreço: R$ " + p.getPreco());
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um produto na lista de disponíveis.");
            }
        });

        btnAdd.addActionListener(e -> {
            Produto p = listDisponiveis.getSelectedValue();
            if (p != null) {
                carrinho.add(p);
                modelCarrinho.addElement(p);
                atualizarTotal();
            }
        });

        btnRemover.addActionListener(e -> {
            Produto p = listCarrinho.getSelectedValue();
            if (p != null) {
                carrinho.remove(p);
                modelCarrinho.removeElement(p);
                atualizarTotal(); 
            }
        });

        btnNota.addActionListener(e -> {
            if (carrinho.isEmpty()) {
                JOptionPane.showMessageDialog(this, "O carrinho está vazio!");
                return;
            }
            
            StringBuilder nota = new StringBuilder();
            nota.append("===== NOTA FISCAL =====\n");
            nota.append("Cliente: ").append(cliente.getNome()).append("\n"); 
            nota.append("CPF: ").append(cliente.getCpf()).append("\n\n"); 
            nota.append("Itens Comprados:\n"); 
            
            for (Produto p : carrinho) {
                nota.append("- ").append(p.getNome()).append(" (R$ ").append(String.format("%.2f", p.getPreco())).append(")\n");
                controller.atualizarEstoque(p.getId(), 1); 
            }
            nota.append("\nTOTAL PAGO: R$ ").append(String.format("%.2f", totalPago)); 
            
            JOptionPane.showMessageDialog(this, nota.toString()); 
            carrinho.clear(); modelCarrinho.clear(); atualizarTotal(); carregarProdutos();
        });

        btnLogout.addActionListener(e -> { new TelaLogin().setVisible(true); dispose(); });
    }

    private void carregarProdutos() {
        modelDisponiveis.clear();
        for (Produto p : controller.listarProdutos()) { modelDisponiveis.addElement(p); } 
    }

    private void atualizarTotal() {
        totalPago = carrinho.stream().mapToDouble(Produto::getPreco).sum();
        lblTotal.setText("Total a Pagar: R$ " + String.format("%.2f", totalPago)); 
    }
}