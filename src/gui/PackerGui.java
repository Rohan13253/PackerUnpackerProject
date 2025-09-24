package gui;

import PackerUnpacker.PackerX;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class PackerGui {
    public static void main(String[] args) {
        try {
            JFrame frame = new JFrame("Packer");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(null);
            frame.setSize(400, 250);

            JLabel label1 = new JLabel("Directory Name:");
            label1.setBounds(10, 50, 120, 25);

            JTextField field1 = new JTextField(20);
            field1.setBounds(140, 50, 200, 25);

            JLabel label2 = new JLabel("Packed File Name:");
            label2.setBounds(10, 90, 120, 25);

            JTextField field2 = new JTextField(20);
            field2.setBounds(140, 90, 200, 25);

            JButton button = new JButton("Pack");
            button.setBounds(140, 150, 120, 25);

            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String dirName = field1.getText().trim();
                    String packName = field2.getText().trim();

                    if (dirName.isEmpty() || packName.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "Both fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    PackerX mobj = new PackerX(packName, dirName);
                    boolean success = mobj.PackingActivity();

                    if (success) {
                        JOptionPane.showMessageDialog(frame, "Packing completed successfully!");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Packing failed! Check console for details.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            frame.add(button);
            frame.add(label1);
            frame.add(field1);
            frame.add(label2);
            frame.add(field2);

            frame.setVisible(true);
        } catch (Exception eobj) {
            eobj.printStackTrace();
        }
    }
}
