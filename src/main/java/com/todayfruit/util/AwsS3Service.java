package com.todayfruit.util;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.todayfruit.config.BasicException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;


import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.prefs.BackingStoreException;

import static com.todayfruit.config.BasicResponseStatus.*;
//import com.todayfruit.config.BasicException;

@Service
@RequiredArgsConstructor
public class AwsS3Service {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;  //버킷 이름
    private final AmazonS3 amazonS3;  //AmazonS3 객체




    // Amazon S3에 파일 업로드
    public URI uploadFile(MultipartFile imageFile, String UUID_fileName) throws BasicException  { //이미지 파일과 UUID가 적용된 파일명을 파라미터로 받아옴.

       //String UUID_fileName = createFileNameToDB(imageFile);  //파일명을 불러와서 고유 식별자와 합친 문자열을 생성

       ObjectMetadata objectMetadata = new ObjectMetadata();  //inputStream은 Byte로 전달이 되기 때문에 파일에 대한 정보를 추가하기 위해 ObjectMetadata 활용
       objectMetadata.setContentLength(imageFile.getSize());       //HTTP Header의 ContentLength와
       objectMetadata.setContentType(imageFile.getContentType());  //ContentType 지정


       //S3에 파일 업로드
       try {

            //(파일) inputstream 설정
            InputStream inputStream = imageFile.getInputStream();  //InputStream : 데이터가 들어오는 통로의 역할에 관해 규저앟고 있는 추상 클래스

            //S3에 오브젝트 업로드
            amazonS3.putObject(new PutObjectRequest(bucket, UUID_fileName, inputStream, objectMetadata)  //putObject("버킷이름", "파일 이름", inputStream객체, 파일에 대한 Header 정보)
            .withCannedAcl(CannedAccessControlList.PublicRead));  //PublicRead 권한을 줌


       } catch (Exception exception) {
            throw new BasicException(S3_ERROR_UPLOAD_FILE);  //"S3에 파일 업로드에 실패하였습니다."
       }


       //클라이언트에게 보내기 위해 S3에서 파일 uri 조회
       S3Object s3Object = amazonS3.getObject(bucket, UUID_fileName);   //S3에서 업로드된 해당 파일 조회
       URI s3Uri = s3Object.getObjectContent().getHttpRequest().getURI();


       return s3Uri;
    }





    //UUID를 포함하는 이미지 파일명 리턴
    public String createFileNameToDB(MultipartFile imageFile) {
        String UUID_fileName = UUID.randomUUID().toString().concat(imageFile.getOriginalFilename()); //파일명을 불러와서 고유 식별자와 합친 문자열을 생성
        return UUID_fileName;  //UUID 파일명 리턴
    }









//    //파일명 형식 확인 - uploadFile() 에서 활용
//    private String getFileExtension(String fileName){ // file 형식이 잘못된 경우를 확인하기 위해 만들어진 로직이며, 파일 타입과 상관없이 업로드할 수 있게 하기 위해 .의 존재 유무만 판단하였습니다.
//        try {
//            return fileName.substring(fileName.lastIndexOf("."));
//        } catch (StringIndexOutOfBoundsException e) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 형식의 파일(" + fileName + ") 입니다.");
//            //throw new BasicException(S3_ERROR_INCORRECT_FILENAME);  //상품 등록 실패 에러
//        }
//    }





//    //Amazon S3에 업로드 된 파일을 삭제
//    public void deleteFile(String fileName) {
//        amazonS3.deleteObject(bucket, fileName);   //S3에서 업로드된 해당 파일 삭제   //new DeleteObjectRequest()
//    }




//    //Amazon S3에 업로드 된 파일 조회
//    public URI getFile(String fileName) {
//
//        //S3에서 해당 파일 uri 조회
//        S3Object s3Object = amazonS3.getObject(bucket, fileName);   //S3에서 업로드된 해당 파일 조회       //new GetObjectRequest(bucket, fileName)
//        URI s3Uri = s3Object.getObjectContent().getHttpRequest().getURI();
//        return s3Uri;
//
//    }










//    //Amazon S3에 업로드 된 파일 수정
//    public String modifyFile(String fileName) {
//
//        //S3에서 해당 파일 존재시 삭제
//        boolean isExistObject = amazonS3.doesObjectExist(bucket,fileName);
//        if(isExistObject == true) {
//            return "이미 존재하는 파일입니다.";
//            //존재하기 때문에 파일삭제 API로 넘어감~
//        }
//        //S3에서 해당 파일이 존재하지 않으면
//        else{
//
//            return "존재하지 않는 파일입니다.";
//
//        }
//
//
////        S3Object s3Object = amazonS3.getObject(new GetObjectRequest(bucket, fileName));   //S3에서 업로드된 해당 파일 조회
////        URI s3Uri = s3Object.getObjectContent().getHttpRequest().getURI();
////        return s3Uri;
//
//    }






}
