package kr.ac.kopo.ctc.kopo28.board;

import kr.ac.kopo.ctc.kopo28.board.domain.Sample;
import kr.ac.kopo.ctc.kopo28.board.repository.SampleRepository;
import kr.ac.kopo.ctc.kopo28.board.repository.SampleSpecs;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class SampleRepositoryTest {

    @Autowired
    SampleRepository sampleRepository;
    @Test
    void findAllByName(){
       Map<String, Object> filter = new HashMap<>();
       filter.put("name", "t1");

       PageRequest pageRequest = PageRequest.of(0, 10);
       Page<Sample> page = sampleRepository.findAll(pageRequest);

       for (Sample s : page){
        System.out.println(s.getName());

       }
    }


    @Test
    void findOneByName() {
        List<Sample> samples = sampleRepository.findByNameLike("%t1%");

        for (Sample s : samples) {
            System.out.println(s.getName());
        }


}
}