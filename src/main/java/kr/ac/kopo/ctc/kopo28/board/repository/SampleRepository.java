package kr.ac.kopo.ctc.kopo28.board.repository;

import kr.ac.kopo.ctc.kopo28.board.domain.Sample;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface SampleRepository extends JpaRepository<Sample, Long>, JpaSpecificationExecutor<Sample> {

    List<Sample> findOneByName(String name);

    List<Sample> findByNameLike(String namePattern);

    Page<Sample> findAllByName(String type, Pageable pageable);

    List<Sample> findAllByName(String type);


}
