import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 密码加密测试
 *
 * @author tycoding
 * @date 2019-05-29
 */
@Slf4j
public class PasswordEncoderTest {

    private static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static void main(String[] args) {
        log.info("tycoding >> {}", passwordEncoder.encode("tycoding")); // $2a$10$akcNI/pr9l0kS7JAXFSsNeJpodmdCq2aaNBohjbxXhF1StVtmWTEu
        log.info("admin >> {}", passwordEncoder.encode("admin")); // $2a$10$zVAf02ng.YxGK14F8Riq/uLsNLA.tUYbv5QTPpQNnxDfnEEXW0upK
        log.info("test >> {}", passwordEncoder.encode("test")); // $2a$10$Rh//mbhIE6df8eUnR99gYuEKodd9.400uKMUhSCKnFMvy2pW/lSjy
    }
}
