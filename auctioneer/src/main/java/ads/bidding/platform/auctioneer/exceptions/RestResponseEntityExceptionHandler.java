/*
  Copyrights to Velimir Avramovski, July 2022.
 */
package ads.bidding.platform.auctioneer.exceptions;

import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * This exception handler is responsible to catch and return customised response when an exceptions occurs in our service.
 */
@RestControllerAdvice
public class RestResponseEntityExceptionHandler {

  /**
   * Api error object that we gonna return.
   */
  @Data
  @AllArgsConstructor
  private static class ApiError {

    private int httpStatus;
    private String message;
    private List<String> errors;
    private String path;

  }

  /**
   * Catches and handles EmptyAdBidsExceptions.class exceptions.
   *
   * @param ex      exception.
   * @param request incoming request.
   * @return ResponseEntity<ApiError> with our custom ApiError.
   */
  @ExceptionHandler(EmptyAdBidsExceptions.class)
  public ResponseEntity<ApiError> handleEmptyAdBidsExceptions(EmptyAdBidsExceptions ex, HttpServletRequest request) {
    ApiError apiError =
        new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getLocalizedMessage(),
            Collections.singletonList(ErrorCode.EMPTY_AD_BIDS.toString()), request.getRequestURL().toString());
    return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  /**
   * Catches and handles AdBidRequestException.class exceptions.
   *
   * @param ex      exception.
   * @param request incoming request.
   * @return ResponseEntity<ApiError> with our custom ApiError.
   */
  @ExceptionHandler(AdBidRequestException.class)
  public ResponseEntity<ApiError> handleAdBidRequestException(AdBidRequestException ex, HttpServletRequest request) {
    ApiError apiError =
        new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getLocalizedMessage(),
            Collections.singletonList(ErrorCode.AD_BID_REQUEST_FAILED.toString()), request.getRequestURL().toString());
    return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

}
