/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package FrontEnd;

import javax.swing.*;
import java.awt.*;
//import java.awt.event.ActionEvent;
import java.io.File;
import java.sql.*;

/**
 *
 * @author Jackyyy
 */
public class EditRecipe extends javax.swing.JFrame {
    private int recipeId;
    private String selectedImagePath;

    public EditRecipe(int recipeId) {
        initComponents();
        setLocationRelativeTo(null);
        setResizable(false); 
        this.recipeId = recipeId;
        loadDataFromDatabase();
    }

    private void loadDataFromDatabase() {
        if (!isUserOwner(recipeId)) {
            JOptionPane.showMessageDialog(this, "Anda tidak diizinkan mengedit resep ini!");
            dispose();
            return;
        }
        
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sharetaste", "root", "");
            String query = "SELECT * FROM resep WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, recipeId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                txtJudulResep.setText(rs.getString("title"));
                txtAreaBahan.setText(rs.getString("ingredients"));
                txtAreaLangkah.setText(rs.getString("instructions"));
                selectedImagePath = rs.getString("photo");

                if (selectedImagePath != null && !selectedImagePath.isEmpty()) {
                    ImageIcon icon = new ImageIcon(new ImageIcon(selectedImagePath).getImage().getScaledInstance(
                            lblGambarResep.getWidth(), lblGambarResep.getHeight(), Image.SCALE_SMOOTH));
                    lblGambarResep.setIcon(icon);
                    txtFotoResep.setText(new File(selectedImagePath).getName());
                }
            }

            conn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat data resep: " + e.getMessage());
        }
    }
    
    private boolean isUserOwner(int recipeId) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sharetaste", "root", "");
            PreparedStatement ps = conn.prepareStatement("SELECT user_id FROM resep WHERE id = ?");
            ps.setInt(1, recipeId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int ownerId = rs.getInt("user_id");
                System.out.println("debug owner = " + ownerId + "SessionId " + Session.getUserId());
                conn.close();
                return ownerId == Session.getUserId();
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    private void doSimpan() {
        if (!isUserOwner(recipeId)) {
            JOptionPane.showMessageDialog(this, "Anda tidak diizinkan mengedit resep ini!");
            return;
        }

        String judul = txtJudulResep.getText().trim();
        String bahan = txtAreaBahan.getText().trim();
        String langkah = txtAreaLangkah.getText().trim();

        if (judul.isEmpty() || bahan.isEmpty() || langkah.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field harus diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sharetaste", "root", "");
            String query = "UPDATE resep SET title=?, ingredients=?, instructions=?, photo=? WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, judul);
            ps.setString(2, bahan);
            ps.setString(3, langkah);
            ps.setString(4, selectedImagePath);
            ps.setInt(5, recipeId);

            int rowsAffected = ps.executeUpdate();
            conn.close();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Resep berhasil diperbarui!");
                this.dispose();
                new ViewRecipe().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Resep gagal diperbarui.", "Gagal", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error saat update data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void doDelete() {
        if (!isUserOwner(recipeId)) {
            JOptionPane.showMessageDialog(this, "Anda tidak diizinkan menghapus resep ini!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Apakah kamu yakin ingin menghapus resep ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sharetaste", "root", "");
                String query = "DELETE FROM resep WHERE id = ?";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setInt(1, recipeId);
                int rowsAffected = ps.executeUpdate();
                conn.close();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Resep berhasil dihapus.");
                    this.dispose();
                    new ViewRecipe().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Gagal menghapus resep.");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error saat menghapus: " + e.getMessage());
            }
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        judul = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtFotoResep = new javax.swing.JTextField();
        txtJudulResep = new javax.swing.JTextField();
        bahan = new javax.swing.JLabel();
        btnChooseFile = new javax.swing.JButton();
        lblGambarResep = new javax.swing.JLabel();
        langkah = new javax.swing.JLabel();
        JscrollPane = new javax.swing.JScrollPane();
        txtAreaLangkah = new javax.swing.JTextArea();
        JScrollPane2 = new javax.swing.JScrollPane();
        txtAreaBahan = new javax.swing.JTextArea();
        btnSimpan = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Edit Resep");

        jPanel1.setBackground(new java.awt.Color(0, 47, 91));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setFont(new java.awt.Font("Nirmala UI", 1, 24)); // NOI18N
        jLabel1.setText("Edit Resep");

        judul.setText("Judul Resep");

        jLabel3.setText("Foto Resep");

        txtFotoResep.setEditable(false);
        txtFotoResep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFotoResepActionPerformed(evt);
            }
        });

        txtJudulResep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtJudulResepActionPerformed(evt);
            }
        });

        bahan.setText("Bahan-bahan");

        btnChooseFile.setText("Choose File");
        btnChooseFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChooseFileActionPerformed(evt);
            }
        });

        lblGambarResep.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        langkah.setText("Langkah-langkah");

        txtAreaLangkah.setColumns(20);
        txtAreaLangkah.setRows(5);
        JscrollPane.setViewportView(txtAreaLangkah);

        txtAreaBahan.setColumns(20);
        txtAreaBahan.setRows(5);
        JScrollPane2.setViewportView(txtAreaBahan);

        btnSimpan.setBackground(new java.awt.Color(51, 204, 0));
        btnSimpan.setText("Simpan Perubahan");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        btnBatal.setBackground(new java.awt.Color(153, 153, 153));
        btnBatal.setForeground(new java.awt.Color(255, 255, 255));
        btnBatal.setText("Batal");
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });

        btnDelete.setBackground(new java.awt.Color(255, 102, 102));
        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(197, 197, 197))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtJudulResep)
                    .addComponent(JscrollPane)
                    .addComponent(JScrollPane2)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnChooseFile)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtFotoResep)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(lblGambarResep, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 112, Short.MAX_VALUE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(judul, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(bahan)
                            .addComponent(langkah)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(btnSimpan)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnDelete)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(15, 15, 15)
                .addComponent(judul)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtJudulResep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnChooseFile)
                    .addComponent(txtFotoResep, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblGambarResep, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bahan)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(langkah)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JscrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpan)
                    .addComponent(btnBatal)
                    .addComponent(btnDelete))
                .addGap(55, 55, 55))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(205, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(168, 168, 168))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 553, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(41, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtFotoResepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFotoResepActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFotoResepActionPerformed

    private void txtJudulResepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtJudulResepActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtJudulResepActionPerformed

    private void btnChooseFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChooseFileActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            selectedImagePath = selectedFile.getAbsolutePath();
            txtFotoResep.setText(selectedFile.getName());
            
            try {
                ImageIcon imageIcon = new ImageIcon(selectedImagePath);
                if (imageIcon.getIconWidth() > lblGambarResep.getWidth() || imageIcon.getIconHeight() > lblGambarResep.getHeight()) {
                    imageIcon = new ImageIcon(imageIcon.getImage().getScaledInstance(
                            lblGambarResep.getWidth(), lblGambarResep.getHeight(), java.awt.Image.SCALE_SMOOTH));
                }
                lblGambarResep.setIcon(imageIcon);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Gagal memuat gambar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnChooseFileActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        // TODO add your handling code here:
        doSimpan();        
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        // TODO add your handling code here:
        this.dispose();
        new ViewRecipe().setVisible(true);
    }//GEN-LAST:event_btnBatalActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        doDelete();
    }//GEN-LAST:event_btnDeleteActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EditRecipe(1).setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane JScrollPane2;
    private javax.swing.JScrollPane JscrollPane;
    private javax.swing.JLabel bahan;
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnChooseFile;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel judul;
    private javax.swing.JLabel langkah;
    private javax.swing.JLabel lblGambarResep;
    private javax.swing.JTextArea txtAreaBahan;
    private javax.swing.JTextArea txtAreaLangkah;
    private javax.swing.JTextField txtFotoResep;
    private javax.swing.JTextField txtJudulResep;
    // End of variables declaration//GEN-END:variables
}
