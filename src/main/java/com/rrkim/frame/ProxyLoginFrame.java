package com.rrkim.frame;

import nl.captcha.Captcha;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class ProxyLoginFrame extends JFrame {

    private final String PROXY_URL = "ipcam-proxy.rrkim.com";
    private final String PROXY_PORT = "8081";
    private final Captcha captcha = new Captcha.Builder(200, 50).addText().addBorder().addNoise().build();
    private JTextField captchaTextField;

    public ProxyLoginFrame() {
        setTitle("Login Frame");
        setBounds(0, 0, 450, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel buttonPanel = getButtonPanel();
        JPanel captchaPanel = getCaptchaPanel();

        // 공간 추가
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(new JPanel(), BorderLayout.NORTH);
        panel.add(captchaPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
    }

    private JPanel getButtonPanel() {
        JButton selectFileButton = new JButton("TCI 파일 선택");
        selectFileButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                String captchaText = captchaTextField.getText();
                if(captchaText == null || captchaText.isEmpty() || !captcha.isCorrect(captchaText)) {
                    JOptionPane.showMessageDialog(null, "올바른 캡챠 코드를 입력하세요.");
                    return;
                }

                File selectedFile = fileChooser.getSelectedFile();
                loginProcess(selectedFile);
            }
        });
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(selectFileButton);

        return buttonPanel;
    }

    private JPanel getCaptchaPanel() {
        BufferedImage img = captcha.getImage();
        JLabel captchaLabel = new JLabel(new ImageIcon(img));
        captchaTextField = new JTextField(5);

        JPanel captchaPanel = new JPanel();
        captchaPanel.setLayout(new GridLayout(2,1));
        captchaPanel.add(captchaLabel);
        captchaPanel.add(captchaTextField);

        return captchaPanel;
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
