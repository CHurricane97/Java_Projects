

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.cert.CertificateException;

public class KeystoreManager {

    public KeyStore ks;

    public void loadKeystore(String name, String password) throws Exception, CertificateException, FileNotFoundException, IOException{
        
            ks = KeyStore.getInstance("JKS");
            ks.load(new FileInputStream(name), password.toCharArray());
      
    }



}
