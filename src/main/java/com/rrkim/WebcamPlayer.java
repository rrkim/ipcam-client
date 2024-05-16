package com.rrkim;

import com.rrkim.frame.LoginFrame;
import javax.swing.*;


public class WebcamPlayer {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginFrame::new);
    }
}
