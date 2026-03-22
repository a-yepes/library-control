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
        //compruebo que hay 1 argumento solo
        if (args.length != 1) {
            throw new RuntimeException("Solo debes pasar un argumento");
        }

        Path path = Path.of(args[0]); // pasa el txt a un objeto Path

        //compruebo que el archivo existe
        if (!Files.exists(path)) {
            throw new RuntimeException("El archivo no existe o no es .txt");
        }

        return path; //devuelve la ruta si el archivo existe
    }

    //MAIN
    public static void main(String[] args) {
        Path p = getPathFromArgs(args);
        Library controler = Library.fromFile(p);
        controler.printResume();
    }
}

