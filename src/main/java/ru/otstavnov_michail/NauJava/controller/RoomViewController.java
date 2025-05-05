package ru.otstavnov_michail.NauJava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.otstavnov_michail.NauJava.repository.RoomRepository;

@Controller
@RequestMapping("/custom/rooms/view")
public class RoomViewController {

    @Autowired
    private RoomRepository roomRepository;

    @GetMapping("/list")
    public String roomListView(Model model) {
        model.addAttribute("rooms", roomRepository.findAll());
        return "roomList";
    }
}
