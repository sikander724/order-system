package com.orderprocessing.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orderprocessing.model.order;
import com.orderprocessing.service.OrderService;

import jakarta.jms.Queue;

import java.util.List;

@Controller
public class OrderGraphQLController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private ObjectMapper obj;
    @Autowired
    private JmsTemplate jmsTemplate;

    
    @Autowired
    private Queue orderqueue; 
 //getall 
    @QueryMapping(name="getAllOrders")
    public List<order> getAllOrders() {
        return orderService.getAllOrders();
        
    }

 //get by id
    @QueryMapping(name = "getOrderById")
    public order getOrderById(@Argument Long orderId) {
        return orderService.getOrderById(orderId);
    }
//creating order
    @MutationMapping(name = "createOrder")
    public order createOrder(@Argument OrderInput orderInput) {  
      
        order newOrder = new order();  
        newOrder.setCustomerName(orderInput.customerName());
        newOrder.setProduct(orderInput.product());
        newOrder.setquantity(orderInput.quantity());  
        newOrder.setStatus("Pending");  

     
        order createdOrder = orderService.createOrder(newOrder);
        
        try {
            
            String orderJson = obj.writeValueAsString(createdOrder);
            
            
            jmsTemplate.convertAndSend("order.queue", orderJson);
            System.out.println(" Order Created for ID"+createdOrder.getId()+"  & Sent to ActiveMQ" );
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while sending order to ActiveMQ", e);
        }
        
        return createdOrder;  
    }


    public record OrderInput(String customerName, String product, int quantity) {}
}
