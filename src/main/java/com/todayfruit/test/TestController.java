package com.todayfruit.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/test")
public class TestController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());  // SLF4J 로거 등록

    @Autowired
    public TestController() {}

    /* 테스트 API */
    @ResponseBody
    @GetMapping("/log")
    public String getTest() {
        System.out.println("API 테스트 성공 (콘솔)");

        logger.trace("API 테스트 성공 (추적)");  //설정을 안해서 출력 안됨
        logger.debug("API 테스트 성공 (디버그) ");  //설정을 안해서 출력 안됨
        logger.info("API 테스트 성공 (인포)");
        logger.warn("API 테스트 성공 (경고) ");
        logger.error("API 테스트 성공 (에러)");

        return "API 테스트 성공 (웹 페이지)";
    }
}
