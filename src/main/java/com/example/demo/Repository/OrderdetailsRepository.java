package com.example.demo.Repository;

import com.example.demo.Models.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderdetailsRepository extends JpaRepository<OrderDetail, Long> {
    public List<OrderDetail> findAllByOrderId(Long orderId);
}
