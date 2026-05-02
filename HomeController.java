package com.example.jeu;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class HomeController {
    @Autowired
    PlayerRepository repo;
    String joueurNom = "";
    int nombreSecret = 0;
    int tentatives = 0;
    java.util.Map<String, Integer> savedSecrets = new java.util.HashMap<>();
    java.util.Map<String, Integer> savedTentativesMap = new java.util.HashMap<>();
    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/play")
    public String play(@RequestParam String level, Model model) {

        Random random = new Random();
        tentatives = 0;

        if (level.equals("easy")) {
            nombreSecret = random.nextInt(50) + 1;
            model.addAttribute("message", "Niveau Facile : devine entre 1 et 50");
        }

        if (level.equals("medium")) {
            nombreSecret = random.nextInt(100) + 1;
            model.addAttribute("message", "Niveau Moyen : devine entre 1 et 100");
        }

        if (level.equals("hard")) {
            nombreSecret = random.nextInt(200) + 1;
            model.addAttribute("message", "Niveau Difficile : devine entre 1 et 200");
        }
        model.addAttribute("tries", tentatives);
        return "play";
    }


    @PostMapping("/guess")
    public String guess(@RequestParam int number, Model model) {

        tentatives++;

        if (number < nombreSecret) {
            model.addAttribute("message", "Trop petit !");
        } else if (number > nombreSecret) {
            model.addAttribute("message", "Trop grand !");
        } else {
            int score = 100 - (tentatives * 10);

            if(score < 10){
                score = 10;
            }

            Player p = new Player();
            p.setNom(joueurNom);
            p.setScore(score);

            repo.save(p);

            model.addAttribute("message",
                    "Bravo " + joueurNom + " 🎉 Score : " + score);
        }

        // ✅ AJOUT IMPORTANT
        model.addAttribute("tries", tentatives);

        return "play";
    }
    
    @GetMapping("/save")
    public String save(Model model) {

        savedSecrets.put(joueurNom, nombreSecret);
        savedTentativesMap.put(joueurNom, tentatives);

        model.addAttribute("message", "Partie sauvegardée 💾");
        model.addAttribute("tries", tentatives);

        return "play";
    }

    @GetMapping("/load")
    public String load(Model model) {

        if(!savedSecrets.containsKey(joueurNom)){
            model.addAttribute("message", "Aucune partie sauvegardée ❌");
            return "level";
        }

        nombreSecret = savedSecrets.get(joueurNom);
        tentatives = savedTentativesMap.get(joueurNom);

        model.addAttribute("message", "Partie reprise ▶️");
        model.addAttribute("tries", tentatives);

        return "play";
    }

    @PostMapping("/player")
    public String player(@RequestParam String nom, Model model) {

        joueurNom = nom;

        model.addAttribute("nom", joueurNom);

        return "level";
    }
    @GetMapping("/top")
    public String top(Model model){

        model.addAttribute("players",
                repo.findTop10ByOrderByScoreDesc());

        return "top";
    }
}