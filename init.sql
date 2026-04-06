CREATE DATABASE IF NOT EXISTS quizmath;
USE quizmath;

-- =========================
-- TABLE: kelas
-- =========================
CREATE TABLE kelas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    kelas VARCHAR(100) NOT NULL,
    jurusan VARCHAR(100) NOT NULL,
    UNIQUE (kelas)
);

-- =========================
-- TABLE: siswa
-- =========================
CREATE TABLE siswa (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nama VARCHAR(255) NOT NULL,
    kelas_id INT,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    nis VARCHAR(100),
    no_absen INT,
    FOREIGN KEY (kelas_id)
        REFERENCES kelas(id)
        ON UPDATE CASCADE
        ON DELETE SET NULL
);

-- =========================
-- TABLE: question
-- =========================
CREATE TABLE question (
    id INT AUTO_INCREMENT PRIMARY KEY,
    question_text TEXT NOT NULL,
    answer_type ENUM('SINGLE_CHOICES', 'MULTIPLE_CHOICES') NOT NULL DEFAULT 'SINGLE_CHOICES',
    level VARCHAR(50) NOT NULL,
    topic VARCHAR(100) NOT NULL
);

-- =========================
-- TABLE: options_answer
-- =========================
CREATE TABLE options_answer (
    id INT AUTO_INCREMENT PRIMARY KEY,
    question_id INT NOT NULL,
    answer TEXT,
    label VARCHAR(10),
    score INT NOT NULL DEFAULT 0,
    correct BOOLEAN NOT NULL DEFAULT FALSE,

    FOREIGN KEY (question_id) REFERENCES question(id) ON DELETE CASCADE
);

-- =========================
-- TABLE: question_image
-- =========================
CREATE TABLE question_image (
    id INT AUTO_INCREMENT PRIMARY KEY,
    image_path VARCHAR(255) NOT NULL,
    question_id INT NOT NULL,
    FOREIGN KEY (question_id)
        REFERENCES question(id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

-- =========================
-- TABLE: final_score
-- =========================
CREATE TABLE final_score (
    id INT AUTO_INCREMENT PRIMARY KEY,
    siswa_id INT NOT NULL,
    correct_answer INT NOT NULL DEFAULT 0,
    wrong_answer INT NOT NULL DEFAULT 0,
    total_question INT NOT NULL DEFAULT 0,
    final_score INT NOT NULL DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (siswa_id) REFERENCES siswa(id)
);

-- =========================
-- TABLE: admin
-- =========================
CREATE TABLE admin (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =========================
-- TABLE: siswa_answer
-- =========================
CREATE TABLE siswa_answer (
    id INT AUTO_INCREMENT PRIMARY KEY,
    question_answer_id INT,
    siswa_id INT NOT NULL,
    final_score_id INT NOT NULL,
    FOREIGN KEY (question_answer_id)
        REFERENCES options_answer(id)
        ON UPDATE CASCADE
        ON DELETE SET NULL,
    FOREIGN KEY (siswa_id)
        REFERENCES siswa(id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    FOREIGN KEY (final_score_id)
        REFERENCES final_score(id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

-- =========================
-- TABLE: quiz_setting
-- =========================
CREATE TABLE quiz_setting (
    id INT AUTO_INCREMENT PRIMARY KEY,
    time_limit_minutes INT NOT NULL DEFAULT 10
);

-- =========================
-- INSERT DATA
-- =========================
INSERT INTO admin (user, password)
VALUES ('admin', 'admin123');

INSERT INTO quiz_setting (time_limit_minutes) VALUES (10);

INSERT INTO kelas (kelas, jurusan) VALUES
('XII RPL 1', 'REKAYASA PERANGKAT LUNAK'),
('XII RPL 2', 'REKAYASA PERANGKAT LUNAK'),
('XI RPL 1',  'REKAYASA PERANGKAT LUNAK'),
('XI RPL 2',  'REKAYASA PERANGKAT LUNAK');

INSERT INTO siswa (nama, kelas_id, username, password, nis, no_absen) VALUES
('Afiq Pratama', 1, 'afiq01', 'pass123', 'NIS001', 1),
('Budi Santoso', 1, 'budi02', 'pass123', 'NIS002', 2),
('Citra Lestari', 2, 'citra03', 'pass123', 'NIS003', 3),
('Dewi Rahma', 2, 'dewi04', 'pass123', 'NIS004', 4),
('Eko Saputra', 3, 'eko05', 'pass123', 'NIS005', 5),
('Fajar Nugroho', 3, 'fajar06', 'pass123', 'NIS006', 6),
('Gita Amalia', 1, 'gita07', 'pass123', 'NIS007', 7),
('Hadi Wijaya', 2, 'hadi08', 'pass123', 'NIS008', 8),
('Intan Permata', 3, 'intan09', 'pass123', 'NIS009', 9),
('Joko Santika', 1, 'joko10', 'pass123', 'NIS010', 10);

-- =========================
-- INSERT DATA QUESTION
-- =========================
INSERT INTO question (question_text, answer_type, level, topic) VALUES
('Akar-akar dari persamaan kuadrat x^2 - 5x + 6 = 0 adalah...', 'SINGLE_CHOICES', 'MUDAH', 'Persamaan Kuadrat'), -- question_id = 1
('Jika matriks A = [2  1; 4  3], maka determinan matriks A adalah...', 'SINGLE_CHOICES', 'MUDAH', 'Matriks'), -- question_id = 2
('Suku ke-10 dari barisan aritmatika 2, 5, 8, 11, ... adalah...', 'SINGLE_CHOICES', 'MUDAH', 'Barisan Aritmatika'), -- question_id = 3
('Nilai dari 2log(8) + 3log(9) adalah...', 'SINGLE_CHOICES', 'MUDAH', 'Logaritma'), -- question_id = 4
('Diketahui f(x) = 2x + 3 dan g(x) = x - 1. Rumus fungsi komposisi f(g(x)) adalah...', 'SINGLE_CHOICES', 'MUDAH', 'Fungsi Komposisi'), -- question_id = 5

('Nilai dari sin(30°) + cos(60°) adalah...', 'SINGLE_CHOICES', 'NORMAL', 'Trigonometri'), -- question_id = 6
('Nilai dari lim (x→2) dari fungsi (x^2 - 4) / (x - 2) adalah...', 'SINGLE_CHOICES', 'NORMAL', 'Limit Fungsi'), -- question_id = 7
('Turunan pertama dari f(x) = x^3 - 4x^2 + 5x adalah...', 'SINGLE_CHOICES', 'NORMAL', 'Turunan'), -- question_id = 8
('Dua buah dadu dilempar undi bersama-sama. Peluang munculnya jumlah mata dadu 7 adalah...', 'SINGLE_CHOICES', 'NORMAL', 'Peluang'), -- question_id = 9
('Berapakah rata-rata (mean) dari data tunggal berikut: 4, 5, 6, 7, 8?', 'SINGLE_CHOICES', 'NORMAL', 'Statistika'), -- question_id = 10

('Nilai dari ∫ (dari 0 sampai 2) (3x^2 - 2x + 1) dx adalah...', 'SINGLE_CHOICES', 'SUSAH', 'Integral'), -- question_id = 11
('Diketahui kubus ABCD.EFGH dengan panjang rusuk 4 cm. Jarak dari titik A ke titik G adalah...', 'SINGLE_CHOICES', 'SUSAH', 'Geometri 3D'), -- question_id = 12
('Jari-jari dari lingkaran yang memiliki persamaan x^2 + y^2 - 4x + 6y - 12 = 0 adalah...', 'SINGLE_CHOICES', 'SUSAH', 'Persamaan Lingkaran'), -- question_id = 13
('Diketahui x + y = 3, y + z = 5, dan x + z = 4. Maka nilai dari x + y + z adalah...', 'SINGLE_CHOICES', 'SUSAH', 'SPLTV'), -- question_id = 14
('Jika f(x) = sin(2x)cos(2x), maka turunan pertamanya (f''(x)) adalah...', 'SINGLE_CHOICES', 'SUSAH', 'Turunan Trigonometri'); -- question_id = 15

-- =========================
-- INSERT DATA OPTIONS_ANSWER
-- =========================

-- Soal 1 (Mudah - PersKuadrat)
INSERT INTO options_answer (answer, question_id, score, correct, label) VALUES
('2 dan 3', 1, 1, TRUE, 'A'),
('-2 dan -3', 1, 0, FALSE, 'B'),
('1 dan 6', 1, 0, FALSE, 'C'),
('-1 dan -6', 1, 0, FALSE, 'D');

-- Soal 2 (Mudah - Matriks)
INSERT INTO options_answer (answer, question_id, score, correct, label) VALUES
('6', 2, 0, FALSE, 'A'),
('2', 2, 1, TRUE, 'B'),
('4', 2, 0, FALSE, 'C'),
('10', 2, 0, FALSE, 'D');

-- Soal 3 (Mudah - Barisan)
INSERT INTO options_answer (answer, question_id, score, correct, label) VALUES
('26', 3, 0, FALSE, 'A'),
('29', 3, 1, TRUE, 'B'),
('32', 3, 0, FALSE, 'C'),
('35', 3, 0, FALSE, 'D');

-- Soal 4 (Mudah - Logaritma)
INSERT INTO options_answer (answer, question_id, score, correct, label) VALUES
('4', 4, 0, FALSE, 'A'),
('5', 4, 1, TRUE, 'B'),
('6', 4, 0, FALSE, 'C'),
('7', 4, 0, FALSE, 'D');

-- Soal 5 (Mudah - Fungsi)
INSERT INTO options_answer (answer, question_id, score, correct, label) VALUES
('2x - 2', 5, 0, FALSE, 'A'),
('2x + 1', 5, 1, TRUE, 'B'),
('2x + 2', 5, 0, FALSE, 'C'),
('2x + 4', 5, 0, FALSE, 'D');

-- Soal 6 (Normal - Trigonometri)
INSERT INTO options_answer (answer, question_id, score, correct, label) VALUES
('0.5', 6, 0, FALSE, 'A'),
('1', 6, 1, TRUE, 'B'),
('1.5', 6, 0, FALSE, 'C'),
('2', 6, 0, FALSE, 'D');

-- Soal 7 (Normal - Limit)
INSERT INTO options_answer (answer, question_id, score, correct, label) VALUES
('0', 7, 0, FALSE, 'A'),
('2', 7, 0, FALSE, 'B'),
('4', 7, 1, TRUE, 'C'),
('8', 7, 0, FALSE, 'D');

-- Soal 8 (Normal - Turunan)
INSERT INTO options_answer (answer, question_id, score, correct, label) VALUES
('3x^2 - 8x + 5', 8, 1, TRUE, 'A'),
('3x^2 - 4x + 5', 8, 0, FALSE, 'B'),
('x^2 - 8x + 5', 8, 0, FALSE, 'C'),
('3x^2 + 8x - 5', 8, 0, FALSE, 'D');

-- Soal 9 (Normal - Peluang)
INSERT INTO options_answer (answer, question_id, score, correct, label) VALUES
('1/6', 9, 1, TRUE, 'A'),
('1/4', 9, 0, FALSE, 'B'),
('1/3', 9, 0, FALSE, 'C'),
('1/2', 9, 0, FALSE, 'D');

-- Soal 10 (Normal - Statistika)
INSERT INTO options_answer (answer, question_id, score, correct, label) VALUES
('5.5', 10, 0, FALSE, 'A'),
('6', 10, 1, TRUE, 'B'),
('6.5', 10, 0, FALSE, 'C'),
('7', 10, 0, FALSE, 'D');

-- Soal 11 (Sulit - Integral)
INSERT INTO options_answer (answer, question_id, score, correct, label) VALUES
('4', 11, 0, FALSE, 'A'),
('5', 11, 0, FALSE, 'B'),
('6', 11, 1, TRUE, 'C'),
('8', 11, 0, FALSE, 'D');

-- Soal 12 (Sulit - Geometri)
INSERT INTO options_answer (answer, question_id, score, correct, label) VALUES
('4 cm', 12, 0, FALSE, 'A'),
('16 cm', 12, 0, FALSE, 'B'),
('4√2 cm', 12, 0, FALSE, 'C'),
('4√3 cm', 12, 1, TRUE, 'D');

-- Soal 13 (Sulit - Lingkaran)
INSERT INTO options_answer (answer, question_id, score, correct, label) VALUES
('4', 13, 0, FALSE, 'A'),
('5', 13, 1, TRUE, 'B'),
('6', 13, 0, FALSE, 'C'),
('7', 13, 0, FALSE, 'D');

-- Soal 14 (Sulit - SPLTV)
INSERT INTO options_answer (answer, question_id, score, correct, label) VALUES
('5', 14, 0, FALSE, 'A'),
('6', 14, 1, TRUE, 'B'),
('7', 14, 0, FALSE, 'C'),
('8', 14, 0, FALSE, 'D');

-- Soal 15 (Sulit - Turunan Trigonometri)
INSERT INTO options_answer (answer, question_id, score, correct, label) VALUES
('cos(4x)', 15, 0, FALSE, 'A'),
('2 cos(4x)', 15, 1, TRUE, 'B'),
('4 cos(4x)', 15, 0, FALSE, 'C'),
('sin(4x)', 15, 0, FALSE, 'D');
