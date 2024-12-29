package com.art.galary.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.art.galary.models.Artwork;
import com.art.galary.models.Orders;
import com.art.galary.models.User;
import com.art.galary.repository.OrdersRepository;

@Service
public class OrdersServiceImpl implements OrdersService {
    OrdersRepository ordersRepository;

    @Autowired
    public OrdersServiceImpl(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    @Override
    public void save(Orders orders, User user, Artwork artwork) {
        orders.setStatus("Ordered");
        orders.setUser_id(user.getId());
        orders.setArtwork_id(artwork.getId());
        ordersRepository.save(orders);
        System.out.println("Inside order service" + orders);
    }

    @Override
    public List<Orders> getOrders() {
        return ordersRepository.getOrders();
    }

    @Override
    public List<Orders> findOrdersByUser(int id) {
        return ordersRepository.findOrderByUser(id);
    }

    @Override
    public void updateOrders(Orders orders) {
        ordersRepository.updateOrder(orders);
    }

    @Override
    public Orders findByOrderId(int id) {
        return ordersRepository.findByOrderId(id);
    }
}
