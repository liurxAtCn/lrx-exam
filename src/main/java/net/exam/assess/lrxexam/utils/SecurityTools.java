package net.exam.assess.lrxexam.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

/**
 * @Aytor lrx
 * @Date 2021-09-05
 * tools to handle sec info
 *
 */
public class SecurityTools {

    private static final Logger LOGGER = LoggerFactory.getLogger( SecurityTools.class);

    /**
     * get footprint info from sec msg
     * @param secMsg
     * @return
     */
    public static String  getFootPrint( String secMsg){

        try {
            MessageDigest sha = null;
            sha = MessageDigest.getInstance( "SHA");
            byte[] inputArray = secMsg.getBytes(StandardCharsets.UTF_8);
            byte[] secArray = sha.digest( inputArray);
            Base64.Encoder encoder = Base64.getEncoder();
            return encoder.encodeToString( secArray);
        } catch ( Exception e) {
            LOGGER.error("digest exception",e);
            throw new RuntimeException( e);
        }

    }
}
