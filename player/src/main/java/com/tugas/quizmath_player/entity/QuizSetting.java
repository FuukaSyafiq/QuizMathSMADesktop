package com.tugas.quizmath_player.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "quiz_setting")
public class QuizSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "time_limit_minutes", nullable = false)
    private int timeLimitMinutes = 10;

    public QuizSetting() {
    }

    public QuizSetting(int timeLimitMinutes) {
        this.timeLimitMinutes = timeLimitMinutes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTimeLimitMinutes() {
        return timeLimitMinutes;
    }

    public void setTimeLimitMinutes(int timeLimitMinutes) {
        this.timeLimitMinutes = timeLimitMinutes;
    }
}
