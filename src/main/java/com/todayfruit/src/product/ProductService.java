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
    /* 9. 상품 등록 -  createProduct() */
    public String createProduct(PostProductReq postProductReq, Long userId) throws BasicException {


        //DB에 상품 등록 (배송타입 ,상품제목, 상품가격, 할인율 , 판매수량, 상품설명, 배송일  등)
        try{
            //판매자 인덱스를 통해 판매자 객체를 불러옴
            Optional<User> seller = userDao.findById(userId);   //user_id 로판매자 불러옴.
            postProductReq.setUser(seller.get());                       //DTO에 판매자 정보도 입력 완료


            //상품 DB에 등록
            Product productCreate = new Product();         //productCreate 객체 생성
            BeanUtils.copyProperties(postProductReq,productCreate);  //postProductReq(dto) 객체의 내용을 productCreate 옮긴다. (DB에 저장하기 위함.)
            //productCreate.setUser(seller);

            productDao.save(productCreate);   //"product" DB에 정보 저장
            //System.out.println(productCreate.getId());



            //상품 인덱스를 통해 상품 객체를 불러옴
            Optional<Product> product = productDao.findById(productCreate.getId());   //product_id로 상품 객체를 불러옴.

            List<ProductOption> productOptionListCreate = new ArrayList<ProductOption>();       //List 객체 ("productOptionListCreate") 생성 (상품 옵션이 여러개 이기 때문에 List로 만들어 주어야함)

            //상품 옵션 DB에 상품 옵션 개수별로 상품 등록
            for(int i=0; i < postProductReq.getOptionName().size(); i++){

                ProductOption productOptionCreate = new ProductOption();                    //상품 옵션 엔티티 객체를 1개 생성
                productOptionCreate.setProduct(product.get());                              //상품 객체 (produc_td) 입력
                productOptionCreate.setOptionName(postProductReq.getOptionName().get(i));  //상품 옵션 (option_name) 리스트를 하나씩 불러와 입력

                productOptionListCreate.add(productOptionCreate);                       ///List 객체 ("productOptionListCreate")에 상품 옵션 객체를 1개씩 담는다.
            }
            productOptionDao.saveAll(productOptionListCreate);


            return "상품 등록에 성공하였습니다.";

        } catch (Exception exception) {
            throw new BasicException(DATABASE_ERROR_CREATE_PRODUCT);  //상품 등록 실패 에러
        }
    }
















}
