package main;

/**
 * Represents a car part produced by a machine.
 */
public class CarPart {
    private Integer id;
    private String name;
    private Double weight;
    private Boolean isdefective;

    /**
     * Constructs a CarPart with the specified attributes.
     *
     * @param id        The unique ID of the car part.
     * @param name      The official name of the car part.
     * @param weight    The weight of the car part.
     * @param defective Indicates whether the car part is defective or not.
     */
    public CarPart(int id, String name, double weight, boolean isdefective) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.isdefective = isdefective;
    }

    /**
     * Gets the ID of the car part.
     *
     * @return The ID of the car part.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the car part.
     *
     * @param id The new ID to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the official name of the car part.
     *
     * @return The official name of the car part.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the official name of the car part.
     *
     * @param name The new official name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the weight of the car part.
     *
     * @return The weight of the car part.
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Sets the weight of the car part.
     *
     * @param weight The new weight to set.
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     * Checks if the car part is defective.
     *
     * @return True if the car part is defective, false otherwise.
     */
    public boolean isDefective() {
        return isdefective;
    }

    /**
     * Sets the defective status of the car part.
     *
     * @param defective The new defective status to set.
     */
    public void setDefective(boolean isdefective) {
        this.isdefective = isdefective;
    }

    /**
     * Returns the string representation of the car part (its name).
     *
     * @return The part name.
     */
    public String toString() {
        return this.getName();
    }
}