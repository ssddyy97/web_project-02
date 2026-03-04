package kr.ac.kopo.ctc.kopo28.board.web;

import kr.ac.kopo.ctc.kopo28.board.domain.Sample;
import kr.ac.kopo.ctc.kopo28.board.service.Sampleservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/sample")
public class SampleRestController{

    @Autowired
    private Sampleservice sampleservice;

    @GetMapping("/selectOne")
    public Sample selectOne(@RequestParam Long id) {
        return sampleservice.selectOne(id);
    }

    @GetMapping("/sampleOne")
    public Sample sampleOne() {
        Sample sample = new Sample();
        sample.setId(1L);
        sample.setName("title1");
        return sample;
    }

    @GetMapping("/sampleAll")
    public List<Sample> sampleAll() {
        return sampleservice.findAll();
    }

    @GetMapping("/entityOne")
    public ResponseEntity<Sample> entityOne() {
        Sample sample = new Sample();
        sample.setId(1L);
        sample.setName("title1");
        return ResponseEntity.ok(sample);
    }

    @GetMapping("/entityAll")
    public ResponseEntity<List<Sample>> entityAll() {
        return ResponseEntity.ok(sampleservice.findAll());
    }

    @GetMapping("/entityError")
    public ResponseEntity<Sample> entityError() {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .build();

    }

}