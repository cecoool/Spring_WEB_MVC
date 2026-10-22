package com.example.controller;

import com.example.model.StudentRepository;
import java.sql.SQLException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class StudentController {
    private final StudentRepository repository;

    public StudentController(StudentRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public String index() { return "index"; }

    @GetMapping("/create")
    public String createPage() { return "create"; }

    @GetMapping("/read")
    public String readPage() { return "read"; }

    @GetMapping("/update")
    public String updatePage() { return "update"; }

    @GetMapping("/delete")
    public String deletePage() { return "delete"; }

    @PostMapping("/create")
    public String create(@RequestParam(defaultValue = "") String name, Model model) {
        name = name.trim();
        if (name.isBlank() || name.length() > 100) {
            model.addAttribute("message", "Въведете име от 1 до 100 символа.");
            return "create";
        }
        try {
            boolean saved = repository.save(name);
            model.addAttribute("message", saved ? "Ученикът е добавен." : "Няма добавен ученик.");
        } catch (SQLException e) {
            e.printStackTrace();
            model.addAttribute("message", "Грешка при работа с базата. Вижте терминала.");
        }
        return "create";
    }

    @GetMapping("/students")
    public String read(Model model) {
        try {
            model.addAttribute("students", repository.findAll());
        } catch (SQLException e) {
            e.printStackTrace();
            model.addAttribute("message", "Грешка при работа с базата. Вижте терминала.");
        }
        return "read";
    }

    @PostMapping("/update")
    public String update(@RequestParam String id,
                         @RequestParam String name, Model model) {
        name = name.trim();
        if (name.isBlank() || name.length() > 100) {
            model.addAttribute("message", "Въведете име от 1 до 100 символа.");
            return "update";
        }
        try {
            int studentId = Integer.parseInt(id);
            boolean updated = repository.update(studentId, name);
            if (updated) {
                model.addAttribute("message", "Името е променено.");
            } else {
                model.addAttribute("message", "Ученикът не е намерен.");
            }
        }catch (SQLException e) {
            e.printStackTrace();
            model.addAttribute("message", "Грешка при работа с базата. Вижте терминала.");
        }
        return "update";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam(defaultValue = "") String id, Model model) {
        try {
            int studentId = Integer.parseInt(id);
            if (studentId <= 0) throw new NumberFormatException();
            boolean deleted = repository.delete(studentId);
            model.addAttribute("message", deleted ? "Ученикът е изтрит." : "Ученикът не е намерен.");
        } catch (NumberFormatException e) {
            model.addAttribute("message", "Въведете положително цяло число за ID.");
        } catch (SQLException e) {
            e.printStackTrace();
            model.addAttribute("message", "Грешка при работа с базата. Вижте терминала.");
        }
        return "delete";
    }
}
