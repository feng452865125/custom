package com.chunhe.custom.mybatis;

import com.chunhe.custom.response.ServiceResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Created by xuqiang on 2017/6/12.
 */
public abstract class BaseController {

    /**
     * 操作的响应处理
     * @param result
     * @return
     */
    public ResponseEntity responseDeal(ServiceResponse result) {
        if (!result.isSucc()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result.getContent());
        }
        return ResponseEntity.status(HttpStatus.OK).body(result.getContent());
    }

}
