package response;

public interface Response {

    ProtocolVersion getVersion();

    HttpStatusCode getStatusCode();

    String getBody();
}
