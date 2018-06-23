package cn.ifreehub.viewer.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import cn.ifreehub.viewer.constant.ApiStatus;

import java.io.Serializable;

/**
 * @author Quding Ding
 * @since 2018/5/31
 */
public class ApiWrapper<T> implements Serializable {

  private Integer status;

  private String message;

  private T data;

  private ApiWrapper(Integer status, String message, T data) {
    this.status = status;
    this.message = message;
    this.data = data;
  }

  @JsonIgnore
  public boolean isSuccess() {
    return status == ApiStatus.SUCCESS.code;
  }

  public static <R> ApiWrapper<R> success() {
    return new ApiWrapper<>(ApiStatus.SUCCESS.code, ApiStatus.SUCCESS.message, null);
  }

  public static <R> ApiWrapper<R> success(R data) {
    return new ApiWrapper<>(ApiStatus.SUCCESS.code, ApiStatus.SUCCESS.message, data);
  }

  public static <R> ApiWrapper<R> fail(ApiStatus status) {
    return new ApiWrapper<>(status.code, status.message, null);
  }

  public static <R> ApiWrapper<R> fail(ApiStatus status, String message) {
    return new ApiWrapper<>(status.code, message, null);
  }


  public Integer getStatus() {
    return status;
  }

  public String getMessage() {
    return message;
  }

  public T getData() {
    return data;
  }
}
