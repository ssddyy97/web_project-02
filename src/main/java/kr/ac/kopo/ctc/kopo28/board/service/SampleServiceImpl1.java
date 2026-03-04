package kr.ac.kopo.ctc.kopo28.board.service;

import kr.ac.kopo.ctc.kopo28.board.domain.Sample;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class SampleServiceImpl1 implements CacheService {

    @Override
    public String testNoCache(Long id) {
        sleep(3);
        return "NoCache";
    }

    @Override
    @Cacheable(value = "sample", key = "#id")
    public String testCache(Long id) {
        sleep(3);
        return "cache";
    }

    @Override
    @CacheEvict(value = "sample", key = "#id")
    public void testCacheClear(Long id) {
        // 캐시 제거
    }

    private void sleep(int second) {
        try {
            Thread.sleep(second * 1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
