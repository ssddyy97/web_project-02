package kr.ac.kopo.ctc.kopo28.board.dto;




import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginationInfo {


        private int firstPage;
        private int prevPage;
        private int startPage;
        private int currentPage;
        private int endPage;
        private int nextPage;
        private int lastPage;
        private int totalPageCount;

}


