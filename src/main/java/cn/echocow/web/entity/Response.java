package cn.echocow.web.entity;

import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * RespEntity
 *
 * @author echo
 * @version 1.0
 * @date 18-10-10 下午1:18
 */
@Data
public class Response {
    private int code;
    private String msg;
    private Object data;

    public Response(HttpStatus httpStatus) {
        this.code = httpStatus.value();
        this.msg = httpStatus.getReasonPhrase();
    }

    public Response(HttpStatus httpStatus, Object data) {
        this(httpStatus);
        this.data = data;
    }
}
