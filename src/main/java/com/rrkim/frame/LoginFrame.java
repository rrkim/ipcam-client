package com.rrkim.frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class LoginFrame extends JFrame {

    JTextField hostTextField;
    JTextField portTextField;

    public LoginFrame() {
        setTitle("Login Frame");
        setBounds(0, 0, 450, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel devicePanel = getDevicePanel();
        JPanel buttonPanel = getButtonPanel();

        // 공간 추가
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));
        panel.add(new JPanel());
        panel.add(devicePanel);
        panel.add(buttonPanel);

        add(panel);
        setVisible(true);
    }

    private JPanel getDevicePanel() {
        JLabel hostLabel = new JLabel("호스트:");
        hostTextField = new JTextField(20);

        // 포트 입력란
        portTextField = new JTextField(5);

        // 입력 패널
        JPanel devicePanel = new JPanel();
        devicePanel.setLayout(new FlowLayout());
        devicePanel.add(hostLabel);
        devicePanel.add(hostTextField);
        devicePanel.add(new JLabel(":"));
        devicePanel.add(portTextField);

        return devicePanel;
    }

    private JPanel getButtonPanel() {
        JButton selectFileButton = new JButton("TCI 파일 선택");
        selectFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    loginProcess(hostTextField.getText(), portTextField.getText(), selectedFile);
                }
            }
        });
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(selectFileButton);

        return buttonPanel;
    }

    private void loginProcess(String host, String port, File talchwiFile){
        dispose();

        try {
            new IPCamFrame(host, port, talchwiFile);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "IP 카메라 뷰어를 실행하는 중에 오류가 발생했습니다!");
            System.exit(0);
        }
    }

}
