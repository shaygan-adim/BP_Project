import java.util.*;
import java.io.*;

public class Store {

    private String name;
    private List<Integer> menuByID = new ArrayList<>();

    private static HashMap<String,List<Product>> menuByName = new HashMap<>();
    private static boolean initiated = false;

    private static List<Store> stores = new ArrayList<>();

    private static HashMap<String,Store> storesByName = new HashMap<>();

    public Store(String name, List<Product> menu) {
        this(name,menu,true);

        try {
            FileWriter Writer = new FileWriter("M-"+name+".txt");
            for (Product P : menu){
                Writer.write(P.getProductID()+" ");
            }
            Writer.close();
        }
        catch (IOException e) {}
    }

    private Store(String name, List<Product> menu, boolean f) {
        this.name = name;
        for (Product P : menu){
            menuByID.add(P.getProductID());
        }

        menuByName.put(name, menu);

        storesByName.put(name,this);
        stores.add(this);
    }


    public static void initiate(){
        if (!initiated) {
            initiated=true;
            File myDirectory = new File(System.getProperty("user.dir"));
            File[] a = myDirectory.listFiles();
            for (File i : a) {
                String fileName = i.getName();
                if (fileName.contains("M-")) {
                    try {
                        File UD = new File(fileName);
                        Scanner reader = new Scanner(UD);
                        String[] data = reader.nextLine().split(" ");
                        List<Product> list = new ArrayList<>();
                        for (String j : data){
                            Product.addToInUseProductsID(Integer.valueOf(j));
                            list.add(Product.getProductByID().get(Integer.valueOf(j)));
                        }
                        new Store(fileName.substring(2,fileName.length()-4),list,true);
                    }
                    catch (FileNotFoundException e) {}
                }
            }
        }
    }

    public void addProduct(List<Product> P){
        File UD = new File("M-"+this.name+".txt");
        try {
            Scanner reader = new Scanner(UD);
            String data = reader.nextLine();
            for (Product p : P){
                data+= p.getProductID()+" ";
                menuByName.get(this.name).add(p);
                menuByID.add(p.getProductID());
            }
            try {
                FileWriter Writer = new FileWriter("M-"+this.name+".txt");
                Writer.write(data);
                Writer.close();
            }
            catch (IOException e) {}

        }
        catch (FileNotFoundException e){}
    }

    public static void show() {
        if (stores.size()==0){
            System.out.println("There is no available stores in the system.");
        }
        for (Store s : Store.stores){
            System.out.println("-- "+s.name);
        }
    }

    public void showMenu(){
        System.out.println("Main dish :\n");
        for (String name : Product.getFoods()){
            if (menuByID.contains(Product.getProductByName().get(name).getProductID())){
                Product.getProductByName().get(name).showThis(true);
            }
        }
        System.out.println("\nAppetizer :\n");
        for (String name : Product.getAppetizers()){
            if (menuByID.contains(Product.getProductByName().get(name).getProductID())){
                Product.getProductByName().get(name).showThis(true);
            }
        }
        System.out.println("\nDesert :\n");
        for (String name : Product.getDeserts()){
            if (menuByID.contains(Product.getProductByName().get(name).getProductID())){
                Product.getProductByName().get(name).showThis(true);
            }
        }
        System.out.println("\nDrink :\n");
        for (String name : Product.getDrinks()){
            if (menuByID.contains(Product.getProductByName().get(name).getProductID())){
                Product.getProductByName().get(name).showThis(true);
            }
        }
    }

    public static HashMap<String,Store> getStoresByName(){
        return storesByName;
    }
}


