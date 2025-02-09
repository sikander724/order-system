package com.orderprocessing.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orderprocessing.model.order;
import com.orderprocessing.repo.orderRepo;

import jakarta.jms.Queue;




@RestController
@RequestMapping("/orders")
class orderController{
	@Autowired
	private orderRepo repo;

	   @Autowired
	    private JmsTemplate jmsTemplate;
	    
	    @Autowired
	    private Queue orderqueue;  // ✅ Injected from ActiveMQConfig
	    @Autowired
	    private ObjectMapper obj;
@PostMapping 
public ResponseEntity<order> createOrder(@RequestBody order or) {
    // Save order to database
	
    order createdOrder = repo.save(or);
    
    try {
        // Convert Order to JSON string
        String orderJson = obj.writeValueAsString(createdOrder);
        
        // Send JSON string to ActiveMQ
        jmsTemplate.convertAndSend("order.queue", orderJson);
        System.out.println(" Order Created for ID"+createdOrder.getId()+"  & Sent to ActiveMQ" );
    } catch (JsonProcessingException e) {
        e.printStackTrace();
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    return new ResponseEntity<>(createdOrder, HttpStatus.OK);
}

    @GetMapping("/getAll")
    public ResponseEntity<List<order>> getAllOrders() {
        return new ResponseEntity<>(repo.findAll(), HttpStatus.OK);
        
        
    }

   
}


