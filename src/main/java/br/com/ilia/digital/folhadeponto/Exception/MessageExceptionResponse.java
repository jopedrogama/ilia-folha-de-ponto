package br.com.ilia.digital.folhadeponto.Exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageExceptionResponse {

    private String mensagem;
}
