package gui;

import PackerUnpacker.UnpackerX;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class UnpackerGui
 {
    public static void main(String[] args) {
        try {
            JFrame frame = new JFrame("Unpacker");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(null);
            frame.setSize(400, 200);

            JLabel label1 = new JLabel("Packed File Name:");
            label1.setBounds(20, 50, 150, 25);
            label1.setHorizontalAlignment(JLabel.LEFT);

            JTextField field1 = new JTextField(20);
            field1.setBounds(160, 50, 200, 25);
            field1.setHorizontalAlignment(JTextField.CENTER);
            field1.setBackground(Color.WHITE);
            field1.setForeground(Color.DARK_GRAY);

            JButton button = new JButton("Unpack");
            button.setBounds(140, 100, 120, 30);

            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String packName = field1.getText().trim();

                    if (packName.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "File name is required!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    try {
                        UnpackerX mobj = new UnpackerX(packName);
                        mobj.UnpackingActivity();
                        JOptionPane.showMessageDialog(frame, "Unpacking completed successfully!");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    }
                }
            });

            // Add components before showing
            frame.add(button);
            frame.add(label1);
            frame.add(field1);

            frame.setVisible(true);
        } catch (Exception eobj) {
            eobj.printStackTrace();
        }
    }
}
