import java.util.ArrayList;
import java.util.List;

interface Chair { void sitOn(); }
interface Table { void use(); }

class ModernChair implements Chair { 
    public void sitOn() { System.out.println("Sitting on a sleek Modern Chair."); } 
}
class ModernTable implements Table { 
    public void use() { System.out.println("Using a minimalist Modern Table."); } 
}

class VictorianChair implements Chair { 
    public void sitOn() { System.out.println("Sitting on a royal Victorian Chair."); } 
}
class VictorianTable implements Table { 
    public void use() { System.out.println("Using a heavy Victorian Table."); } 
}

interface FurnitureFactory {
    Chair createChair();
    Table createTable();
}

class ModernFurnitureFactory implements FurnitureFactory {
    public Chair createChair() { return new ModernChair(); }
    public Table createTable() { return new ModernTable(); }
}

interface OrderState {
    void next(Order order);
    void printStatus();
}

class NewOrderState implements OrderState {
    public void next(Order order) { 
        order.setState(new DeliveredState()); 
    }
    public void printStatus() { System.out.println("Order Status: NEW (Processing...)"); }
}

class DeliveredState implements OrderState {
    public void next(Order order) { 
        System.out.println("Order is already delivered.");
    }
    public void printStatus() { System.out.println("Order Status: DELIVERED (Done)"); }
}

class Order {
    private OrderState state = new NewOrderState();
    public void setState(OrderState state) { this.state = state; }
    public void nextState() { state.next(this); }
    public void printStatus() { state.printStatus(); }
}

interface ModernPayment {
    void payInUSD(int amount);
}

class OldCryptoSystem {
    void payWithBitcoin(double btc) {
        System.out.printf("Legacy System: Successfully processed %.5f BTC payment.\n", btc);
    }
}

class CryptoAdapter implements ModernPayment {
    private OldCryptoSystem oldSystem;

    public CryptoAdapter(OldCryptoSystem system) { 
        this.oldSystem = system; 
    }

    @Override
    public void payInUSD(int amount) {
        double btcAmount = amount / 60000.0;
        oldSystem.payWithBitcoin(btcAmount);
    }
}

public class Main {
    public static void main(String[] args) {
        System.out.println("--- 1. Abstract Factory Test ---");
        FurnitureFactory factory = new ModernFurnitureFactory();
        Chair chair = factory.createChair();
        Table table = factory.createTable();
        chair.sitOn();
        table.use();

        System.out.println("\n--- 2. State Pattern Test ---");
        Order order = new Order();
        order.printStatus();
        order.nextState();
        order.printStatus();

        System.out.println("\n--- 3. Adapter Pattern Test ---");
        OldCryptoSystem legacySystem = new OldCryptoSystem();
        ModernPayment payment = new CryptoAdapter(legacySystem);
        payment.payInUSD(3000); 
    }
}
