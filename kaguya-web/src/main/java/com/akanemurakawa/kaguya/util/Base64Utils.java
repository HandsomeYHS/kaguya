package com.akanemurakawa.kaguya.util;

import sun.misc.BASE64Decoder;
import java.io.IOException;

public class Base64Utils {

    /**
     * Base64解码
     */
    public byte[] decodeBuffer(String s) throws IOException {
        return new BASE64Decoder().decodeBuffer(s);
    }

}
