package fcm.fcm.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "FOOD")
public class FoodEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String foodName;

    private int foodKal;

    private Long userId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    public FoodEntity() {}

    // 생성자
    public FoodEntity(String foodName, int foodKal) {
        this.foodName = foodName;
        this.foodKal = foodKal;
    }

}
