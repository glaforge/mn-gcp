package mngcp;

import io.micronaut.http.annotation.*;
import io.micronaut.core.annotation.Introspected;
import java.util.List;

@Controller("/vegetable")
public class VegetableController {

    @Get(uri="/", produces="application/json")
    public List<Vegetable> index() {
        return List.of(
            new Vegetable("Potato", 80),
            new Vegetable("Tomato", 20),
            new Vegetable("Cucumber", 15),
            new Vegetable("Carrot", 42)
        );
    }
}

@Introspected
class Vegetable {
    private String name;
    private int calories;

    public Vegetable(String name, int calories) {
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