# IBM SkillsBuild Gamified App

A full-stack web application designed to boost user engagement with the IBM SkillsBuild platform through gamification features like achievement badges, leaderboards, and social integration.

This project was developed as part of a collaborative university group project for our Software Engineering module.


## About The Project

The goal of this project was to design and build an external web application that integrates with the concept of IBM's SkillsBuild platform. We aimed to increase user motivation and consistency by introducing gamification elements that reward users for completing courses and maintaining learning streaks. The application includes a full suite of features from account management and course tracking to competitive leaderboards and a friends system.

---

## Key Features

*  Account Management:** Secure user registration, login, and profile management (username, password, profile picture changes). 
*  Course Management:** Users can search and filter a catalogue of courses, enrol in them, and manually track their completion progress. 
*  Gamification Engine:**
    * *Achievement Badges:** Unlock badges for completing a certain number of courses (e.g., "First Steps," "Expert Learner"). 
    * Learning Streaks:** Earn special badges for consistent daily learning. 
    * Points & Leaderboards:** Gain points for completing courses and compare rankings on global and friends-only leaderboards. 
    * Cosmetic Unlocks:** Equip profile picture borders based on achievements. 
*  Social System:** A complete friends management system, allowing users to add, remove, and view friends, and compete on a friends-only leaderboard. 

---

## Tech Stack

* **Backend:** Java, Spring Boot, Spring Security
* **Frontend:** Thymeleaf, HTML, CSS, JavaScript
* **Database:** MySQL / PostgreSQL
* **Build Tool:** Maven

## My Specific Contributions

As a key member of the development team, I was directly responsible for the design and implementation of the core gamification loop.

* **Course Progress & Completion System:** I developed the feature allowing users to manually update and track their progress on enrolled courses. This included implementing the back-end logic to validate progress input (1-100) and update the database in real-time. 

* <img width="407" height="344" alt="image" src="https://github.com/user-attachments/assets/d9ddc328-c85b-4230-b868-a2ecd4136dd3" />
* <img width="186" height="375" alt="image" src="https://github.com/user-attachments/assets/6478dffe-306a-45ce-875e-9b34d438ee55" />
* <img width="320" height="439" alt="image" src="https://github.com/user-attachments/assets/96ad8510-6b65-41d5-9617-2e18d5dccc97" />
* <img width="793" height="563" alt="image" src="https://github.com/user-attachments/assets/1ec843fb-4422-4dbb-8ab1-90a57102d255" />
* <img width="940" height="659" alt="image" src="https://github.com/user-attachments/assets/1724e5d1-50a4-4ddb-bd42-1e0c245c7c66" />


* **Achievement & Badge Unlocks:** I designed and implemented the system that automatically awards achievement badges based on user data. The course completion logic I built directly triggers this system, checking if a user has met the criteria for badges like "First Steps" (1 course) or "Explorer" (5 courses). 
* **User Profile Page Development:** I created the "My Profile" page, which acts as the central hub for a user's progress. [cite_start]I handled both the back-end logic to fetch and display user stats (points, courses completed, streak) and the front-end development to present the unlocked badges and personal information. 
* **Scrum Master Role:** I took on the role of Scrum Master for a key sprint, where I facilitated daily stand-ups, managed the sprint board, assigned user stories for review, and ensured the team was coordinated and on track to meet deadlines.

---

## The Team

This project was a collaborative effort by a talented team of student developers.

* Karthigan Mageswaran
* Jakub Baclawski
* Emre Uzum
* Fahd Farooqi O.
* Ben Mann
* Sahra Osman

---

## Getting Started

To get a local copy up and running, please follow these steps.

### Prerequisites

* JDK 17 or higher
* Apache Maven
* An SQL Database (MySQL, PostgreSQL, etc.)

### Installation

1.  **Clone the repo**
    
2.  **Configure Database Credentials**
    * Navigate to `src/main/resources/application.properties`.
    * Enter your database URL, username, and password in the appropriate fields.
3.  **Build and Launch the Application**
    * You can run the application directly from your IDE (like IntelliJ) by running the `Group34Application` class.
4.  **Access the Application**
    * Open your browser and navigate to `http://localhost:8080`.
