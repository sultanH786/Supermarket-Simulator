package supermarket;

import supermarket.Customer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.Timer;

public class Simulator extends JFrame {
    ArrayList<BlockingQueue<Customer>> lines;
    Stats stats;
    public static Timer timer;
    public static JPanel clerkPanel[];
    public int checkoutCounter;
    public final static int maxQueueSize = 7;

    final JLabel statsPanelLabel = new JLabel(" ");
    final JLabel statsPanelLabel1 = new JLabel(" ");
    final JLabel totalProcessedItems=new JLabel(" ");

    JPanel checkOutsPanel;
    JPanel queue[] ;
    JPanel queueLines[][];
    JPanel statsPanel=new JPanel();
    Sections section[][];
    final JLabel utilization[];

public Simulator(ArrayList<BlockingQueue<Customer>> nLine,int checkoutCounter) {
    this.lines=nLine;
    this.checkoutCounter=checkoutCounter;
    queue = new JPanel[checkoutCounter];
    stats=new Stats(checkoutCounter);
    queueLines = new JPanel[checkoutCounter][maxQueueSize];
    section=new Sections[checkoutCounter][maxQueueSize];
    utilization=new JLabel[checkoutCounter];
    createFrame();
    createView();
    timer = new Timer(50, new TimerListener());
    timer.start();
}
public void createFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setVisible(true);
}
public void createView()
{
    checkOutsPanel = new JPanel();

    JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    sp.setResizeWeight(0.7);
    checkOutsPanel.setLayout(new GridLayout(checkoutCounter + 1, 1, 1, 1));
    clerkPanel = new JPanel[checkoutCounter];
    for (int count = 0; count < checkoutCounter; count++) {
            clerkPanel[count] = new JPanel();
            clerkPanel[count].setBorder(BorderFactory.createLineBorder(Color.black));
            clerkPanel[count].add(new JLabel("Checkout " + (count + 1)));
            queue[count] = new JPanel();
            queue[count].setLayout(new GridLayout(1, maxQueueSize + 1, 2, 1));
            queue[count].setBorder(BorderFactory.createLineBorder(Color.black));
            queue[count].add(clerkPanel[count]);

            for (int i = 0; i < maxQueueSize; i++) {
                    queueLines[count][i] = new JPanel();
                    queueLines[count][i].setBorder(BorderFactory.createLineBorder(Color.black));
                    queue[count].add(queueLines[count][i]);
            }

            checkOutsPanel.add(queue[count]);
    }


    statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));

    sp.add(checkOutsPanel);
    sp.add(statsPanel);
    getContentPane().add(sp, BorderLayout.CENTER);
    statsPanel.add(statsPanelLabel);
    statsPanel.add(Box.createVerticalStrut(20));
    statsPanel.add(statsPanelLabel1);
    statsPanel.add(Box.createVerticalStrut(20));
    statsPanel.add(totalProcessedItems);
    for(int i=0;i<checkoutCounter;i++)
    {
            utilization[i]=new JLabel();
            statsPanel.add(Box.createVerticalStrut(20));
    statsPanel.add(utilization[i]);
    }
    statsPanel.add(Box.createVerticalStrut(20));
    for (int count = 0; count < checkoutCounter; count++) {
            for (int i = 0; i < maxQueueSize; i++) {
                    section[count][i] = new Sections();
                    queueLines[count][i].add(section[count][i]);
            }
    }
}
public void showCustomer() {
    Object [] arr = null;
        for (int count = 0; count < checkoutCounter; count++) {
            for (int i = 0; i < maxQueueSize; i++) {
		try {
                    if (lines.get(count)!=null){
			arr =  lines.get(count).toArray();
			section[count][i].setCustomer((((Customer) arr[i]).getItems()));
                    }
		} catch (IndexOutOfBoundsException e) {
                    section[count][i].setCustomer(0);
		}
	}
    }
}

public void displayStats()
{
    statsPanelLabel1.setText("TOTAL LOST CUSTOMERS: " + (stats.getLostCustomer()));
    statsPanelLabel.setText("TOTAL CUSTOMERS SERVERD: " + (stats.getTotalCustomersServed()));
    totalProcessedItems.setText("TOTAL PRODUCTS PROCESSED: "+(stats.getTotalProductsProcessed()));
    int[] a=stats.getUtilization();
    for(int i=0;i<checkoutCounter;i++)
    {

            utilization[i].setText("CHECKOUT COUNTER " +String.valueOf(i+1)+" UTILIZATION: " +a[i]);
    }
}

    private class TimerListener implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                    showCustomer();
                    displayStats();
                    checkOutsPanel.revalidate();
                    checkOutsPanel.repaint();
            }
    }

}
