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

## 📂 Project Structure  
cat << 'EOF'
.
├── src/
│   ├── main/
│   │   ├── java/org/example/powerpark/       # Java source code (controllers, models, db)
│   │   └── resources/                        # FXML, config.properties (ignored), images
├── database/                                # SQL scripts for schema and sample data
├── .gitignore                               # Git ignore file
├── pom.xml                                  # Maven project configuration
├── mvnw / mvnw.cmd                          # Maven wrapper scripts
├── README.md                                # Project documentation
EOF

