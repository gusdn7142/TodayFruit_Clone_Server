package com.todayfruit.src.purchase;

import com.todayfruit.src.purchase.model.domain.Purchase;
import com.todayfruit.src.user.model.domain.Logout;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseDao extends JpaRepository<Purchase, Long> {



}
