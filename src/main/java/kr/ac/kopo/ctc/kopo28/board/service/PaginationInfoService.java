package kr.ac.kopo.ctc.kopo28.board.service;

import kr.ac.kopo.ctc.kopo28.board.dto.PaginationInfo;
import org.springframework.stereotype.Service;

@Service
public class PaginationInfoService {

    public PaginationInfo getPaginationInfo(int currentPage, int pagesPerBlock, int itemsPerPage, int totalItems) {
        PaginationInfo info = new PaginationInfo();

        if (pagesPerBlock <= 0 || itemsPerPage <= 0 || totalItems < 0) {
            info.setFirstPage(-1);
            info.setCurrentPage(-1);
            info.setStartPage(-1);
            info.setEndPage(-1);
            info.setPrevPage(-1);
            info.setNextPage(-1);
            info.setLastPage(-1);
            info.setTotalPageCount(-1);
            return info;
        }

        boolean isCurrentPageInvalid = currentPage < 1;
        if (isCurrentPageInvalid) {
            currentPage = 1;
        }

        int totalPageCount = (int) Math.ceil((double) totalItems / itemsPerPage);
        if (totalItems == 0) {
            totalPageCount = 1; // If no items, show 1 page.
        }


        if (currentPage > totalPageCount) {
            currentPage = totalPageCount;
        }

        int currentBlock = (int) Math.ceil((double) currentPage / pagesPerBlock);
        int startPage = (currentBlock - 1) * pagesPerBlock + 1;
        int endPage = Math.min(startPage + pagesPerBlock - 1, totalPageCount);

        int prevPage = (startPage > 1) ? startPage - 1 : -1;
        int nextPage = (endPage < totalPageCount) ? endPage + 1 : -1;
        int lastPage = (endPage < totalPageCount) ? totalPageCount : -1;

        info.setFirstPage(isCurrentPageInvalid ? -1 : 1);
        info.setCurrentPage(currentPage);
        info.setStartPage(startPage);
        info.setEndPage(endPage);
        info.setPrevPage(prevPage);
        info.setNextPage(nextPage);
        info.setLastPage(lastPage);
        info.setTotalPageCount(totalPageCount);


        return info;
    }
}
