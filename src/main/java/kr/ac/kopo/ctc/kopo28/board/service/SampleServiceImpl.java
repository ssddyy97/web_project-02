package kr.ac.kopo.ctc.kopo28.board.service;

import kr.ac.kopo.ctc.kopo28.board.domain.Sample;
import kr.ac.kopo.ctc.kopo28.board.repository.SampleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SampleServiceImpl implements Sampleservice {

    @Autowired
    private SampleRepository sampleRepository;

    @Override
    public List<Sample> findAll() {
        return sampleRepository.findAll();
    }

    @Override
    public Sample selectOne(Long id) {
        return sampleRepository.findById(id).get();
    }

    @Override
    public void testNoTransaction() {
        Sample sample = sampleRepository.findById(1L).get();
        sample.setName("update1");
        sampleRepository.save(sample);

        throw new RuntimeException("No transaction test exception");
    }

    @Transactional
    @Override
    public void testTransaction() {
        Sample sample = sampleRepository.findById(1L).get();
        sample.setName("update2");
        sampleRepository.save(sample);

        throw new RuntimeException("Transaction test exception");
    }
}
