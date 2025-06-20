package com.example.exe2update.repository;

import com.example.exe2update.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
  Optional<Order> findByOrderId(Integer orderId);

  @Query("SELECT SUM(o.totalAmount) FROM Order o")
  Double calculateTotalRevenue();

  @Query(value = """
          SELECT MONTH(order_date) AS month, SUM(total_amount) AS revenue
          FROM orders
          WHERE MONTH(order_date) BETWEEN :fromMonth AND :toMonth
            AND YEAR(order_date) = :year
          GROUP BY MONTH(order_date)
          ORDER BY MONTH(order_date)
      """, nativeQuery = true)
  List<Object[]> getRevenueByMonth(
      @Param("fromMonth") int fromMonth,
      @Param("toMonth") int toMonth,
      @Param("year") int year);

}