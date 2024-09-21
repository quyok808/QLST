package com.example.demo.Service;

import com.example.demo.Models.OrderDetail;
import com.example.demo.Repository.OrderdetailsRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Service
@AllArgsConstructor
public class OrderDetailsService {
    private final OrderdetailsRepository _orderDetailsRepository;

    //GET
    public List<OrderDetail> getAll(){
        return _orderDetailsRepository.findAll();
    }
    //POST
    public OrderDetail addProductToOrderDetail(OrderDetail newOrderDetails){
        return _orderDetailsRepository.save(newOrderDetails);
    }
    //DELETE
    public void deleteOrderDetail(Long id){
        _orderDetailsRepository.deleteById(id);
    }

    //PUT
    public void updateOrderDetail(@NotNull OrderDetail updateOrderDetail){
        var currentOrderDetail = _orderDetailsRepository.findById(updateOrderDetail.getId())
                .orElseThrow(() -> new IllegalStateException("Order Detail with ID " + updateOrderDetail.getId() + "does not exists !!!"));
        currentOrderDetail.setQuantity(updateOrderDetail.getQuantity());
        _orderDetailsRepository.save(currentOrderDetail);
    }
}
