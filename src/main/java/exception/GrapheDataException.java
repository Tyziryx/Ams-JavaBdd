package main.java.exception;

public class GrapheDataException extends RuntimeException {
  public GrapheDataException(String message) {
    super(message);
  }

  public GrapheDataException(String message, Throwable cause) {
    super(message, cause);
  }
}