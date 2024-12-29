package com.art.galary.repository;

import java.util.List;

import com.art.galary.models.Orders;

public interface OrdersRepository {
    public void save(Orders orders);
    public Orders findByOrderId(int id);
    public void updateOrder(Orders orders);
    public List<Orders> getOrders();
    public List<Orders> findOrderByUser(int id);
}
