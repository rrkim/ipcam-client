package com.rrkim;

import com.rrkim.frame.ProxyLoginFrame;
import javax.swing.*;


public class WebcamPlayer {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ProxyLoginFrame::new);
    }
}
