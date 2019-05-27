package com.tuzserik.github.shorties.client;

import java.io.*;
import java.net.Socket;

class Pitcher {
    private FileReader fromFile;
    private FileWriter toFile;
    private ObjectInputStream from;
    private ObjectOutputStream to;

    Pitcher(Socket socket) throws IOException{
        from = new ObjectInputStream(socket.getInputStream());
        to = new ObjectOutputStream(socket.getOutputStream());
        try {
            fromFile = new FileReader("com/tuzserik/github/shorties/client/collectionStorage.csv");
            toFile = new FileWriter("com/tuzserik/github/shorties/client/collectionStorage.csv");
        }
        catch (FileNotFoundException e){
            System.err.println("Фаил не был найден!");
            System.exit(-3);
        }
        catch (IOException e){
            e.printStackTrace();
            System.exit(-4);
        }
    }



    void pitch(String s) throws IOException {
        to.writeUTF(s);
        to.flush();
        System.out.println(from.readUTF());
    }

    void complexPitch(String s) throws IOException {
        to.writeUTF(s);
        to.flush();
        toFile.write(from.readUTF());
        toFile.flush();
    }

    void importPitch(String s) throws IOException {
        to.writeUTF(s+fromFile.read());
        to.flush();
    }

    void closeAll() throws IOException{
        fromFile.close();
        toFile.close();
    }
}
//+