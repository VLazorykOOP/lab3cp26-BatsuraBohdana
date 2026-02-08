import java.util.*;

// ==========================================
// 1. ШАБЛОН STATE
// ==========================================

interface State {
    void handleRequest();
}

class NewOrderState implements State {
    @Override
    public void handleRequest() {
        System.out.println("Стан системи: Об'єкт створено. Очікування дій.");
    }
}

class ProcessingState implements State {
    @Override
    public void handleRequest() {
        System.out.println("Стан системи: Об'єкт в обробці. Виконання операцій.");
    }
}

class OrderContext {
    private State state;

    public void setState(State state) {
        this.state = state;
    }

    public void applyState() {
        state.handleRequest();
    }
}

// ==========================================
// 2. ШАБЛОН ABSTRACT FACTORY
// ==========================================

interface Button { void render(); }
interface Checkbox { void render(); }

class WinButton implements Button {
    public void render() { System.out.println("Factory: Генерація компонента Button."); }
}
class WinCheckbox implements Checkbox {
    public void render() { System.out.println("Factory: Генерація компонента Checkbox."); }
}

interface GUIFactory {
    Button createButton();
    Checkbox createCheckbox();
}

class WindowsFactory implements GUIFactory {
    public Button createButton() { return new WinButton(); }
    public Checkbox createCheckbox() { return new WinCheckbox(); }
}

// ==========================================
// 3. ШАБЛОН ADAPTER
// ==========================================

interface JSONReader {
    void readJSON();
}

class OldXMLSystem {
    void processXML() {
        System.out.println("Adapter: Виклик методу старої системи (XML).");
    }
}

class XMLToJSONAdapter implements JSONReader {
    private OldXMLSystem xmlSystem;

    public XMLToJSONAdapter(OldXMLSystem xmlSystem) {
        this.xmlSystem = xmlSystem;
    }

    @Override
    public void readJSON() {
        System.out.print("Adapter: Конвертація вхідного запиту... ");
        xmlSystem.processXML();
    }
}

// ==========================================
// ГОЛОВНИЙ КЛАС
// ==========================================

public class main3 {
    public static void main(String[] args) {
        System.out.println("--- Виконання програми ---\n");

        // Тестування State
        System.out.println("[Модуль управління станами]");
        OrderContext context = new OrderContext();
        context.setState(new NewOrderState());
        context.applyState();
        context.setState(new ProcessingState());
        context.applyState();
        
        System.out.println();

        // Тестування Abstract Factory
        System.out.println("[Модуль генерації компонентів]");
        GUIFactory factory = new WindowsFactory();
        Button btn = factory.createButton();
        Checkbox chk = factory.createCheckbox();
        btn.render();
        chk.render();

        System.out.println();

        // Тестування Adapter
        System.out.println("[Модуль сумісності інтерфейсів]");
        OldXMLSystem oldSystem = new OldXMLSystem();
        JSONReader adapter = new XMLToJSONAdapter(oldSystem);
        adapter.readJSON();
        
        System.out.println("\n--------------------------");
    }
}
