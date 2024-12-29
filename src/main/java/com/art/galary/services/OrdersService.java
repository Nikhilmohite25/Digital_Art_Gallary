package com.art.galary.services;

import java.util.List;
import org.springframework.core.annotation.Order;

import com.art.galary.models.Artwork;
import com.art.galary.models.Orders;
import com.art.galary.models.User;

public interface OrdersService {
    void save(Orders orders, User user, Artwork artwork);
    List<Orders> getOrders();
    List<Orders> findOrdersByUser(int id);
    void updateOrders(Orders orders);
    Orders findByOrderId(int id);
}
