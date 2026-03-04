package kr.ac.kopo.ctc.kopo28.board;

import kr.ac.kopo.ctc.kopo28.board.domain.Sample;

import java.util.List;


public interface SampleDao1 {
    List<Sample> findAll();
    Sample findById(Long id);
}
