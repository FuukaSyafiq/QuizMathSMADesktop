# QuizMathSMA

**QuizMathSMA** is a desktop quiz application designed to help high school students learn mathematics, particularly topics on 2D shapes and 3D solids. This application is built using **Java Swing** for the user interface and **MySQL** as the database to store questions, answers, and student scores.

---

## Main Features

- **Admin Login** - Teachers can login to manage questions and view quiz results
- **Student Login** - Students can login to take quizzes
- **Class Management** - Add and view class data
- **Student Management** - Add and view student data
- **Question Management** - Add, edit, and delete math questions with image support
- **Interactive Quiz** - Students can answer questions with a timer
- **Multiple Choice** - Supports single choice and multiple choice questions
- **Leaderboard** - Displays student rankings based on scores
- **Score History** - Teachers can view student scores

---

## Technologies Used

| Category | Technology |
|----------|-----------|
| Programming Language | Java 21 |
| UI Framework | Java Swing + FlatLaf |
| Build System | Apache Maven |
| Database | MySQL |
| ORM | Hibernate |
| Date Picker Library | LGoodDatePicker |

---

## Project Structure

```
quizmath_sma/
├── player/                  # Main application (Admin + Player)
│   ├── src/main/java/       # Java source code
│   ├── src/main/resources/  # Resources like images
│   └── pom.xml              # Maven configuration
├── init.sql                 # SQL script for database setup
├── compose.yml              # Docker Compose for MySQL
└── Makefile                 # Build & Run system
```

---

## How to Run

### Prerequisites

1. **Java 21** or higher
2. **Apache Maven 3.9+**
3. **Docker** (to run MySQL via Docker Compose)

### Steps

#### 1. Clone and Navigate to Project Directory

```bash
cd QuizMathSMADesktop
```

#### 2. Run MySQL Using Docker Compose

```bash
make db
```

Or alternatively:

```bash
docker compose up -d
```

This will run the MySQL container with:
- Port: 3306
- Username: root
- Password: root

#### 3. Setup Database

Run the SQL script to create tables and initial data:

```bash
docker exec -i mysqlquizmath mysql -uroot -proot < init.sql
```

#### 4. Build and Run the Application

```bash
make player
```

Or manually:

```bash
cd player
mvn clean package
java -jar target/quizmath_player-1.0-SNAPSHOT.jar
```

---

## Default Accounts

After running `init.sql`, the following accounts are available:

| Role | Username | Password |
|------|----------|----------|
| Admin | admin | admin123 |

For students, you can use existing usernames in the database such as:
- afiq01 / pass123
- budi02 / pass123
- citra03 / pass123
- etc (see `init.sql` for the full list)

---

## How to Use

### For Admin/Teachers:

1. Login with username `admin` and password `admin123`
2. From the admin dashboard, you can:
   - Manage classes
   - Manage students
   - Add/edit/delete questions
   - View leaderboard
   - View student score history

### For Students:

1. Login with student username and password
2. Select quiz level (Easy, Normal, Hard)
3. Answer the given questions
4. View quiz results after completion

---

## License

This project was created for school assignment purposes, but you are free to use it as well.
