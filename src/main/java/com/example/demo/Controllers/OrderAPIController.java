package com.example.demo.Controllers;

import com.example.demo.Models.Category;
import com.example.demo.Models.Order;
import com.example.demo.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("v1/api/order")
public class OrderAPIController {
    @Autowired
    private OrderService _orderService;

    @GetMapping
    public List<Order> getAllOrders(){
        return _orderService.getAllOrder();
    }
    @PostMapping
    public Order addOrder(@RequestBody Order newOrder){
        return _orderService.addOrder(newOrder);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCate(@PathVariable Long id) {
        Order order = _orderService.getOrderById(id)
                .orElseThrow(() -> new RuntimeException("Category not found on :: "
                        + id));
        _orderService.deleteOrder(id);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<Order> updateCategory(@PathVariable Long id, @RequestBody Order updateOrder){
        Order currentOrder = _orderService.getOrderById(id)
                .orElseThrow(() -> new RuntimeException("Category not found on :: " + id));
        updateOrder.setId(id);
        _orderService.updateOrder(updateOrder);
        return ResponseEntity.ok(updateOrder);
    }
}
