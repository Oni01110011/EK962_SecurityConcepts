package at.tgm.adauth;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
    "ldap.domain=test.local",
    "ldap.url=ldap://localhost:389"
})
class AdAuthApplicationTests {

    @Test
    void contextLoads() {
    }
}
