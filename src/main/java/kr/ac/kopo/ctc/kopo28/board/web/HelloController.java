package kr.ac.kopo.ctc.kopo28.board.web;

import kr.ac.kopo.ctc.kopo28.board.domain.Board;
import kr.ac.kopo.ctc.kopo28.board.repository.BoardRepository;
import kr.ac.kopo.ctc.kopo28.board.service.BoardItemService;
import kr.ac.kopo.ctc.kopo28.board.service.CalculatorMain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/hello")
public class HelloController {

    @GetMapping("/")
    public String home() {
        return "index";
    }


    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("name", "kopo28");
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";

    }


    @GetMapping("/admin")
    public String admin() {

        return "admin";
    }


    private final BoardRepository boardRepository;

    public HelloController(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @RequestMapping(value = "")
    public String hello(Model model) {

        int sum = BoardItemService.add(3, 5);

        PageRequest pageable = PageRequest.of(0, 10);

        List<String> myItems = new ArrayList<>();
        myItems.add("aaa");
        myItems.add("bbb");
        myItems.add("ccc");

        model.addAttribute("name", "홍길동");
        model.addAttribute("sum", sum);
        model.addAttribute("myItems", myItems);

        return "hello";
    }


}

