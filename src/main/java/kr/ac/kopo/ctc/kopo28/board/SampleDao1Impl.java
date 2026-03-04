package kr.ac.kopo.ctc.kopo28.board;

import kr.ac.kopo.ctc.kopo28.board.domain.Sample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SampleDao1Impl implements SampleDao1 {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Sample> findAll() {
        return jdbcTemplate.query("SELECT * FROM sample", new BeanPropertyRowMapper<>(Sample.class));
    }

    @Override
    public Sample findById(Long id) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM sample WHERE id = ?",
                new BeanPropertyRowMapper<>(Sample.class),
                id
        );
    }
}