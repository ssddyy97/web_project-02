package kr.ac.kopo.ctc.kopo28.board;


import kr.ac.kopo.ctc.kopo28.board.domain.Sample;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class SampleDao1Test {


    @Autowired
    private SampleDao1 SampleDao1;

    @Test
    void findAll(){
        List<Sample> list = SampleDao1.findAll();
        assertEquals(2, list.size());


    }

    @Test
    void findById(){
        Sample sample = SampleDao1.findById(1L);
        assertEquals(1L, sample.getId());
    }
}
