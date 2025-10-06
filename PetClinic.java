import java.util.*;
import java.io.*;

public class PetClinic {
    Scanner scan = new Scanner(System.in);
    ClinicManager clinicManager = new ClinicManager();
    private String clinicName;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);


        PetClinic myClinic = new PetClinic();
        myClinic.chooseClinic();
        myClinic.menu();
        String petsFile = myClinic.clinicName.replaceAll("\\s+", "") + "_Pets.txt";
        myClinic.clinicManager.savePetsToFile(petsFile);
    }

    private void chooseClinic(){
        try {
            System.out.println("which clinic would you like to manage?");
            System.out.println("1. Ulster Pet Clinic\t 2. University Pet Clinic");
            int clinicChoice = scan.nextInt();
            scan.nextLine();

            boolean valid = false;
            while (!valid) {
                if (clinicChoice == 1) {
                    clinicName = "Ulster Pet Clinic";
                    valid = true;
                }
                else if (clinicChoice == 2){
                    clinicName = "University Pet Clinic";
                    valid = true;
                }
                else System.out.println("Invalid selection try again");
                String petsFile = clinicName.replaceAll("\\s+", "") + "_Pets.txt";
                clinicManager.loadPetsFromFile(petsFile);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // menu system so user can clearly choose what they want to do.
    public void menu(){
        try {
            String cont = "n";
            while (cont.equalsIgnoreCase("n")) {
                System.out.println("What would you like to do?" + "\n1. Add a Pet\t2. Delete a Pet" +
                        "\n3. Modify Pet Details\t4. View report" +
                        "\n5. View all Pets\t6. Search pet" +
                        "\n7. View pets across all clinics" +
                        "\n\t8.Save and Quit Program");
                System.out.print("Enter the number your choice: ");
                int menuChoice = scan.nextInt();
                scan.nextLine();

                // Menu Choices
                if (menuChoice == 1) { addPet(); }
                else if (menuChoice == 2){ deletePet(); }
                else if (menuChoice == 3){ modifyPet(); }
                else if (menuChoice == 4){ printReport(); }
                else if (menuChoice == 5){ viewAllPets(); }
                else if (menuChoice == 6){ searchPet(); }
                else if (menuChoice == 7){ viewClinics(); }
                else if (menuChoice == 8){
                    System.out.println("Are you sure you want to quit? 'y' for yes");
                    cont = scan.nextLine();
                }

                else System.out.println("Error: invalid selection");
            }
            // This catch block will also catch mismatch Exceptions within each function in the menu, returning the user to the menu instead of
            // crashing the program.
        } catch (InputMismatchException ie){
            System.out.println("Error: must input the number of the program you would like to run.\nReturning to menu...");
        }
    }
    //Program to add a pet to the clinic system
    private void addPet(){
        try {
            // Pets details are entered before pet type is declared to save lines of code, improving code efficiency.
            System.out.println(" ");
            // Space added for readability as this is a new function.
            System.out.println("Please enter the information for the pet being added, leaving any of these fields " +
                    "empty will result in the pet's details not being saved beyond the current session.");


            String name;
            int age = -1;
            String colour;
            double weight = -1;

            // Validate name
            while (true) {
                System.out.println("What is the pet's name?:");
                name = scan.nextLine().trim();
                if (!name.isEmpty()) break;
                System.out.println("Error: Name cannot be empty.");
            }

            // Validate age
            while (true) {
                System.out.println("What is the pet's age?:");
                if (scan.hasNextInt()) {
                    age = scan.nextInt();
                    if (age >= 0) {
                        scan.nextLine();
                        break;
                    }
                } else {
                    scan.next(); // discard invalid input
                }
                System.out.println("Error: Please enter a valid positive number for age.");
            }

            // Validate colour
            while (true) {
                System.out.println("What colour is this pet?");
                colour = scan.nextLine().trim();
                if (!colour.isEmpty()) break;
                System.out.println("Error: Colour cannot be empty.");
            }

            // Validate weight
            while (true) {
                System.out.println("What is the pet's weight in KG? (Please use numbers only)");
                if (scan.hasNextDouble()) {
                    weight = scan.nextDouble();
                    if (weight >= 0) {
                        scan.nextLine();
                        break;
                    }
                } else {
                    scan.next(); // discard invalid input
                }
                System.out.println("Error: Please enter a valid positive number for weight.");
            }

            // In this example the list includes Dogs and Cats, this code is easily expanded upon to add other animals
            // such as birds or rabbits.
            String choice;

            while (true) {
                System.out.println("What type of pet is it? Choose from the list below: \n Dog | Cat ");
                choice = scan.nextLine().trim();

                if (choice.equalsIgnoreCase("dog") || choice.equalsIgnoreCase("cat")) {
                    break;
                } else {
                    System.out.println("Error: Please enter 'Dog' or 'Cat'.");
                }
            }

            if (choice.equalsIgnoreCase("dog")) {

                String breed;
                while (true) {
                    System.out.println("What breed is this dog?");
                    breed = scan.nextLine().trim();

                    if (!breed.isEmpty()) break;
                    else System.out.println("Error: Breed cannot be empty.");
                }

                Dog dog = new Dog(name, age, colour, weight, breed);
                System.out.println(dog.speak());

                clinicManager.addPet(dog);
            }
            else if (choice.equalsIgnoreCase("cat")){

                String breed;
                while (true) {
                    System.out.println("What breed is this cat?");
                    breed = scan.nextLine().trim();

                    if (!breed.isEmpty()) break;
                    else System.out.println("Error: Breed cannot be empty.");
                }

                Cat cat = new Cat(name, age, colour, weight, breed);
                System.out.println(cat.speak());
                clinicManager.addPet(cat);
            }
            else System.out.println("Error: invalid selection");
        }
        catch (InputMismatchException ie){
            System.out.println("Error: Incorrect input for question, e.g: Age must be a number");
            addPet();
        }
        catch (Exception e){
            System.out.println("Error:" + e);
        }
    }

    private void viewAllPets() {
        try {
            if (clinicManager.getAllPets().isEmpty()) {
                System.out.println("No pets in the clinic.");
            } else {
                System.out.println("\t\t\t All pets in " + clinicName); // User friendly formatting
                System.out.println("---------------------------------------------------------------------");
                for (Pet pet : clinicManager.getAllPets()) {
                    System.out.println(pet.getName() + " - " + pet.getAge() + " years old - Colour: " +
                            pet.getColour() + " - Weight: " + pet.getWeight() + "kg");
                }
                System.out.println("---------------------------------------------------------------------");
            }
            System.out.println("Press enter to return to menu"); // Allows user to return to menu only when they are ready.
            scan.nextLine();
            System.out.println("Returning to menu...:");
            Thread.sleep(1000);
        }
        catch (InterruptedException e){
            throw new RuntimeException(e);
        }
    }

    private void deletePet() {
        System.out.println("\nEnter the name of the pet you want to delete:");
        String nameToDelete = scan.nextLine();

        // Search for the pet by name
        Pet petToDelete = null;
        for (Pet pet : clinicManager.getAllPets()) {
            if (pet.getName().equalsIgnoreCase(nameToDelete)) {
                petToDelete = pet;
                break;
            }
        }

        // If the pet is found, delete it
        if (petToDelete != null) {
            clinicManager.getAllPets().remove(petToDelete);
            System.out.println(petToDelete.getName() + " has been removed from the clinic.");
        } else {
            System.out.println("Error: Pet with the name \"" + nameToDelete + "\" not found.");
        }
    }


    private void modifyPet() {
        System.out.println("\nEnter the name of the pet you want to modify:");
        String nameToModify = scan.nextLine();

        // Search for the pet by name
        Pet petToModify = null;
        for (Pet pet : clinicManager.getAllPets()) {
            if (pet.getName().equalsIgnoreCase(nameToModify)) {
                petToModify = pet;
                break;
            }
        }

        // If the pet is found, proceed to modify it
        if (petToModify != null) {
            System.out.println("Pet found: " + petToModify.getName());
            System.out.println("What would you like to modify?");
            System.out.println("1. Modify Name\n2. Modify Age\n3. Modify Colour\n4. Modify Weight\n5. Modify Breed\n6. Exit");

            int modifyChoice = scan.nextInt();
            scan.nextLine();

            // I have used a SWITCH here instead of an if statement as all inputs are numbers only and error
            // handling is in place for this, I could've done the same thing for the menu but decided against
            // it in case I want the inputs to be something like 'add' for example.
            switch (modifyChoice) {
                case 1:
                    System.out.println("Enter the new name:");
                    String newName = scan.nextLine();
                    petToModify.setName(newName);
                    break;
                case 2:
                    System.out.println("Enter the new age:");
                    int newAge = scan.nextInt();
                    petToModify.setAge(newAge);
                    break;
                case 3:
                    System.out.println("Enter the new colour:");
                    String newColour = scan.nextLine();
                    petToModify.setColour(newColour);
                    break;
                case 4:
                    System.out.println("Enter the new weight:");
                    double newWeight = scan.nextDouble();
                    petToModify.setWeight(newWeight);
                    break;
                case 5:
                    if (petToModify instanceof Dog) {
                        System.out.println("Enter the new breed for the dog:");
                        String newBreed = scan.nextLine();
                        ((Dog) petToModify).setBreed(newBreed);
                    } else if (petToModify instanceof Cat) {
                        System.out.println("Enter the new breed for the cat:");
                        String newBreed = scan.nextLine();
                        ((Cat) petToModify).setBreed(newBreed);
                    }
                    break;
                case 6:
                    System.out.println("Exiting modification.");
                    break;
                default:
                    System.out.println("Invalid choice."); // print invalid if user doesn't enter something valid.
            }

            System.out.println("Pet details updated successfully!");
        } else {
            System.out.println("Error: Pet with the name \"" + nameToModify + "\" not found.");
        }
    }

    // Looks through each colour individually, adds 1 to the count for every time 1 colour is found, highest count found is outputted.
    private String getMostFrequentColor(List<String> colors) {
        if (colors.isEmpty()) return null;

        String mostCommon = null;
        int maxCount = 0;

        for (String color : colors) {
            int count = 0;
            for (String compareColor : colors) {
                if (color.equals(compareColor)) {
                    count++;
                }
            }

            if (count > maxCount) {
                maxCount = count;
                mostCommon = color;
            }
        }

        return mostCommon;
    }

    private void printReport() {
        int dogCount = 0;
        int catCount = 0;

        ArrayList<String> dogColors = new ArrayList<>();
        ArrayList<String> catColors = new ArrayList<>();

        for (Pet pet : clinicManager.getAllPets()) {
            if (pet instanceof Dog) {
                dogCount++;
                dogColors.add(pet.getColour().toLowerCase());
            } else if (pet instanceof Cat) {
                catCount++;
                catColors.add(pet.getColour().toLowerCase());
            }
        }

        String mostCommonDogColor = getMostFrequentColor(dogColors);
        String mostCommonCatColor = getMostFrequentColor(catColors);

        StringBuilder report = new StringBuilder();
        report.append("\n\t\t").append(clinicName).append(" - Pet Report\n");
        report.append("------------------------------------------\n");
        report.append("Total number of dogs registered: ").append(dogCount).append("\n");
        report.append("Total number of cats registered: ").append(catCount).append("\n");
        report.append("Total pets in clinic: ").append(dogCount + catCount).append("\n");
        // Ternary operators used for outputting N/A if there isn't a most common dog colour.
        report.append("Most common dog color: ").append(mostCommonDogColor != null ? mostCommonDogColor : "N/A").append("\n");
        report.append("Most common cat color: ").append(mostCommonCatColor != null ? mostCommonCatColor : "N/A").append("\n");
        report.append("------------------------------------------\n");

        // Save the report to the appropriate clinic
        String filename = clinicName.replaceAll("\\s+", "") + "_Details.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(report.toString());
            System.out.println("Report saved to file: " + filename);
        } catch (IOException e) {
            System.out.println("Error saving report: " + e.getMessage());
        }

        // Read the report back from the file and print it
        System.out.println("\nReading report from file...\n");
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading report: " + e.getMessage());
        }

        System.out.println("Press enter to return to menu...");
        scan.nextLine();
    }

    private void displayPet(Pet pet) {
        System.out.println("\nPet Details:");
        System.out.println("Name: " + pet.getName());
        System.out.println("Age: " + pet.getAge());
        System.out.println("Colour: " + pet.getColour());
        System.out.println("Weight: " + pet.getWeight() + "kg");
        if (pet instanceof Dog) {
            System.out.println("Breed: " + ((Dog) pet).getBreed());
            System.out.println("Type: Dog");
        } else if (pet instanceof Cat) {
            System.out.println("Breed: " + ((Cat) pet).getBreed());
            System.out.println("Type: Cat");
        }
    }

    private void searchPet() {
        try {
            System.out.println("Would you like to search by:\n1. Name\n2. Colour\n3. Return to menu");
            int choice = scan.nextInt();
            scan.nextLine();

            boolean found = false;

            if (choice == 1) {
                System.out.print("Enter pet name to search: ");
                String name = scan.nextLine();

                for (Pet pet : clinicManager.getAllPets()) {
                    if (pet.getName().equalsIgnoreCase(name)) {
                        displayPet(pet);
                        System.out.println("Pet says: " + pet.speak());
                        found = true;
                    }
                }
            } else if (choice == 2) {
                System.out.print("Enter pet colour to search: ");
                String colour = scan.nextLine();
                for (Pet pet : clinicManager.getAllPets()) {
                    if (pet.getColour().equalsIgnoreCase(colour)) {
                        displayPet(pet);
                        System.out.println(pet.speak());
                        found = true;
                    }
                }

            } else if (choice == 3) found = true;
            else System.out.println("Invalid choice.");

            if (!found) System.out.println("No matching pets found.");

            System.out.println("Press Enter to return to the menu...");
            scan.nextLine();
        } catch (InputMismatchException ie){
            System.out.println("Error: Input must be the number; 1. Name | 2. Colour\nPlease try again..");
            scan.nextLine();
            searchPet();
        }
    }


    private void viewClinics() {
        String[] clinicFiles = {"UlsterPetClinic_Pets.txt", "UniversityPetClinic_Pets.txt"};
        for (String file : clinicFiles) {
            System.out.println("\n--- Pets from " + file.replace("_Pets.txt", " ").replaceAll("([a-z])([A-Z])", "$1 $2") + " ---"); // Regex code referenced in design report.

            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                boolean hasPets = false;
                while ((line = br.readLine()) != null) {
                    hasPets = true;
                    String[] data = line.split(",");
                    if (data.length == 6) {
                        String name = data[0];
                        String age = data[1];
                        String colour = data[2];
                        String weight = data[3];
                        String breed = data[4];
                        String type = data[5];
                        System.out.println(name + " - " + age + " years - " + colour + " - " + weight + "kg - " + breed + " - " + type);
                    }
                }
                if (!hasPets) System.out.println("No pets in this clinic.");
            } catch (IOException e) {
                System.out.println("Error reading from " + file + ": " + e.getMessage());
            }
        }

        System.out.println("\nPress enter to return to menu...");
        scan.nextLine();
    }

}


