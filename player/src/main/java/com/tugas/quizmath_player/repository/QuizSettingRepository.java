package com.tugas.quizmath_player.repository;

import com.tugas.quizmath_player.entity.QuizSetting;
import com.tugas.quizmath_player.database.Database;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class QuizSettingRepository {

    // Returns the quiz time limit in minutes. Defaults to 10 if none found.
    public int getQuizTimeLimit() {
        try (Session session = Database.getSessionFactory().openSession()) {
            List<QuizSetting> settings = session.createQuery("FROM QuizSetting", QuizSetting.class).list();
            if (settings != null && !settings.isEmpty()) {
                return settings.get(0).getTimeLimitMinutes();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 10; // Default fallback fallback
    }

    // Updates or creates the quiz time limit
    public void saveOrUpdateQuizTimeLimit(int minutes) {
        Transaction tx = null;
        try (Session session = Database.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            List<QuizSetting> settings = session.createQuery("FROM QuizSetting", QuizSetting.class).list();
            if (settings != null && !settings.isEmpty()) {
                QuizSetting setting = settings.get(0);
                setting.setTimeLimitMinutes(minutes);
                session.merge(setting);
            } else {
                QuizSetting setting = new QuizSetting(minutes);
                session.persist(setting);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }
}
