
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
    private static final Charset UTF_8 = StandardCharsets.UTF_8;
    private JFrame frame;
    private JTextField inputText;
    private JTextArea resultTextArea;

    public MD5() {
        frame = new JFrame("MD5 Hash Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel(new GridLayout(3, 1));

        JLabel inputLabel = new JLabel("Input String:");
        inputText = new JTextField();

        JButton calculateButton = new JButton("Calculate MD5 Hash");
        calculateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String input = inputText.getText();
                if (input.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter an input string.");
                    return;
                }

                String md5Hash = calculateMD5Hash(input);
                displayResult(input, input.length(), md5Hash, md5Hash.length());
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

    private String calculateMD5Hash(String input) {
        byte[] md5InBytes = digest(input.getBytes(UTF_8));
        return bytesToHex(md5InBytes);
    }

    private byte[] digest(byte[] input) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
        return md.digest(input);
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    private void displayResult(String input, int inputLength, String md5Hash, int md5Length) {
        String result = String.format("Input (string)      : %s\n" +
                "Input (length)      : %d\n" +
                "MD5 (hex)           : %s\n" +
                "MD5 (length)        : %d", input, inputLength, md5Hash, md5Length);
        resultTextArea.setText(result);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MD5();
            }
        });
    }
}
