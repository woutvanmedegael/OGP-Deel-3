import java.util.ArrayList;

import Activity.Activity;
import hillbillies.model.scheduler.Scheduler;
import hillbillies.model.scheduler.Task;

public class test {

	public static void main(String[] args) {
		ArrayList<Activity> activities = new ArrayList<Activity>();
		activities.add(new Activity());
		Scheduler scheduler = new Scheduler();
		Task task1 = new Task("abc",5,activities);
		Task task2 = new Task("def",10,activities);
		Task task3 = new Task("ghi",1,activities);
		scheduler.add(task1);
		System.out.println(scheduler.getTasks());
		scheduler.add(task2);
		System.out.println(scheduler.getTasks());
		scheduler.add(task3);
		System.out.println(scheduler.getTasks());
		System.out.println(scheduler.getHighestPrio());
		
	}

}
