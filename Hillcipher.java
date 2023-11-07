/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cybersecurity;

import java.awt.Color;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class MyFrame extends JFrame {
    JButton b1, b2;
    JLabel l1, l2;
    JTextField t1, t2;

    MyFrame() {
        super("Hill Cipher");
        l1 = new JLabel("Enter Text");
        l2 = new JLabel("Enter Key Matrix");
        t1 = new JTextField();
        t2 = new JTextField();

        l1.setBounds(20, 20, 100, 20);
        t1.setBounds(120, 20, 120, 20);
        l2.setBounds(20, 60, 100, 20);
        t2.setBounds(120, 60, 120, 20);

        b1 = new JButton("Encrypt");
        b2 = new JButton("Decrypt");
        

        b1.setBounds(20, 180, 100, 30);
        b2.setBounds(140, 180, 100, 30);

        add(l1);
        add(l2);
        add(b1);
        add(b2);
        add(t1);
        add(t2);
        setLayout(null);

        // Add action listeners for the buttons
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputText = t1.getText();
                int key=7;

                String encryptedText = encrypt(inputText, key);
                t1.setText(encryptedText);
                JOptionPane.showMessageDialog(null,"Encrypted "+encryptedText);  
            }
        });

        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputText = t1.getText();
                int key = 7;

                String decryptedText = decrypt(inputText, key);
                t1.setText(decryptedText);
               JOptionPane.showMessageDialog(null,"Decrypted :"+decryptedText );  
            
            }
        });
    }

    // Caesar Cipher encryption
    private String encrypt(String text, int key) {
        StringBuilder result = new StringBuilder();

        for (char character : text.toCharArray()) {
            if (Character.isLetter(character)) {
                char base = Character.isUpperCase(character) ? 'A' : 'a';
                result.append((char) ((character - base + key) % 26 + base));
            } else {
                result.append(character);
            }
        }

        return result.toString();
    }

    // Caesar Cipher decryption
    private String decrypt(String text, int key) {
        return encrypt(text, 26 - key); // Decrypting is just encrypting with the inverse key
    }
}

public class Hillcipher {
    public static void main(String[] args) {
        MyFrame f = new MyFrame();
        f.setSize(500, 300);
         f.getContentPane().setBackground(Color.lightGray);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
