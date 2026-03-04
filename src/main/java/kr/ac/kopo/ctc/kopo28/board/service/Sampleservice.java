package kr.ac.kopo.ctc.kopo28.board.service;

import kr.ac.kopo.ctc.kopo28.board.domain.Sample;

import java.util.List;

public interface Sampleservice{
    Sample selectOne(Long id);
    List<Sample> findAll();
    void testNoTransaction();
    void testTransaction();


}
