package com.rrkim.frame;

import com.rrkim.dto.CameraIdentity;
import com.rrkim.task.StreamThread;
import com.rrkim.utility.*;
import org.apache.commons.codec.DecoderException;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class IPCamFrame extends CanvasFrame {

    private final String streamUrl = "/stream";
    private final String secureKeyUrl = "/auth/secure-key";
    private final String host;
    private final String port;
    private final File talchwiFile;

    public IPCamFrame(String host, String port, File talchwiFile) {
        super("IP Cam Viewer");
        this.host = host;
        this.port = port;
        this.talchwiFile = talchwiFile;
        setLocation(50, 50);
        setCanvasScale(0.5);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        StreamThread streamThread = new StreamThread(this);
        streamThread.start();

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (streamThread.isAlive()) {
                    streamThread.interrupt();
                }
            }
        });
    }

    public void setFrameData(BufferedImage bufferedImage) {
        Frame frameData;
        try (Java2DFrameConverter paintConverter = new Java2DFrameConverter()) {
            frameData = paintConverter.getFrame(bufferedImage);
        }

        if(frameData == null) { return; }
        this.showImage(frameData);
    }

    public String getStreamUrl() {
        return streamUrl;
    }

    public String getSecureKeyUrl() {
        return secureKeyUrl;
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    public File getTalchwiFile() {
        return talchwiFile;
    }
}
