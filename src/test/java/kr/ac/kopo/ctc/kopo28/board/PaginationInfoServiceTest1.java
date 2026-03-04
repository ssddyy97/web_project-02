package kr.ac.kopo.ctc.kopo28.board;

import kr.ac.kopo.ctc.kopo28.board.dto.PaginationInfo;
import kr.ac.kopo.ctc.kopo28.board.service.PaginationInfoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PaginationInfoServiceTest1 {

    @Autowired
    PaginationInfoService paginationInfoService;

    void check(PaginationInfo info, int firstPage, int prevPage, int startPage, int currentPage, int endPage, int nextPage, int lastPage, int totalpagecount) {
        assertEquals(prevPage, info.getPrevPage());
        assertEquals(firstPage, info.getFirstPage());
        assertEquals(startPage, info.getStartPage());
        assertEquals(currentPage, info.getCurrentPage());
        assertEquals(endPage, info.getEndPage());
        assertEquals(nextPage, info.getNextPage());
        assertEquals(lastPage, info.getLastPage());
        assertEquals(totalpagecount, info.getTotalPageCount());
    }


    @Test
    void getPaginationInfo14() {
        check(paginationInfoService.getPaginationInfo(27, 10, 100, 2657), 1, 20, 21, 27, 27, -1, -1,27);
    }

    @Test
    void getPaginationInfo15() {
        check(paginationInfoService.getPaginationInfo(-27, 10, 100, 2657), -1, -1, 1, 1, 10, 11, 27,27);
    }
}
