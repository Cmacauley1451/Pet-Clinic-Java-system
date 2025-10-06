import java.io.*;
import java.util.ArrayList;

public class ClinicManager {

    private ArrayList<Pet> allPets = new ArrayList<Pet>();

    public void addPet(Pet pet) {
        allPets.add(pet);
    }

    public ArrayList<Pet> getAllPets() {
        return allPets;
    }


    public void loadPetsFromFile(String filename) {
        allPets.clear(); // Clear any existing data before loading
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 6) {
                    String name = data[0];
                    int age = Integer.parseInt(data[1]);
                    String colour = data[2];
                    double weight = Double.parseDouble(data[3]);
                    String breed = data[4];
                    String type = data[5];

                    if (type.equalsIgnoreCase("dog")) {
                        allPets.add(new Dog(name, age, colour, weight, breed));
                    } else if (type.equalsIgnoreCase("cat")) {
                        allPets.add(new Cat(name, age, colour, weight, breed));
                    }
                }
            }
            System.out.println("Pet data loaded from: " + filename);
        } catch (FileNotFoundException e) {
            System.out.println("No pet data found for this clinic (" + filename + "). Starting fresh.");
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public void savePetsToFile(String filename) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (Pet pet : allPets) {
                String type = pet instanceof Dog ? "dog" : "cat";
                String breed = pet instanceof Dog ? ((Dog) pet).getBreed() : ((Cat) pet).getBreed();
                bw.write(pet.getName() + "," + pet.getAge() + "," + pet.getColour() + "," + pet.getWeight() + "," + breed + "," + type);
                bw.newLine();
            }
            System.out.println("Pet data saved to: " + filename);
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }
}

