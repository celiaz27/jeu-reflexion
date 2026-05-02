package com.example.jeu;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nom;
    private int score;
    private int savedSecret;
    private int savedTentatives;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {   // ✅ j’ajoute aussi ça (important)
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getSavedSecret() {
        return savedSecret;
    }

    public void setSavedSecret(int savedSecret) {
        this.savedSecret = savedSecret;
    }

    public int getSavedTentatives() {
        return savedTentatives;
    }

    public void setSavedTentatives(int savedTentatives) {
        this.savedTentatives = savedTentatives;
    }
}