import java.util.*;
import java.util.Map.Entry;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class Storage {

    private HashMap<String,Integer> stock;
    private int depotID;

    private static List<Storage> storages = new ArrayList<>();
    private static boolean initiated = false;

    private Storage(HashMap<String,Integer> stock, int depotID) {
        this.stock = stock;
        this.depotID = depotID;
        storages.add(this);
    }

    public Storage(HashMap<String,Integer> stock) {
        this(stock,storages.size()+1);
        try {
            StringBuilder d = new StringBuilder();
            for (Entry<String, Integer> i : stock.entrySet()) {
                d.append(i.getKey()).append(" ").append(i.getValue()).append("\n");
            }
            FileWriter Writer = new FileWriter("D"+this.depotID+".txt");
            Writer.write(d.toString());
            Writer.close();
        }
        catch (IOException e) {}
    }

    public static void initiate() {
        if (!initiated) {
            initiated=true;
            File myDirectory = new File(System.getProperty("user.dir"));
            File[] a = myDirectory.listFiles();
            for (File i : a) {
                String fileName = i.getName();
                if (fileName.charAt(0)=='D') {
                    try {
                        File UD = new File(fileName);
                        Scanner reader = new Scanner(UD);
                        HashMap<String,Integer> s = new HashMap<>();
                        while (reader.hasNextLine()) {
                            String data = reader.nextLine();
                            String[] information = data.split(" ");
                            s.put(information[0], Integer.valueOf(information[1]));
                        }
                        new Storage(s,Integer.valueOf(fileName.substring(1,2)));
                    }
                    catch (FileNotFoundException e) {}
                }
            }
        }
    }
    public void add(String ingredient, int mass) {
        if (this.stock.containsKey(ingredient)) {
            this.stock.replace(ingredient, this.stock.get(ingredient)+mass);
        }
        else {
            this.stock.put(ingredient, mass);
        }
        try {
            StringBuilder d = new StringBuilder();
            for (Entry<String, Integer> i : this.stock.entrySet()) {
                d.append(i.getKey()).append(" ").append(i.getValue()).append("\n");
            }
            FileWriter Writer = new FileWriter("D"+this.depotID+".txt");
            Writer.write(d.toString());
            Writer.close();
        }
        catch (IOException e) {}
    }

    public void useThis(String ingredient, int amount) {
        this.stock.replace(ingredient, this.stock.get(ingredient) ,this.stock.get(ingredient)-amount);
        try {
            StringBuilder d = new StringBuilder();
            for (Entry<String, Integer> i : this.stock.entrySet()) {
                d.append(i.getKey()).append(" ").append(i.getValue()).append("\n");
            }
            FileWriter Writer = new FileWriter("D"+this.depotID+".txt");
            Writer.write(d.toString());
            Writer.close();
        }
        catch (IOException e) {}
    }

    public static void use(String ingredient, int amount){
        int i =0;
        while (amount!=0){
            if (Storage.getStorages().get(i).amountOf(ingredient)>=amount){

                Storage.getStorages().get(i).useThis(ingredient,amount);
                amount = 0;
            }
            else{
                amount-=Storage.getStorages().get(i).amountOf(ingredient);
                Storage.getStorages().get(i).useThis(ingredient,Storage.getStorages().get(i).amountOf(ingredient));
            }
        }
    }

    public int amountOf(String ingredient) {
        return this.stock.getOrDefault(ingredient, 0);
    }

    public static int totalAmountOf(String ingredient) {
        int sum = 0 ;
        for (Storage i : storages) {
            sum+=i.amountOf(ingredient);
        }
        return sum;
    }

    public void showStock() {
        for (Entry<String,Integer> i : this.stock.entrySet()) {
            System.out.println("\n"+i.getKey()+" : "+i.getValue());
        }
    }

    public static List<Storage> getStorages(){
        return storages;
    }

    public static int number() {
        return storages.size();
    }

    public HashMap<String,Integer> getStock() { return this.stock; }

}
