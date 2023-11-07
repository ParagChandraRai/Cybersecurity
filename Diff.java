/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cybersecurity;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class DiffieHellmanFrame extends JFrame {
    JLabel labelP, labelG, labelAliceSecret, labelBobSecret, labelResult;
    JTextField textFieldP, textFieldG, textFieldAliceSecret, textFieldBobSecret, textFieldResult;
    JButton calculateButton;

    DiffieHellmanFrame() {
        setTitle("Diffie-Hellman Key Exchange");
        setLayout(null);

        labelP = new JLabel("Enter modulo (p):");
        labelG = new JLabel("Enter primitive root (g):");
        labelAliceSecret = new JLabel("Choose Alice's secret no (a):");
        labelBobSecret = new JLabel("Choose Bob's secret no (b):");
        labelResult = new JLabel("Shared Secret Key:");

        textFieldP = new JTextField();
        textFieldG = new JTextField();
        textFieldAliceSecret = new JTextField();
        textFieldBobSecret = new JTextField();
        textFieldResult = new JTextField();
        textFieldResult.setEditable(false);

        calculateButton = new JButton("Calculate Shared Secret Key");

        labelP.setBounds(20, 20, 180, 20);
        textFieldP.setBounds(220, 20, 100, 20);
        labelG.setBounds(20, 50, 180, 20);
        textFieldG.setBounds(220, 50, 100, 20);
        labelAliceSecret.setBounds(20, 80, 220, 20);
        textFieldAliceSecret.setBounds(220, 80, 100, 20);
        labelBobSecret.setBounds(20, 110, 220, 20);
        textFieldBobSecret.setBounds(220, 110, 100, 20);
        labelResult.setBounds(20, 140, 180, 20);
        textFieldResult.setBounds(220, 140, 100, 20);
        calculateButton.setBounds(100, 180, 200, 30);

        add(labelP);
        add(textFieldP);
        add(labelG);
        add(textFieldG);
        add(labelAliceSecret);
        add(textFieldAliceSecret);
        add(labelBobSecret);
        add(textFieldBobSecret);
        add(labelResult);
        add(textFieldResult);
        add(calculateButton);

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int p = Integer.parseInt(textFieldP.getText());
                int g = Integer.parseInt(textFieldG.getText());
                int a = Integer.parseInt(textFieldAliceSecret.getText());
                int b = Integer.parseInt(textFieldBobSecret.getText());

                int A = (int) Math.pow(g, a) % p;
                int B = (int) Math.pow(g, b) % p;

                int S_A = (int) Math.pow(B, a) % p;
                int S_B = (int) Math.pow(A, b) % p;

                if (S_A == S_B) {
                    textFieldResult.setText(Integer.toString(S_A));
                } else {
                    textFieldResult.setText("Alice and Bob cannot communicate with each other!");
                }
            }
        });
    }
}

public class Diff{
    public static void main(String[] args) {
        DiffieHellmanFrame frame = new DiffieHellmanFrame();
        frame.setSize(400, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
