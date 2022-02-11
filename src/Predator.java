//02.11
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

public class Predator extends Animal{

    // The predator's strength, if it is strong enough he can attack other predators
    private static int strength;

    public Predator (int strength, Field field, Location location, String name, int maximumTemperature, int minimumTemperature,  boolean isFemale, int maxAge, int breedingAge, double breedingProbability, int maxLitterSize, int nutritionalValue, boolean randomAge) {
        super(field, location, name, maximumTemperature, minimumTemperature,  isFemale, maxAge, breedingAge, breedingProbability, maxLitterSize, nutritionalValue, randomAge);

        this.strength = strength;
    }

    /**
     * This is what the fox does most of the time: it hunts for
     * rabbits. In the process, it might breed, die of hunger,
     * or die of old age.
     * @param newSpecies A list to return newly born foxes.
     */
    public void act(List<Species> newSpecies)
    {
        incrementAge();
        incrementHunger();
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();

        ArrayList<Animal> neighboringAnimals = super.getNeighboringAnimalsList(field, it);
        checkForAttack(neighboringAnimals);

        if(isAlive()) {
            if (super.canReproduce(neighboringAnimals)){
                reproduce(newSpecies);
            }
            // Move towards a source of food if found.
            Location newLocation = findFood(neighboringAnimals);
            if(newLocation == null) {
                // No food found - try to move to a free location.
                newLocation = getField().freeAdjacentLocation(getLocation());
            }
            // See if it was possible to move.
            if(newLocation != null) {
                setLocation(newLocation);
            }
            else {
                // Overcrowding.
                setDead();
            }
        }
    }

    private Location findFood(ArrayList<Animal> neighboringAnimals)
    {
        for (Animal animal : neighboringAnimals) {
            if(!(animal instanceof Predator)){
                animal.setDead();
                foodLevel += animal.getNutritionalValue();
                return animal.getLocation();
            }
        }
        // No food found
        return null;
    }

    private void checkForAttack(ArrayList<Animal> neighboringAnimals)
    {
        ArrayList<Predator> hordeMembers = new ArrayList<>();

        for (int i =0; i < neighboringAnimals.size(); i++) {
            if (neighboringAnimals.get(i) instanceof Predator) {

                Predator neighboringPredator = (Predator)neighboringAnimals.get(i);
                String nameOfInvestigatedHorde = neighboringPredator.getName();
                int totalHordeStrength =  neighboringPredator.getStrength();

                hordeMembers.add(neighboringPredator);
                for (int j =0; j < neighboringAnimals.size(); j++) {
                    if (nameOfInvestigatedHorde.equals(neighboringAnimals.get(j).getName())) {
                        Predator predatorObject = (Predator)neighboringAnimals.get(i);
                        totalHordeStrength += predatorObject.getStrength();
                        hordeMembers.add(predatorObject);
                    }
                }
                if (totalHordeStrength > strength) {
                    attackedByHorde(hordeMembers);
                    break;
                } else {
                    hordeMembers.clear();
                }

            }

        }
    }

    private void attackedByHorde(ArrayList<Predator> hordeMembers)
    {
        int foodLevelAddedToEachHordeMember = this.getNutritionalValue() / hordeMembers.size();
        for (Predator predator : hordeMembers) {
            predator.incrementFoodLevel(foodLevelAddedToEachHordeMember);
        }
        this.setDead();
    }

    protected void reproduce(List<Species> newOfThisKind)
    {
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = super.breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Predator young = new Predator(strength, field, loc, getName(), getMaximumTemperature(), getMinimumTemperature(), getIsFemale(), getMaxAge(), getBreedingAge(), getBreedingProbability(), getMaxLitterSize(),getNutritionalValue(),false);
            newOfThisKind.add(young);
        }
    }

    public void incrementFoodLevel(int value) {
        foodLevel += value;
    }

    public int getStrength() {
        return strength;
    }
}
