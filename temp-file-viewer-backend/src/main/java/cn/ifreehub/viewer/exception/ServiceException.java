package cn.ifreehub.viewer.exception;

/**
 * @author Quding Ding
 * @since 2018/6/20
 */
public class ServiceException extends RuntimeException {

  public ServiceException(Throwable cause) {
    super(cause);
  }

  public ServiceException(String message) {
    super(message);
  }

  public ServiceException(String message, Throwable cause) {
    super(message, cause);
  }
}
