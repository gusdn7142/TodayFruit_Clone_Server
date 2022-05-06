package com.todayfruit.src.product;






import com.todayfruit.config.BasicException;
import com.todayfruit.src.product.model.domain.Product;
import com.todayfruit.src.product.model.domain.ProductOption;
import com.todayfruit.src.product.model.request.PostProductReq;
import com.todayfruit.src.user.UserDao;
import com.todayfruit.src.user.model.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.todayfruit.config.BasicResponseStatus.DATABASE_ERROR_CREATE_PRODUCT;

@Service
@RequiredArgsConstructor  //final이 붙거나 @NotNull 이 붙은 필드의 생성자를 자동 생성해주는 롬복 어노테이션
public class ProductService {

    private final ProductDao productDao;
    private final UserDao userDao;
    private final ProductOptionDao productOptionDao;
    //private final JwtService jwtservice;




///////////////////////////////////////////////////////////////////////////////////////////////////
    /* 8. 상품 등록 -  createProduct() */
    public String createProduct(PostProductReq postProductReq, Long userId) throws BasicException {

//        //이메일 중복 검사 ("ACTIVE"가 1일떄 포함)
//        if (userDao.checkByemail(postUserReq.getEmail()) != null){   //테이블에 같은 이메일이 여러개면 오류난다....List로 처리해야함 그럴땐!!
//            throw new BasicException(POST_USERS_EXISTS_EMAIL); //"이미 가입된 이메일 입니다."
//        }
////        User checkUser = userDao.findByEmail(postUserReq.getEmail());  //테이블에 같은 이메일이 여러개면 오류난다....List로 처리해야함 그럴땐!!
//
//        //닉네임 중복 검사 ("ACTIVE"가 1일떄 포함)
//        if (userDao.checkNickName(postUserReq.getNickName()) != null){   //테이블에 같은 이메일이 여러개면 오류난다....List로 처리해야함 그럴땐!!
//            throw new BasicException(POST_USERS_EXISTS_NICKNAME); //"이미 존재하는 닉네임 입니다."
//        }


        //DB에 상품 등록 (배송타입 ,상품제목, 상품가격, 할인율 , 판매수량, 상품설명, 배송일  등)
        try{
            //판매자 인덱스를 통해 판매자 정보를 불러옴
            Optional<User> seller = userDao.findById(userId);   //판매자 불러옴.
            postProductReq.setUser(seller.get());                       //DTO에 판매자 정보도 입력 완료


            //상품 등록
            Product productCreate = new Product();         //productCreate 객체 생성
            BeanUtils.copyProperties(postProductReq,productCreate);  //postProductReq(dto) 객체의 내용을 productCreate 옮긴다. (DB에 저장하기 위함.)
            //productCreate.setUser(seller);

            productDao.save(productCreate);   //"product" DB에 정보 저장
            //System.out.println(productCreate.getId());



            //상품 인덱스를 통해 상품 정보를 불러옴
            Optional<Product> product = productDao.findById(productCreate.getId());   //상품 정보를 불러옴.
            ProductOption productOptionCreate = new ProductOption();            //productCreate 객체 생성


            //상품 옵션 개수별로 상품 등록
            for(int i=0; i < postProductReq.getOptionName().size(); i++){
                productOptionCreate.setOptionName(postProductReq.getOptionName().get(i));  //상품 옵션 리스트 불러옴
                productOptionCreate.setProduct(product.get());                              //상품 정보 입력
                productOptionDao.save(productOptionCreate);   //"productOption" DB에 정보 저장
            }




            return "상품 등록에 성공하였습니다.";

        } catch (Exception exception) {
            throw new BasicException(DATABASE_ERROR_CREATE_PRODUCT);  //상품 등록 실패 에러
        }
    }
















}
