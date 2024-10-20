package com.finance.app.fundstransfer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Base class for integration tests
 *
 * @author <a href="https://github.com/Elaserph">elaserph</a>
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public abstract class FundsTransferApplicationTests {
    @Autowired
    protected MockMvc mockMvc;
}
