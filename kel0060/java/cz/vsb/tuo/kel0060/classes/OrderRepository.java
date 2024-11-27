package cz.vsb.tuo.kel0060.classes;

import Entities.OrderSystem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

/**
 * A generic repository for managing Order entities.
 */
public class OrderRepository {
    private final EntityManagerFactory emf;
    private final EntityManager em;

    public OrderRepository() {
        this.emf = Persistence.createEntityManagerFactory("OrdersPU");
        this.em = emf.createEntityManager();
    }

    public void addOrder(OrderSystem order) {
        em.getTransaction().begin();
        em.persist(order);
        em.getTransaction().commit();
    }

    public OrderSystem getOrderById(Long id, Class<? extends OrderSystem> orderClass) {
        return em.find(orderClass, id);
    }

    public List<? extends OrderSystem> getAllOrders(Class<? extends OrderSystem> orderClass) {
        return em.createQuery("FROM " + orderClass.getSimpleName(), orderClass).getResultList();
    }


    public void close() {
        em.close();
        emf.close();
    }
}