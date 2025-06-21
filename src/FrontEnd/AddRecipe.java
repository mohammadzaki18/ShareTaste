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
import java.io.File;
import java.sql.*;

public class AddRecipe extends JFrame {
    private JTextField tfJudul, tfFoto;
    private JTextArea taBahan, taLangkah;
    private JButton simpanButton, backButton, uploadButton;
    private JLabel previewLabel;
    private String selectedImagePath;

    public AddRecipe() {
        setTitle("Add Recipe");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(0, 47, 91));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Add Your New Taste");
        titleLabel.setFont(new Font("Nirmala UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);
        gbc.gridwidth = 1;

        JLabel lblJudul = new JLabel("Title:");
        lblJudul.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(lblJudul, gbc);

        tfJudul = new JTextField();
        gbc.gridx = 1;
        panel.add(tfJudul, gbc);

        JLabel lblFoto = new JLabel("Photo:");
        lblFoto.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(lblFoto, gbc);

        tfFoto = new JTextField();
        tfFoto.setEditable(false);
        gbc.gridx = 1;
        panel.add(tfFoto, gbc);

        uploadButton = new JButton("Add Photo");
        uploadButton.setFont(new Font("OCR A Extended", Font.PLAIN, 12));
        uploadButton.addActionListener(e -> chooseImage());
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(uploadButton, gbc);

        previewLabel = new JLabel();
        previewLabel.setPreferredSize(new Dimension(150, 100));
        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(previewLabel, gbc);

        JLabel lblBahan = new JLabel("Ingredients:");
        lblBahan.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(lblBahan, gbc);

        taBahan = new JTextArea(3, 20);
        taBahan.setLineWrap(true);
        taBahan.setWrapStyleWord(true);
        JScrollPane bahanScroll = new JScrollPane(taBahan);
        bahanScroll.setPreferredSize(new Dimension(250, 70));
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(bahanScroll, gbc);

        JLabel lblLangkah = new JLabel("Instructions:");
        lblLangkah.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(lblLangkah, gbc);

        taLangkah = new JTextArea(3, 20);
        taLangkah.setLineWrap(true);
        taLangkah.setWrapStyleWord(true);
        JScrollPane langkahScroll = new JScrollPane(taLangkah);
        langkahScroll.setPreferredSize(new Dimension(250, 70));
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(langkahScroll, gbc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(0, 47, 91));

        simpanButton = new JButton("Save");
        simpanButton.setFont(new Font("OCR A Extended", Font.PLAIN, 12));
        simpanButton.setBackground(Color.WHITE);
        simpanButton.setForeground(new Color(0, 51, 153));
        simpanButton.addActionListener(e -> saveRecipe());

        backButton = new JButton("Back");
        backButton.setFont(new Font("OCR A Extended", Font.PLAIN, 12));
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(new Color(0, 51, 153));
        backButton.addActionListener(e -> {
            new home().setVisible(true);
            dispose();
        });

        buttonPanel.add(simpanButton);
        buttonPanel.add(backButton);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.CENTER;
        panel.add(buttonPanel, gbc);

        setContentPane(panel);
    }

    private void chooseImage() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            selectedImagePath = selectedFile.getAbsolutePath();
            tfFoto.setText(selectedImagePath);

            ImageIcon icon = new ImageIcon(new ImageIcon(selectedImagePath)
                    .getImage().getScaledInstance(150, 100, Image.SCALE_SMOOTH));
            previewLabel.setIcon(icon);
        }
    }

    private void saveRecipe() {
        String title = tfJudul.getText().trim();
        String photo = tfFoto.getText().trim();
        String ingredients = taBahan.getText().trim();
        String instructions = taLangkah.getText().trim();

        if (title.isEmpty() || photo.isEmpty() || ingredients.isEmpty() || instructions.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field wajib diisi!");
            return;
        }

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sharetaste", "root", "");
            String sql = "INSERT INTO resep (title, photo, ingredients, instructions, user_id) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, title);
            pst.setString(2, photo);
            pst.setString(3, ingredients);
            pst.setString(4, instructions);
            pst.setInt(5, 1);  // Sementara pakai user_id = 1 (nanti sesuaikan jika ada login session)

            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Resep berhasil disimpan!");
            pst.close();
            conn.close();

            new home().setVisible(true);
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AddRecipe().setVisible(true));
    }
}
