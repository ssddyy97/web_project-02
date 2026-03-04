package kr.ac.kopo.ctc.kopo28.board;

import kr.ac.kopo.ctc.kopo28.board.domain.Sample;
import kr.ac.kopo.ctc.kopo28.board.repository.SampleRepository;
import kr.ac.kopo.ctc.kopo28.board.service.Sampleservice;
import kr.ac.kopo.ctc.kopo28.board.web.SampleController;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(SampleController.class)
class SampleControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @MockitoBean
    private SampleRepository sampleRepository;

    @MockitoBean
    private Sampleservice sampleservice;


    @Test
    void testGetSample() throws Exception {
        // Sample 클래스에 (Long id, String name) 생성자가 있다고 가정
        Sample sample = new Sample(1L, "title1");

        Mockito.when(sampleservice.selectOne(1L)).thenReturn(sample);

        mockMvc.perform(get("/sample/selectOne")
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("sample"))
                .andExpect(model().attribute("sample", hasProperty("id", is(1L))))
                .andExpect(model().attribute("sample", hasProperty("name", is("title1"))))
                .andDo(print());
    }
}