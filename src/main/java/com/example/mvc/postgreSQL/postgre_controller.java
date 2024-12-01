package com.example.mvc.postgreSQL;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.example.mvc.postgreSQL.dto.board_dto;
import com.example.mvc.postgreSQL.dto.create_dto;
import com.example.mvc.postgreSQL.dto.data_dto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequestMapping("/postgre")
@Slf4j
public class postgre_controller {
    
    @Autowired
    postgre_service service;

    @GetMapping("")
    public String main_board_page(
        @RequestParam(name = "title", required = false) String title,
        @RequestParam(name = "page", defaultValue = "0") int page,
        @RequestParam(name = "size", defaultValue = "5") int size,
        @RequestParam(name = "sort", defaultValue = "createdAt") String[] sorts,
        Model model
    ) {

        List<Sort.Order> orders = new ArrayList<>();
        for(String sort : sorts) {
            if(sort.equals("createdAt")) {
                orders.add(Sort.Order.desc(sort));
            }
            else
                orders.add(Sort.Order.asc(sort));
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(orders));
        Page<board_dto> dataPage = service.all_data2(pageable, title);
        int totalPage = dataPage.getTotalPages();
        page++;
        if(page > totalPage) {
            page = totalPage;
        }

        model.addAttribute("testList", dataPage);
        model.addAttribute("page", page);
        model.addAttribute("totalPage", totalPage);
        log.info("{}",dataPage.getTotalPages());
        
        return "db/postgreboard";
    }
    @RequestMapping(method = RequestMethod.POST)
    public String create_data(@ModelAttribute create_dto data, Model model, Authentication auth) {
        if(auth != null) {
            service.create_data(data, auth.getName());
        }
        else {
            service.create_data(data, null);
        }
        return "redirect:/postgre";
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public String detail_data(@PathVariable(name = "id") Long id, Model model) {

        data_dto detail = service.get_data(id);
        model.addAttribute("detail", detail);
        model.addAttribute("id", id);
        return "db/postgreDetail";
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.POST)
    public String update_data(@PathVariable(name = "id") Long id, @ModelAttribute data_dto data, Model model,Authentication auth) {
        log.info("in update");
        data_dto detail;
        if(auth != null)
            detail  = service.update_data(id, data,auth.getName());
        else
            detail  = service.update_data(id, data,null);
        model.addAttribute("detail", detail);
        return "db/postgreDetail";
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public String delete_data(@PathVariable(name = "id") Long id, Model model, Authentication auth) {
        
        log.info("in delete");
        if(auth != null) {
            service.delete_data(id, auth.getName());
        }
        else {
            service.delete_data(id, null);
        }
        return "redirect:/postgre";
    }
    
//     @GetMapping("/testcase")
//     public String getMethodName() {
//         Random random = new Random();
//         String[] name = {null,"1111","2222","3333","4444"};
//         for(int i = 0; i < 5; i++) {
//
//             for(int j = 0; j < 2000; j++) {
//                 String tmp = "";
//                 for(int p = 0; p < 5; p++) {
//                     tmp += (char)('a' + random.nextInt(26));
//                 }
//                 create_dto data = new create_dto(tmp, "sample");
//                 service.create_data(data, name[i]);
//             }
//         }
//         return "home";
//     }
//
}
