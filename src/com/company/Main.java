package com.company;

import java.util.*;
import java.text.*;
public class Main{
    static List <Order> ord = new ArrayList<>();
    static int idi=0,numbers;
    static String Id;
    static String discount;
    static ArrayList <String> order_list = new ArrayList<>() ;
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        System.out.println("====================================");
        System.out.println("Welcome to \"Eat&Chat\" Pizza Order!");
        System.out.println("====================================");
        int oper;
        while(true){
            while(true){
                System.out.print("What operation you want to do?\n");
                System.out.print("1 ---> Make order\n");
                System.out.print("2 ---> Complete\n");
                System.out.print("3 ---> Add something\n");
                System.out.print("4 ---> Search by Id\n");
                System.out.print("5 ---> Search by Date\n");
                System.out.print("6 ---> Show my order\n");
                System.out.print("7 ---> Show most popular pizza type\n");
                System.out.print("8 ---> Show most popular pizza size\n");
                oper = in.nextInt();
                if(oper < 1 && oper > 8){
                    System.out.print("WRONG INPUT DATA\n");
                    continue;
                }
                break;
            }
            if(oper == 1){
                Id = getId();
                order_list.add(Id);
                System.out.print("> Is it your BIRTHDAY? (10% discount available on presenting ID)  (Y/N):  ");
                discount = in.next();
                PrintChoices(discount);
            }
            if(oper == 2){
                numbers++;
                SearchById(Id);
                System.out.println(getTotalPrice(Id));
                System.out.println("====================================");
                System.out.println("Your order will be done after 30 minut!");
                System.out.println("====================================");
            }
            if(oper == 3){
                order_list.add(Id);
                PrintChoices(discount);
            }
            if(oper == 4){
                String req = in.next();
                System.out.print("Search by ID: " + Id + "\n");
                SearchById(req);
            }
            if(oper == 5){
                String dateFind = in.next();
                SearchByDate(dateFind);
            }
            if(oper == 6)
                SearchById(Id);
            if(oper == 7)
                if(numbers != 0)
                    mostPopularPizzaType();
                else System.out.println("No result\n");
            if(oper == 8)
                if(numbers != 0)
                    mostPopularSize();
                else System.out.print("No result\n");
        }
    }
    public static String getId(){
        idi++;
        String ret = String.format("%04d", idi);
        return ret;
    }
    public static void PrintChoices(String is){
        Scanner in = new Scanner(System.in);
        numbers++;
        System.out.print("What pizza size you would like to order?\n");
        System.out.print("20cm size costs 1000\n");
        System.out.print("30cm size costs 1500\n");
        System.out.print("40cm size costs 2000\n");
        int price =0;
        int size = in.nextInt();
        if(size == 20) price+=1000;
        if(size == 30) price+=1500;
        if(size == 40) price+=2000;
        String[] topping = new String[5];
        System.out.print("Do you want Cheese?\n");
        topping[1] = in.next();
        System.out.print("Do you want Meat?\n");
        topping[2] = in.next();
        System.out.print("Do you want Sauage?\n");
        topping[3] = in.next();
        System.out.print("Do you want Vegetables?\n");
        topping[4] = in.next();
        for(int i = 1; i <= 4; ++i)
            if(topping[i].charAt(0) == 'Y' || topping[i].charAt(0) == 'y')
                price+=200;
        ord.add(new Order(Id,price,is,size,topping));
    }
    static class Order{
        String Id,bd,date,time;
        int price,size;
        String[] topping = new String[5];
        Order(String Id,int price , String bday, int size, String[] tp) {
            this.Id = Id;
            this.price = price;
            this.bd = bday;
            this.size = size;
            int j = 5;
            while(--j > 0)
                this.topping[j] = tp[j];
            Date dateNow = new Date();
            SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd.MM.yyyy");
            SimpleDateFormat formatForDateNow1 = new SimpleDateFormat("HH:mm");
            this.date = formatForDateNow.format(dateNow);
            this.time = formatForDateNow1.format(dateNow);
        }
        void printer() {
            System.out.println(Id + " " + date + " " + time + " " + price + " " + bd + " " + size +  " " + topping[1] + " " + topping[2] + " " + topping[3] + " " + topping[4]);
        }
    }


    public static void printCurrentTime(){
        Date currentTime = new Date();
        Date time = new Date(currentTime.getTime());
        SimpleDateFormat formater = new SimpleDateFormat("HH:MM");
        System.out.print(formater.format(time));
    }

    public static int getTotalPrice(String Id) {
        int sum = 0;
        System.out.print("Total Price:\n");
        for(int i = 0; i < ord.size(); ++i)
            if(ord.get(i).Id == Id){
                sum += ord.get(i).price;
                if(ord.get(i).bd.charAt(0) == 'Y' || ord.get(i).bd.charAt(0) == 'y')
                    sum-=sum/10;
            }
        return sum;
    }
    public static void SearchById(String Id) {
        int res = 0;
        System.out.print("Search by ID: " + Id + "\n");
        for(int i = 0; i < ord.size(); ++i)
            if(ord.get(i).Id.equals(Id)){
                res++;
                ord.get(i).printer();
            }
        if(res == 0)
            System.out.println("No result");
    }
    public static void SearchByDate(String Date) {
        int res = 0;
        System.out.print("Search by date: " + Date + "\n");
        for(int i = 0; i < ord.size(); ++i)
            if((ord.get(i).date).equals(Date)) {
                ord.get(i).printer();
                res++;
            }
        if(res == 0)
            System.out.println("No result");
    }
    public static void mostPopularSize(){
        int[] s = new int[5];
        for(int i = 0; i < ord.size(); ++i)
            if(ord.get(i).size == 20)
                s[2]++;
            else if(ord.get(i).size == 30)
                s[3]++;
            else
                s[4]++;
        System.out.println("Most popular size(s):");
        if(s[2] >= s[3]&& s[2] >= s[4])
            System.out.println("20");
        if(s[3] >= s[4] && s[3] >= s[2])
            System.out.println("30");
        if(s[4] >= s[2] && s[4] >= s[3])
            System.out.println("40");
    }
    public static void mostPopularPizzaType(){
        System.out.print("Most popular pizza type(s):\n");
        int checking = 0,mx=0;
        int[] pointer = new int[11112];
        for(int i = 0; i < ord.size(); ++i) {
            checking = 0;
            if(ord.get(i).topping[1].charAt(0) == 'Y' || ord.get(i).topping[1].charAt(0) == 'y')
                checking+=1000;
            if(ord.get(i).topping[2].charAt(0) == 'Y' || ord.get(i).topping[2].charAt(0) == 'y')
                checking+=100;
            if(ord.get(i).topping[3].charAt(0) == 'Y' || ord.get(i).topping[3].charAt(0) == 'y')
                checking+=10;
            if(ord.get(i).topping[4].charAt(0) == 'Y' || ord.get(i).topping[4].charAt(0) == 'y')
                checking+=1;
            pointer[checking]++;
            if(pointer[checking] > mx)
                mx = pointer[checking];
        }
        for(int i = 0; i < ord.size(); ++i) {
            checking=0;
            if(ord.get(i).topping[1].charAt(0) == 'Y' || ord.get(i).topping[1].charAt(0) == 'y')
                checking+=1000;
            if(ord.get(i).topping[2].charAt(0) == 'Y' || ord.get(i).topping[2].charAt(0) == 'y')
                checking+=100;
            if(ord.get(i).topping[3].charAt(0) == 'Y' || ord.get(i).topping[3].charAt(0) == 'y')
                checking+=10;
            if(ord.get(i).topping[4].charAt(0) == 'Y' || ord.get(i).topping[4].charAt(0) == 'y')
                checking+=1;
            if(pointer[checking] == mx) {
                if(checking / 1000 > 0) {
                    System.out.print("Cheese");
                    if (checking > 1000)
                        System.out.print("+");
                }
                if(checking / 100 % 10 > 0) {
                    System.out.print("Meat");
                    if (checking - checking/1000*1000 > 100 )
                        System.out.print("+");
                }
                if(checking/ 10 % 10 > 0) {
                    System.out.print("Sausage");
                    if (checking - checking/1000*1000 - (checking-checking/1000*1000)/100 * 100 > 10)
                        System.out.print("+");
                }
                if(checking % 10 > 0)
                    System.out.print("Vegetables");
                System.out.print("\n");
                pointer[checking]--;
            }
        }
    }
}
