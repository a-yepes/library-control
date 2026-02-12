package pio.daw;

import java.nio.file.Files;
import java.nio.file.Path;



public class App {
    /**
     * Parse the arguments of the program to get the library registry file
     * path. Exits the program if the args are not correct or the file does
     * not exists.
     * @param args program args.
     * @return Path to file if exists.
     */
    public static Path getPathFromArgs(String[] args){
        Path path = Path.of(args[0]); // pasa el txt a un objeto Path

        //compruebo que el archivo existe y es un .txt
        if (!Files.exists(path) || !path.toString().endsWith(".txt")) {
            System.err.println("El archivo no existe o no es .txt");
            System.exit(1);
        }

    return path; //devuelve la ruta si el archivo existe 
}



//MAIN
    public static void main(String[] args) {
        Path p = getPathFromArgs(args);
        Controlable controler = Library.fromFile(p);
        controler.printResume();
    }
}


