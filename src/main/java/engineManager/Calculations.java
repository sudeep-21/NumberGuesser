package engineManager;

public class Calculations {
    int number;
    String[] hint = new String[5];
    public void randomGen() {
        int max = 50, min = 1;

        number = (int)(Math.random() * ((max - min + 1) + min));
    }
    public Calculations() {
        randomGen();
    }
}
