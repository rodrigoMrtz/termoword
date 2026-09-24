package br.com.termoword.exception;

import java.time.LocalDateTime;

public record ApiErrorResponse(
    int status,
    String message,
    LocalDateTime timestamp
) {
}
