import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.RunnableFuture;

public class Responsive implements Runnable {

    private final int taskLength;
    private final int taskNumber;
    TaskFinished fin = new TaskFinished();


    public Responsive(int taskLength, int taskNumber) {
        this.taskLength = taskLength;
        this.taskNumber = taskNumber;
    }



    public void launch() {
        int count = 1;


        while (count <= 10) {
            System.out.println("Please enter the length of task " + count + " in ms: ");
            Scanner scan = new Scanner(System.in);
            int taskLength = scan.nextInt();
            Runnable r = new Responsive(taskLength, count);
            Thread t = new Thread(r);
            t.start();
            count++;
            fin.printFinishedTasks();
        }
        while (fin.count > 0) {
            fin.printFinishedTasks();
        }
    }

    public void run(){
        try {
            Thread.sleep(this.taskLength);
        } catch (InterruptedException ex) {

        }
        fin.taskFinished(this.taskNumber);
    }

    public static void main(String[] args) {
        Responsive start = new Responsive(0 ,0);
        start.launch();
    }
}

class TaskFinished {
    private static List<Integer> finishedTasks = new ArrayList<>();
    protected static int count = 10;

    public synchronized void taskFinished(int taskNumber) {
        finishedTasks.add(taskNumber);
    }

    public synchronized void printFinishedTasks() {
        if (finishedTasks.size() > 0) {
            System.out.println("Finished tasks: " + finishedTasks);
            count -= finishedTasks.size();
            finishedTasks.clear();

        }
    }
}
