package exceptions;

import lombok.Getter;
import lombok.NonNull;
import response.Response;

/**
 * Класс описывает ошибку, связанную с http ответом
 */
@Getter
public class HttpResponseException extends RuntimeException {
    private final Response response;
    private final String reason;

    public HttpResponseException(Response response) {
        this(response, "");
    }

    public HttpResponseException(@NonNull Response response, @NonNull String reason) {
        super(reason + "\n" + response);
        this.response = response;
        this.reason = reason;
    }


    @Override
    public String toString() {
        return reason + "\n" + response.toString();
    }
}
