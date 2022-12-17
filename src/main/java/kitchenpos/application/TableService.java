package kitchenpos.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kitchenpos.domain.OrderTable;
import kitchenpos.domain.OrderTableRepository;
import kitchenpos.exception.AlreadyJoinedTableGroupException;
import kitchenpos.exception.EntityNotFoundException;
import kitchenpos.ui.dto.OrderTableRequest;
import kitchenpos.ui.dto.OrderTableResponse;

@Service
public class TableService {
	private final OrderTableRepository orderTableRepository;

	public TableService(OrderTableRepository orderTableRepository) {
		this.orderTableRepository = orderTableRepository;
	}

	@Transactional
	public OrderTableResponse create(OrderTableRequest request) {
		OrderTable orderTable = request.toOrderTable();
		orderTable = orderTableRepository.save(orderTable);

		return new OrderTableResponse(orderTable);
	}

	@Transactional
	public OrderTable create(final OrderTable orderTable) {
		orderTable.detachTableGroup();
		return orderTableRepository.save(orderTable);
	}

	public List<OrderTableResponse> list() {
		return OrderTableResponse.of(orderTableRepository.findAll());
	}

	public List<OrderTable> findAll() {
		return orderTableRepository.findAll();
	}

	@Transactional
	public OrderTableResponse changeEmpty(Long orderTableId, OrderTableRequest request) {
		return new OrderTableResponse(changeEmpty(orderTableId, request.toOrderTable()));
	}

	@Transactional
	public OrderTable changeEmpty(Long orderTableId, OrderTable orderTable) {
		final OrderTable savedOrderTable = findById(orderTableId);

		if (savedOrderTable.hasTableGroup()) {
			throw new AlreadyJoinedTableGroupException();
		}

		savedOrderTable.validateChangeEmpty();

		savedOrderTable.changeEmpty(orderTable.isEmpty());

		return orderTableRepository.save(savedOrderTable);
	}

	@Transactional
	public OrderTableResponse changeNumberOfGuests(Long orderTableId, OrderTableRequest request) {
		return new OrderTableResponse(changeNumberOfGuests(orderTableId, request.toOrderTable()));
	}

	@Transactional
	public OrderTable changeNumberOfGuests(Long orderTableId, OrderTable orderTable) {
		final OrderTable savedOrderTable = findById(orderTableId);

		savedOrderTable.changeNumberOfGuests(orderTable);

		return savedOrderTable;
	}

	public OrderTable findById(Long orderTableId) {
		return orderTableRepository.findById(orderTableId)
			.orElseThrow(EntityNotFoundException::new);
	}

	public List<OrderTable> findAllById(List<Long> orderTableId) {
		return orderTableRepository.findAllById(orderTableId);
	}
}
