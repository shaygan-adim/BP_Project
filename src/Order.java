import javax.swing.text.html.parser.Entity;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Order {
    private int orderID;
    private User customer;
    private List<Product> order;

    private static int counter;
    private static HashMap<Integer,Order> ordersByOrderID = new HashMap<>();
    private static boolean initiated = false;
    static {
        File myDirectory = new File(System.getProperty("user.dir"));
        File[] a = myDirectory.listFiles();
        boolean flag = false;
        for (File i : a) {
            String fileName = i.getName();
            if (fileName.equals("O.txt")) {
                flag = true;
            }
        }
        if (!flag) {
            try {
                FileWriter Writer = new FileWriter("O.txt");
                Writer.write("0\n");
                Writer.close();
            }
            catch (IOException e) {}
            counter = 0;
        }
        else {
            try {
                File UD = new File("O.txt");
                Scanner reader = new Scanner(UD);
                String data = reader.nextLine();
                counter = Integer.valueOf(data);
                reader.close();
            }
            catch (FileNotFoundException e) {}

        }
    }

    private Order(User customer, List<Product> order,int id) {
        this.customer = customer;
        this.order = order;
        this.orderID = id;

        ordersByOrderID.put(this.orderID,this);
    }

    public Order(User customer, List<Product> order) {
        this(customer,order,counter+1);
        counter++;

        try {
            File UD = new File("O.txt");
            Scanner reader = new Scanner(UD);
            reader.nextLine();
            StringBuilder data= new StringBuilder(counter + "\n");
            while (reader.hasNextLine()) {
                data.append(reader.nextLine()).append("\n");
            }
            reader.close();
            data.append(this.orderID).append(" ").append(this.customer.getNationalID()).append(" ");
            for (Product p : order) {
                data.append(p.getProductID()).append(" ");
            }
            try {
                FileWriter Writer = new FileWriter("O.txt");
                Writer.write(data.toString());
                Writer.close();
            }
            catch (IOException e) {}
        }
        catch (FileNotFoundException e) {}
    }

    public static void initiate() {
        if (!initiated) {
            initiated = true;
            try {
                File UD = new File("O.txt");
                Scanner reader = new Scanner(UD);
                reader.nextLine();
                while (reader.hasNextLine()) {
                    String[] information = reader.nextLine().split(" ");
                    List<Product> ORDER = new ArrayList<>();
                    for (int i = 2 ; i<information.length ; i++) {
                        ORDER.add(Product.getProductByID().get(Integer.valueOf(information[i])));
                    }
                    new Order(User.getUser().get(User.getCustomer().get(information[1])),ORDER,Integer.valueOf(information[0]));
                }
                reader.close();
            }
            catch (FileNotFoundException e) {}
        }
    }

    public static void removeOrder(int orderid) {
        try {
            File UD = new File("O.txt");
            Scanner reader = new Scanner(UD);
            reader.nextLine();
            StringBuilder data= new StringBuilder(counter + "\n");
            while (reader.hasNextLine()) {
                String a = reader.nextLine();
                if (a.substring(0,a.indexOf(" ")).equals(String.valueOf(orderid))) {
                    continue;
                }
                data.append(a).append("\n");
            }
            reader.close();
            try {
                FileWriter Writer = new FileWriter("O.txt");
                Writer.write(data.toString());
                Writer.close();
            }
            catch (IOException e) {}
        }
        catch (FileNotFoundException e) {}
        Order.ordersByOrderID.remove(orderid);
    }

    public static void show(){
        for (Map.Entry<Integer,Order> O : Order.getOrders().entrySet()){
            O.getValue().showThis("s");
        }
    }

    public void showThis(String who){
        String data = "Order ID : "+this.orderID+" | Products : ";
        for (Product P : this.order){
            data+=P.getName()+" ";
        }
        data+=" | Price : "+Product.totalPrice(this.order);
        if (who.equals("s")){
            data+=" | Customer ID : "+this.customer.getNationalID();
        }
        System.out.println(data);
    }

    public User getCustomer() {
        return this.customer;
    }

    public int getOrderID () {
        return this.orderID;
    }

    public static HashMap<Integer,Order> getOrders(){
        return ordersByOrderID;
    }

    public static List<Order> getOrdersOF(String ID){
        List<Order> orders = new ArrayList<>();
        for (Map.Entry<Integer,Order> O : ordersByOrderID.entrySet()){
            if (O.getValue().getCustomer().getNationalID().equals(ID)){
                orders.add(O.getValue());
            }
        }
        return orders;
    }

}
