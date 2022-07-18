package com.save_backend.src.suspect;


import com.save_backend.config.exception.BaseException;
import com.save_backend.config.response.BaseResponse;
import com.save_backend.src.suspect.model.GetSuspectRes;
import com.save_backend.src.suspect.model.PostSuspectReq;
import com.save_backend.src.suspect.model.PostSuspectRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.save_backend.config.response.BaseResponseStatus.*;

@RestController
@RequestMapping("/suspect")
public class SuspectController {

    @Autowired
    private final SuspectProvider suspectProvider;
    @Autowired
    private final SuspectService suspectService;


    public SuspectController(SuspectProvider suspectProvider, SuspectService suspectService) {
        this.suspectProvider = suspectProvider;
        this.suspectService = suspectService;
    }

    /**
     * 1. 학대의심자 정보 생성 API
     * [POST] /suspect
     */
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostSuspectRes> createSuspect(@RequestBody PostSuspectReq postSuspectReq){
        try {
            // 아동인덱스 필수
            if(postSuspectReq.getChildIdx() == 0) {
                return new BaseResponse<>(MISSING_ESSENTIAL_CHILD_INDEX);
            }
            // 성별 필수
            if(postSuspectReq.getSuspectGender() == null) {
                return new BaseResponse<>(MISSING_ESSENTIAL_GENDER);
            }
            // 나이 필수
            if(postSuspectReq.getSuspectAge() == null) {
                return new BaseResponse<>(MISSING_ESSENTIAL_AGE);
            }
            // 아동과의 관계 필수
            if(postSuspectReq.getRelationWithChild() == null) {
                return new BaseResponse<>(MISSING_ESSENTIAL_RELATION_WITH_CHILD);
            }
            PostSuspectRes postSuspectRes = suspectService.createSuspect(postSuspectReq);
            return new BaseResponse<>(postSuspectRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 2. 학대의심자 정보 조회 API
     * 학대의심자 인덱스를 통해 특정 의심자 정보 조회
     */
    @ResponseBody
    @GetMapping("/{suspectIdx}")
    public BaseResponse<GetSuspectRes> getSuspectByIdx(@PathVariable("suspectIdx")int suspectIdx){
        try {
            GetSuspectRes getSuspectRes = suspectProvider.getSuspectByIdx(suspectIdx);
            return new BaseResponse<>(getSuspectRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());

        }
    }


}