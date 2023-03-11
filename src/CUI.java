import java.util.*;
import java.util.Map.Entry;

public class CUI {

    private static boolean managerProcedure() {
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.println("\n(( Sharif+ Automation System ))");
            System.out.println("Manager menu\n");
            System.out.println("Enter a command.\n");
            System.out.println("** RegisterStaff ( add a Staff to the system )");
            System.out.println("** RegisterCustomer ( add a Customer to the system )");
            System.out.println("** RegisterStore ( add a Store to the system )");
            System.out.println("** RegisterDepot ( add a Depot to the system )");
            System.out.println("** RegisterProduct ( add a Product to the system )");
            System.out.println("** CheckProducts ( print all products of the system )");
            System.out.println("** AddProductTo{Store's name} ( add products to the store )");
            System.out.println("** CheckDepot {depot ID} ( print the supply in the depot )");
            System.out.println("** AmountOf{Ingredient} optional:{depot ID} ( print the amount of the ingredient in total or (optional) in a specific depot )");
            System.out.println("** AddToDepot {depot ID} ( adding some amount of some ingredients to a depot )");
            System.out.println("** AddAll {amount} ( adding some amount to all ingredients of all depots )");
            System.out.println("** RemoveFromDepot {depot ID} ( removing some amount of some ingredients from a depot )");
            System.out.println("** RemoveAll {amount} ( removing some amount of all ingredients of all depots )");
            System.out.println("** Home ( back to homepage )\n\n");
            String command = input.nextLine();
            if (command.equals("Home")) {
                return true;
            }
            if (command.equals("RegisterStaff")) {
                System.out.println("Enter the Staff's information : ( Name-National ID-Password )\n");
                String data = input.nextLine();
                if (!data.equals("CANCEL_OPERATION")){
                    String[] information = data.split("-");
                    if (information.length!=3){
                        System.out.println("\nInvalid command! Try again.");
                    }
                    else{
                        try{
                            int id = Integer.valueOf(information[1]);
                            boolean f = true;
                            for (User u : User.getUser()){
                                if (u.getNationalID().equals(information[1])){
                                    f = false;
                                }
                            }
                            if (f){
                                new User("S",information[0],information[1],information[2]);
                                System.out.println(information[0]+" added successfully to the system as a Staff.");
                            }
                            else{
                                System.out.println("There is another registered user with id "+information[1]+" in the system.");
                            }
                        }
                        catch (java.lang.NumberFormatException e){
                            System.out.println("\nInvalid ID! Try again.");
                        }
                    }
                }
                else{
                    System.out.println("Operation cancelled.\n");
                }
            }
            else if (command.toLowerCase().contains("registerstaff")){
                System.out.println("\nDo you mean 'RegisterStaff' ?");
                System.out.println("Invalid command! Try again.");
            }
            else if (command.equals("RegisterCustomer")) {
                System.out.println("Enter the Customer's information : ( Name-National ID-Password )\n");
                String data = input.nextLine();
                if (!data.equals("CANCEL_OPERATION")){
                    String[] information = data.split("-");
                    if (information.length!=3){
                        System.out.println("\nInvalid command! Try again.");
                    }
                    else{
                        try{
                            int id = Integer.valueOf(information[1]);
                            boolean f = true;
                            for (User u : User.getUser()){
                                if (u.getNationalID().equals(information[1])){
                                    f = false;
                                }
                            }
                            if (f){
                                new User("C",information[0],information[1],information[2]);
                                System.out.println(information[0]+" added successfully to the system as a Customer.");
                            }
                            else{
                                System.out.println("There is another registered user with id "+information[1]+" in the system.");
                            }
                        }
                        catch (java.lang.NumberFormatException e){
                            System.out.println("\nInvalid ID! Try again.");
                        }
                    }
                }
                else{
                    System.out.println("Operation cancelled.\n");
                }
            }
            else if (command.toLowerCase().contains("registercustomer")){
                System.out.println("\nDo you mean 'RegisterCustomer' ?");
                System.out.println("Invalid command! Try again.");
            }
            else if (command.equals("RegisterStore")) {
                System.out.println("Enter the Store's name :\n");
                String name = input.nextLine();
                if (!name.equals("CANCEL_OPERATION")){
                    System.out.println("\nEnter the Store's products : ( product1's ID-product2's ID-... ) :\n");
                    String data = input.nextLine();
                    if (!data.equals("CANCEL_OPERATION")){
                        String[] information = data.split("-");
                        try{
                            List<Integer> IDs = new ArrayList<>();
                            for (String str : information){
                                IDs.add(Integer.valueOf(str));
                            }
                            List<Product> list = new ArrayList<>();
                            int j = 0;
                            for (String i : information){
                                if (!Product.getInUseProductsID().contains(Integer.valueOf(i))){
                                    if (!Product.getProductByID().containsKey(Integer.valueOf(i))){
                                        System.out.println("There is no product with id "+i+" in the system.");
                                    }
                                    else{
                                        list.add(Product.getProductByID().get(Integer.valueOf(i)));
                                        Product.addToInUseProductsID(Integer.valueOf(i));
                                        j++;
                                    }
                                }
                                else{
                                    System.out.println("Product with id "+i+" is a store's product.");
                                }
                            }
                            if (list.size()==0){
                                System.out.println("\nNo entry product qualified for this Store, so registering failed. Try again.");
                            }
                            else{
                                new Store(name, list);
                                System.out.println("\n"+name+" store added successfully to the system with "+j+" products.");
                            }
                        }
                        catch (java.lang.NumberFormatException e){
                            System.out.println("\nInvalid ID! Try again.");
                        }
                    }
                    else{
                        System.out.println("Operation cancelled.\n");
                    }
                }
                else{
                    System.out.println("Operation cancelled.\n");
                }
            }
            else if (command.toLowerCase().contains("registerstore")){
                System.out.println("\nDo you mean 'RegisterStore' ?");
                System.out.println("Invalid command! Try again.");
            }
            else if (command.equals("RegisterDepot")) {
                System.out.println("Enter the Depot's initial supply : ( Ingredient1-Amount1-Ingredient2-Amount2-... )\n");
                String data = input.nextLine();
                if (!data.equals("CANCEL_OPERATION")){
                    String[] information = data.split("-");
                    if (information.length%2!=0 || information.length==0){
                        System.out.println("\nInvalid command! Try again.");
                    }
                    else{
                        try {
                            List<Integer> amounts = new ArrayList<>();
                            for (int i = 0 ; i<information.length/2 ; i++){
                                amounts.add(Integer.valueOf(information[i*2+1]));
                            }
                            HashMap<String,Integer> sup = new HashMap<>();
                            Storage thisStorage = null;
                            for (int i = 0 ; i<information.length/2 ; i++) {
                                if (i==0){
                                    sup.put(information[i*2], Integer.valueOf(information[i*2+1]));
                                    thisStorage = new Storage(sup);
                                }
                                else{
                                    thisStorage.add(information[i*2],Integer.valueOf(information[i*2+1]));
                                }
                            }
                            System.out.println("The Depot successfully added to the system with id of "+Storage.number());
                        }
                        catch (java.lang.NumberFormatException e) {
                            System.out.println("\nOne or more amount entries are not an integer number! Try again.");
                        }
                    }
                }
                else{
                    System.out.println("Operation cancelled.\n");
                }
            }
            else if (command.toLowerCase().contains("registerdepot")){
                System.out.println("\nDo you mean 'RegisterDepot' ?");
                System.out.println("Invalid command! Try again.");
            }
            else if (command.equals("RegisterProduct")) {
                System.out.println("Enter the Product's information : ( Name-Type[FO,DR,DE,AP]-Price )\n");
                String data = input.nextLine();
                if (!data.equals("CANCEL_OPERATION")){
                    String[] information1 = data.split("-");
                    if (information1.length!=3){
                        System.out.println("\nInvalid command! Try again.");
                    }
                    else if (!information1[1].equals("FO") && !information1[1].equals("DR") && !information1[1].equals("DE") && !information1.equals("AP")){
                        System.out.println("\nInvalid type of product! Try again.");
                    }
                    else{
                        try {
                            int price = Integer.valueOf(information1[2]);
                            System.out.println("Enter the Product's ingredients : ( ingredient1-amount1-ingredient2-amount2-... )\n");
                            data = input.nextLine();
                            if (!data.equals("CANCEL_OPERATION")){
                                String[] information2 = data.split("-");
                                if (information2.length%2!=0 || information2.length==0){
                                    System.out.println("\nInvalid command! Try again.");
                                }
                                else{
                                    try {
                                        List<Integer> amounts = new ArrayList<>();
                                        for (int i = 0 ; i<information2.length/2 ; i++){
                                            amounts.add(Integer.valueOf(information2[i*2+1]));
                                        }
                                        HashMap<String, Integer> ing = new HashMap<>();
                                        for (int i = 0 ; i<information2.length ; i++){
                                            if (i%2==0){
                                                ing.put(information2[i],Integer.valueOf(information2[i+1]));
                                            }
                                        }
                                        Product thisProduct = new Product(information1[0],information1[1],price,ing);
                                        System.out.println("Added successfully to the system with id "+thisProduct.getProductID()+".");
                                    }
                                    catch (java.lang.NumberFormatException e) {
                                        System.out.println("\nOne or more amount entries are not an integer number! Try again.");
                                    }
                                }
                            }
                            else{
                                System.out.println("Operation cancelled.\n");
                            }
                        }
                        catch (java.lang.NumberFormatException e){
                            System.out.println("\nInvalid price! Try again.");
                        }
                    }
                }
                else{
                    System.out.println("Operation cancelled.\n");
                }
            }
            else if (command.toLowerCase().contains("registerproduct")){
                System.out.println("\nDo you mean 'RegisterProduct' ?");
                System.out.println("Invalid command! Try again.");
            }
            else if (command.contains("CheckProducts")) {
                Product.show();
            }
            else if (command.toLowerCase().contains("checkproducts")){
                System.out.println("\nDo you mean 'CheckProducts' ?");
                System.out.println("Invalid command! Try again.");
            }
            else if (command.contains("AddProductTo")){
                try{
                    if (!Store.getStoresByName().containsKey(command.substring(12))){
                        System.out.println("There is no registered store named "+command.substring(12)+" in the system.");
                    }
                    else{
                        System.out.println("Enter the products : ( Product1's ID-Product2's ID-... )");
                        String data = input.nextLine();
                        if (!data.equals("CANCEL_OPERATION")){
                            String[] information = data.split("-");
                            if (information.length==0){
                                System.out.println("\nInvalid command! Try again.");
                            }
                            else{
                                try{
                                    List<Integer> IDs = new ArrayList<>();
                                    for (String str : information){
                                        IDs.add(Integer.valueOf(str));
                                    }
                                    List<Product> list = new ArrayList<>();
                                    int j = 0;
                                    for (String i : information){
                                        if (!Product.getInUseProductsID().contains(Integer.valueOf(i))){
                                            if (!Product.getProductByID().containsKey(Integer.valueOf(i))){
                                                System.out.println("There is no product with id "+i+" in the system.");
                                            }
                                            else{
                                                list.add(Product.getProductByID().get(Integer.valueOf(i)));
                                                Product.addToInUseProductsID(Integer.valueOf(i));
                                                j++;
                                            }
                                        }
                                        else{
                                            System.out.println("Product with id "+i+" is a store's product.");
                                        }
                                    }
                                    if (list.size()==0){
                                        System.out.println("\nNo entry product qualified for this Store, so adding failed. Try again.");
                                    }
                                    else{
                                        Store.getStoresByName().get(command.substring(12)).addProduct(list);
                                        System.out.println("\n"+j+" products added successfully to "+command.substring(12)+".");
                                    }
                                }
                                catch (java.lang.NumberFormatException e){
                                    System.out.println("\nInvalid ID! Try again.");
                                }
                            }
                        }
                        else{
                            System.out.println("Operation cancelled.\n");
                        }
                    }
                }
                catch (Exception e) {
                    System.out.println("\nInvalid command! Try again.");
                }
            }
            else if (command.contains("CheckDepot")) {
                try{
                    int id = Integer.valueOf(command.substring(command.indexOf(" ")+1))-1;
                    if (Storage.getStorages().size()<=id){
                        System.out.println("There is no registered depot with id "+(id+1)+".");
                    }
                    else{
                        Storage.getStorages().get(id).showStock();
                    }
                }
                catch( NumberFormatException e) {
                    System.out.println("Invalid ID! Try again.");
                }
            }
            else if (command.toLowerCase().contains("checkdepot")) {
                System.out.println("\nDo you mean 'CheckDepot' ?");
                System.out.println("Invalid command! Try again.");
            }
            else if (command.contains("AmountOf")) {
                if (!command.contains(" ")) {
                    System.out.println(Storage.totalAmountOf(command.substring(8)));
                }
                else {
                    try {
                        int id = Integer.valueOf(command.substring(command.indexOf(" ")+1))-1;
                        if (Storage.getStorages().size()<=id){
                            System.out.println("There is no registered depot with id "+(id+1)+".");
                        }
                        else{
                            System.out.println(Storage.getStorages().get(id).amountOf(command.substring(8,command.indexOf(" "))));
                        }
                    }
                    catch ( NumberFormatException e) {
                        System.out.println("Invalid ID! Try again.");
                    }
                }
            }
            else if (command.toLowerCase().contains("amountof")){
                System.out.println("\nDo you mean 'AmountOf' ?");
                System.out.println("Invalid command! Try again.");
            }
            else if (command.contains("AddToDepot")) {
                try{
                    int id = Integer.valueOf(command.substring(command.indexOf(" ")+1))-1;
                    if (Storage.getStorages().size()<=id){
                        System.out.println("There is no registered depot with id "+(id+1)+".");
                    }
                    else{
                        System.out.println("Enter the names and value of ingredients you want to add to the depot : ( Ingredient1-Amount1-Ingredient2-Amount2-... )\n");
                        String data = input.nextLine();
                        if (!data.equals("CANCEL_OPERATION")){
                            String[] information2 = data.split("-");
                            if (information2.length%2!=0 || information2.length==0){
                                System.out.println("\nInvalid command! Try again.");
                            }
                            else{
                                try {
                                    List<Integer> amounts = new ArrayList<>();
                                    for (int i = 0 ; i<information2.length/2 ; i++){
                                        amounts.add(Integer.valueOf(information2[i*2+1]));
                                    }
                                    HashMap<String, Integer> ing = new HashMap<>();
                                    for (int i = 0 ; i<information2.length ; i++){
                                        if (i%2==0){
                                            Storage.getStorages().get(id).add(information2[i],Integer.valueOf(information2[i+1]));
                                        }
                                    }
                                    System.out.println("Ingredients added to the depot successfully.");
                                }
                                catch (java.lang.NumberFormatException e) {
                                    System.out.println("\nOne or more amount entries are not an integer number! Try again.");
                                }
                            }
                        }
                        else{
                            System.out.println("Operation cancelled.\n");
                        }
                    }
                }
                catch (NumberFormatException e){
                    System.out.println("Invalid ID! Try again.");
                }
            }
            else if (command.toLowerCase().contains("addtodepot")){
                System.out.println("\nDo you mean 'AddToDepot' ?");
                System.out.println("Invalid command! Try again.");
            }
            else if (command.contains("AddAll")){
                try {
                    int amount = Integer.valueOf(command.substring(command.indexOf(" ")+1));
                    for (Storage s : Storage.getStorages()){
                        for (Map.Entry<String, Integer> ing : s.getStock().entrySet()){
                            s.add(ing.getKey(),amount);
                        }
                    }
                    System.out.println("\nAdded "+amount+" to all ingredients.");
                }
                catch (NumberFormatException e){
                    System.out.println("\nInvalid amount!");
                }
            }
            else if (command.toLowerCase().contains("addall")){
                System.out.println("\nDo you mean 'AddAll' ?");
                System.out.println("Invalid command! Try again.");
            }
            else if (command.contains("RemoveFromDepot")) {
                try{
                    int id = Integer.valueOf(command.substring(command.indexOf(" ")+1))-1;
                    if (Storage.getStorages().size()<=id){
                        System.out.println("There is no registered depot with id "+(id+1)+".");
                    }
                    else{
                        System.out.println("Enter the names and value of ingredients you want to remove from the depot : ( Ingredient1-Amount1-Ingredient2-Amount2-... )\n");
                        String data = input.nextLine();
                        if (!data.equals("CANCEL_OPERATION")){
                            String[] information2 = data.split("-");
                            if (information2.length%2!=0 || information2.length==0){
                                System.out.println("\nInvalid command! Try again.");
                            }
                            else{
                                try {
                                    List<Integer> amounts = new ArrayList<>();
                                    for (int i = 0 ; i<information2.length/2 ; i++){
                                        amounts.add(Integer.valueOf(information2[i*2+1]));
                                    }
                                    HashMap<String, Integer> ing = new HashMap<>();
                                    for (int i = 0 ; i<information2.length ; i++){
                                        if (i%2==0){
                                            if (Storage.getStorages().get(id).amountOf(information2[i])>=Integer.valueOf(information2[i+1])){
                                                Storage.getStorages().get(id).add(information2[i],-1*Integer.valueOf(information2[i+1]));
                                            }
                                            else{
                                                Storage.getStorages().get(id).add(information2[i],-1*Storage.getStorages().get(id).amountOf(information2[i]));
                                            }
                                        }
                                    }
                                    System.out.println("Ingredients removed from the depot successfully.");
                                }
                                catch (java.lang.NumberFormatException e) {
                                    System.out.println("\nOne or more amount entries are not an integer number! Try again.");
                                }
                            }
                        }
                        else{
                            System.out.println("Operation cancelled.\n");
                        }
                    }
                }
                catch (NumberFormatException e){
                    System.out.println("Invalid ID! Try again.");
                }
            }
            else if (command.toLowerCase().contains("removefromdepot")){
                System.out.println("\nDo you mean 'AddToDepot' ?");
                System.out.println("Invalid command! Try again.");
            }
            else if (command.contains("RemoveAll")){
                try {
                    int amount = Integer.valueOf(command.substring(command.indexOf(" ")+1));
                    for (Storage s : Storage.getStorages()){
                        for (Map.Entry<String, Integer> ing : s.getStock().entrySet()){
                            if (ing.getValue()<amount){
                                s.add(ing.getKey(), -1*ing.getValue());
                            }
                            else{
                                s.add(ing.getKey(),-1*amount);
                            }
                        }
                    }
                    System.out.println("\nRemoved "+amount+" from all ingredients.");
                }
                catch (NumberFormatException e){
                    System.out.println("\nInvalid amount!");
                }
            }
            else if (command.toLowerCase().contains("removeall")){
                System.out.println("\nDo you mean 'AddAll' ?");
                System.out.println("Invalid command! Try again.");
            }
            else{
                System.out.println("\nInvalid command! Try again.");
            }
        }
    }

    private static boolean staffProcedure(String ID) {
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.println("\n(( Sharif+ Automation System ))");
            System.out.println("Staff menu\n");
            System.out.println("Enter a command.\n");
            System.out.println("** RegisterCustomer ( add a Customer to the system )");
            System.out.println("** RegisterProduct ( add a Product to the system )");
            System.out.println("** CheckProducts ( print all products of the system )");
            System.out.println("** CheckOrders ( check all pending orders )");
            System.out.println("** DeliverOrder {OrderID1-OrderID2-...} ( deliver an order )");
            System.out.println("** Home ( back to homepage )\n\n");
            String command = input.nextLine();
            if (command.equals("Home")) {
                return true;
            }
            if (command.equals("RegisterCustomer")) {
                System.out.println("Enter the Customer's information : ( Name-National ID-Password )\n");
                String[] information = input.nextLine().split("-");
                if (information.length!=3){
                    System.out.println("\nInvalid command! Try again.");
                }
                else{
                    try{
                        int id = Integer.valueOf(information[1]);
                        boolean f = true;
                        for (User u : User.getUser()){
                            if (u.getNationalID().equals(information[1])){
                                f = false;
                            }
                        }
                        if (f){
                            new User("C",information[0],information[1],information[2]);
                            System.out.println(information[0]+" added successfully to the system as a Customer.");
                        }
                        else{
                            System.out.println("There is another registered user with id "+information[1]+" in the system.");
                        }
                    }
                    catch (java.lang.NumberFormatException e){
                        System.out.println("\nInvalid ID! Try again.");
                    }
                }
            }
            else if (command.toLowerCase().contains("registercustomer")){
                System.out.println("\nDo you mean 'RegisterCustomer' ?");
                System.out.println("Invalid command! Try again.");
            }
            else if (command.equals("RegisterProduct")) {
                System.out.println("Enter the Product's information : ( Name-Type[FO,DR,DE,AP]-Price )\n");
                String[] information1 = input.nextLine().split("-");
                if (information1.length!=3){
                    System.out.println("\nInvalid command! Try again.");
                }
                else if (!information1[1].equals("FO") && !information1[1].equals("DR") && !information1[1].equals("DE") && !information1.equals("AP")){
                    System.out.println("\nInvalid type of product! Try again.");
                }
                else{
                    try {
                        int price = Integer.valueOf(information1[2]);
                        System.out.println("Enter the Product's ingredients : ( ingredient1-amount1-ingredient2-amount2-... )\n");
                        String[] information2 = input.nextLine().split("-");
                        if (information2.length%2!=0 || information2.length==0){
                            System.out.println("\nInvalid command! Try again.");
                        }
                        else{
                            try {
                                List<Integer> amounts = new ArrayList<>();
                                for (int i = 0 ; i<information2.length/2 ; i++){
                                    amounts.add(Integer.valueOf(information2[i*2+1]));
                                }
                                HashMap<String, Integer> ing = new HashMap<>();
                                for (int i = 0 ; i<information2.length ; i++){
                                    if (i%2==0){
                                        ing.put(information2[i],Integer.valueOf(information2[i+1]));
                                    }
                                }
                                Product thisProduct = new Product(information1[0],information1[1],price,ing);
                                System.out.println("Added successfully to the system with id "+thisProduct.getProductID()+".");
                            }
                            catch (java.lang.NumberFormatException e) {
                                System.out.println("\nOne or more amount entries are not an integer number! Try again.");
                            }
                        }
                    }
                    catch (java.lang.NumberFormatException e){
                        System.out.println("\nInvalid price! Try again.");
                    }
                }
            }
            else if (command.toLowerCase().contains("registerproduct")){
                System.out.println("\nDo you mean 'RegisterProduct' ?");
                System.out.println("Invalid command! Try again.");
            }
            else if (command.equals("CheckOrders")){
                Order.show();
            }
            else if (command.contains("CheckProducts")) {
                Product.show();
            }
            else if (command.toLowerCase().contains("checkproducts")){
                System.out.println("\nDo you mean 'CheckProducts' ?");
                System.out.println("Invalid command! Try again.");
            }
            else if (command.toLowerCase().contains("checkorders")){
                System.out.println("\nDo you mean 'CheckOrders' ?");
                System.out.println("Invalid command! Try again.");
            }
            else if (command.contains("DeliverOrder")){
                try {
                    int flag = 0;
                    List<String> orders1 = Arrays.asList(command.substring(13).split("-"));
                    List<Integer> orders2 = new ArrayList<>();
                    for (Entry<Integer,Order> O : Order.getOrders().entrySet()){
                        if (orders1.contains(String.valueOf(O.getKey()))){
                            orders2.add(O.getKey());
                            flag++;
                        }
                    }
                    for (int id : orders2){
                        Order.removeOrder(id);
                    }
                    if (flag!=orders1.size()){
                        System.out.println("\nSome of the OrderID's that you entered are not in the system or they're not an integer number.");
                        System.out.println(flag+" orders delivered to customers successfully.");
                    }
                    else{
                        System.out.println("\nAll orders delivered to customers successfully.");
                    }
                }
                catch (Exception e){
                    System.out.println("\nInvalid command! Try again.");
                }
            }
            else if (command.toLowerCase().contains("deliverorders")){
                System.out.println("\nDo you mean 'DeliverOrders' ?");
                System.out.println("Invalid command! Try again.");
            }
            else{
                System.out.println("\nInvalid command! Try again.");
            }
        }
    }

    private static boolean customerProcedure(String ID) {
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.println("\n(( Sharif+ Automation System ))");
            System.out.println("Customer menu\n");
            System.out.println("Enter a command.\n");
            System.out.println("** CheckStores ( shows available stores in the system )");
            System.out.println("** CheckMenu {Store name} ( prints the menu of the store )");
            System.out.println("** Order ( Order products )");
            System.out.println("** CheckMyOrders ( shows all pending orders of yours )");
            System.out.println("** Home ( back to homepage )\n");
            String command = input.nextLine();
            if (command.equals("Home")) {
                return true;
            }
            else if (command.equals("CheckStores")) {
                System.out.println("\nAvailable stores :\n");
                Store.show();
            }
            else if (command.toLowerCase().equals("checkstores")){
                System.out.println("\nDo you mean 'CheckStores' ?");
                System.out.println("Invalid command! Try again.");
            }
            else if (command.split(" ")[0].equals("CheckMenu") && command.split(" ").length==2) {
                if (Store.getStoresByName().containsKey(command.split(" ")[1])){
                    System.out.println("\n"+command.split(" ")[1]+" menu :\n");
                    Store.getStoresByName().get(command.split(" ")[1]).showMenu();
                }
                else{
                    System.out.println("There is no store named "+command.split(" ")[1]+" in the system.");
                    System.out.println("Use 'CheckStores' command to see available stores.");
                }
            }
            else if (command.split(" ")[0].equals("CheckMenu")){
                System.out.println("\nInvalid command! Try again.");
            }
            else if (command.toLowerCase().contains("checkmenu")){
                System.out.println("\nDo you mean 'CheckMenu' ?");
                System.out.println("Invalid command! Try again.");
            }
            else if (command.equals("Order")) {
                System.out.println("Enter your order : ( product1's ID-product1's ID-product1's ID-... )");
                String data = input.nextLine();
                if (!data.equals("CANCEL_OPERATION")){
                    String[] info = data.split("-");
                    if (info.length==0){
                        System.out.println("Invalid command! Try again.");
                    }
                    else{
                        List<Product> list = new ArrayList<>();
                        for (String str : info){
                            try {
                                if (!Product.getProductByID().containsKey(Integer.valueOf(str))){
                                    System.out.println("There are no product in system with id "+Integer.valueOf(str)+". Read menu carefully and try again.");
                                }
                                else if (!Product.getProductByID().get(Integer.valueOf(str)).isAvailable()){
                                    System.out.println("Unfortunately "+Product.getProductByID().get(Integer.valueOf(str)).getName()+" is not available right now.");
                                }
                                else{
                                    list.add(Product.getProductByID().get(Integer.valueOf(str)));
                                    for (Map.Entry<String,Integer> ing : Product.getProductByID().get(Integer.valueOf(str)).getIngredients().entrySet()){
                                        Storage.use(ing.getKey(),ing.getValue());
                                    }
                                }
                            }
                            catch (java.lang.NumberFormatException e){
                                System.out.println(str+" is not an integer number. Product ID's are all integers.");
                            }
                        }
                        if (list.size()>0){
                            System.out.println("\nOrder review :");
                            for (Product p : list){
                                p.showThis(false);
                            }
                            System.out.println("\nTotal price of your order is "+Product.totalPrice(list));

                            Order thisOrder = new Order(User.getUser().get(User.getCustomer().get(ID)), list);
                            System.out.println("\nYour order submitted successfully with id "+thisOrder.getOrderID()+".");
                        }
                    }
                }
                else{
                    System.out.println("Operation cancelled.\n");
                }
            }
            else if (command.toLowerCase().equals("order")){
                System.out.println("\nDo you mean 'Order' ?");
                System.out.println("Invalid command! Try again.");
            }
            else if (command.equals("CheckMyOrders")) {
                List<Order> orders = Order.getOrdersOF(ID);
                System.out.println("You have "+orders.size()+" pending orders.\n");
                if (orders.size()>0){
                    for (Order O : orders){
                        O.showThis("c");
                    }
                }
            }
            else if (command.toLowerCase().contains("checkmyorders")){
                System.out.println("\nDo you mean 'CheckMyOrders' ?");
                System.out.println("Invalid command! Try again.");
            }
            else {
                System.out.println("\nInvalid command! Try again.");
            }
        }
    }

    private static String loginProcedure() {
        Scanner input = new Scanner(System.in);
        System.out.println("\n(( Sharif+ Automation System ))");
        System.out.println("Login page\n");
        System.out.println("Login as ? ( M / S / C )\n");
        String TYPE = input.nextLine();
        if (TYPE.equals("CANCEL_OPERATION")){
            return "Error2";
        }
        if (!(TYPE.equals("M") || TYPE.equals("S") || TYPE.equals("C"))) {
            System.out.println("\nInvalid type !\nChoose between 'M'anager / 'S'taff / 'C'ustomer.\n");
            return "Error";
        }
        System.out.println("Enter your ID :\n");
        String ID = input.nextLine();
        if (ID.equals("CANCEL_OPERATION")){
            return "Error2";
        }
        System.out.println("Enter your password :\n");
        String PASS = input.nextLine();
        if (PASS.equals("CANCEL_OPERATION")){
            return "Error2";
        }
        if (TYPE.equals("M")) {
            if (!User.getManager().containsKey(ID)) {
                System.out.println("\nWrong ID !\nTry again.\n");
                return "Error";
            }
            else if (!User.getUser().get(User.getManager().get(ID)).getPassword().equals(PASS)) {
                System.out.println("\nWrong password !\nTry again.\n");
                return "Error";
            }
            else {
                System.out.println("\nWelcome "+User.getUser().get(User.getManager().get(ID)).getName()+" !");
                System.out.println("You have successfully logined as Manager.");
                return "M";
            }
        }
        if (TYPE.equals("S")) {
            if (!User.getStaff().containsKey(ID)) {
                System.out.println("\nWrong ID !\nTry again.\n");
                return "Error";
            }
            else if (!User.getUser().get(User.getStaff().get(ID)).getPassword().equals(PASS)) {
                System.out.println("\nWrong password !\nTry again.\n");
                return "Error";
            }
            else {
                System.out.println("\nWelcome "+User.getUser().get(User.getStaff().get(ID)).getName()+" !");
                System.out.println("You have successfully logined as Staff.");
                return "S"+ID;
            }
        }
        if (TYPE.equals("C")) {
            if (!User.getCustomer().containsKey(ID)) {
                System.out.println("\nWrong ID !\nTry again.\n");
                return "Error";
            }
            else if (!User.getUser().get(User.getCustomer().get(ID)).getPassword().equals(PASS)) {
                System.out.println("\nWrong password !\nTry again.\n");
                return "Error";
            }
            else {
                System.out.println("\nWelcome "+User.getUser().get(User.getCustomer().get(ID)).getName()+" !");
                System.out.println("You have successfully logined as Customer.");
                return "C"+ID;
            }
        }
        return String.valueOf(ID);
    }
    private static void mainLoop() {
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.println("(( Sharif+ Automation System ))");
            System.out.println("Main menu\n");
            System.out.println("Enter a command.\n");
            System.out.println("** Login");
            System.out.println("** Help\n");
            String command = input.nextLine();
            if (command.equals("Login")) {
                String LOG = loginProcedure();
                if (LOG.equals("M")) {
                    managerProcedure();
                }
                if (LOG.contains("S")) {
                    staffProcedure(LOG.substring(1));
                }
                if (LOG.contains("C")){
                    customerProcedure(LOG.substring(1));
                }
                if (LOG.equals("Error2")){
                    System.out.println("Operation cancelled successfully.\n");
                }
                while (LOG.equals("Error")){
                    LOG = loginProcedure();
                    if (LOG.equals("M")) {
                        managerProcedure();
                    }
                    if (LOG.contains("S")) {
                        staffProcedure(LOG.substring(1));
                    }
                    if (LOG.contains("C")){
                        customerProcedure(LOG.substring(1));
                    }
                }
            } else if (command.toLowerCase().equals("login")) {
                System.out.println("Do you mean 'Login' ?");
                System.out.println("Invalid command! Try again.\n");
            }
            else if (command.equals("Help")){
                System.out.println("// Usage of every command is explained in front of it.");
                System.out.println("// You can use 'CANCEL_OPERATION' for cancelling every running operation in each step.\n");
            }
            else if (command.toLowerCase().equals("help")){
                System.out.println("Do you mean 'Help' ?");
                System.out.println("Invalid command! Try again.\n");
            }
            else{
                System.out.println("Invalid command! Try again.\n");
            }
        }
    }
    public static void main(String[] args) {
        User.initiate();
        Product.initiate();
        Storage.initiate();
        Store.initiate();
        Order.initiate();
        mainLoop();
    }
}
