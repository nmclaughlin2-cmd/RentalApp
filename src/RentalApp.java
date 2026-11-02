public class RentalApp {
    public static void main(String[] args) {

        Vehicle regular = new Vehicle("DMW 311", "Generic Sedan");

        Car car = new Car("4JP 748", "Station Wagon", 65.00, 5);
        Truck truck = new Truck("OGX 800", "Pickup Truck", 90.00, 2.5);

        Vehicle[] vehicles = {regular, car, truck};

        System.out.println("All Vehicles: ");
        for (Vehicle vehicle : vehicles) {
            vehicle.printDetails();
            System.out.println();
        }

        System.out.println("Set price to -$10:");
        car.setDailyPrice(-10.00);
        System.out.printf("The car's current daily price is $%.2f.%n%n",
                car.getDailyPrice());

        System.out.println("Three day rental costs: ");
        System.out.printf("Car, no discount: $%.2f%n",
                car.calculateRentalCost(3));
        System.out.printf("Car, 10%% discount: $%.2f%n",
                car.calculateRentalCost(3, 10));

        System.out.printf("Truck, no discount (including insurance): $%.2f%n",
                truck.calculateRentalCost(3));
        System.out.printf("Truck, 10%% discount (including insurance): $%.2f%n",
                truck.calculateRentalCost(3, 10));

        System.out.printf("%nTotal number of vehicles: %d%n",
                Vehicle.getVehicleCount());
    }
}

class Vehicle {
    private String plateNumber;
    private String modelName;
    private double dailyPrice;

    private static int vehicleCount = 0;

    // If no price is supplied, use the required default of $50 per day.
    public Vehicle(String plateNumber, String modelName) {
        this(plateNumber, modelName, 50.00);
    }

    public Vehicle(String plateNumber, String modelName, double dailyPrice) {
        this.plateNumber = plateNumber;
        this.modelName = modelName;
        this.dailyPrice = 50.00;
        setDailyPrice(dailyPrice);
        vehicleCount++;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public String getModelName() {
        return modelName;
    }

    public double getDailyPrice() {
        return dailyPrice;
    }

    // The field is private, so changes must go through this setter.
    public void setDailyPrice(double newPrice) {
        if (newPrice > 0) {
            dailyPrice = newPrice;
        } else {
            System.out.println("Warning: daily price must be greater than $0. "
                    + "The old price was kept.");
        }
    }

    // Subclasses can override this to include additional daily charges.
    protected double getDailyRate() {
        return dailyPrice;
    }

    public double calculateRentalCost(int days) {
        return calculateRentalCost(days, 0);
    }

    public double calculateRentalCost(int days, double discountPercent) {
        if (days < 0) {
            throw new IllegalArgumentException("Days cannot be negative.");
        }
        if (discountPercent < 0 || discountPercent > 100) {
            throw new IllegalArgumentException(
                    "Discount must be between 0 and 100 percent.");
        }

        double total = getDailyRate() * days;
        return total * (1 - discountPercent / 100.0);
    }

    public void printDetails() {
        System.out.println("Vehicle");
        System.out.println("Plate number: " + plateNumber);
        System.out.println("Model: " + modelName);
        System.out.printf("Daily price: $%.2f%n", dailyPrice);
    }

    public static int getVehicleCount() {
        return vehicleCount;
    }
}

class Car extends Vehicle {
    private int numberOfSeats;

    public Car(String plateNumber, String modelName,
               double dailyPrice, int numberOfSeats) {
        super(plateNumber, modelName, dailyPrice);
        this.numberOfSeats = numberOfSeats;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    @Override
    public void printDetails() {
        System.out.println("Car");
        System.out.println("Plate number: " + getPlateNumber());
        System.out.println("Model: " + getModelName());
        System.out.printf("Daily price: $%.2f%n", getDailyPrice());
        System.out.println("Number of seats: " + numberOfSeats);
    }
}

class Truck extends Vehicle {
    private double loadCapacityTons;
    private static final double INSURANCE_PER_DAY = 20.00;

    public Truck(String plateNumber, String modelName,
                 double dailyPrice, double loadCapacityTons) {
        super(plateNumber, modelName, dailyPrice);
        this.loadCapacityTons = loadCapacityTons;
    }

    public double getLoadCapacityTons() {
        return loadCapacityTons;
    }

    @Override
    protected double getDailyRate() {
        return getDailyPrice() + INSURANCE_PER_DAY;
    }

    @Override
    public void printDetails() {
        System.out.println("Truck");
        System.out.println("Plate number: " + getPlateNumber());
        System.out.println("Model: " + getModelName());
        System.out.printf("Daily price: $%.2f%n", getDailyPrice());
        System.out.printf("Insurance per day: $%.2f%n", INSURANCE_PER_DAY);
        System.out.printf("Load capacity: %.2f tons%n", loadCapacityTons);
    }
}
