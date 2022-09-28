package setup;

import java.util.Random;

/** RandomHello selects and prints a random greeting. */
public class RandomHello {

    /**
     * Prints a random greeting to the console.
     *
     * @param args command-line arguments (ignored)
     */
    public static void main(String[] args) {
        RandomHello randomHello = new RandomHello();
        System.out.println(randomHello.getGreeting());
    }

    /** @return a greeting, randomly chosen from five possibilities */
    public String getGreeting() {
        // set Random # generator
        Random randomGenerator = new Random();

        // set array of greetings
        String[] greetings = new String[5];
        greetings[0] = "Hello World";
        greetings[1] = "Hola Mundo";
        greetings[2] = "Bonjour Monde";
        greetings[3] = "Hallo Welt";
        greetings[4] = "Ciao Mondo";

        // set random int from 0 (incl) to size of array (excl)
        int n = randomGenerator.nextInt(greetings.length);
        return greetings[n];
    }
}