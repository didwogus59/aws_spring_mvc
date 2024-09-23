package com.example.mvc.mongodb;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/r2dbc")
public class react_controller {
    @GetMapping("")
    public String all_data() {
        return "db/r2dbcBoard";
    }

    @GetMapping("/{id}")
    public String all_data(@PathVariable("id") String id, Model model) {
        model.addAttribute("id", id);
        return "db/r2dbcDetail";
    }
}
