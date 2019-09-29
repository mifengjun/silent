public class Main {

    public static void main(String[] args) {
        out:
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                System.out.println("Hello World!");
                continue out;
            }
        }
    }
}
