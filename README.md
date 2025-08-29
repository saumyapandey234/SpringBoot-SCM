# Smart Contact Manager
A Spring Boot-based web application that helps users securely manage their personal contacts in the cloud.
It supports Google & GitHub OAuth login, local authentication, and Cloudinary image storage for contact pictures.
Pagination is implemented for efficient browsing of large contact lists.

# Features

Authentication: Local login + OAuth (Google, GitHub)
Manage personal contacts securely in the cloud
Cloudinary integration for storing contact images
Pagination for smooth navigation across contact lists
Thymeleaf-based responsive UI

# Tech Stack
Backend: Spring Boot, Spring Security, Spring Data JPA
Frontend: Thymeleaf, TailwindCSS
Database: MySQL
Cloud Storage: Cloudinary
Authentication: Google OAuth, GitHub OAuth, Local Login

📸 Screenshots
<img width="957" height="483" alt="1" src="https://github.com/user-attachments/assets/a18e62c7-a979-499e-8396-4e2ee59cde16" />
<img width="960" height="322" alt="2" src="https://github.com/user-attachments/assets/f92e4ee8-467f-40c6-bbc7-6fadf6d08458" />

# Installation & Setup

Clone the repository
git clone https://github.com/your-username/SmartContactManager.git
cd SmartContactManager

# Configure application.properties with:
Database credentials
Google & GitHub OAuth keys
Cloudinary API keys
Run the application
mvn spring-boot:run

# Open in browser:

http://localhost:9192

# Future Enhancements
Export/Import contacts in CSV/Excel
Add profile picture editing
Role-based access control

# Author
Saumya Pandey
📧 [saumya1617pandey@gmail.com]
