package com.cgr.handler;

import com.cgr.entity.ResponseModel;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理 Spring Security 的认证异常（如用户名或密码错误等）
     * 返回 401 Unauthorized
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ResponseModel<?>> handleAuthenticationException(AuthenticationException ex) {
        // ResponseModel.authenticatedError(code=402, msg)
        ResponseModel body = new ResponseModel(401,  ex.getMessage(),"认证异常");
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(body);
    }

    /**
     * 处理 DuplicateKeyException 异常（如用户名重复）
     */
    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<ResponseModel<?>> handleDuplicateKeyException(DuplicateKeyException ex) {
        ResponseModel<?> body = ResponseModel.error("用户名已存在");
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(body);
    }

    /**
     * 处理 Spring Security 的授权异常（如权限不足）
     * 返回 403 Forbidden
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ResponseModel<?>> handleAccessDeniedException(AccessDeniedException ex) {
        ResponseModel<?> body = ResponseModel.error("权限不足");
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(body);
    }

    /**
     * 捕获其他未处理的运行时异常
     * 返回 500 Internal Server Error
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseModel<?>> handleOtherExceptions(Exception ex) {
        ex.printStackTrace();
        ResponseModel<?> body = ResponseModel.error("服务器内部错误：" + ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(body);
    }
}
