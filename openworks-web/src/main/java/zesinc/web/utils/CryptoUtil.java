package zesinc.web.utils;

import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import zesinc.core.config.Config;

public class CryptoUtil {

    /** 비밀번호 salt */
    public static final String PASSWD_SALT = Config.getString("passwd-config.passwdSalt", "zesinc");
    /** 비밀번호 iv */
    public static final String PASSWORD_IV = Config.getString("passwd-config.passwordIv", "openworks");
    /** 비밀번호 passPhrase */
    public static final String PASSWORD_PASS_PHRASE = Config.getString("passwd-config.passwordPassPhrase", "v4.0");
    /** 비밀번호 iteration */
    public static final int PASSWD_ITERATION = Config.getInt("passwd-config.passwdIteration", 1000);
    /** 비밀번호 keySize */
    public static final int PASSWD_KEY_SIZE = Config.getInt("passwd-config.passwdKeySize", 128);

    public static String decrypt(String ciphertext) throws Exception {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        KeySpec spec = new PBEKeySpec(PASSWORD_PASS_PHRASE.toCharArray(), Hex.decodeHex(PASSWD_SALT.toCharArray()), PASSWD_ITERATION, PASSWD_KEY_SIZE);
        SecretKey key = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(Hex.decodeHex(PASSWORD_IV.toCharArray())));
        byte[] decrypted = cipher.doFinal(Base64.decodeBase64(ciphertext));
        return new String(decrypted, "UTF-8");
    }

}
