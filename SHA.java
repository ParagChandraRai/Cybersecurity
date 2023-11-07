/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cybersecurity;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.swing.*;

public class SHA {
    private JFrame frame;
    private JTextField inputText;
    private JTextArea resultTextArea;

    public SHA() {
        frame = new JFrame("SHA-256 Hash Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);

        JPanel panel = new JPanel(new GridLayout(3, 1));

        JLabel inputLabel = new JLabel("Input String:");
        inputText = new JTextField();

        JButton calculateButton = new JButton("Calculate SHA-256 Hash");
        calculateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String input = inputText.getText();
                if (input.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter an input string.");
                    return;
                }

                try {
                    String sha256Hash = calculateSHA256Hash(input);
                    displayResult(input, sha256Hash);
                } catch (NoSuchAlgorithmException ex) {
                    JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
                }
            }
        });

        resultTextArea = new JTextArea();
        resultTextArea.setEditable(false);

        panel.add(inputLabel);
        panel.add(inputText);
        panel.add(calculateButton);

        frame.add(panel, BorderLayout.NORTH);
        frame.add(resultTextArea, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private String calculateSHA256Hash(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(input.getBytes(StandardCharsets.UTF_8));

        BigInteger number = new BigInteger(1, hash);
        StringBuilder hexString = new StringBuilder(number.toString(16));

        while (hexString.length() < 64) {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }

    private void displayResult(String input, String sha256Hash) {
        String result = "Input String: " + input + "\n" +
                "SHA-256 Hash: " + sha256Hash;
        resultTextArea.setText(result);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SHA();
            }
        });
    }
}
