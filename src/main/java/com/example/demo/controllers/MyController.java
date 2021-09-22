package com.example.demo.controllers;

import com.example.demo.PizzaAction;
import com.example.demo.models.Pizza;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MyController {

    @Autowired
    private PizzaAction pizzaAction;

    @GetMapping(path = "/add")
    public @ResponseBody String add(@RequestParam String name, @RequestParam String maker, @RequestParam int size) {
        Pizza pizza = new Pizza();
        pizza.setMaker(maker);
        pizza.setName(name);
        pizza.setSize(size);
        pizzaAction.save(pizza);
        return "Saved!";
    }

    @GetMapping(path = "/getall")
    public @ResponseBody Iterable<Pizza> getAll() {
        return pizzaAction.findAll();
    }

    // 1. Вывод информации из базы в html формате
    // 2. Форму добавления пиццы

    @GetMapping(path = "/getlist")
    public String showList(Model model) {
        Iterable<Pizza> pizzas = pizzaAction.findAll();
        model.addAttribute("pizzas",pizzas);
        return "pizzaList";
    }

    @GetMapping(path = "/addform")
    public String showForm(Model model) {
        Pizza pizzaForm = new Pizza();
        model.addAttribute("pizzaForm",pizzaForm);
        return "pizzaAdd";
    }

    @Value("${error.message}")
    private String errorMessage;

    @PostMapping(path = "/addpizza")
    public String addPizzaAction(Model model, @ModelAttribute("pizzaForm") Pizza p) {

        String name = p.getName();
        String maker = p.getMaker();
        int size = p.getSize();

        if(name != null && name.length()>0 && maker != null && maker.length()>0 &&
                size>0) {
            Pizza pizza = new Pizza();
            pizza.setMaker(maker);
            pizza.setName(name);
            pizza.setSize(size);
            pizzaAction.save(pizza);
            return "redirect:/getlist";
        }

        model.addAttribute("errorMessage", errorMessage);

        return "pizzaAdd";
    }

}
