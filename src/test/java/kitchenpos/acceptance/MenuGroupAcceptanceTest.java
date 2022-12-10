package kitchenpos.acceptance;

import java.util.function.ToLongFunction;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kitchenpos.AcceptanceTest2;
import kitchenpos.domain.MenuGroup;

@DisplayName("메뉴 그룹 관리")
class MenuGroupAcceptanceTest extends AcceptanceTest2<MenuGroup> {

	static final String REQUEST_PATH = "/api/menu-groups";
	static final String 메뉴명 = "푸드";

	Long 메뉴_그룹_아이디;

	/**
	 * Feature: 메뉴 그룹 관리
	 * When 메뉴 그룹 등록을 요청하면
	 * Then 메뉴 그룹 등록에 성공한다
	 */
	@Test
	void 메뉴_관리() {
		메뉴_그룹_등록되어_있음();
	}

	public MenuGroup 메뉴_그룹_등록되어_있음() {
		ExtractableResponse<Response> 등록_요청_응답 = 등록_요청(메뉴_그룹());
		return 등록됨(등록_요청_응답);
	}

	@Override
	protected String getRequestPath() {
		return REQUEST_PATH;
	}

	@Override
	protected ToLongFunction<MenuGroup> idExtractor() {
		return MenuGroup::getId;
	}

	@Override
	protected Class<MenuGroup> getDomainClass() {
		return MenuGroup.class;
	}

	public static MenuGroup 메뉴_그룹() {
		MenuGroup menuGroup = new MenuGroup();
		menuGroup.setName(메뉴명);
		return menuGroup;
	}

}
