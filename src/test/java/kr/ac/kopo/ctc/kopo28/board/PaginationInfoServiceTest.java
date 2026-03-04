package kr.ac.kopo.ctc.kopo28.board;

import kr.ac.kopo.ctc.kopo28.board.dto.PaginationInfo;
import kr.ac.kopo.ctc.kopo28.board.service.PaginationInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class PaginationInfoServiceTest {


    @Autowired
    PaginationInfoService paginationInfoService;

    void check(PaginationInfo paginationInfo, int firstPage, int prevPage, int startPage, int currentPage, int endPage, int nextPage, int lastPage) {

        assertEquals(paginationInfo.getPrevPage(), prevPage);
        assertEquals(paginationInfo.getFirstPage(), firstPage);
        assertEquals(paginationInfo.getStartPage(), startPage);
        assertEquals(paginationInfo.getCurrentPage(), currentPage);
        assertEquals(paginationInfo.getEndPage(), endPage);
        assertEquals(paginationInfo.getNextPage(), nextPage);
        assertEquals(paginationInfo.getLastPage(), lastPage);
    }

    @Test

    void getPaginationInfo01() {
        PaginationInfo info = paginationInfoService.getPaginationInfo(1, 5, 10, 1);
        check(info, 1, -1, 1, 1, 1, -1, -1);



    }


}
