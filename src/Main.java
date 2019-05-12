import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Socket socket = null;
        Pitcher pitcher = null;
        Scanner scanner = new Scanner(System.in);
        boolean isCommand;

        try {
            socket = new Socket("localhost", 2038);
            pitcher = new Pitcher(socket);
            System.out.println("Соединение успешно установленно!");
        } catch (IOException e){System.err.println("Не удалось установить соединение с сервером :(");}

        try {
            while (true) {
                isCommand = false;
                System.out.print("Введите команду: ");
                String input = scanner.nextLine();

                if (input.equals("quit")){
                    isCommand = true;
                    System.out.println("Вы желаете выйти без сохранения изменений?");
                    System.out.println("Для установки текущей коллекции в качестве коллекции по-умолчанию на сервере введите \"overwrite\"");
                    System.out.println("Для сохранения текущей коллекции на устройстве введите \"save\"");
                    System.out.println("Для выполнения обоих действий введите \"saveAll\"");
                    System.out.println("Для выхода без сохранения введите \"quit\"");

                    input = scanner.nextLine();
                    if (input.equals("overwrite")){
                        assert pitcher != null;
                        pitcher.pitch(input);
                    }
                    if (input.equals("save")){
                        assert pitcher != null;
                        pitcher.complexPitch(input);
                    }
                    if (input.equals("saveAll")){
                        assert pitcher != null;
                        pitcher.pitch("overwrite");
                        pitcher.complexPitch("save");
                    }
                    if (input.equals("quit")){
                        break;
                    }

                    System.out.println("Для завершения работы только клиента введите \"quit\"");
                    System.out.println("Для завершения работы и клиента и сервера введите \"quitAll\"");

                    input = scanner.nextLine();
                    if (input.equals("quitAll")){
                        assert pitcher != null;
                        pitcher.pitch(input);
                    }
                    if (input.contains("quit")){
                        System.out.println("Работа клиента заверщена!");
                        break;
                    }
                }

                if(input.equals("help")){
                    isCommand = true;
                    System.out.println("Список доступных команд");
                    System.out.println("import");
                    System.out.println("initialize");
                    System.out.println("load");
                    System.out.println("start");
                    System.out.println("add <object>");
                    System.out.println("remove <object>");
                    System.out.println("add_if_max <object>");
                    System.out.println("remove_last");
                    System.out.println("show");
                    System.out.println("info");
                    System.out.println("quit");
                }

                if ((input.equals("info")) || (input.equals("show")) ||
                        (input.equals("initialize")) || (input.equals("load")) ||
                        (input.equals("remove_last")) || (input.equals("start")) ||
                        (input.contains(" "))){
                    isCommand = true;
                    assert pitcher != null;
                    pitcher.pitch(input);
                }

                if (input.equals("import")){
                    isCommand = true;
                    assert pitcher != null;
                    pitcher.importPitch(input);
                }

                if (!isCommand){
                    System.out.println("Введенное сочетание не является командой");
                    System.out.println("Введите \"help\" чтобы получить список команд");
                }
            }
            assert socket != null;
            assert pitcher != null;
            pitcher.closeAll();
            scanner.close();
            socket.close();
        } catch (IOException e){e.printStackTrace();}
    }
}
//++