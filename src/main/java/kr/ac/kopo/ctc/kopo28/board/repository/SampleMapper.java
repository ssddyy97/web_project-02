package kr.ac.kopo.ctc.kopo28.board.repository;

import org.apache.ibatis.annotations.Mapper;

import kr.ac.kopo.ctc.kopo28.board.domain.Sample;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

@Mapper

public interface SampleMapper {

    List<Sample> findAll();
    Sample findById(Long id);
    List<Sample> findAllByName(String name, RowBounds rowBounds);


}
