import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileWriter;

public class User {

    private String name;
    private String nationalID;
    private String type;
    private String password;

    private static HashMap<String,Integer> manager = new HashMap<>();
    private static HashMap<String,Integer> staff = new HashMap<>();
    private static HashMap<String,Integer> customer = new HashMap<>();
    private static List<User> user = new ArrayList<>();
    private static boolean initiated = false;

    public User(String type,String name,String nationalID,String password,boolean flag) {
        this.name = name;
        this.nationalID = nationalID;
        this.type = type;
        this.password = password;

        if (type.equals("M")) {
            if (manager.isEmpty()) {
                manager.put(nationalID, user.size());
                user.add(this);
            }
        }
        if (type.equals("S")) {
            if (staff.isEmpty() || !staff.containsKey(nationalID)) {
                staff.put(nationalID,user.size());
                user.add(this);
            }
        }
        if (type.equals("C")) {
            if (customer.isEmpty() || !customer.containsKey(nationalID)) {
                customer.put(nationalID,user.size());
                user.add(this);
            }
        }
    }

    public User(String type,String name,String nationalID,String password) {
        this(type,name,nationalID,password,true);
        try {
            StringBuilder newData = new StringBuilder();
            for (User i : user) {
                newData.append(i.type).append(" ").append(i.name).append(" ").append(i.nationalID).append(" ").append(i.password).append("\n");
            }
            FileWriter Writer = new FileWriter("UD.txt");
            Writer.write(newData.toString());
            Writer.close();
        }
        catch (IOException e) {}
    }

    public static void initiate() {
        if (!initiated) {
            try {
                File UD = new File("UD.txt");
                Scanner reader = new Scanner(UD);
                while (reader.hasNextLine()) {
                    String data = reader.nextLine();
                    String[] information = data.split(" ");
                    new User(information[0],information[1],information[2],information[3],true);
                }
                reader.close();
            }
            catch (FileNotFoundException e) {}
            initiated = true;
        }

    }

    public void remove(){
        user.remove(this);
        if (this.type.equals("S")){
            staff.remove(this.nationalID);
        }
        if (this.type.equals("C")){
            customer.remove(this.nationalID);
        }
        try {
            StringBuilder newData = new StringBuilder();
            for (User i : user) {
                newData.append(i.type).append(" ").append(i.name).append(" ").append(i.nationalID).append(" ").append(i.password).append("\n");
            }
            FileWriter Writer = new FileWriter("UD.txt");
            Writer.write(newData.toString());
            Writer.close();
        }
        catch (IOException e) {}
    }
    public String getName() {
        return this.name;
    }
    public String getNationalID() {
        return this.nationalID;
    }
    public String getPassword() {
        return this.password;
    }

    static List<User> getUser() {
        return user;
    }
    static HashMap<String,Integer> getManager() {
        return manager;
    }
    static HashMap<String,Integer> getStaff() {
        return staff;
    }
    static HashMap<String,Integer> getCustomer() {
        return customer;
    }

}
