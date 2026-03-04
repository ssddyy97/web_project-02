package kr.ac.kopo.ctc.kopo28.board;

import kr.ac.kopo.ctc.kopo28.board.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/sample")
public class SampleController2 {

    @Autowired
    private CacheService cacheService;

    @GetMapping("/noCache")
    @ResponseBody
    public String noCache(@RequestParam(value = "id", defaultValue = "1") Long id) {
        return cacheService.testNoCache(id);
    }

    @GetMapping("/cache")
    @ResponseBody
    public String cache(@RequestParam(value = "id", defaultValue = "1") Long id) {
        return cacheService.testCache(id);
    }

    @GetMapping("/cacheClear")
    @ResponseBody
    public String cacheClear(@RequestParam(value = "id", defaultValue = "1") Long id) {
        cacheService.testCacheClear(id);
        return "cache cleared";
    }
}

