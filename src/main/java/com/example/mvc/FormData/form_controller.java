package com.example.mvc.FormData;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/form")
public class form_controller {
    
    @RequestMapping(method = RequestMethod.GET)
    public String form_get(Model model) {
        model.addAttribute("test", new form_data());
        return "form/form_post";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String form_post(@RequestParam String title, @RequestParam String data, Model model) {
        model.addAttribute("test", new form_data(title, data));
       return "form/form_get";
    }


}
