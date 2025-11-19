/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tugas.quizmath_player.entity;

/**
 *
 * @author syafiq
 */
public class Answer {
    public int id;
    public String answer;
    public String label;
    public int score;
    public boolean correct;
    public String image_answer;
    
    // Constructor lengkap
    public Answer(int id, String answer, String label, int score, boolean correct, String image_path) {
        this.id = id;
        this.answer = answer;
        this.score = score;
        this.label = label;
        this.image_answer = image_path;
        this.correct = correct;
    }
    public Answer(String answer, String label, int score, boolean correct, String image_path) {
        this.id = 0;
        this.answer = answer;
        this.score = score;
        this.image_answer = image_path;
        this.label = label;
        this.correct = correct;
    }
}
