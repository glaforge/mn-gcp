package mngcp;

import io.micronaut.http.annotation.*;
import java.util.List;

@Controller("/fruit")
public class FruitController {

    @Get(uri="/", produces="application/json")
    public List<Fruit> index() {
        return List.of(
            new Fruit("Apple", 52),
            new Fruit("Banana", 89),
            new Fruit("Mango", 60),
            new Fruit("Strawberry", 35)
        );
    }
}

class Fruit {
    private String name;
    private int calories;

    public Fruit(String name, int calories) {
        this.name = name;
        this.setCalories(calories);
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return name + " (" + calories + ")";
    }
}