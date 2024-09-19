package com.example.demo.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date Orderdate;
    private String Customername;
    private String Email;
    private String Address;
    private String City;
    private int Total;
    private String Note;
    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderdetails;
}
