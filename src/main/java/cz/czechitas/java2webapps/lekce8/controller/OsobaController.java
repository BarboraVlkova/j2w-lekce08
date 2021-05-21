package cz.czechitas.java2webapps.lekce8.controller;

import cz.czechitas.java2webapps.lekce8.entity.Osoba;
import cz.czechitas.java2webapps.lekce8.repository.OsobaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class OsobaController {

    /**
     * budu potrebovat repository, vytvorim fieldy pro typ OsobaRepository
     * vytvorim construktor (zcervena to), kde bude jako parametr repository
     * doplnim @Autowired
     */

    private final OsobaRepository repository;


    @Autowired
    public OsobaController(OsobaRepository repository) {
        this.repository = repository;
    }


    private final List<Osoba> seznamOsob = List.of(
            new Osoba(1L, "Božena", "Němcová", LocalDate.of(1820, 2, 4), "Vídeň", null, null)
    );


    @InitBinder
    public void nullStringBinding(WebDataBinder binder) {
        //prázdné textové řetězce nahradit null hodnotou
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }


    @GetMapping("/")
    public Object seznam() {
        return new ModelAndView("seznam")
                .addObject("osoby", repository.findAll());
    }


    //TODO nacist seznam osob
    @GetMapping("/novy")
    public Object novy() {
        return new ModelAndView("detail")
                .addObject("osoba", new Osoba());
    }


    //TODO uložit údaj o nové osobě
    @PostMapping("/novy")
    public Object pridat(@ModelAttribute("osoba") @Valid Osoba osoba, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "detail";
        }
        osoba.setId(null);
        repository.save(osoba);
        return "redirect:/";
    }


    //TODO načíst údaj o osobě
    @GetMapping("/{id:[0-9]+}")
    public Object detail(@PathVariable long id) {
        Optional<Osoba> osoba = repository.findById(id);
        if (osoba.isPresent()) {
            return new ModelAndView("detail")
                    .addObject("osoba", osoba.get());
        }
        return null;
    }


    //TODO uložit údaj o osobě
    @PostMapping("/{id:[0-9]+}")
    public Object ulozit(@PathVariable long id, @ModelAttribute("osoba") @Valid Osoba osoba, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "detail";
        }
        osoba.setId(id);
        repository.save(osoba);
        return "redirect:/";
    }


    //TODO smazat údaj o osobě
    @PostMapping(value = "/{id:[0-9]+}", params = "akce=smazat")
    public Object smazat(@PathVariable long id) {
        repository.deleteById(id);
        return "redirect:/";
    }
}
