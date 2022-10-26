package clientImps.apache;

import client.AbstractHttpClient;
import headers.Header;
import headers.Headers;
import lombok.SneakyThrows;
import org.apache.hc.client5.http.classic.methods.*;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import request.ReqLine;
import request.Request;
import request.payload.ReqPayload;
import response.*;
import utils.StringUtils;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ApacheClient extends AbstractHttpClient {

    @Override
    protected Response sendHttp(Request request) {
        ReqLine reqLine = request.getReqLine();
        HttpUriRequest apacheRequest = createApacheRequestByStartLine(reqLine);
        addHeaders(apacheRequest, request.getHeaders());
        setBody(apacheRequest, request.getBody());
        try (var httpClient = HttpClients.createDefault()) {
            CloseableHttpResponse apacheResponse = httpClient.execute(apacheRequest);
            return formApacheRes(apacheResponse);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static HttpUriRequest createApacheRequestByStartLine(ReqLine reqLine) {
        URI uri = reqLine.getUri();
        switch (reqLine.getHttpMethod()) {
            case GET:
                return new HttpGet(uri);
            case HEAD:
                return new HttpHead(uri);
            case DELETE:
                return new HttpDelete(uri);
            case OPTIONS:
                return new HttpOptions(uri);
            case TRACE:
                return new HttpTrace(uri);
            case POST:
                return new HttpPost(uri);
            case PUT:
                return new HttpPut(uri);
            case PATCH:
                return new HttpPatch(uri);
            default:
                throw new IllegalStateException();
        }
    }

    private void addHeaders(HttpUriRequest apacheRequest, Headers headers) {
        headers.forEach(header -> {
            apacheRequest.addHeader(header.getName(), header.getValue());
        });
    }

    private void setBody(HttpUriRequest apacheRequest, ReqPayload body) {
        if (body != null) {
            HttpEntityBuilderVisitor visitor = new HttpEntityBuilderVisitor();
            body.accept(visitor);
            HttpEntity entity = visitor.build();
            apacheRequest.setEntity(entity);
        }
    }

    @SneakyThrows
    private Response formApacheRes(CloseableHttpResponse apacheResponse) {
        // Изъять из апач ответа
        HttpStatusCode statusCode = HttpStatusCode.getByValue(apacheResponse.getCode());
        var apacheProtocol = apacheResponse.getVersion();
        ProtocolVersion version = new ProtocolVersion(apacheProtocol.getProtocol(), apacheProtocol.getMajor(), apacheProtocol.getMinor());
        List<Header> headers = Arrays.stream(apacheResponse.getHeaders())
                .map(it -> new Header(it.getName(), it.getValue()))
                .collect(Collectors.toList());

        HttpEntity entity = apacheResponse.getEntity();
        String body = entity != null ? EntityUtils.toString(entity) : "";

        // засетить в Response
        StatusLine statusLine = new StatusLine(version, statusCode);
        ResponseImpl response = new ResponseImpl(statusLine);
        if (!headers.isEmpty()) {
            response = response.setHeaders(new Headers(headers));
        }
        if (StringUtils.isNonBlank(body)) {
            response = response.setBody(body);
        }

        return response;
    }
}