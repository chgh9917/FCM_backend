package fcm.fcm.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "FOOD")
public class FoodEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String foodName;

    private int foodKal;

    public FoodEntity() {}

    // 생성자
    public FoodEntity(String foodName, int foodKal) {
        this.foodName = foodName;
        this.foodKal = foodKal;
    }

}
