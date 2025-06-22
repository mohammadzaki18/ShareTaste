package FrontEnd;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ViewRecipe extends JFrame {
    private JPanel contentPanel, myRecipePanel, otherRecipePanel;
    private final int currentUserId = Session.getUserId();

    public ViewRecipe() {
        setTitle("Daftar Resep");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initComponents();
        loadData();
        setResizable(false); 
    }

    private void initComponents() {
        contentPanel = new JPanel();
        contentPanel.setBackground(new Color(0, 47, 91));
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        

        // Tombol Home
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(new Color(0, 47, 91));
        JButton homeButton = new JButton("Home");
        homeButton.setFont(new Font("Nirmala UI", Font.PLAIN, 14));
        homeButton.setBackground(new Color(255, 255, 255));
        homeButton.setForeground(new Color(0, 47, 91));
        homeButton.addActionListener(e -> {
            new home().setVisible(true);
            this.dispose();
        });
        headerPanel.add(homeButton);
        contentPanel.add(headerPanel);

        // Label My Taste
        JLabel myRecipeLabel = new JLabel("My Taste");
        myRecipeLabel.setFont(new Font("Nirmala UI", Font.BOLD, 24));
        myRecipeLabel.setForeground(Color.WHITE);
        myRecipeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        contentPanel.add(myRecipeLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        myRecipePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        myRecipePanel.setBackground(new Color(0, 47, 91));
        contentPanel.add(myRecipePanel);

        // Label Other Taste
        JLabel otherRecipeLabel = new JLabel("Other Taste");
        otherRecipeLabel.setFont(new Font("Nirmala UI", Font.BOLD, 24));
        otherRecipeLabel.setForeground(Color.WHITE);
        otherRecipeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        contentPanel.add(otherRecipeLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        otherRecipePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        otherRecipePanel.setBackground(new Color(0, 47, 91));
        contentPanel.add(otherRecipePanel);

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        setContentPane(scrollPane);
    }

    private void loadData() {
        loadMyRecipes();
        loadOtherRecipes();
    }

    private void loadMyRecipes() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sharetaste", "root", "");
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM resep WHERE user_id = ?");
            ps.setInt(1, currentUserId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String photo = rs.getString("photo");
                String ingredients = rs.getString("ingredients");
                String instructions = rs.getString("instructions");
                JPanel card = createRecipeCard(id, title, photo, ingredients, instructions, currentUserId, Session.getFullName());
                myRecipePanel.add(card);
            }

            rs.close();
            ps.close();
            conn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error load my data: " + e.getMessage());
        }
    }

    private void loadOtherRecipes() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sharetaste", "root", "");
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM resep WHERE user_id <> ?");
            ps.setInt(1, currentUserId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String photo = rs.getString("photo");
                String ingredients = rs.getString("ingredients");
                String instructions = rs.getString("instructions");
                int userId = rs.getInt("user_id");
                String username = getUserFullName(userId, conn);
                JPanel card = createRecipeCard(id, title, photo, ingredients, instructions, userId, username);
                otherRecipePanel.add(card);
            }

            rs.close();
            ps.close();
            conn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error load other data: " + e.getMessage());
        }
    }


    private JPanel createRecipeCard(int id, String title, String photo, String ingredients, String instructions, int userId, String username) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(200, 260));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

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

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Nirmala UI", Font.BOLD, 14));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(titleLabel);

        if (userId != currentUserId) {
            JLabel userLabel = new JLabel("By: " + username);
            userLabel.setFont(new Font("Nirmala UI", Font.ITALIC, 12));
            userLabel.setForeground(Color.DARK_GRAY);
            userLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            card.add(userLabel);
        }

        JButton lihatButton = new JButton("Detail");
        lihatButton.setFont(new Font("Nirmala UI", Font.PLAIN, 12));
        lihatButton.setForeground(Color.WHITE);
        lihatButton.setBackground(new Color(0, 47, 91));
        lihatButton.addActionListener(e -> {
            String message = "Bahan-bahan:\n" + ingredients + "\n\nLangkah-langkah:\n" + instructions;
            JOptionPane.showMessageDialog(this, message, "Detail: " + title, JOptionPane.INFORMATION_MESSAGE);
        });
        card.add(lihatButton);

        if (userId == currentUserId) {
            JButton editButton = new JButton("Edit");
            editButton.setFont(new Font("Nirmala UI", Font.PLAIN, 12));
            editButton.setForeground(Color.WHITE);
            editButton.setBackground(new Color(255, 153, 51));
            editButton.addActionListener(e -> {
                EditRecipe editFrame = new EditRecipe(id);
                editFrame.setVisible(true);
                this.dispose();
            });
            card.add(editButton);
        }


        return card;
    }

    private String getUserFullName(int userId, Connection conn) {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT fullname FROM user WHERE id = ?");
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("fullname");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Unknown";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ViewRecipe().setVisible(true));
    }
}
