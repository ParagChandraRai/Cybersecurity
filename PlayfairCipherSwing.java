import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashSet;
import java.util.Set;

class Playfair {
    private String key;
    private String plainText;
    private char[][] matrix = new char[5][5];

    public Playfair(String key, String plainText) {
        this.key = key.toLowerCase();
        this.plainText = plainText.toLowerCase();
    }

    public void cleanPlayFairKey() {
        LinkedHashSet<Character> set = new LinkedHashSet<Character>();
        String newKey = "";

        for (int i = 0; i < key.length(); i++)
            set.add(key.charAt(i));

        for (Character character : set)
            newKey += character;

        key = newKey;
    }

    public void generateCipherKey() {
        Set<Character> set = new LinkedHashSet<Character>();

        for (int i = 0; i < key.length(); i++) {
            if (key.charAt(i) == 'j')
                continue;
            set.add(key.charAt(i));
        }

        String tempKey = key;

        for (int i = 0; i < 26; i++) {
            char ch = (char) (i + 97);
            if (ch == 'j')
                continue;
            if (!set.contains(ch))
                tempKey += ch;
        }

        for (int i = 0, idx = 0; i < 5; i++)
            for (int j = 0; j < 5; j++)
                matrix[i][j] = tempKey.charAt(idx++);

       StringBuilder matrixText = new StringBuilder("Playfair Cipher Key Matrix:\n");
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                matrixText.append(matrix[i][j]).append(" ");
            }
            matrixText.append("\n");
        }

        JOptionPane.showMessageDialog(null, matrixText.toString(), "Playfair Cipher Key Matrix", JOptionPane.INFORMATION_MESSAGE);    }

    public String formatPlainText() {
        String message = "";
        int len = plainText.length();

        for (int i = 0; i < len; i++) {
            if (plainText.charAt(i) == 'j')
                message += 'i';
            else
                message += plainText.charAt(i);
        }

        for (int i = 0; i < message.length(); i += 2) {
            if (message.charAt(i) == message.charAt(i + 1))
                message = message.substring(0, i + 1) + 'x' + message.substring(i + 1);
        }

        if (len % 2 == 1)
            message += 'x';

        return message;
    }

    public String[] formPairs(String message) {
        int len = message.length();
        String[] pairs = new String[len / 2];

        for (int i = 0, cnt = 0; i < len / 2; i++)
            pairs[i] = message.substring(cnt, cnt += 2);

        return pairs;
    }

    public int[] getCharPos(char ch) {
        int[] keyPos = new int[2];

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (matrix[i][j] == ch) {
                    keyPos[0] = i;
                    keyPos[1] = j;
                    break;
                }
            }
        }
        return keyPos;
    }

    public String encryptMessage() {
        String message = formatPlainText();
        String[] msgPairs = formPairs(message);
        StringBuilder encText = new StringBuilder();

        for (String msgPair : msgPairs) {
            char ch1 = msgPair.charAt(0);
            char ch2 = msgPair.charAt(1);
            int[] ch1Pos = getCharPos(ch1);
            int[] ch2Pos = getCharPos(ch2);

            if (ch1Pos[0] == ch2Pos[0]) {
                ch1Pos[1] = (ch1Pos[1] + 1) % 5;
                ch2Pos[1] = (ch2Pos[1] + 1) % 5;
            } else if (ch1Pos[1] == ch2Pos[1]) {
                ch1Pos[0] = (ch1Pos[0] + 1) % 5;
                ch2Pos[0] = (ch2Pos[0] + 1) % 5;
            } else {
                int temp = ch1Pos[1];
                ch1Pos[1] = ch2Pos[1];
                ch2Pos[1] = temp;
            }

            encText.append(matrix[ch1Pos[0]][ch1Pos[1]]);
            encText.append(matrix[ch2Pos[0]][ch2Pos[1]]);
        }

        return encText.toString();
    }
}

public class PlayfairCipherSwing {
    private JFrame frame;
    private JTextField keyField;
    private JTextField inputField;
    private JTextArea outputArea;
    private Playfair playfair;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PlayfairCipherSwing());
    }

    public PlayfairCipherSwing() {
        frame = new JFrame("Playfair Cipher");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setLayout(null);

        JLabel keyLabel = new JLabel("Enter the key for Playfair Cipher:");
        keyLabel.setBounds(20, 20, 220, 20);
        frame.add(keyLabel);

        keyField = new JTextField();
        keyField.setBounds(250, 20, 220, 20);
        frame.add(keyField);

        JLabel inputLabel = new JLabel("Enter the plaintext to be encipher:");
        inputLabel.setBounds(20, 60, 220, 20);
        frame.add(inputLabel);

        inputField = new JTextField();
        inputField.setBounds(250, 60, 220, 20);
        frame.add(inputField);

        JButton encryptButton = new JButton("Encrypt");
        encryptButton.setBounds(20, 100, 150, 30);
        frame.add(encryptButton);

        JButton clearButton = new JButton("Clear");
        clearButton.setBounds(200, 100, 150, 30);
        frame.add(clearButton);

        outputArea = new JTextArea();
        outputArea.setBounds(20, 140, 450, 100);
        outputArea.setEditable(false);
        frame.add(outputArea);

        playfair = new Playfair("", "");

        encryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String key = keyField.getText();
                String input = inputField.getText();
                playfair = new Playfair(key, input);
                playfair.cleanPlayFairKey();
                playfair.generateCipherKey();
                String encText = playfair.encryptMessage();
                outputArea.setText("Encrypted Message:\n" + encText);
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                keyField.setText("");
                inputField.setText("");
                outputArea.setText("");
            }
        });

        frame.setVisible(true);
    }
}
