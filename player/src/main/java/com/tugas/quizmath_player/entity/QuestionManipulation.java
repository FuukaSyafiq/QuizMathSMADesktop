/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tugas.quizmath_player.entity;

import java.util.List;

/**
 *
 * @author syafiq
 */
public class QuestionManipulation {
    public int id;
    public String question_text;
    public String question_image;
    public String answer_type;
    public String level;
    public String topic;
    public List<Answer> answers;

    // Constructor dengan semua parameter
    public QuestionManipulation(int id, String question_text, String question_image,
                    String answer_type,
                    String level, String topic, List<Answer> answers) {
        this.id = id;
        this.question_text = question_text;
        this.question_image = question_image;
        this.answer_type = answer_type;
        this.level = level;
        this.topic = topic;
        // kalau null, pakai list kosong biar aman
        this.answers = answers;
}
}
