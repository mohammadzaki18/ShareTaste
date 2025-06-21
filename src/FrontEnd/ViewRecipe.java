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
import java.awt.*;
import java.sql.*;

public class ViewRecipe extends JFrame {
    private JPanel contentPanel;

    public ViewRecipe() {
        setTitle("Daftar Resep");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initComponents();
        loadData();
    }

    private void initComponents() {
        contentPanel = new JPanel();
        contentPanel.setBackground(new Color(0, 47, 91));
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        
        JLabel myRecipeLabel = new JLabel("My Taste");
        myRecipeLabel.setFont(new Font("Nirmala UI", Font.BOLD, 24));
        myRecipeLabel.setForeground(Color.WHITE);
        myRecipeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        contentPanel.add(myRecipeLabel);
        
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        JPanel myRecipePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        myRecipePanel.setBackground(new Color(0, 47, 91));
        contentPanel.add(myRecipePanel);

        JLabel otherRecipeLabel = new JLabel("Other Taste");
        otherRecipeLabel.setFont(new Font("Nirmala UI", Font.BOLD, 24));
        otherRecipeLabel.setForeground(Color.WHITE);
        otherRecipeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        contentPanel.add(otherRecipeLabel);
        
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        JPanel otherRecipePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        otherRecipePanel.setBackground(new Color(0, 47, 91));
        contentPanel.add(otherRecipePanel);
        
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        setContentPane(scrollPane);

        // Simpan ke field supaya loadData bisa akses
        this.myRecipePanel = myRecipePanel;
        this.otherRecipePanel = otherRecipePanel;
    }

    private JPanel myRecipePanel, otherRecipePanel;

    private void loadData() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sharetaste", "root", "");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM resep");

            while (rs.next()) {
                String title = rs.getString("title");
                String photo = rs.getString("photo");
                String ingredients = rs.getString("ingredients");
                String instructions = rs.getString("instructions");
                int userId = rs.getInt("user_id");

                JPanel recipeCard = createRecipeCard(title, photo, ingredients, instructions);
                if (userId == 1) { // Ganti ke session id user jika ada
                    myRecipePanel.add(recipeCard);
                } else {
                    otherRecipePanel.add(recipeCard);
                }
            }

            conn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error load data: " + e.getMessage());
        }

        revalidate();
        repaint();
    }

    private JPanel createRecipeCard(String title, String photo, String ingredients, String instructions) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(200, 250));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        // Gambar
        JLabel imgLabel;
        if (photo != null && !photo.isEmpty()) {
            ImageIcon icon = new ImageIcon(new ImageIcon(photo).getImage().getScaledInstance(200, 100, Image.SCALE_SMOOTH));
            imgLabel = new JLabel(icon);
        } else {
            imgLabel = new JLabel();
            imgLabel.setPreferredSize(new Dimension(200, 100));
            imgLabel.setBackground(Color.GRAY);
            imgLabel.setOpaque(true);
        }
        card.add(imgLabel);

        // Judul
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Nirmala UI", Font.BOLD, 14));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(titleLabel);

        // Deskripsi langkah (potongan)
        String preview = instructions.split("\\.")[0] + "...";
        JLabel descLabel = new JLabel("1. " + preview);
        descLabel.setFont(new Font("Nirmala UI", Font.PLAIN, 12));
        descLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(descLabel);

        // Tombol lihat
        JButton lihatButton = new JButton("Detail");
        lihatButton.setFont(new Font("Nirmala UI", Font.PLAIN, 12));
        lihatButton.setForeground(Color.WHITE);
        lihatButton.setBackground(new Color(0, 47, 91));
        lihatButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, instructions, "Detail " + title, JOptionPane.INFORMATION_MESSAGE);
        });
        card.add(lihatButton);

        return card;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ViewRecipe().setVisible(true));
    }
}
