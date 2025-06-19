/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FrontEnd;

/**
 *
 * @author Zah
 */

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ViewRecipe extends JFrame {

    private JTable table;
    private JButton backButton;

    public ViewRecipe() {
        setTitle("Lihat Resep");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(0, 47, 91));

        JLabel titleLabel = new JLabel("Daftar Resep", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Nirmala UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        panel.add(titleLabel, BorderLayout.NORTH);

        // Tabel
        table = new JTable();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Title");
        model.addColumn("Photo");
        model.addColumn("Ingredients");
        model.addColumn("Instructions");

        // Contoh data (bisa diganti ambil dari database)
        model.addRow(new Object[]{
                "Nasi Goreng",
                "C:/gambar/nasigoreng.jpg",
                "Nasi, Telur, Bumbu, Kecap",
                "Tumis bumbu, masukkan nasi, aduk rata"
        });
        model.addRow(new Object[]{
                "Es Teh",
                "C:/gambar/esteh.jpg",
                "Teh, Gula, Es Batu",
                "Seduh teh, beri gula, tambahkan es"
        });

        table.setModel(model);
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Tombol kembali
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(0, 47, 91));

        backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(100, 30));
        backButton.setFont(new Font("OCR A Extended", Font.PLAIN, 12));
        backButton.setForeground(new Color(0, 51, 153));
        backButton.setBackground(Color.WHITE);
        backButton.addActionListener(e -> {
            new home().setVisible(true);
            dispose();
        });

        buttonPanel.add(backButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ViewRecipe().setVisible(true));
    }
}
