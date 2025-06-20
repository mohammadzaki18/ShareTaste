/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java
 */
package FrontEnd;

import javax.swing.SwingUtilities;

public class ShareTaste {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                register registerFrame = new register();
                registerFrame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
                registerFrame.setLocationRelativeTo(null);
                registerFrame.setVisible(true);
            }
        });
    }
}