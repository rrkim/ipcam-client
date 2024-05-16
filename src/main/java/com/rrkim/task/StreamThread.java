package com.rrkim.task;

import com.rrkim.dto.CameraIdentity;
import com.rrkim.frame.IPCamFrame;
import com.rrkim.utility.*;
import org.apache.commons.codec.DecoderException;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class StreamThread extends Thread {

    private final IPCamFrame ipCamFrame;

    public StreamThread(IPCamFrame ipCamFrame) {
        this.ipCamFrame = ipCamFrame;
    }

    public void run() {
        try {
            getStreamVideo();
        } catch (DecoderException | InvalidAlgorithmParameterException | NoSuchPaddingException |
                 IllegalBlockSizeException | IOException | NoSuchAlgorithmException | BadPaddingException |
                 InvalidKeySpecException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    private void getStreamVideo() throws IOException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, DecoderException, InvalidKeySpecException {
        HttpURLConnection streamConnection = getHttpUrlConnection(ipCamFrame.getStreamUrl());

        try {
            HttpURLConnection getSecureKeyConnection = getHttpUrlConnection(ipCamFrame.getSecureKeyUrl());
            String secureKeyResponseString = HttpUtility.getHttpResponse(getSecureKeyConnection);

            SecureKey secureKey = JsonUtility.convertObject(secureKeyResponseString, SecureKey.class);
            String secureKeyString = secureKey.getSecureKey();

            String tci = FileUtility.getText(ipCamFrame.getTalchwiFile());
            String decodedTciString = StringUtility.decodeBase64(tci);
            CameraIdentity cameraIdentity = JsonUtility.convertObject(decodedTciString, CameraIdentity.class);
            String privateKey = cameraIdentity.getCredential();

            String symmetricKey = RsaUtility.decryptData(secureKeyString, privateKey);
            System.out.println("symmetricKey = " + symmetricKey);

            while(true) {
                String encodedFrameString = HttpUtility.getHttpResponse(streamConnection, '\0');
                byte[] frameBytes = AesUtility.decryptData(encodedFrameString, symmetricKey);

                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(frameBytes);
                BufferedImage bufferedImage = ImageIO.read(byteArrayInputStream);

                ipCamFrame.setFrameData(bufferedImage);
            }
        } finally {
            streamConnection.disconnect();
        }
    }

    private HttpURLConnection getHttpUrlConnection(String path) throws IOException {
        String url = String.format("http://%s:%s%s", ipCamFrame.getHost(), ipCamFrame.getPort(), path);
        return HttpUtility.getHttpUrlConnection(url, "GET");
    }
}
