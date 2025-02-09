package com.orderprocessing.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.orderprocessing.model.order;
import com.orderprocessing.repo.orderRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private orderRepo repo;
    @Autowired
    private ObjectMapper obj;
//getall 
    public List<order> getAllOrders() {
        return repo.findAll();
    }
//getbyorder
    public order getOrderById(Long id) {
        return repo.findById(id).orElse(null);
    }
//create
    public order createOrder(order order) {
        return repo.save(order);
    }
    
    
    
    
    //order processing service



    @JmsListener(destination = "order.queue")
    public void processOrder(String orderJson) {
        try {
          //json to object
            order order = obj.readValue(orderJson, order.class);

            System.out.println(" processing for ID= " + order.getId());

          
            Thread.sleep(5000);

         
            order.setStatus("Processed");
            
            repo.save(order);

            System.out.println("Order completed for ID=" + order.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    
    
    
    
    
    
    
    
    
}
