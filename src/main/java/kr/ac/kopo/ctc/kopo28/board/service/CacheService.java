package kr.ac.kopo.ctc.kopo28.board.service;

public interface CacheService {
    String testNoCache(Long id);

    String testCache(Long id);

    void testCacheClear(Long id);
}

