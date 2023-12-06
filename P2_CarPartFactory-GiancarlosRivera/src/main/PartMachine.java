package main;

import interfaces.Queue;

import java.util.Random;

import data_structures.ListQueue;

/**
 * Represents a machine that produces car parts on a conveyor belt.
 */
public class PartMachine {
	
	private int id;
    private CarPart p1;
    private int period;
    private double weightError;
    private int chanceOfDefective;
    private Queue<Integer> timer;
    private Queue<CarPart> conveyorBelt;
    private int totalPartsProduced;
    /**
     * Initializes a new PartMachine with the given parameters.
     *
     * @param id                The unique identifier for the machine.
     * @param p1                The prototype car part produced by the machine.
     * @param period            The production period for each car part.
     * @param weightError       The acceptable weight error for produced car parts.
     * @param chanceOfDefective The chance of producing a defective part.
     */
    public PartMachine(int id, CarPart p1, int period, double weightError, int chanceOfDefective) {
    	this.id = id;
        this.p1 = p1;
        this.period = period;
        this.weightError = weightError;
        this.chanceOfDefective = chanceOfDefective;
        this.timer = initializeTimer(period);
        this.conveyorBelt = initializeConveyorBelt();
        this.totalPartsProduced = 0;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Queue<Integer> getTimer() {
        return timer;
    }

    public void setTimer(Queue<Integer> timer) {
        this.timer = timer;
    }

    public CarPart getPart() {
        return p1;
    }

    public void setPart(CarPart part) {
        this.p1 = part;
    }

    public Queue<CarPart> getConveyorBelt() {
        return conveyorBelt;
    }

    public void setConveyorBelt(Queue<CarPart> conveyorBelt) {
        this.conveyorBelt = conveyorBelt;
    }

    public int getTotalPartsProduced() {
        return totalPartsProduced;
    }

    public void setTotalPartsProduced(int totalPartsProduced) {
        this.totalPartsProduced = totalPartsProduced;
    }

    public double getWeightError() {
        return weightError;
    }

    public void setWeightError(double weightError) {
        this.weightError = weightError;
    }

    public int getChanceOfDefective() {
        return chanceOfDefective;
    }

    public void setChanceOfDefective(int chanceOfDefective) {
        this.chanceOfDefective = chanceOfDefective;
    }
    
    /**
     * Initializes the timer queue for the machine.
     *
     * @param period The production period for each car part.
     * @return A queue representing the timer for the machine.
     */

    private Queue<Integer> initializeTimer(int period) {
        Queue<Integer> timer = new ListQueue<>();
        for (int i = period - 1; i >= 0; i--) {
            timer.enqueue(i);
        }
        return timer;
    }
    /**
     * Initializes the conveyor belt queue for the machine.
     *
     * @return A queue representing the conveyor belt for the machine.
     */
    private Queue<CarPart> initializeConveyorBelt() {
    	Queue<CarPart> conveyorBelt = new ListQueue<>();
        int initialSize = 10; // Change this to the desired initial size
        for (int i = 0; i < initialSize; i++) {
            conveyorBelt.enqueue(null);
        }
        return conveyorBelt;
    }

    /**
     * Advances the timer by one tick, simulating the passage of time.
     *
     * @return The current value of the timer after the tick.
     */
    public int tickTimer() {
        int currentValue = timer.dequeue();
        timer.enqueue(currentValue);
        return currentValue;
    }
    /**
     * Produces a car part and places it on the conveyor belt.
     *
     * @return The car part produced in this cycle, or null if no part is produced.
     */
    public CarPart produceCarPart() {
    	 CarPart Part = null;
    	 if(this.timer.front() == 0) {
    	 
    	        if(!conveyorBelt.isEmpty()) {
    	        	conveyorBelt.dequeue(); }
    	        
    	        int id = p1.getId();
    	        String name = p1.getName();
    	        Random random = new Random();
    	        double maxWeight = p1.getWeight() + weightError;
    	        double minWeight = p1.getWeight() - weightError;
    	       
    	        
    	        double weight = minWeight + random.nextDouble() * (maxWeight - minWeight);
    	        boolean isdefective = totalPartsProduced % chanceOfDefective == 0;
    	        CarPart newPart = new CarPart(id,name,weight,isdefective);
    	        this.conveyorBelt.enqueue(newPart);
    	        totalPartsProduced++;
    	        Part = newPart;
    	        }else {
    	        	this.conveyorBelt.enqueue(this.conveyorBelt.dequeue());
    	        }
    	    tickTimer();
    	    return Part;
    	    }


    /**
     * Returns a string representation of the Part Machine, including its ID, the
     * name of the produced part, and the total number of parts produced.
     *
     * @return A string representation of the Part Machine.
     */
    @Override
    public String toString() {
        return "Machine " + this.getId() + " Produced: " + this.getPart().getName() + " " + this.getTotalPartsProduced();
    }
    /**
     * Prints the content of the conveyor belt, showing the machine ID, and
     * representing parts as |P| and empty spaces as _.
     */
    public void printConveyorBelt() {
        // String we will print
        String str = "";
        // Iterate through the conveyor belt
        for(int i = 0; i < this.getConveyorBelt().size(); i++){
            // When the current position is empty
            if (this.getConveyorBelt().front() == null) {
                str = "_" + str;
            }
            // When there is a CarPart
            else {
                str = "|P|" + str;
            }
            // Rotate the values
            this.getConveyorBelt().enqueue(this.getConveyorBelt().dequeue());
        }
        System.out.println("|Machine " + this.getId() + "|" + str);
    }
}