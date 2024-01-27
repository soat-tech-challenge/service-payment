package br.com.grupo63.techchallenge.payment.gateway.payment;

import br.com.grupo63.techchallenge.common.gateway.repository.IJpaRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentJpaRepository extends JpaRepository<PaymentPersistenceEntity, Long>, IJpaRepository<PaymentPersistenceEntity> {

    @Query("SELECT p FROM PaymentPersistenceEntity p WHERE p.orderId = :orderId")
    Optional<PaymentPersistenceEntity> findByOrderId(Long orderId);

}
