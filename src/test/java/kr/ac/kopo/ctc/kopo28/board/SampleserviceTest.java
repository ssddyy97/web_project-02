package kr.ac.kopo.ctc.kopo28.board;

import kr.ac.kopo.ctc.kopo28.board.domain.Sample;
import kr.ac.kopo.ctc.kopo28.board.service.Sampleservice;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SampleserviceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Sampleservice sampleservice;

    @Test
    @WithMockUser
    void testSelectOne() throws Exception {
        // Given: Prepare the mock service to return a sample object
        Sample sample = new Sample(1L, "title1");
        // Corrected to use the actual method name "selectOne"
        when(sampleservice.selectOne(1L)).thenReturn(sample);

        // When & Then: Perform the GET request and verify the result
        // The request now correctly passes "id" as a request parameter
        mockMvc.perform(get("/sample/selectOne?id=1"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}