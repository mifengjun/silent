import com.lvgo.unname.ConcurrentListTaskHandler;

import java.util.ArrayList;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i <= 10; i++) {
            strings.add("µÚ" + i + "¸ö");
        }
        new ConcurrentListTaskHandler<String>(null) {
            /**
             * execute task
             *
             * @param s task element
             */
            @Override
            protected void run(String s) {

            }

            /**
             * execute task with param
             *
             * @param s         task element
             * @param mapParams task param
             */
            @Override
            protected void runWithParam(String s, Map<String, Object> mapParams) {

            }
        }.execute(20);
    }
}
