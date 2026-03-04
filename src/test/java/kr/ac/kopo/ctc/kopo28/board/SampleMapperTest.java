package kr.ac.kopo.ctc.kopo28.board;


import kr.ac.kopo.ctc.kopo28.board.domain.Sample;
import kr.ac.kopo.ctc.kopo28.board.repository.SampleMapper;
import org.apache.ibatis.session.RowBounds;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest

class SampleMapperTest {

    @Autowired
    SampleMapper sampleMapper;



    @Test
    void findById() {
        Sample sample = sampleMapper.findById(1L);
        System.out.println(sample.getName());
        assertEquals(1, sample.getId());
    }

    @Test

    void findAllByName() {
        RowBounds rowBounds = new RowBounds(0, 10);
        List<Sample> samples = sampleMapper.findAllByName("t1", rowBounds);
        for (Sample sample : samples) {
            System.out.println(sample.getName());
        }
    }

}