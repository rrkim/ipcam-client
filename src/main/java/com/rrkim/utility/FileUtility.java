package com.rrkim.utility;

import java.io.*;

public class FileUtility {

    public static String getText(File file) throws IOException {
        if(!file.exists()) { return null; }

        StringBuilder text = new StringBuilder();
        try(FileReader reader = new FileReader(file);
            BufferedReader br = new BufferedReader(reader)) {
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
            }
        }
        return text.toString();
    }
}
