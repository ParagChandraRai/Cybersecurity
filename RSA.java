import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.BigInteger;

class RSASwingFrame extends JFrame {
    JLabel labelP, labelQ, labelMessage, labelEncryptedMessage, labelDecryptedMessage;
    JTextField textFieldP, textFieldQ, textFieldMessage, textFieldEncryptedMessage, textFieldDecryptedMessage;
    JButton calculateButton;

    RSASwingFrame() {
        setTitle("RSA Encryption and Decryption");
        setLayout(null);

        labelP = new JLabel("Enter the 1st prime number (p):");
        labelQ = new JLabel("Enter the 2nd prime number (q):");
        labelMessage = new JLabel("Enter the message to be encrypted/decrypted:");
        labelEncryptedMessage = new JLabel("Encrypted Message:");
        labelDecryptedMessage = new JLabel("Decrypted Message:");

        textFieldP = new JTextField();
        textFieldQ = new JTextField();
        textFieldMessage = new JTextField();
        textFieldEncryptedMessage = new JTextField();
        textFieldEncryptedMessage.setEditable(false);
        textFieldDecryptedMessage = new JTextField();
        textFieldDecryptedMessage.setEditable(false);

        calculateButton = new JButton("Calculate");

        labelP.setBounds(20, 20, 280, 20);
        textFieldP.setBounds(320, 20, 200, 20);
        labelQ.setBounds(20, 50, 280, 20);
        textFieldQ.setBounds(320, 50, 200, 20);
        labelMessage.setBounds(20, 80, 280, 20);
        textFieldMessage.setBounds(320, 80, 200, 20);
        labelEncryptedMessage.setBounds(20, 110, 280, 20);
        textFieldEncryptedMessage.setBounds(320, 110, 200, 20);
        labelDecryptedMessage.setBounds(20, 140, 280, 20);
        textFieldDecryptedMessage.setBounds(320, 140, 200, 20);
        calculateButton.setBounds(180, 180, 200, 30);

        add(labelP);
        add(textFieldP);
        add(labelQ);
        add(textFieldQ);
        add(labelMessage);
        add(textFieldMessage);
        add(labelEncryptedMessage);
        add(textFieldEncryptedMessage);
        add(labelDecryptedMessage);
        add(textFieldDecryptedMessage);
        add(calculateButton);

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                int p = Integer.parseInt(textFieldP.getText());
                int q = Integer.parseInt(textFieldQ.getText());
                int n = p * q;
                int z = (p - 1) * (q - 1);
                int d = 0;
                int message = Integer.parseInt(textFieldMessage.getText());
                double c;
                BigInteger msgback;

                int publicKeyExponent = 0; // Rename variable
                int privateKeyExponent = 0; // Rename variable

                for (int exponent = 2; exponent < z; exponent++) {
                    if (gcd(exponent, z) == 1) {
                        publicKeyExponent = exponent;
                        break;
                    }
                }

                for (int i = 0; i <= 9; i++) {
                    int x = 1 + (i * z);
                    if (x % publicKeyExponent == 0) {
                        privateKeyExponent = x / publicKeyExponent;
                        break;
                    }
                }

                c = (Math.pow(message, publicKeyExponent)) % n;
                BigInteger N = BigInteger.valueOf(n);
                BigInteger C = BigDecimal.valueOf(c).toBigInteger();
                msgback = (C.pow(privateKeyExponent)).mod(N);

                textFieldEncryptedMessage.setText(String.valueOf(c));
                textFieldDecryptedMessage.setText(msgback.toString());
            }
        });
    }

    static int gcd(int e, int z) {
        if (e == 0)
            return z;
        else
            return gcd(z % e, e);
    }
}

public class RSA {
    public static void main(String[] args) {
        RSASwingFrame frame = new RSASwingFrame();
        frame.setSize(550, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
