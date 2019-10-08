import com.lvgo.unname.SyncRunList;
import com.lvgo.unname.ThreadPoolFactory;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

public class Main {

    public static void main(String[] args) {
        ExecutorService threadPool = ThreadPoolFactory.newThreadPool();


        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            strings.add("第" + i + "个");
        }
        new SyncRunList<String>(strings) {
            /**
             * 单元素任务执行
             *
             * @param task 元素
             */
            @Override
            protected void taskRun(String task) {
                System.out.println("task = " + task);
            }
        }.execute(threadPool, 5);
        System.out.println("strings = " + strings);
    }
}
