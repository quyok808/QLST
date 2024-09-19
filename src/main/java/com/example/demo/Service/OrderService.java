package com.example.demo.Service;

import com.example.demo.Models.Order;
import com.example.demo.Models.OrderDetail;
import com.example.demo.Repository.OrderRepository;
import com.example.demo.Repository.OrderdetailsRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class OrderService {
    private final OrderRepository _orderRepository;
    private final OrderdetailsRepository _orderdetailsRepository;
    //GET

    public List<Order> getAllOrder(){
        return _orderRepository.findAll();
    }


    public Optional<Order> getOrderById(Long id){
        return _orderRepository.findById(id);
    }
    //POST

    public Order addOrder(Order newOrder){
        return _orderRepository.save(newOrder);
    }
    //DELETE

    public void deleteOrder(Long id){
        var orderDetails = _orderdetailsRepository.findAllByOrderId(id);
        if (orderDetails != null){
            _orderdetailsRepository.deleteAll(orderDetails);
        }
        _orderRepository.deleteById(id);
    }
    //PUT
    public void updateOrder (@NotNull Order updateOrder){
        var currentOder = _orderRepository.findById(updateOrder.getId())
                .orElseThrow(() -> new IllegalStateException("Order with id " + updateOrder.getId() + " does not exists !!!"));
        currentOder.setNote(updateOrder.getNote());
        currentOder.setAddress(updateOrder.getAddress());
        currentOder.setCustomername(updateOrder.getCustomername());
        currentOder.setCity(updateOrder.getCity());
        currentOder.setEmail(updateOrder.getEmail());

        _orderRepository.save(currentOder);
    }
}
