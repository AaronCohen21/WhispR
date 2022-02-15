package com.aaroncohen.WhispR;

import java.io.InputStream;

/**
 * Code written by Aaron Cohen
 * File Created On: 02/14/2022
 */

public interface FileLoader {
    default InputStream getFileFromResourceAsStream(String fileName) {

        // The class loader that loaded the class
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        // the stream holding the file content
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }

    }
}
