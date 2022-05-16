package com.todayfruit.src.product;






import com.todayfruit.config.BasicException;
import com.todayfruit.src.product.model.domain.Product;
import com.todayfruit.src.product.model.domain.ProductOption;
import com.todayfruit.src.product.model.request.PatchProductReq;
import com.todayfruit.src.product.model.request.PostProductReq;
import com.todayfruit.src.product.model.response.GetProductAndUserRes;
import com.todayfruit.src.product.model.response.GetProductOptionRes;
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



        //DB에 상품 등록 (배송타입 ,상품제목, 상품 이미지, 상품가격, 할인율 , 판매수량, 상품설명, 배송일  등)
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


        try{
            //DB에서 상품 정보 수정 (배송타입 ,상품제목, 상품 이미지, 상품가격, 할인율 , 판매수량, 상품설명, 배송일  등)
            if(patchProductReq.getDeliveryType() != null){
                productDao.modifyProduct(patchProductReq.getDeliveryType()
                                        ,patchProductReq.getTitle()
                                        ,patchProductReq.getImage()
                                        ,patchProductReq.getPrice()
                                        ,patchProductReq.getDiscountRate()
                                        ,patchProductReq.getSaleCount()
                                        ,patchProductReq.getDescription()
                                        ,patchProductReq.getDeliveryDay()
                                        ,patchProductReq.getDiscountPrice()
                                        ,productId);
            }
        } catch(Exception exception){
            throw new BasicException(DATABASE_ERROR_MODIFY_FAIL_PRODUCT);
        }




        try{
            //상품 옵션 변경
            Optional<Product> product = productDao.findById(productId);   //상품 인덱스를 통해 상품 객체를 불러옴

                for(int i=0; i < patchProductReq.getOptionName().size(); i++) {
                    productOptionDao.modifyOptionName(patchProductReq.getOptionName().get(i), patchProductReq.getProductOptionId().get(i) ,product.get());
                }
        } catch(Exception exception){
            throw new BasicException(DATABASE_ERROR_MODIFY_FAIL_PRODUCTS_OptionName);
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



 //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* 13. 상품 삭제 API - deleteUser()   */
    public void deleteProduct(Long productId) throws BasicException {

        //상품 삭제 여부 조회 (유저가 계속 클릭시..)
        if(productDao.checkStatusPrdouct(productId) == null){   //상품이 삭제되었다면..
            throw new BasicException(PATCH_PRODUCTS_DELETE_PRDOCUT);  //"삭제된 상품 입니다."
        }


        try{
            //상품 정보 삭제
            productDao.deleteProduct(productId);

            //상품 옵션 정보 삭제
            Optional<Product> productDelete = productDao.findById(productId);
            productOptionDao.deleteProductOption(productDelete.get());


        } catch(Exception exception){
            throw new BasicException(DATABASE_ERROR_DELETE_PRODUCTS);   //'상품 삭제에 실패하였습니다.'
        }

    }






///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* 14. 상품 옵션 조회 API   */
    public List<GetProductOptionRes>  getProductOptions(Long productId) throws BasicException {

        //상품 객체 불러오기
        Product productGet = productDao.checkStatusPrdouct(productId);

        //상품 삭제 여부 조회
        if(productGet == null){   //상품이 삭제되었다면..
            throw new BasicException(PATCH_PRODUCTS_DELETE_PRDOCUT);  //"삭제된 상품 입니다."
        }


        try{

            //상품 옵션 조회
            List<GetProductOptionRes> getProductOptionRes = productOptionDao.getProductOptions(productGet);
            return getProductOptionRes;

        } catch(Exception exception){
            System.out.println(exception);
            throw new BasicException(DATABASE_ERROR_GET_FAIL_PRODUCT_OPTIONS);   //'상품 옵션 조회에 실패하였습니다.'
        }

    }



}










