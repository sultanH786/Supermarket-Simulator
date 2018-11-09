package supermarket;

public class Stats {

    static int lostCustomers=0,time,total,products,sum=0;
    static int utilization[];
    int checkoutCounter;
    Stats(int checkoutCounter){
        this.checkoutCounter = checkoutCounter;
        utilization = new int[checkoutCounter];
    }

    public synchronized void setLostCustomer(){
            lostCustomers++;
    }

    public int getLostCustomer(){
            return lostCustomers;
    }
    public synchronized void setUtilization(int util){
        utilization[util]+=1;

    }

    public int[] getUtilization(){
        return utilization;
    }

    public synchronized void setWaitingTime(long time){
        time+=time;
    }

    public int getWaitingTime(){
        return time;
    }

    public synchronized void setTotalCustomersServed(){
        total++;
    }
    public int getTotalCustomersServed(){
        return total;
    }

    public synchronized void setTotalProductsProcessed(int a){
        products+=a;
    }

    public int getTotalProductsProcessed(){
        return products;
    }
}
