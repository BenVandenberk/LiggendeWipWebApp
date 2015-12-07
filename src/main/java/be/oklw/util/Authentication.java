package be.oklw.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

public class Authentication {

    private static SecureRandom random = new SecureRandom();

    public static boolean isJuistPaswoord(String paswoord, byte[] hashedPw, byte[] salt) {
        byte[] nieuweHash = hashPw(paswoord, salt);
        return Arrays.equals(nieuweHash, hashedPw);
    }

    public static byte[] hashPw(String paswoord, byte[] salt) {
        byte[] paswoordArray = paswoord.getBytes();
        byte[] saltEnPaswoord = new byte[salt.length + paswoordArray.length];
        System.arraycopy(salt, 0, saltEnPaswoord, 0, salt.length);
        System.arraycopy(paswoordArray, 0, saltEnPaswoord, salt.length, paswoordArray.length);

        byte[] paswoordHash = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            paswoordHash = md.digest(saltEnPaswoord);
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }

        return paswoordHash;
    }

    public static byte[] nextSalt() {
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

}
