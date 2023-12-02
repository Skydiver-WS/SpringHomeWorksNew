package com.example.webappliststudents.controller;


import com.example.webappliststudents.model.Student;
import com.example.webappliststudents.service.StudentsListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final StudentsListService listService;
    @GetMapping("/")
    public String getAllListStudent(Model model){
        model.addAttribute("list", listService.findAll());
        return "index";
    }

    @PostMapping("/student/create")
    public String createStudent(@ModelAttribute Student student){
        listService.save(student);
        return "redirect:/";
    }

    @DeleteMapping("/student/delete")
    public String deleteStudent(@PathVariable Long id){
        listService.delete(id);
        return "redirect:/";
    }

    @PostMapping("/student/update")
    public String editStudent(@PathVariable Student student){
        listService.update(student);
        return "redirect:/";
    }




}
