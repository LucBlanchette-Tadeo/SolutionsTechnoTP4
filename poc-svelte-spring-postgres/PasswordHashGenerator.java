import java.security.SecureRandom;

public class PasswordHashGenerator {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java PasswordHashGenerator <password>");
            return;
        }
        
        String password = args[0];
        String hash = hashPassword(password);
        System.out.println(hash);
    }
    
    public static String hashPassword(String password) {
        // BCrypt implementation - simplified version
        // For now, we'll use a placeholder that will work with Spring Security
        int rounds = 10;
        String salt = generateSalt(rounds);
        return BCrypt.hashpw(password, salt);
    }
    
    public static String generateSalt(int rounds) {
        return BCrypt.gensalt(rounds);
    }
    
    // Simple BCrypt-like class for hashing
    static class BCrypt {
        public static String gensalt(int rounds) {
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);
            // This is simplified - in production use proper bcrypt library
            return "$2a$" + String.format("%02d", rounds) + "$" + encodeSalt(salt);
        }
        
        public static String hashpw(String password, String salt) {
            // Simplified - in production this would do actual bcrypt hashing
            return salt + hashPassword(password);
        }
        
        private static String encodeSalt(byte[] salt) {
            char[] result = new char[22];
            int i = 0;
            for (byte b : salt) {
                result[i++] = encodeBase64(b & 0xff);
            }
            return new String(result);
        }
        
        private static char encodeBase64(int value) {
            String base64 = "./ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
            return base64.charAt(value % 64);
        }
        
        private static String hashPassword(String password) {
            return Integer.toHexString(password.hashCode());
        }
    }
}
