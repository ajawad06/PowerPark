# ⚡ PowerPark  
**EV Charging Station Management System**

Manage and monitor electric vehicle charging stations with ease using PowerPark.  
Provides user & admin dashboards, booking management, station status, and more.

---

## 📜 Project Overview  
PowerPark is a JavaFX desktop application backed by MySQL.  
Users can browse and book charging slots, while admins manage stations and bookings.  
A simple and efficient solution to manage EV charging infrastructure.

---

## 🎯 Features  
- User login & role-based access (Admin, User)  
- Real-time charging station availability & slot booking  
- Admin dashboard for managing stations, slots, and users  
- Booking history & recent bookings table  
- Responsive JavaFX UI with FXML  
- Secure database connection using MySQL  
- Session management & input validation

---

## 🧱 Object-Oriented Design

This project follows core OOP principles including encapsulation, inheritance, polymorphism, and abstraction to create a clean, modular, and maintainable codebase. Different entities like users, admins, stations, and bookings are represented as classes with clear responsibilities.

---

## 📂 Project Structure  
```bash
  .
├── src/
│   ├── main/           
│         ├── java/org/example/powerpark     # java source code i.e. model, business logic etc.
│         └── resources/                     # fxml,images,css,config-properties
│
├── database               # SQL scripts for schema and data
├── .gitignore             # git ignore file
├── pom.xml                # Maven project configuration
├── mvnw / mvnw.cmd        # Maven wrapper scripts
└── README.md
```
---

## ⚙️ Development Setup

### Prerequisites  
- JDK 17 or newer  
- Maven 3.8+  
- MySQL server installed and running  
- IDE (IntelliJ IDEA recommended) with JavaFX support  

### Database Setup  
1. Create the database:
     ```bash 
        CREATE DATABASE powerpark;
      ```
2. Run the SQL scripts in **database** folder to create tables and insert sample data.

3. Update **config.properties** (not committed to Git) with your DB credentials:
     ```bash
      db.url=jdbc:mysql://localhost:3306/powerpark?useSSL=false&serverTimezone=UTC
      db.user=root
      db.password=your_password_here
      ```

--- 

## 🎮 Application Controls

| Action                | Description                                |
|-----------------------|--------------------------------------------|
| Login                 | Enter username, password, and select role |
| Booking               | Browse stations and book available slots  |
| Payment               | Choose to pay the amount calculated at the time of booking or later |
| Admin Panel           | Add/edit/delete stations, slots, and users|
| Logout                | Exit current session                       |

---

## 📢 Notes  
- The **config.properties** file is ignored by Git for security — create your own copy locally.  
- Make sure MySQL server is running before starting the app.  
- This project uses JavaFX 17 and Maven for dependency management.

---

## 🤝 Contributing  
Feel free to submit issues or pull requests for improvements!

---
