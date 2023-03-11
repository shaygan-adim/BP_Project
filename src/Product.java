import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

public class Product {

    private String name;

    private int productID;
    private String type;
    private int price;
    private HashMap<String,Integer> ingredients;

    private static int counter = 0;
    private static boolean initiated = false;
    private static boolean FLAG = false;
    private static List<Product> products = new ArrayList<>();
    private static List<Integer> inUseProductsID = new ArrayList<>();
    private static List<String> foods = new ArrayList<>();
    private static List<String> drinks = new ArrayList<>();
    private static List<String> deserts = new ArrayList<>();
    private static List<String> appetizers = new ArrayList<>();
    private static HashMap<Integer,Product> productByID = new HashMap<>();

    private static HashMap<String,Product> productByName = new HashMap<>();

    static {
        File myDirectory = new File(System.getProperty("user.dir"));
        File[] a = myDirectory.listFiles();
        boolean flag = false;
        for (File i : a) {
            String fileName = i.getName();
            if (fileName.equals("P.txt")) {
                flag = true;
                FLAG = true;
                break;
            }
        }
        if (!flag) {
            try {
                FileWriter Writer = new FileWriter("P.txt");
                Writer.close();
            }
            catch (IOException e) {}
        }
        else{
            try {
                File UD = new File("P.txt");
                Scanner reader = new Scanner(UD);
                String data1 = "";
                String data2;
                while (reader.hasNextLine()) {
                    data1 = reader.nextLine();
                    if (reader.hasNextLine()){
                        data2 = reader.nextLine();
                    }
                }
                counter = Integer.valueOf(data1.split(" ")[0])+1;
            }
            catch (FileNotFoundException e) {}
        }
    }

    private Product(String name,String type,int price,HashMap<String,Integer> ingredients,int id) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.ingredients = ingredients;
        this.productID = id;

        products.add(this);
        productByName.put(name,this);
        productByID.put(id,this);

        if (this.type.equals("FO")) {
            foods.add(this.name);
        }
        if (this.type.equals("DR")) {
            drinks.add(this.name);
        }
        if (this.type.equals("DE")) {
            deserts.add(this.name);
        }
        if (this.type.equals("AP")) {
            appetizers.add(this.name);
        }
    }
    public Product(String name,String type,int price,HashMap<String,Integer> ingredients) {
        this(name,type,price,ingredients,counter);
        counter++;

        StringBuilder data = new StringBuilder();
        for (Product P : products){
            data.append(P.productID).append(" ").append(P.type).append(" ").append(P.name).append(" ").append(P.price).append("\n").append(P.ingredients).append("\n");
        }
        try {
            FileWriter Writer = new FileWriter("P.txt");
            Writer.write(data.toString());
            Writer.close();
        }
        catch (IOException e) {}
    }

    public static void initiate(){
        if (!initiated && FLAG){
            initiated = true;
            try {
                File UD = new File("P.txt");
                Scanner reader = new Scanner(UD);
                while (reader.hasNextLine()) {
                    String data = reader.nextLine();
                    String[] information1 = data.split(" ");
                    data = reader.nextLine();
                    String[] information2 = data.split(",");
                    HashMap<String,Integer> ing = new HashMap<>();
                    for (int i = 0 ; i<information2.length ; i++){
                        if (i== information2.length-1){
                            ing.put(information2[i].split("=")[0].substring(1),Integer.valueOf(information2[i].split("=")[1].substring(0,information2[i].split("=")[1].length()-1)));
                        }
                        else{
                            ing.put(information2[i].split("=")[0].substring(1),Integer.valueOf(information2[i].split("=")[1]));
                        }
                    }
                    new Product(information1[2],information1[1],Integer.valueOf(information1[3]),ing,Integer.valueOf(information1[0]));
                }
            }
            catch (FileNotFoundException e) {}
        }
    }

    public int getPrice() {
        return this.price;
    }

    public String getName() {
        return this.name;
    }

    public String getType() {
        return this.type;
    }

    public int getProductID(){
        return this.productID;
    }

    public HashMap<String,Integer> getIngredients(){return ingredients;}

    public static HashMap<String, Product> getProductByName(){
        return productByName;
    }

    public static List<String> getFoods(){
        return foods;
    }

    public static List<String> getDrinks(){
        return drinks;
    }

    public static List<String> getDeserts(){
        return deserts;
    }

    public static List<String> getAppetizers(){
        return appetizers;
    }

    public static HashMap<Integer,Product> getProductByID(){
        return productByID;
    }

    public static void show(){
        String availability;
        for (Product p : products){
            if (p.isAvailable()){
                availability = "Available";
            }
            else{
                availability = "Not Available";
            }
            System.out.println("-- Name : "+p.getName()+" | Type : "+p.getType()+" | Price : "+p.getPrice()+" | Product ID : "+p.getProductID()+" | "+availability);
        }
    }

    public void showThis(boolean a){
        String availability;
        if (this.isAvailable()){
            availability = "Available";
        }
        else{
            availability = "Not Available";
        }
        if (a){
            System.out.println("-- Name : "+this.getName()+" | Price : "+this.getPrice()+" | Product ID : "+this.getProductID()+" | "+availability);
        }
        else {
            System.out.println("-- Name : "+this.getName()+" | Price : "+this.getPrice()+" | Product ID : "+this.getProductID());
        }
    }

    public boolean isAvailable(){
        for (Entry<String,Integer> ing: this.ingredients.entrySet()){
            if (ing.getValue()>Storage.totalAmountOf(ing.getKey())){
                return false;
            }
        }
        return true;
    }

    public static int totalPrice(List<Product> P){
        int s = 0;
        for (Product p : P){
            s+=p.getPrice();
        }
        return s;
    }

    public static List<Integer> getInUseProductsID(){
        return inUseProductsID;
    }

    public static void addToInUseProductsID(Integer I){
        inUseProductsID.add(I);
    }
}
