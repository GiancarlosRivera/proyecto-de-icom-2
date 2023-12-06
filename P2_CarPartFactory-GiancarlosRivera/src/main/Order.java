package main;

import interfaces.Map;

public class Order {
	

    private Integer id;
    private String customerName;
    private Map<Integer, Integer> requestedParts;
    private Boolean fulfilled;

    /**
     * Constructs an Order with the specified attributes.
     *
     * @param id             The unique ID of the order.
     * @param customerName   The name of the customer making the order.
     * @param requestedParts The parts requested and the quantity needed, represented as a Map.
     * @param isFulfilled    Indicates whether the order has been fulfilled.
     */

   
    public Order(int id, String customerName, Map<Integer, Integer> requestedParts, boolean fulfilled) {

        this.id = id;
        this.customerName = customerName;
        this.requestedParts = requestedParts;
        this.fulfilled = fulfilled;

    }


    /**
     * Gets the ID of the order.
     *
     * @return The ID of the order.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the order.
     *
     * @param id The new ID to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Checks if the order is fulfilled.
     *
     * @return True if the order is fulfilled, false otherwise.
     */
    public boolean isFulfilled() {
        return fulfilled;
    }



    /**
     * Sets the fulfilled status of the order.
     *
     * @param isFulfilled The new fulfilled status to set.
     */
    public void setFulfilled(boolean fulfilled) {
        this.fulfilled = fulfilled;
    }

    /**
     * Gets the parts requested and the quantity needed.
     *
     * @return A Map representing the requested parts and their quantities.
     */
    public Map<Integer, Integer> getRequestedParts() {
        return requestedParts;
    }

    /**
     * Sets the parts requested and the quantity needed.
     *
     * @param requestedParts The new Map of requested parts and their quantities.
     */
    public void setRequestedParts(Map<Integer, Integer> requestedParts) {
        this.requestedParts = requestedParts;
    }

    /**
     * Gets the name of the customer making the order.
     *
     * @return The name of the customer.
     */
    public String getCustomerName() {
        return customerName;
    }
    
    /**
     * Sets the name of the customer making the order.
     *
     * @param customerName The new name of the customer.
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }


    /**
     * Returns the order's information in the following format: {id} {customer name} {number of parts requested} {isFulfilled}
     */
    @Override
    public String toString() {
        return String.format("%d %s %d %s", this.getId(), this.getCustomerName(), this.getRequestedParts().size(), (this.isFulfilled())? "FULFILLED": "PENDING");
    }

    
    
}
