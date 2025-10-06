public class Cat extends Pet{
    private String breed;
    Pet petDetails;


    public Cat(String name, int age, String colour, double weight, String breed) {
        super(name, age, colour, weight);
        this.breed = breed;

        petDetails = new Pet(name, age, colour, weight);
    }

    @Override
    public String speak(){

        return "Meow! I am " + getName() + " a " + getAge() + " year old " + breed;
    }

    public String getBreed(){
        return breed;
    }
    public void setBreed(String newBreed){ breed = newBreed;}

}
