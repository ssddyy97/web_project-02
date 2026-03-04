package kr.ac.kopo.ctc.kopo28.board;


import kr.ac.kopo.ctc.kopo28.board.domain.Sample;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SampleRestControllerTest {
            @Autowired
            private TestRestTemplate restTemplate;

            @Test
            @Sql("/data.sql")
            void testGetSample() throws Exception {
                URI url = UriComponentsBuilder.fromUriString("/api/sample/selectOne")
                        .queryParam("id", 1)
                        .build()
                        .toUri();

            Sample response =  restTemplate.getForObject(url, Sample.class);


            assertEquals(1L, response.getId());
            assertEquals("t1", response.getName());

            }

}