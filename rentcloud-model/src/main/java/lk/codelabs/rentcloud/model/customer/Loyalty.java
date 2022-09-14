package lk.codelabs.rentcloud.model.customer;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "loyaltyPoint")
@Data
public class Loyalty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int transactionId;

    @ManyToOne
    @JoinColumn
    Customer customer;
    int point;
    LocalDateTime expireDate;
}
