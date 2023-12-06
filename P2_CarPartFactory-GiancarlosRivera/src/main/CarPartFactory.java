package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import data_structures.HashTableSC;
import data_structures.BasicHashFunction;
import data_structures.ArrayList;
import data_structures.LinkedStack;
import interfaces.List;
import interfaces.Stack;
import interfaces.Map;
import interfaces.Queue;
import interfaces.Entry;

public class CarPartFactory {

	private List<PartMachine> machines;
    private Stack<CarPart> production;
    private Map<Integer, CarPart> partCatalog;
    private Map<Integer, List<CarPart>> inventory;
    private List<Order> orders;
    private Map<Integer, Integer> defectives;

    public CarPartFactory(String orderPath, String partsPath) throws IOException {
    	this.machines = new ArrayList<PartMachine>();
    	this.production = new LinkedStack<CarPart>();
        this.partCatalog = new HashTableSC<>(20, new BasicHashFunction());
        this.inventory = new HashTableSC<>(20, new BasicHashFunction());
        this.orders = new ArrayList<>();
        this.defectives = new HashTableSC<>(20, new BasicHashFunction());
        
        setupOrders(orderPath);
        setupMachines(partsPath);
        
        setupCatalog();
        setupInventory();
    }
    public List<PartMachine> getMachines() {
    	return machines;

    }
    public void setMachines(List<PartMachine> machines) {
    	this.machines = machines;

    }
    public Stack<CarPart> getProductionBin() {
    	return production;

    }
    public void setProductionBin(Stack<CarPart> production) {
    	this.production = production;

    }
    public Map<Integer, CarPart> getPartCatalog() {
    	return partCatalog;

    }
    public void setPartCatalog(Map<Integer, CarPart> partCatalog) {
    	this.partCatalog = partCatalog;
    }
    public Map<Integer, List<CarPart>> getInventory() {
    	return inventory;

    }
    public void setInventory(Map<Integer, List<CarPart>> inventory) {
    	this.inventory = inventory;
    }
    public List<Order> getOrders() {
    	return orders;

    }
    public void setOrders(List<Order> orders) {
    	this.orders = orders;

    }
    public Map<Integer, Integer> getDefectives() {
    	return defectives;
    }
    public void setDefectives(Map<Integer, Integer> defectives) {
    	this.defectives = defectives;

    }

    public void setupOrders(String path) throws IOException {
    	 try (BufferedReader br = new BufferedReader(new FileReader("input/orders.csv"))) {
    	        String line;
    	        boolean firstLine = true; // Flag to skip the header

    	        while ((line = br.readLine()) != null) {
    	            if (firstLine) {
    	                firstLine = false;
    	                continue; // Skip the header
    	            }

    	            // Split the line into parts based on commas
    	            String[] parts = line.split(",");

    	            // Extract information from the parts array
    	            int orderId = Integer.parseInt(parts[0]);
    	            String customerName = parts[1];
    	            String requestedPartsString = parts[2];

    	            // Parse the requested parts string into a Map<Integer, Integer>
    	            Map<Integer, Integer> requestedParts = parseRequestedParts(requestedPartsString);

    	            // Create an Order instance and add it to the list of orders
    	            Order order = new Order(orderId, customerName, (interfaces.Map<Integer, Integer>) requestedParts, false); // Initially, all orders are unfulfilled
    	            orders.add(order);
    	        }
    	    } catch (IOException | NumberFormatException e) {
    	        e.printStackTrace(); // Handle exceptions appropriately based on your requirements
    	    }
    	}

    	// Helper method to parse the requested parts string into a Map<Integer, Integer>
    	private Map<Integer, Integer> parseRequestedParts(String requestedPartsString) {
    		 Map<Integer, Integer> requestedParts = new HashTableSC<>(20, new BasicHashFunction());

    		    // Remove parentheses and split the requested parts string into tuples based on "-"
    		    String[] tuples = requestedPartsString.replace("(", "").replace(")", "").split("-");

    		    for (String tuple : tuples) {
    		        // Split each tuple into partId and count based on space
    		        String[] pair = tuple.trim().split(" ");

    		        // Extract partId and count
    		        int partId = Integer.parseInt(pair[0]);
    		        int count = Integer.parseInt(pair[1]);

    		        // Add the partId and count to the map
    		        requestedParts.put(partId, count);
    		    }

    		    return requestedParts;
    		}
    public void setupMachines(String path) throws IOException {
    	 try (BufferedReader br = new BufferedReader(new FileReader(path))) {
             String line;
             boolean firstLine = true;

             while ((line = br.readLine()) != null) {
                 if (firstLine) {
                     firstLine = false;
                     continue;
                 }

                 String[] parts = line.split(",");
                 int partId = Integer.parseInt(parts[0]);
                 String partName = parts[1];
                 double weight = Double.parseDouble(parts[2]);
                 double weightError = Double.parseDouble(parts[3]);
                 int chanceDefective = Integer.parseInt(parts[5].trim());

                 CarPart part = new CarPart(partId, partName, weight, false);
                 partCatalog.put(partId, part);

                 int period = Integer.parseInt(parts[4]);
                 PartMachine machine = new PartMachine(partId, part, period, weightError, chanceDefective);
                 machines.add(machine);
             }
         } catch (IOException | NumberFormatException e) {
             e.printStackTrace();
         }
     }
    public void setupCatalog() {
    	try (BufferedReader br = new BufferedReader(new FileReader("input/parts.csv"))) {
            String line;
            boolean firstLine = true; // Flag to skip the header

            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue; // Skip the header
                }

                // Split the line into parts based on commas
                String[] parts = line.split(",");

                // Extract information from the parts array
                int partId = Integer.parseInt(parts[0]); // Assuming the first column is the part ID
                String partName = parts[1]; // Assuming the second column is the part name
                double weight = Double.parseDouble(parts[2]); // Assuming the third column is the weight
                @SuppressWarnings("unused")
				double weightError = Double.parseDouble(parts[3]); // Assuming the fourth column is the weight error

                // Create a CarPart instance and add it to the partCatalog map
                CarPart carPart = new CarPart(partId, partName, weight, false); // Initially, all parts are not defective
                partCatalog.put(partId, carPart);
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace(); // Handle exceptions appropriately based on your requirements
        }
    }
    public void setupInventory() {
    	for (CarPart carPart : partCatalog.getValues()) {
            int partId = carPart.getId();
            inventory.put(partId, new ArrayList<>()); // Initialize an empty list for each partId
        }
    }
    public void storeInInventory() {
    	while (!production.isEmpty()) {
            CarPart part = production.pop();
            if (part != null) {
                if (!part.isDefective()) {
                    inventory.get(part.getId()).add(part);
                }
                // Remove the defective part from the production bin
                // This ensures that defective parts are not added to inventory
            }
        }
    	}
    public void runFactory(int days, int minutes) {
    	 for (int day = 0; day < days; day++) {
    	        System.out.println("Day " + (day + 1));

    	        for (int minute = 0; minute < minutes; minute++) {
    	            // Iterate over machines to run them for each minute
    	            for (PartMachine machine : machines) {
    	                runMachineForMinute(machine);
    	            }
    	        }

    	        // At the end of the day
    	        // 1. Empty conveyor belts
    	        emptyConveyorBelts();
    	        
    	        // 2. Store items in inventory
    	        storeInInventory();
    	        
    	        // 3. Process orders
    	        processOrders();
    	    }

    	    // After all days have passed, generate a report
    	    generateReport();
    	}

    	// Helper method to run a machine for a minute
    	private void runMachineForMinute(PartMachine machine) {
    	    Queue<CarPart> conveyorBelt = (Queue<CarPart>) machine.getConveyorBelt();
    	    
    	    // Check if there is something available on the machine
    	    CarPart producedPart = machine.produceCarPart();
    	    
    	    // If a part is produced, add it to the production bin
    	    if (producedPart != null) {
    	        ((interfaces.Queue<CarPart>) conveyorBelt).enqueue(producedPart);
    	    }
    	    
    	    // Tick the timer for the machine
    	    machine.tickTimer();
    	}

    	// Helper method to empty conveyor belts
    	private void emptyConveyorBelts() {
    		for (PartMachine machine : machines) {
    	        machine.getConveyorBelt().clear(); // Clear the conveyor belt
    	    }
    	}

   
    public void processOrders() {
    	 for (Order order : orders) {
    	        if (!order.isFulfilled()) {
    	            if (canFulfillOrder(order)) {
    	                fulfillOrder(order);
    	            }
    	        }
    	    }
    	}

    	// Helper method to check if the order can be fulfilled
    	private boolean canFulfillOrder(Order order) {
    		 Map<Integer, Integer> requestedParts = (Map<Integer, Integer>) order.getRequestedParts();

    		    for (Map.Entry<Integer, Integer> entry : requestedParts.entrySet()) {
    		        int partId = entry.getKey();
    		        int requiredQuantity = entry.getValue();

    		        if (!inventory.containsKey(partId) || inventory.get(partId).size() < requiredQuantity) {
    		            // If the part is not in inventory or there are not enough, the order cannot be fulfilled
    		            return false;
    		        }
    		    }

    		    return true; // All parts are available in sufficient quantity
    		}
    	
    	    // Helper method to fulfill the order
    	    private void fulfillOrder(Order order) {
    	    	Map<Integer, Integer> requestedParts = order.getRequestedParts();

    	        for (Map.Entry<Integer, Integer> entry : requestedParts.entrySet()) {
    	            int partId = entry.getKey();
    	            int requiredQuantity = entry.getValue();

    	            List<CarPart> partsList = inventory.get(partId);

    	            // Remove the required quantity of parts from inventory
    	            Iterator<CarPart> iterator = partsList.iterator();
    	            for (int i = 0; i < requiredQuantity && iterator.hasNext(); i++) {
    	                CarPart removedPart = iterator.next();
    	                
    	                // Update defective count if applicable
    	                if (defectives.containsKey(partId) && removedPart.isDefective()) {
    	                    defectives.put(partId, defectives.get(partId) + 1);
    	                }

    	                iterator.remove();
    	            }
    	        }

    	        // Update order status
    	        order.setFulfilled(true);
    	    }

    	// Helper method to count defective parts in a list
    	private int countDefective(List<CarPart> parts) {
    	    int defectiveCount = 0;
    	    for (CarPart part : parts) {
    	        if (part.isDefective()) {
    	            defectiveCount++;
    	        }
    	    }
    	    return defectiveCount;
    	}
    /**
     * Generates a report indicating how many parts were produced per machine,
     * how many of those were defective and are still in inventory. Additionally, 
     * it also shows how many orders were successfully fulfilled. 
     */
    public void generateReport() {
        String report = "\t\t\tREPORT\n\n";
        report += "Parts Produced per Machine\n";
        for (PartMachine machine : this.getMachines()) {
            report += machine + "\t(" + 
            this.getDefectives().get(machine.getPart().getId()) +" defective)\t(" + 
            this.getInventory().get(machine.getPart().getId()).size() + " in inventory)\n";
        }
       
        report += "\nORDERS\n\n";
        for (Order transaction : this.getOrders()) {
            report += transaction + "\n";
        }
        System.out.println(report);
    }

   

}