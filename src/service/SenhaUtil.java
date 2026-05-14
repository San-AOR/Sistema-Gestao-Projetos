package service;

import java.security.MessageDigest;

public class SenhaUtil {

    public static String criptografar(String senha) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(senha.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            System.out.println("Erro ao criptografar senha: " + e.getMessage());
            return senha;
        }
    }
}