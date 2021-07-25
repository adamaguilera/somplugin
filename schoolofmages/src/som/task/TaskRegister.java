package som.task;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;
import som.Main;

import java.util.ArrayList;
import java.util.List;

public class TaskRegister {
    final List<Task> tasks = new ArrayList<>();
    final BukkitScheduler scheduler = Bukkit.getScheduler();
    final long TASK_DELAY = 0L;
    final long TASK_PERIOD = 10L;
    int taskID;

    public int start() {
       this.taskID = scheduler.scheduleSyncRepeatingTask(
               Main.GET_INSTANCE(),
               this::onTick,
               TASK_DELAY,
               TASK_PERIOD);
       return taskID;
    }

    public void stop () {
        scheduler.cancelTask(this.taskID);
    }

    public void onTick () {
        tasks.forEach(Task::onTick);
    }

    public boolean addTask (final Task task) {
        return tasks.add(task);
    }

    public boolean removeTask (final Task task) {
        return tasks.remove(task);
    }

}
