package response;

public interface Response {

    HttpVersion getVersion();

    HttpStatusCode getStatusCode();

    String getBody();
}
