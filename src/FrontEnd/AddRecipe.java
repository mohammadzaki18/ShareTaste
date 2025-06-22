package FrontEnd;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.sql.*;

/**
 * Form untuk menambahkan resep baru.
 * Mewarisi fitur-fitur umum dari BaseForm.
 */
public class AddRecipe extends BaseForm { //️ Pewarisan dari BaseForm
    private JTextField tfJudul, tfFoto;
    private JTextArea taBahan, taLangkah;
    private JButton simpanButton, backButton, uploadButton;
    private JLabel previewLabel;
    private String selectedImagePath;
    
    
    /**
     * Konstruktor utama AddRecipe.
     * Memanggil konstruktor superclass (BaseForm) dan menyiapkan komponen UI.
     */
    public AddRecipe() {
        super("Add Recipe"); // Memanggil konstruktor BaseForm dengan parameter judul form
        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(0, 47, 91));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Share Your New Taste");
        titleLabel.setFont(new Font("Nirmala UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);
        gbc.gridwidth = 1;

        // Judul
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(createLabel("Title:"), gbc);

        tfJudul = new JTextField();
        gbc.gridx = 1;
        panel.add(tfJudul, gbc);

        // Foto
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(createLabel("Photo:"), gbc);

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

        // Bahan
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(createLabel("Ingredients:"), gbc);

        taBahan = new JTextArea(3, 20);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(createTextAreaScroll(taBahan), gbc);

        // Langkah
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(createLabel("Instructions:"), gbc);

        taLangkah = new JTextArea(3, 20);
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(createTextAreaScroll(taLangkah), gbc);

        // Tombol
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

    // Fungsi memilih gambar
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

    // Simpan data ke database
    private void saveRecipe() {
        String title = tfJudul.getText().trim();
        String photo = tfFoto.getText().trim();
        String ingredients = taBahan.getText().trim();
        String instructions = taLangkah.getText().trim();

        if (title.isEmpty() || photo.isEmpty() || ingredients.isEmpty() || instructions.isEmpty()) {
            showError("Semua field wajib diisi!");
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
            pst.setInt(5, Session.getUserId());

            pst.executeUpdate();
            showSuccess("Resep berhasil disimpan!");
            pst.close();
            conn.close();

            new ViewRecipe().setVisible(true);
            dispose();
        } catch (Exception e) {
            showError("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AddRecipe().setVisible(true));
    }
}
