package kitchenpos.ui;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kitchenpos.application.MenuGroupService;
import kitchenpos.ui.dto.MenuGroupRequest;
import kitchenpos.ui.dto.MenuGroupResponse;

@RestController
public class MenuGroupRestController {
	private final MenuGroupService menuGroupService;

	public MenuGroupRestController(final MenuGroupService menuGroupService) {
		this.menuGroupService = menuGroupService;
	}

	@PostMapping("/api/menu-groups")
	public ResponseEntity<MenuGroupResponse> create(@RequestBody MenuGroupRequest menuGroupRequest) {
		final MenuGroupResponse created = menuGroupService.create(menuGroupRequest);
		final URI uri = URI.create("/api/menu-groups/" + created.getId());
		return ResponseEntity.created(uri).body(created);
	}

	@GetMapping("/api/menu-groups")
	public ResponseEntity<List<MenuGroupResponse>> list() {
		return ResponseEntity.ok().body(menuGroupService.list());
	}
}
