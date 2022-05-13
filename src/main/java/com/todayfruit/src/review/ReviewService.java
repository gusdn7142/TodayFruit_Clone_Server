package com.todayfruit.src.review;



import com.todayfruit.config.BasicException;
import com.todayfruit.src.product.ProductDao;
import com.todayfruit.src.product.model.domain.Product;
import com.todayfruit.src.product.model.request.PatchProductReq;
import com.todayfruit.src.review.model.domain.Review;
import com.todayfruit.src.review.model.request.PatchReviewReq;
import com.todayfruit.src.review.model.request.PostReviewReq;
import com.todayfruit.src.review.model.response.GetReviewRes;
import com.todayfruit.src.user.UserDao;
import com.todayfruit.src.user.model.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.todayfruit.config.BasicResponseStatus.*;

@Service
@RequiredArgsConstructor  //final이 붙거나 @NotNull 이 붙은 필드의 생성자를 자동 생성해주는 롬복 어노테이션
public class ReviewService {

    private final ReviewDao reviewDao;
    private final ReviewScoreDao reviewScoreDao;
    private final UserDao userDao;
    private final ProductDao productDao;


////////////////////////////////////////////////////////////////////////////////////////////////
    /* 20. 상품 리뷰 작성 API -  createProduct() */
    public String createReview(PostReviewReq postReviewReq, Long userId, Long productId) throws BasicException {


        //사용자 인덱스를 통해 사용자 객체를 불러옴
        User reviewer = userDao.checkStatusUser(userId);

        //회원 탈퇴 여부 확인 (유저가 계속 클릭시..)
        if(reviewer == null){
            throw new BasicException(PATCH_USERS_DELETE_USER);  //"회원 탈퇴된 계정입니다."
        }
        postReviewReq.setUser(reviewer);                //DTO에 사용자 정보 입력 완료


        //상품 인덱스를 통해 상품 객체를 불러옴
        Product reviewProduct = productDao.checkStatusPrdouct(productId);   //user_id 로판매자 불러옴.

        //상품 삭제 여부 조회
        if(reviewProduct == null){   //상품이 삭제되었다면..
            throw new BasicException(PATCH_PRODUCTS_DELETE_PRDOCUT);  //"삭제된 상품 입니다."
        }
        postReviewReq.setProduct(reviewProduct);                //DTO에 상품 객체 입력 완료



        //리뷰 중복 작성 검사
        if(reviewDao.checkStatusReview(reviewer, reviewProduct) != null){
            throw new BasicException(POST_REVIEWS_EXISTS_REVIEW);  //"이미 해당 상품의 리뷰를 작성하셨습니다."
        }



        //리뷰 점수에 맞는 별 이미지 받아오가
        String star = reviewScoreDao.convertScore(postReviewReq.getScore());
        if(star==null){
            throw new BasicException(POST_REVIEWS_NOT_EXISTS_STAR);  //"리뷰 점수를 Star 이미지로 변환하는데 실패하였습니다."
        }
        postReviewReq.setStar(star);



        //DB에 리뷰 등록 (리뷰 내용, 리뷰 점수)
        try{

            //리뷰 정보 DB에 등록
            Review reviewCreate = new Review();         //productCreate 객체 생성
            BeanUtils.copyProperties(postReviewReq,reviewCreate);  //postReviewReq(dto) 객체의 내용을 ReviewCreate 옮긴다. (DB에 저장하기 위함.)

            reviewDao.save(reviewCreate);   //"review" DB에 정보 저장

            return "리뷰 등록에 성공하였습니다.";

        } catch (Exception exception) {
            throw new BasicException(DATABASE_ERROR_CREATE_REVIEW);  //리뷷 등록 실패 에러
        }
    }



///////////////////////////////////////////////////////////////////////////////////////////////////
    /* 21. 상품 리뷰 조회 -  getReviews() */
    public List<GetReviewRes> getReviews(Long productId) throws BasicException {

        //상품 인덱스를 통해 상품 객체를 불러옴
        Product reviewProduct = productDao.checkStatusPrdouct(productId);   //user_id 로판매자 불러옴.

        //상품 삭제 여부 조회
        if(reviewProduct == null){   //상품이 삭제되었다면..
            throw new BasicException(PATCH_PRODUCTS_DELETE_PRDOCUT);  //"삭제된 상품 입니다."
        }



        //상품 리뷰 정보 조회
        try {
            List<GetReviewRes> getReviewRes = reviewDao.getReviews(reviewProduct);

            return getReviewRes;

        } catch (Exception exception) {
            System.out.println(exception);
            throw new BasicException(DATABASE_ERROR_GET_FAIL_REVIEW);  //"상품 리뷰 조회에 실패하였습니다."
        }



    }






//////////////////////////////////////////////////////////////////////////////////////////////
    /* 22. 리뷰 작성자와 수정자가 일치하는지 확인  -  checkReviewer()  (상품 리뷰 수정 API에서 활용) */
    public void checkReviewer(Long userId, Long reviewId) throws BasicException {


        //사용자 인덱스를 통해 사용자 객체를 불러옴
        User reviewer = userDao.checkStatusUser(userId);

        //회원 탈퇴 여부 확인 (유저가 계속 클릭시..)
        if(reviewer == null){
            throw new BasicException(PATCH_USERS_DELETE_USER);  //"회원 탈퇴된 계정입니다."
        }



        //리뷰 작성자와 수정자가 일치하는지 확인
        if(reviewDao.checkReviewer(reviewId, reviewer) == null){
            throw new BasicException(PATCH_REIVEWS_NOT_SAME_REVIEWER);  //"리뷰 작성자와 수정자가 일치하지 않습니다."
        }


    }




/////////////////////////////////////////////////////////////////////////////////////////////////
    /* 22. 상품 리뷰 수정 -  modifyReview() */
    public String modifyReview(PatchReviewReq patchReviewReq, Long userId, Long reviewId) throws BasicException {


        //리뷰 삭제 여부 조회 (유저가 계속 클릭시..)
        if(reviewDao.checkStatusReview(reviewId) == null){   //리뷰가 삭제되었다면..
            throw new BasicException(PATCH_PRODUCTS_DELETE_REVIEW);  //"삭제된 리뷰 입니다."
        }


        //리뷰 점수에 맞는 별 이미지 받아오가
        String star = reviewScoreDao.convertScore(patchReviewReq.getScore());
        if(star==null){
            throw new BasicException(POST_REVIEWS_NOT_EXISTS_STAR);  //"리뷰 점수를 Star 이미지로 변환하는데 실패하였습니다."
        }
        patchReviewReq.setStar(star);


        try{

            //DB에서 리뷰 정보 수정 (리뷰 내용, 리뷰 점수, 리뷰 별, 리뷰 이미지 등등)
           reviewDao.modifyReview(patchReviewReq.getScore(),patchReviewReq.getStar(), patchReviewReq.getImage(), patchReviewReq.getContent(), reviewId);

            return "상품 리뷰 수정에 성공하였습니다.";

        } catch(Exception exception){
            throw new BasicException(DATABASE_ERROR_MODIFY_FAIL_REVIEW);
        }

    }



////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* 23. 상품 리뷰 삭제 API - deleteReview()   */
    public void deleteReview(Long reviewId) throws BasicException {

        //리뷰 삭제 여부 조회 (유저가 계속 클릭시..)
        if(reviewDao.checkStatusReview(reviewId) == null){   //리뷰가 삭제되었다면..
            throw new BasicException(PATCH_PRODUCTS_DELETE_REVIEW);  //"삭제된 리뷰 입니다."
        }



        try{
            //상품 리뷰 삭제
            reviewDao.deleteReview(reviewId);


        } catch(Exception exception){
            throw new BasicException(DATABASE_ERROR_DELETE_REVIEWS);   //'리뷰 삭제에 실패하였습니다.'
        }

    }









}
