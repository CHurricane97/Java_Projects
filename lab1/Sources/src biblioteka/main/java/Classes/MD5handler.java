package Classes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class MD5handler {

    public String givenFile_generatingChecksum(String pth)
            throws NoSuchAlgorithmException, IOException {
        String filename = pth;


        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(Files.readAllBytes(Paths.get(filename)));
        byte[] bytes = md.digest();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16)
                    .substring(1));
        }
        String generatedmd5 = null;
        generatedmd5 = sb.toString();

        return generatedmd5;
    }

   public boolean checkIfSame(String s1, String s2) {

        return (s1.equals(s2));

    }
}