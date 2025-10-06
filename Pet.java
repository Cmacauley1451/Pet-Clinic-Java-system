import java.util.ArrayList;

public class Pet {
    private String name;
    private int age;
    private String colour;
    private double weight;

    public Pet(String name, int age, String colour, double weight){
        this.name = name;
        this.age = age;
        this.colour = colour;
        this.weight = weight;
    }

    // Getters
    public String getName(){ return name; }
    public int getAge(){ return age; }
    public String getColour(){ return colour; }
    public double getWeight(){ return weight; }

    public String speak(){
        return "Hello, I am a pet";
    }

    // Setters
    public void setName(String newName){ name = newName;}
    public void setAge (int newAge){ age = newAge;}
    public void setColour (String newColour){ colour = newColour;}
    public void setWeight ( double newWeight){ weight = newWeight;}


}
