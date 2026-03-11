package com.scm.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scm.entities.MyOrder;
import com.scm.entities.User;

public interface OrderRepo extends JpaRepository<MyOrder, Long> {
  List<MyOrder> findByUser(User user);

  List<MyOrder> findByStatus(String status);

}
