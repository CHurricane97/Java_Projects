

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.Base64;

public class RSAAlgorithm {
    Cipher cipher;
    int maxEncryptionBytes;

    int maxDecryptionBytes;
    
    public String encryptFile(String message, RSAPrivateKey privateKey) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);

        byte[] plainText = message.getBytes();
        int counter = 0;
        ArrayList<Byte> bytes = new ArrayList<Byte>();

        while (counter < plainText.length) {
            byte[] tmp;

            if (counter + maxEncryptionBytes < plainText.length) {
                tmp = new byte[maxEncryptionBytes];
            } else tmp = new byte[plainText.length - counter];

            int loop = plainText.length - counter;

            for (int i = 0; i < loop; i++) {
                if (i == maxEncryptionBytes) break;
                tmp[i] = plainText[counter];
                counter++;
            }

            byte[] cipherText = cipher.doFinal(tmp);

            for (byte b : cipherText) {
                bytes.add(b);
            }
        }

        byte[] b = new byte[bytes.size()];
        for (int i = 0; i < bytes.size(); i++) {
            b[i] = bytes.get(i);
        }

        return Base64.getEncoder().encodeToString(b);
    }

    public String decryptFile(String decryptedMessage, RSAPublicKey publicKey) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        cipher.init(Cipher.DECRYPT_MODE, publicKey);

        byte[] decode = Base64.getDecoder().decode(decryptedMessage);
        int counter = 0;
        StringBuilder resultDecrypt = new StringBuilder();

        while (counter < decode.length) {

            byte[] tmp = new byte[maxDecryptionBytes];

            for (int i = 0; i < decode.length; i++) {
                if (i == maxDecryptionBytes) break;
                tmp[i] = decode[counter];
                counter++;
            }

            byte[] cipherText = cipher.doFinal(tmp);
            resultDecrypt.append(new String(cipherText));
        }

        return resultDecrypt.toString();
    }

    public RSAAlgorithm(int keySize) {
        try {
            cipher = Cipher.getInstance("RSA");
            maxDecryptionBytes = (int) Math.floor((keySize/8.0));
            maxEncryptionBytes = (int) Math.floor((keySize/8.0)) - 11;
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        KeyPairGenerator keyPairGenerator = null;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(600);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

        RSAAlgorithm rsaAlgorithm = new RSAAlgorithm(publicKey.getModulus().bitLength());
        String s = rsaAlgorithm.encryptFile("sss", privateKey);
        System.out.println(rsaAlgorithm.decryptFile(s, publicKey));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
