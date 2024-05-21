package com.rrkim.frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ProxyLoginFrame extends JFrame {

    private final String PROXY_URL = "localhost";
    private final String PROXY_PORT = "8081";

    public ProxyLoginFrame() {
        setTitle("Login Frame");
        setBounds(0, 0, 450, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel buttonPanel = getButtonPanel();

        // 공간 추가
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));
        panel.add(new JPanel());
        panel.add(buttonPanel);

        add(panel);
        setVisible(true);
    }

    private JPanel getButtonPanel() {
        JButton selectFileButton = new JButton("TCI 파일 선택");
        selectFileButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                loginProcess(selectedFile);
            }
        });
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(selectFileButton);

        return buttonPanel;
    }

    private void loginProcess(File talchwiFile){
        dispose();

        try {
            new IPCamFrame(PROXY_URL, PROXY_PORT, talchwiFile);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "IP 카메라 뷰어를 실행하는 중에 오류가 발생했습니다!");
            System.exit(0);
        }
    }

}
