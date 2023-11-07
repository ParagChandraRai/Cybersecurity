/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cybersecurity;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class DES {
    private JFrame frame;
    private JTextField inputText;
    private JTextField encryptedText;
    private JTextField decryptedText;

    private DESExample desExample;

    public DES() {
        frame = new JFrame("DES Encryption/Decryption");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel(new GridLayout(4, 2));

        JLabel inputLabel = new JLabel("Input Text:");
        inputText = new JTextField();

        JButton encryptButton = new JButton("Encrypt");
        encryptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String input = inputText.getText();
                if (input.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter text to encrypt.");
                    return;
                }

                try {
                    String encryptedMsg = desExample.encrypt(input);
                    encryptedText.setText(encryptedMsg);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Encryption failed: " + ex.getMessage());
                }
            }
        });

        JButton decryptButton = new JButton("Decrypt");
        decryptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String encryptedMsg = encryptedText.getText();
                if (encryptedMsg.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter text to decrypt.");
                    return;
                }

                try {
                    String decryptedMsg = desExample.decrypt(encryptedMsg);
                    decryptedText.setText(decryptedMsg);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Decryption failed: " + ex.getMessage());
                }
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
        panel.add(encryptButton);
        panel.add(decryptButton);
        panel.add(encryptedLabel);
        panel.add(encryptedText);
        panel.add(decryptedLabel);
        panel.add(decryptedText);

        frame.add(panel);
        frame.setVisible(true);

        try {
            SecretKey key = KeyGenerator.getInstance("DES").generateKey();
            desExample = new DESExample(key);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Initialization failed: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new DES();
            }
        });
    }
}

class DESExample {
    Cipher ecipher;
    Cipher dcipher;

    DESExample(SecretKey key) throws Exception {
        ecipher = Cipher.getInstance("DES");
        dcipher = Cipher.getInstance("DES");
        ecipher.init(Cipher.ENCRYPT_MODE, key);
        dcipher.init(Cipher.DECRYPT_MODE, key);
    }

    public String encrypt(String str) throws Exception {
        byte[] utf8 = str.getBytes(StandardCharsets.UTF_8);
        byte[] enc = ecipher.doFinal(utf8);
        return Base64.getEncoder().encodeToString(enc);
    }

    public String decrypt(String str) throws Exception {
        byte[] dec = Base64.getDecoder().decode(str);
        byte[] utf8 = dcipher.doFinal(dec);
        return new String(utf8, StandardCharsets.UTF_8);
    }
} 
