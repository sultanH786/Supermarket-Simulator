package supermarket;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class Supermarket extends javax.swing.JFrame {
    public Supermarket() {
        initComponents();
    }
    @SuppressWarnings("unchecked")
    private void initComponents() {

        numberOfCheckoutCounters = new javax.swing.JTextField();
        start = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        numberOfCheckoutCounters.setToolTipText("Enter the no. of checkout counters");
        numberOfCheckoutCounters.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
                numberOfCheckoutCountersActionPerformed(evt);
            }
        });

        start.setText("START SIMULATION");
        start.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(123, 123, 123)
                        .addComponent(start))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addComponent(numberOfCheckoutCounters, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(79, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(120, 120, 120)
                .addComponent(numberOfCheckoutCounters, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(start)
                .addContainerGap(118, Short.MAX_VALUE))
        );

        pack();
    }

    private void startActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startActionPerformed
        checkoutCounter = Integer.valueOf(numberOfCheckoutCounters.getText());
        for(int i=0;i<8;i++){
	    lines.add(new LinkedBlockingQueue());
	 }

        Simulate simulate=new Simulate(lines,checkoutCounter);
        Thread threadS = new Thread(simulate);

        Simulator frame = new Simulator(lines,checkoutCounter);
        frame.setVisible(true);
        threadS.start();
        for(int i=0;i<checkoutCounter;i++){
            SimulateCustomer simulateCustomer = new SimulateCustomer(lines.get(i),i,checkoutCounter);
            Thread threadC = new Thread(simulateCustomer);
            threadC.start();
        }
    }

    private void numberOfCheckoutCountersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numberOfCheckoutCountersActionPerformed
    }

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Supermarket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Supermarket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Supermarket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Supermarket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Supermarket().setVisible(true);
            }
        });
    }
    static int checkoutCounter;
    static ArrayList <BlockingQueue<Customer>> lines=new ArrayList<BlockingQueue<Customer>>();
    private javax.swing.JTextField numberOfCheckoutCounters;
    private javax.swing.JButton start;
}
class Simulate extends Thread{
    private long start = System.currentTimeMillis();
    Stats stats;
    Random random = new Random();
    ArrayList<BlockingQueue<Customer>> lines;
    int checkoutCounter;
    public Simulate(ArrayList<BlockingQueue<Customer>> nLine, int checkoutCounter){
    	this.lines=nLine;
    	this.checkoutCounter=checkoutCounter;
    	stats = new Stats(checkoutCounter);
    }
    public void run(){
	while ((System.currentTimeMillis()-start)<=60000L){
            try{
            int waitTime= random.nextInt(60);
            BlockingQueue<Customer> queue=min(lines);
            int items=random.nextInt(199)+1;
            if(queue.size()==6){
                stats.setLostCustomer();
                Thread.sleep(waitTime);

            }
        	else
        	{
        	if(queue==lines.get(0)){
                    if (items<10){
                        Customer customer=new Customer();
                        customer.setItems(items);
                        customer.setStartingTime(System.currentTimeMillis());
                        queue.put(customer);
                        Thread.sleep(waitTime+100);
                    }
                    else{

                    }
        	}
                    else{
                        Customer customer=new Customer();
            		customer.setItems(items);
            		customer.setStartingTime(System.currentTimeMillis());
                        queue.put(customer);
                        Thread.sleep(waitTime+100);
        	}

            }
        }
        catch (InterruptedException ex) {
            System.out.println(ex);
        }
    }


}
	 public BlockingQueue<Customer> min(ArrayList<BlockingQueue<Customer>> nLine)
	 {
            ArrayList<BlockingQueue<Customer>> mLine=nLine;
            BlockingQueue<Customer> min=nLine.get(0);
            for(int i=0;i<mLine.size();i++){
                if(mLine.get(i).size()<min.size()){
                    min=mLine.get(i);
                    }
		 }
		 return min;
	 }
}

class SimulateCustomer extends Thread
{
	private long start = System.currentTimeMillis();
	BlockingQueue<Customer> line;
	Stats stats;
	Random random = new Random();
	int rd,time;
	long totalTimeTaken;
	int checkoutCounter;
	int totalCashier;
	public SimulateCustomer(BlockingQueue<Customer> lines, int checkoutCounter, int totalCashier)
	{
		this.line=lines;
		this.checkoutCounter=checkoutCounter;
		this.totalCashier=totalCashier;
		stats =new Stats(totalCashier);
	}
	public void run() {
	while ((System.currentTimeMillis()-start)<=60000L)
    {
        try
        {
            rd=random.nextInt(5)+1;
            Customer customer = line.take();
            time=rd*(customer.getItems());
            totalTimeTaken=System.currentTimeMillis()-customer.getStartingTime();
            stats.setUtilization(checkoutCounter);
            stats.setWaitingTime(totalTimeTaken);
            stats.setTotalCustomersServed();
            stats.setTotalProductsProcessed(customer.getItems());
            Thread.sleep(time*10+40);

        }
        catch (InterruptedException exception) {
            System.out.println(exception);
        }
    }
	}
}
