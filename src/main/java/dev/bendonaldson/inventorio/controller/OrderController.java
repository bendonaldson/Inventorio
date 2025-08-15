package dev.bendonaldson.inventorio.controller;

import dev.bendonaldson.inventorio.dto.OrderRequestDto;
import dev.bendonaldson.inventorio.dto.OrderResponseDto;
import dev.bendonaldson.inventorio.exception.ResourceNotFoundException;
import dev.bendonaldson.inventorio.mapper.OrderMapper;
import dev.bendonaldson.inventorio.model.Order;
import dev.bendonaldson.inventorio.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @Autowired
    public OrderController(OrderService orderService, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    @GetMapping
    public List<OrderResponseDto> findAll() {
        return orderService.findAll().stream()
                .map(orderMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public OrderResponseDto findById(@PathVariable Long id) {
        return orderService.findById(id)
                .map(orderMapper::toResponseDto)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id " + id));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public OrderResponseDto create(@Valid @RequestBody OrderRequestDto orderDto) {
        Order savedOrder = orderService.save(orderDto);
        return orderMapper.toResponseDto(savedOrder);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        orderService.deleteById(id);
    }
}
