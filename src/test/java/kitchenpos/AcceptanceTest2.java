package kitchenpos;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.function.ToLongFunction;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kitchenpos.utils.DatabaseCleanUtils;
import kitchenpos.utils.RestAssuredUtils;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AcceptanceTest2<T> {

    @LocalServerPort
    int port;

    @Autowired
    private DatabaseCleanUtils databaseCleanup;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
        databaseCleanup.cleanUp();
    }

    public ExtractableResponse<Response> 등록_요청(T requestBody) {
        return 등록_요청(getRequestPath(), requestBody);
    }

    private ExtractableResponse<Response> 등록_요청(String requestPath, T requestBody) {
        return RestAssuredUtils.post(requestPath, requestBody);
    }

    public T 등록됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        return response.body().as(getDomainClass());
    }

    protected void 등록_실패함(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isNotEqualTo(HttpStatus.OK.value());
    }

    protected List<T> 목록_조회() {
        ExtractableResponse<Response> response = RestAssuredUtils.get(getRequestPath());
        return response.body().jsonPath().getList(".", getDomainClass());
    }

    protected void 목록_조회됨(List<T> actualList, Long expectedId) {
        assertThat(actualList)
            .extracting(idExtractor()::applyAsLong)
            .contains(expectedId);
    }

    protected abstract String getRequestPath();

    protected abstract ToLongFunction<T> idExtractor();

    protected abstract Class<T> getDomainClass();
}
