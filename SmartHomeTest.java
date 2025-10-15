// Interface 1: Controllable
interface Controllable {
    void turnOn();
    void turnOff();
    default void showStatus() {
        System.out.println("Controllable device");
    }
}

// Interface 2: Configurable
interface Configurable {
    void configure(String setting);
    default void showStatus() {
        System.out.println("Configurable device");
    }
}

// SmartAppliance class implementing both interfaces
class SmartAppliance implements Controllable, Configurable {
    private String name;
    private boolean isOn;
    private String currentSetting;
    private StringBuilder activityLog;
    
    public SmartAppliance(String name) {
        this.name = name;
        this.isOn = false;
        this.currentSetting = "Default";
        this.activityLog = new StringBuilder();
    }
    
    // Implementing Controllable methods
    @Override
    public void turnOn() {
        isOn = true;
        String log = name + " is now ON\n";
        activityLog.append(log);
        System.out.println(log.trim());
    }
    
    @Override
    public void turnOff() {
        isOn = false;
        String log = name + " is now OFF\n";
        activityLog.append(log);
        System.out.println(log.trim());
    }
    
    // Implementing Configurable methods
    @Override
    public void configure(String setting) {
        this.currentSetting = setting;
        String log = name + " configured to: " + setting + "\n";
        activityLog.append(log);
        System.out.println(log.trim());
    }
    
    // Overriding configure method (overloaded version)
    public void configure(String setting, String message) {
        this.currentSetting = setting;
        String log = name + " configured successfully with setting: " + setting + "\n";
        activityLog.append(log);
        System.out.println(log.trim());
    }
    
    // Resolving diamond problem by overriding showStatus()
    @Override
    public void showStatus() {
        // Calling both default implementations
        System.out.println("=== Status of " + name + " ===");
        Controllable.super.showStatus();
        Configurable.super.showStatus();
        System.out.println("Current State: " + (isOn ? "ON" : "OFF"));
        System.out.println("Current Setting: " + currentSetting);
        System.out.println("========================");
    }
    
    // Overloaded operate methods
    public void operate(String mode) {
        String log = name + " operating in " + mode + " mode\n";
        activityLog.append(log);
        System.out.println(log.trim());
    }
    
    public void operate(int duration) {
        String log = name + " operating for " + duration + " minutes\n";
        activityLog.append(log);
        System.out.println(log.trim());
    }
    
    // Display activity log
    public void displayActivityLog() {
        System.out.println("\n--- Activity Log for " + name + " ---");
        System.out.print(activityLog.toString());
        System.out.println("--- End of Log ---\n");
    }
    
    public String getName() {
        return name;
    }
}

// Specific appliance types
class SmartLight extends SmartAppliance {
    public SmartLight(String name) {
        super(name);
    }
}

class SmartFan extends SmartAppliance {
    public SmartFan(String name) {
        super(name);
    }
}

class SmartAC extends SmartAppliance {
    public SmartAC(String name) {
        super(name);
    }
}

// Test Program
public class SmartHomeTest {
    public static void main(String[] args) {
        System.out.println("=== SMART HOME AUTOMATION SYSTEM ===\n");
        
        // Step 1: Create several SmartAppliance objects
        SmartLight light = new SmartLight("Smart Light");
        SmartFan fan = new SmartFan("Smart Fan");
        SmartAC ac = new SmartAC("Smart AC");
        
        // Step 2: Store them in ArrayList of Controllable type (polymorphism)
        java.util.ArrayList<Controllable> appliances = new java.util.ArrayList<>();
        appliances.add(light);
        appliances.add(fan);
        appliances.add(ac);
        
        System.out.println("--- Demonstrating Polymorphism ---");
        System.out.println("All appliances stored as Controllable type\n");
        
        // Step 3: Use loop to perform actions
        System.out.println("--- Performing Actions on All Appliances ---\n");
        for (Controllable appliance : appliances) {
            SmartAppliance smartApp = (SmartAppliance) appliance;
            
            // Turn on the appliance
            appliance.turnOn();
            
            // Configure the appliance
            if (smartApp instanceof SmartLight) {
                ((Configurable) appliance).configure("Brightness: 80%");
                smartApp.operate("Night");
                smartApp.operate(120);
            } else if (smartApp instanceof SmartFan) {
                ((Configurable) appliance).configure("Speed: High");
                smartApp.operate("Turbo");
                smartApp.operate(60);
            } else if (smartApp instanceof SmartAC) {
                ((Configurable) appliance).configure("Temperature: 22°C");
                smartApp.operate("Cooling");
                smartApp.operate(180);
            }
            
            System.out.println();
        }
        
        // Step 4: Demonstrate diamond problem resolution
        System.out.println("\n--- Demonstrating Diamond Problem Resolution ---\n");
        for (Controllable appliance : appliances) {
            SmartAppliance smartApp = (SmartAppliance) appliance;
            smartApp.showStatus();
        }
        
        // Turn off all appliances
        System.out.println("--- Turning Off All Appliances ---\n");
        for (Controllable appliance : appliances) {
            appliance.turnOff();
            System.out.println();
        }
        
        // Step 5: Display activity logs
        System.out.println("\n=== ACTIVITY LOGS ===");
        for (Controllable appliance : appliances) {
            SmartAppliance smartApp = (SmartAppliance) appliance;
            smartApp.displayActivityLog();
        }
        
        System.out.println("=== END OF SMART HOME AUTOMATION TEST ===");
    }
}
