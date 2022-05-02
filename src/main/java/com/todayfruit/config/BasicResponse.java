package com.todayfruit.config;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.List;

import com.todayfruit.config.BasicResponseStatus;
//대체가능 : import static com.todayfruit.config.BasicResponseStatus.SUCCESS;





@Getter
@AllArgsConstructor   //생성자 자동 생성
@JsonPropertyOrder({"responseStatus", "responseCode", "message", "result"})  //json 출력 형식 설정
public class BasicResponse {  //제네릭

    private String responseStatus;  //요청 상태
    private int responseCode;             //응답 코드
    private String message;      //응답 메시지

    @JsonInclude(JsonInclude.Include.NON_NULL)   //실패일떄 null이기 때문에 출력 안되게 설정!
    private Object result;           //Json body 출력



    /* 요청에 성공한 경우 */
    public BasicResponse(Object result) {
        this.responseStatus = BasicResponseStatus.SUCCESS.getResponseStatus();  //ex, true
        this.responseCode = BasicResponseStatus.SUCCESS.getResponseCode();        //ex, 상태코드 : 1000
        this.message = BasicResponseStatus.SUCCESS.getMessage();   //ex, 메시지 : "요청에 성공하였습니다."

        this.result = result;  //성공 응답은 결과만 들어가면 된다.
    }


    /* 요청에 실패한 경우 */
    public BasicResponse(BasicResponseStatus status) {
        this.responseStatus = status.getResponseStatus();  //ex, DATABASE_ERROR의 FAIL에 해당
        this.responseCode = status.getResponseCode();  //ex, DATABASE_ERROR의 5000에 해당
        this.message = status.getMessage();  //ex, DATABASE_ERROR의 "DB에서 조회에 실해하였습니다."에 해당


    }



}
