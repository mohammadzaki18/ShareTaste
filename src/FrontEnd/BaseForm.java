package FrontEnd;

import javax.swing.*;
import java.awt.*;

/**
 * Class induk untuk form GUI — menyediakan fungsi umum untuk reuse.
 */
public class BaseForm extends JFrame {

    public BaseForm(String title) {
        setTitle(title);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
    }

    /** Membuat label berwarna putih */
    protected JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        return label;
    }

    /** Membuat text area dengan scrollbar */
    protected JScrollPane createTextAreaScroll(JTextArea textArea) {
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(textArea);
        scroll.setPreferredSize(new Dimension(250, 70));
        return scroll;
    }

    /** Menampilkan pesan error */
    protected void showError(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    /** Menampilkan pesan sukses */
    protected void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}
