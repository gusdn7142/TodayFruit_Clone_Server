package com.todayfruit.src.product;






import com.todayfruit.config.BasicException;
import com.todayfruit.src.product.model.domain.Product;
import com.todayfruit.src.product.model.domain.ProductOption;
import com.todayfruit.src.product.model.request.PatchProductReq;
import com.todayfruit.src.product.model.request.PostProductReq;
import com.todayfruit.src.product.model.response.GetProductAndUserRes;
import com.todayfruit.src.product.model.response.GetProductRes;
import com.todayfruit.src.product.model.response.GetProductsRes;
import com.todayfruit.src.user.UserDao;
import com.todayfruit.src.user.model.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.todayfruit.config.BasicResponseStatus.*;

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


        //(할인율과 상품 가격 활용해서) 할인된 상품 가격 넣기
        String str_price = postProductReq.getPrice().substring(0, postProductReq.getPrice().length()-1);  //상품가격에서 "원" 제거
        int num_price = Integer.parseInt(str_price);  //int형으로 전환
        String discountPrice = null;

        switch (postProductReq.getDiscountRate()) {  //할인율로 할인가격 계산
            case 0:
                discountPrice = postProductReq.getDiscountPrice();
                break;
            default:
                discountPrice = (int)(num_price - num_price * (postProductReq.getDiscountRate() / 100.0)) + "원";   //1000 - 1000*0.1
                break;
        }
        postProductReq.setDiscountPrice(discountPrice);



        //DB에 상품 등록 (배송타입 ,상품제목, 상품가격, 할인율 , 판매수량, 상품설명, 배송일  등)
        try{
            //판매자 인덱스를 통해 판매자 객체를 불러옴
            Optional<User> seller = userDao.findById(userId);   //user_id 로판매자 불러옴.
            postProductReq.setUser(seller.get());                       //DTO에 판매자 정보도 입력 완료


            //상품 DB에 등록
            Product productCreate = new Product();         //productCreate 객체 생성
            BeanUtils.copyProperties(postProductReq,productCreate);  //postProductReq(dto) 객체의 내용을 productCreate 옮긴다. (DB에 저장하기 위함.)
            //productCreate.setUser(seller);

            //System.out.println(productCreate.getDeliveryType());


            productDao.save(productCreate);   //"product" DB에 정보 저장
            //System.out.println(productCreate.getId());



            //상품 인덱스를 통해 상품 객체를 불러옴
            Optional<Product> product = productDao.findById(productCreate.getId());   //product_id로 상품 객체를 불러옴.

            List<ProductOption> productOptionListCreate = new ArrayList<ProductOption>();       //List 객체 ("productOptionListCreate") 생성 (상품 옵션이 여러개 이기 때문에 List로 만들어 주어야함)

            //상품 옵션 DB에 상품 옵션 개수별로 상품 등록
            for(int i=0; i < postProductReq.getOptionName().size(); i++){

                ProductOption productOptionCreate = new ProductOption();                    //상품 옵션 엔티티 객체를 1개 생성
                productOptionCreate.setProduct(product.get());                              //상품 객체 (product_td) 입력
                productOptionCreate.setOptionName(postProductReq.getOptionName().get(i));  //상품 옵션 (option_name) 리스트를 하나씩 불러와 입력

                productOptionListCreate.add(productOptionCreate);                       ///List 객체 ("productOptionListCreate")에 상품 옵션 객체를 1개씩 담는다.
            }
            productOptionDao.saveAll(productOptionListCreate);


            return "상품 등록에 성공하였습니다.";

        } catch (Exception exception) {
            throw new BasicException(DATABASE_ERROR_CREATE_PRODUCT);  //상품 등록 실패 에러
        }
    }






//////////////////////////////////////////////////////////////////////////////////////////////////
    /* 10. 상품 정보 수정 -  modifyProduct() */
    public String modifyProduct(PatchProductReq patchProductReq, Long productId) throws BasicException {


        //DB에 상품 정보 수정 (배송타입 ,상품제목, 상품가격, 할인율 , 판매수량, 상품설명, 배송일  등)

        try{
            //배송 타입 변경
            if(patchProductReq.getDeliveryType() != null){
                productDao.modifyDeliveryType(patchProductReq.getDeliveryType(), productId);
            }
        } catch(Exception exception){
            throw new BasicException(DATABASE_ERROR_MODIFY_FAIL_USER_NAME);   //"이름 변경시 오류가 발생하였습니다."
        }

        try{
            //상품 제목 변경
            if(patchProductReq.getTitle() != null){
                productDao.modifyTitle(patchProductReq.getTitle(), productId);
            }
        } catch(Exception exception){
            throw new BasicException(DATABASE_ERROR_MODIFY_FAIL_USER_NAME);   //"이름 변경시 오류가 발생하였습니다."
        }

        try{
            //상품 가격 변경 & 상품 할인율 변경 & 상품 할인가격 생성
            if(patchProductReq.getPrice() != null & patchProductReq.getDiscountRate() >= 0){

                //(할인율과 상품 가격 활용해서) 할인된 상품 가격 넣기
                String str_price = patchProductReq.getPrice().substring(0, patchProductReq.getPrice().length()-1);  //상품가격에서 "원" 제거
                int num_price = Integer.parseInt(str_price);  //int형으로 전환
                String discountPrice = null;

                switch (patchProductReq.getDiscountRate()) {  //할인율로 할인가격 계산
                    case 0:
                        discountPrice = patchProductReq.getDiscountPrice();
                        break;
                    default:
                        discountPrice = (int)(num_price - num_price * (patchProductReq.getDiscountRate() / 100.0)) + "원";   //1000 - 1000*0.1
                        break;
                }
                patchProductReq.setDiscountPrice(discountPrice);

                //DB에 변경사항 반영
                productDao.modifyPrice(patchProductReq.getPrice(), productId);
                productDao.modifyDiscountRate(patchProductReq.getDiscountRate(), productId);
                productDao.modifyDiscountPrice(patchProductReq.getDiscountPrice(), productId);
            }
        } catch(Exception exception){
            throw new BasicException(DATABASE_ERROR_MODIFY_FAIL_USER_NAME);   //"이름 변경시 오류가 발생하였습니다."
        }


//        try{
//            //상품 할인율 변경
//            if(patchProductReq.getDiscountRate() >= 0){
//                System.out.println(patchProductReq.getDiscountRate());
//                productDao.modifyDiscountRate(patchProductReq.getDiscountRate(), productId);
//            }
//        } catch(Exception exception){
//            throw new BasicException(DATABASE_ERROR_MODIFY_FAIL_USER_NAME);   //"이름 변경시 오류가 발생하였습니다."
//        }



        try{
            //상품 판매수량 변경
            if(patchProductReq.getSaleCount() >= 10){
                System.out.println(patchProductReq.getSaleCount());
                productDao.modifySaleCount(patchProductReq.getSaleCount(), productId);
            }
        } catch(Exception exception){
            throw new BasicException(DATABASE_ERROR_MODIFY_FAIL_USER_NAME);   //"이름 변경시 오류가 발생하였습니다."
        }



        try{
            //상품 설명 변경
            if(patchProductReq.getDescription() != null){
                productDao.modifyDescription(patchProductReq.getDescription(), productId);
            }
        } catch(Exception exception){
            throw new BasicException(DATABASE_ERROR_MODIFY_FAIL_USER_NAME);   //"이름 변경시 오류가 발생하였습니다."
        }



        try{
            //상품 배송일 변경
            if(patchProductReq.getDeliveryDay() != null){
                productDao.modifyDeliveryDay(patchProductReq.getDeliveryDay(), productId);
            }
        } catch(Exception exception){
            throw new BasicException(DATABASE_ERROR_MODIFY_FAIL_USER_NAME);   //"이름 변경시 오류가 발생하였습니다."
        }


        try{
            //상품 옵션 변경
            Optional<Product> product = productDao.findById(productId);   //상품 인덱스를 통해 상품 객체를 불러옴

            if(patchProductReq.getOptionName().size() > 0){
                for(int i=0; i < patchProductReq.getOptionName().size(); i++) {
                    productOptionDao.modifyOptionName(patchProductReq.getOptionName().get(i), patchProductReq.getProductOptionId().get(i) ,product.get());
                }

            }


        } catch(Exception exception){
            throw new BasicException(DATABASE_ERROR_MODIFY_FAIL_USER_NAME);   //"이름 변경시 오류가 발생하였습니다."
        }

            return "상품 정보 변경에 성공하였습니다.";
    }






//////////////////////////////////////////////////////////////////////////////////////////////////
    /* 11. 전체 상품 조회 -  getProducts() */
    public List<GetProductsRes> getProducts() throws BasicException {


    //전체 상품 정보 조회
        try {
            List<GetProductsRes> getProductsRes = productDao.getProducts();

            return getProductsRes;

        } catch (Exception exception) {
          //System.out.println(exception);
            throw new BasicException(DATABASE_ERROR_GET_FAIL_PRODUCTS);  //전체상품 조회 실패 에러
        }



    }




/////////////////////////////////////////////////////////////////////////////////////////////////
    /* 12. 특정 상품 조회 -  getProduct() */
    public GetProductAndUserRes getProduct(Long productId) throws BasicException {


        //특정 상품 정보 조회
        try {
            //판매자 객체 불러오기
            User user = productDao.getUserByProductId(productId);
            //System.out.println(user.getName());

            //특정 상품 레코드 불러오기
            GetProductRes getProductRes = productDao.getProduct(productId);

            //DTO에 객체 입력
            GetProductAndUserRes getProductAndUserRes = new GetProductAndUserRes(getProductRes, user.getName());

            return getProductAndUserRes;

        } catch (Exception exception) {
            //System.out.println(exception);
            throw new BasicException(DATABASE_ERROR_GET_FAIL_PRODUCTS);  //상품 조회 실패 에러
        }



    }




}










