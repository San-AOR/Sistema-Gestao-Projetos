import org.junit.Test;
import static org.junit.Assert.*;
import service.SenhaUtil;

public class UsuarioServiceTest {

    @Test
    public void testCriptografarSenha() {
        String senha = "admin123";
        String hash = SenhaUtil.criptografar(senha);
        assertNotNull("Hash nao deve ser nulo", hash);
        assertEquals("Hash MD5 deve ter 32 caracteres", 32, hash.length());
        System.out.println("OK - testCriptografarSenha: " + hash);
    }

    @Test
    public void testSenhasDiferentesGeramHashesDiferentes() {
        String hash1 = SenhaUtil.criptografar("senha1");
        String hash2 = SenhaUtil.criptografar("senha2");
        assertNotEquals("Hashes devem ser diferentes", hash1, hash2);
        System.out.println("OK - testSenhasDiferentesGeramHashesDiferentes");
    }

    @Test
    public void testMesmaSenhaGeraHashIgual() {
        String hash1 = SenhaUtil.criptografar("minhasenha");
        String hash2 = SenhaUtil.criptografar("minhasenha");
        assertEquals("A mesma senha deve gerar o mesmo hash", hash1, hash2);
        System.out.println("OK - testMesmaSenhaGeraHashIgual");
    }

    @Test
    public void testSenhaVaziaRetornaHash() {
        String hash = SenhaUtil.criptografar("");
        assertNotNull("Hash de senha vazia nao deve ser nulo", hash);
        assertEquals("Hash de senha vazia deve ter 32 caracteres", 32, hash.length());
        System.out.println("OK - testSenhaVaziaRetornaHash: " + hash);
    }
}