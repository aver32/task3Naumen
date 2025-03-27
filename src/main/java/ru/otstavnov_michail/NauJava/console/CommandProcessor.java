package ru.otstavnov_michail.NauJava.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.otstavnov_michail.NauJava.service.IGuestService;

@Component
public class CommandProcessor {

    private final IGuestService guestService;

    @Autowired
    public CommandProcessor(IGuestService guestService) {
        this.guestService = guestService;
    }

    public void processCommand(String input) {
        String[] cmd = input.split(" ");
        try {
            switch (cmd[0].toLowerCase()) {
                case "create" -> {
                    Long id = Long.valueOf(cmd[1]);
                    String name = cmd[2];
                    String roomNumber = cmd[3];
                    String email = cmd[4];
                    guestService.createGuest(id, name, roomNumber, email);
                    System.out.println("Гость успешно добавлен.");
                }
                case "get" -> {
                    Long id = Long.valueOf(cmd[1]);
                    System.out.println(guestService.getGuestById(id));
                }
                case "update" -> {
                    Long id = Long.valueOf(cmd[1]);
                    String newName = cmd[2];
                    int newRoomNumber = Integer.parseInt(cmd[3]);
                    guestService.updateGuest(id, newName, newRoomNumber);
                    System.out.println("Информация о госте обновлена.");
                }
                case "delete" -> {
                    Long id = Long.valueOf(cmd[1]);
                    guestService.deleteGuestById(id);
                    System.out.println("Гость удалён.");
                }
                default -> System.out.println("Неизвестная команда. Доступные команды: create, get, list, update, delete, exit.");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Ошибка: недостаточно параметров для команды.");
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: неверный формат числового значения.");
        }
    }
}
