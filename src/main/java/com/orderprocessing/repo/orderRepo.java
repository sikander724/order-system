package com.orderprocessing.repo;
import com.orderprocessing.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface orderRepo extends JpaRepository<order, Long> {
}
