package cn.cqray.springboot.response;


/**
 * 响应体异常
 * @author Cqray
 */
public class ResponseException extends RuntimeException {

    private String code;
    private String message;
    private Object data;
    private Object page;

    public ResponseException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseException(String code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResponseException(String code, String message, Object data, Object page) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.page = page;
    }

    public ResponseData getResponseData() {
        return ResponseData.newResponseData(code, message, data, page);
    }

    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }
}
