package kr.ac.kopo.ctc.kopo28.board.web;


import kr.ac.kopo.ctc.kopo28.board.domain.Sample;
import kr.ac.kopo.ctc.kopo28.board.repository.SampleRepository;
import kr.ac.kopo.ctc.kopo28.board.service.Sampleservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/sample")
@Controller
public class SampleController {


    @Autowired
    private SampleRepository sampleRepository;

    @Autowired
    private Sampleservice sampleservice;

    @GetMapping("/list")
    public String list(Model model) {
        List<Sample> samples = sampleRepository.findAll();
        model.addAttribute("samples", samples);
        return "sample";
    }

    @GetMapping("/selectOne")
    public String selectOne(Model model, @RequestParam Long id) {
        Sample sample = sampleservice.selectOne(id);
        model.addAttribute("sample", sample);
        return "sample";
    }
}