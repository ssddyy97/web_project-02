package kr.ac.kopo.ctc.kopo28.board;

import kr.ac.kopo.ctc.kopo28.board.dto.PaginationInfo;
import kr.ac.kopo.ctc.kopo28.board.service.PaginationInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PaginationInfoServiceTest2 {

    @Autowired
    private PaginationInfoService paginationInfoService;

    private void assertPagination(int totalItems, int itemsPerPage, int pagesPerBlock, int currentPage,
                                  int expectedFirstPage, int expectedPrevPage, int expectedStartPage,
                                  int expectedCurrentPage, int expectedEndPage, int expectedNextPage,
                                  int expectedLastPage, int expectedTotalPageCount) {
        PaginationInfo paginationInfo = paginationInfoService.getPaginationInfo(currentPage, pagesPerBlock, itemsPerPage, totalItems);
        assertEquals(expectedFirstPage, paginationInfo.getFirstPage());
        assertEquals(expectedPrevPage, paginationInfo.getPrevPage());
        assertEquals(expectedStartPage, paginationInfo.getStartPage());
        assertEquals(expectedCurrentPage, paginationInfo.getCurrentPage());
        assertEquals(expectedEndPage, paginationInfo.getEndPage());
        assertEquals(expectedNextPage, paginationInfo.getNextPage());
        assertEquals(expectedLastPage, paginationInfo.getLastPage());
        assertEquals(expectedTotalPageCount, paginationInfo.getTotalPageCount());
    }

    @Test
    void testPagination1() {
        assertPagination(1, 1, 1, 5, 1, -1, 1, 1, 1, -1, -1, 1);
    }

    @Test
    void testPagination2() {
        assertPagination(5, 1, 10, 5, 1, -1, 1, 5, 5, -1, -1, 5);
    }

    @Test
    void testPagination3() {
        assertPagination(50, 10, 10, 5, 1, -1, 1, 5, 5, -1, -1, 5);
    }

    @Test
    void testPagination4() {
        assertPagination(50, 10, 10, 5, 1, -1, 1, 5, 5, -1, -1, 5);
    }

    @Test
    void testPagination5() {
        assertPagination(100, 10, 10, 5, 1, -1, 1, 5, 10, -1, -1, 10);
    }

    @Test
    void testPagination6() {
        assertPagination(100, 10, 10, 6, 1, -1, 1, 6, 10, -1, -1, 10);
    }

    @Test
    void testPagination7() {
        assertPagination(95, 10, 10, 9, 1, -1, 1, 9, 10, -1, -1, 10);
    }

    @Test
    void testPagination8() {
        assertPagination(95, 10, 10, 10, 1, -1, 1, 10, 10, -1, -1, 10);
    }

    @Test
    void testPagination9() {
        assertPagination(100, 10, 1, 3, 1, 2, 3, 3, 3, 4, 10, 10);
    }

    @Test
    void testPagination10() {
        assertPagination(7, 2, 2, 1, 1, -1, 1, 1, 2, 3, 4, 4);
    }

    @Test
    void testPagination11() {
        assertPagination(7, 2, 2, 2, 1, -1, 1, 2, 2, 3, 4, 4);
    }

    @Test
    void testPagination12() {
        assertPagination(7, 2, 2, 3, 1, 2, 3, 3, 4, -1, -1, 4);
    }

    @Test
    void testPagination13() {
        assertPagination(7, 2, 2, 4, 1, 2, 3, 4, 4, -1, -1, 4);
    }
}