package world.hello.com.start.tasks;

/**
 * Created by a Jedi Master.
 */
public interface TaskHandler {
    void onPreExecute();
    void onResult(Object result);
}
