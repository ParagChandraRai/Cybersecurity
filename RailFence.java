
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class RailFenceFrame extends JFrame {
    JLabel labelPlain, labelDepth, labelResult;
    JTextField textFieldPlain, textFieldDepth, textFieldResult;
    JButton encryptButton, decryptButton;

    RailFenceFrame() {
        setTitle("Rail Fence Cipher");
        setLayout(null);

        labelPlain = new JLabel("Enter plain text:");
        labelDepth = new JLabel("Enter depth for Encryption:");
        labelResult = new JLabel("Result:");

        textFieldPlain = new JTextField();
        textFieldDepth = new JTextField();
        textFieldResult = new JTextField();
        textFieldResult.setEditable(false);

        encryptButton = new JButton("Encrypt");
        decryptButton = new JButton("Decrypt");

        labelPlain.setBounds(20, 20, 150, 20);
        textFieldPlain.setBounds(180, 20, 200, 20);
        labelDepth.setBounds(20, 50, 150, 20);
        textFieldDepth.setBounds(180, 50, 200, 20);
        labelResult.setBounds(20, 80, 100, 20);
        textFieldResult.setBounds(180, 80, 200, 20);
        encryptButton.setBounds(180, 110, 90, 30);
        decryptButton.setBounds(290, 110, 90, 30);

        add(labelPlain);
        add(textFieldPlain);
        add(labelDepth);
        add(textFieldDepth);
        add(labelResult);
        add(textFieldResult);
        add(encryptButton);
        add(decryptButton);

        encryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String plainText = textFieldPlain.getText();
                int depth = Integer.parseInt(textFieldDepth.getText());
                String cipherText = RailFenceBasic.Encryption(plainText, depth);
                textFieldResult.setText(cipherText);
                
            }
        });

        decryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cipherText = textFieldResult.getText();
                int depth = Integer.parseInt(textFieldDepth.getText());
                String plainText = RailFenceBasic.Decryption(cipherText, depth);
                textFieldResult.setText(plainText);
            }
        });
    }
}

class RailFenceBasic {
   static String Encryption(String plainText, int depth) {
    int len = plainText.length();
    char[][] rail = new char[depth][len];
    
    // Initialize the rail matrix with spaces
    for (int i = 0; i < depth; i++) {
        for (int j = 0; j < len; j++) {
            rail[i][j] = ' ';
        }
    }
    
    int dir = -1; // Direction (up or down)
    int row = 0;
    int col = 0;
    
    for (int i = 0; i < len; i++) {
        if (row == 0 || row == depth - 1) {
            dir = -dir; // Change direction at the top or bottom rail
        }
        
        rail[row][col] = plainText.charAt(i);
        row += dir;
        col++;
    }
    
    StringBuilder cipherText = new StringBuilder();
    for (int i = 0; i < depth; i++) {
        for (int j = 0; j < len; j++) {
            if (rail[i][j] != ' ') {
                cipherText.append(rail[i][j]);
            }
        }
    }
    
    return cipherText.toString();
}

static String Decryption(String cipherText, int depth) {
    int len = cipherText.length();
    char[][] rail = new char[depth][len];
    
    for (int i = 0; i < depth; i++) {
        for (int j = 0; j < len; j++) {
            rail[i][j] = ' ';
        }
    }
    
    int dir = -1;
    int row = 0;
    int col = 0;
    
    // Mark the positions in the rail matrix with '*'
    for (int i = 0; i < len; i++) {
        if (row == 0 || row == depth - 1) {
            dir = -dir;
        }
        
        rail[row][col] = '*';
        row += dir;
        col++;
    }
    
    int index = 0;
    
    for (int i = 0; i < depth; i++) {
        for (int j = 0; j < len; j++) {
            if (rail[i][j] == '*' && index < len) {
                rail[i][j] = cipherText.charAt(index++);
            }
        }
    }
    
    row = 0;
    col = 0;
    dir = -1;
    
    StringBuilder plainText = new StringBuilder();
    
    // Read the characters from the rail matrix
    for (int i = 0; i < len; i++) {
        if (row == 0 || row == depth - 1) {
            dir = -dir;
        }
        
        plainText.append(rail[row][col]);
        row += dir;
        col++;
    }
    
    return plainText.toString();
}

}

public class RailFence {
    public static void main(String[] args) {
        RailFenceFrame frame = new RailFenceFrame();
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
