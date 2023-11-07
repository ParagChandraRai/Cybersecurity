package cybersecurity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class vigenere {
    private JFrame frame;
    private JTextField inputText;
    private JTextField keyText;
    private JTextField encryptedText;
    private JTextField decryptedText;

    public vigenere() {
        frame = new JFrame("Vigenere Cipher");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);

        JPanel panel = new JPanel(new GridLayout(5, 2));

        JLabel inputLabel = new JLabel("Input Text:");
        inputText = new JTextField();

        JLabel keyLabel = new JLabel("Key:");
        keyText = new JTextField();

        JButton encryptButton = new JButton("Encrypt");
        encryptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String input = inputText.getText();
                String key = keyText.getText();
                String encryptedMsg = encrypt(input, key);
                encryptedText.setText(encryptedMsg);
            }
        });

        JButton decryptButton = new JButton("Decrypt");
        decryptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String encryptedMsg = encryptedText.getText();
                String key = keyText.getText();
                String decryptedMsg = decrypt(encryptedMsg, key);
                decryptedText.setText(decryptedMsg);
            }
        });

        JLabel encryptedLabel = new JLabel("Encrypted Text:");
        encryptedText = new JTextField();
        encryptedText.setEditable(false);

        JLabel decryptedLabel = new JLabel("Decrypted Text:");
        decryptedText = new JTextField();
        decryptedText.setEditable(false);

        panel.add(inputLabel);
        panel.add(inputText);
        panel.add(keyLabel);
        panel.add(keyText);
        panel.add(encryptButton);
        panel.add(decryptButton);
        panel.add(encryptedLabel);
        panel.add(encryptedText);
        panel.add(decryptedLabel);
        panel.add(decryptedText);

        frame.add(panel);

        frame.setVisible(true);
    }

    // Function to encrypt the input text using the Vigenere cipher
  public static String encrypt(String text, String key) {
        StringBuilder result = new StringBuilder();
        text = text.toUpperCase();
        key = key.toUpperCase();
        int keyLength = key.length();
        for (int i = 0, j = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c < 'A' || c > 'Z') {
                result.append(c);
            } else {
                char encryptedChar = (char) ((c + key.charAt(j) - 2 * 'A') % 26 + 'A');
                result.append(encryptedChar);
                j = (j + 1) % keyLength;
            }
        }
        return result.toString();
    }

    // Function to decrypt the input text using the Vigenere cipher
    public static String decrypt(String text, String key) {
        StringBuilder result = new StringBuilder();
        text = text.toUpperCase();
        key = key.toUpperCase();
        int keyLength = key.length();
        for (int i = 0, j = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c < 'A' || c > 'Z') {
                result.append(c);
            } else {
                char decryptedChar = (char) ((c - key.charAt(j) + 26) % 26 + 'A');
                result.append(decryptedChar);
                j = (j + 1) % keyLength;
            }
        }
        return result.toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new vigenere();
            }
        });
    }
}