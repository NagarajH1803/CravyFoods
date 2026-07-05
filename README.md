# CravyFoods - Full-Stack Java Web Application

**CravyFoods** is a dynamic, full-stack food delivery web application built using standard Advanced Java technologies (Servlets, JDBC, and JSP) running on an **Apache Tomcat 10.1+** server with a **MySQL** database.

This project is configured directly as a native **Eclipse Dynamic Web Project** (non-Maven) for easy import and execution.

---

## 🛠️ Technology Stack Used
- **Core Java** & **Advanced Java** (Jakarta Servlets 6.0, JDBC)
- **View Layer**: HTML5, CSS3 (Custom Dark Theme), JSP (JavaServer Pages 3.1)
- **Database**: MySQL Database
- **Server Runtime**: Apache Tomcat 10.1
- **Architecture**: MVC (Model-View-Controller) with DAO (Data Access Object) Pattern
- **Session Management**: Session-based user tracking and cart persistence

---

## 📁 Project Directory Structure
```text
CravyFoods/
├── .project                                    # Eclipse project definition
├── .classpath                                  # Eclipse compiler classpath
├── .settings/                                  # Eclipse Web Tools Platform settings
│   ├── org.eclipse.wst.common.component
│   └── org.eclipse.wst.common.project.facet.core.xml
├── db_schema.sql                               # Database tables and sample inserts
├── src/                                        # Java source code
│   └── com/cravyfoods/
│       ├── pojo/                               # POJO domain classes (Model)
│       │   ├── User.java
│       │   ├── Restaurant.java
│       │   ├── Menu.java
│       │   ├── CartItem.java
│       │   └── OrderHistory.java
│       ├── dao/                                # Data Access Object interfaces
│       │   ├── UserDAO.java
│       │   ├── RestaurantDAO.java
│       │   ├── MenuDAO.java
│       │   └── OrderHistoryDAO.java
│       ├── daoimpl/                            # DAO JDBC implementations
│       │   ├── UserDAOImpl.java
│       │   ├── RestaurantDAOImpl.java
│       │   ├── MenuDAOImpl.java
│       │   └── OrderHistoryDAOImpl.java
│       ├── servlet/                            # Controller Servlets
│       │   ├── LoginServlet.java
│       │   ├── RegisterServlet.java
│       │   ├── RestaurantServlet.java
│       │   ├── MenuServlet.java
│       │   ├── CartServlet.java
│       │   ├── CheckoutServlet.java
│       │   ├── OrderHistoryServlet.java
│       │   └── LogoutServlet.java
│       └── util/                               # Connection Utility
│           └── DBConnection.java
└── WebContent/                                 # Web Pages and Client-Side Assets
    ├── WEB-INF/
    │   ├── web.xml                             # Web application descriptor
    │   └── lib/                                # Directory for JDBC jar files
    ├── css/
    │   └── style.css                           # Main custom CSS stylesheet
    ├── login.html                              # User authentication view
    ├── register.html                           # User registration view
    ├── restaurants.jsp                         # Restaurant list view
    ├── menu.jsp                                # Menu item listing view
    ├── cart.jsp                                # Cart detail view
    ├── orderConfirmation.jsp                   # Checkout success view
    └── orderHistory.jsp                        # Past orders history view
```

---

## 🚀 Setup and Installation Instructions

### Step 1: Set Up the MySQL Database
1. Open your **MySQL Workbench** or **MySQL Command Line Client**.
2. Run the SQL commands provided in [db_schema.sql](file:///C:/Users/NAGARAJ%20HALLUR/.gemini/antigravity-ide/scratch/CravyFoods/db_schema.sql) to create the `cravyfoods_db` database and load the 10 famous restaurants, menu items, and a default test user.
3. If your MySQL credentials are not `root` / `root`, open the connection class [DBConnection.java](file:///C:/Users/NAGARAJ%20HALLUR/.gemini/antigravity-ide/scratch/CravyFoods/src/com/cravyfoods/util/DBConnection.java) and modify `USERNAME` and `PASSWORD` to match your local credentials.

### Step 2: Set Up Classpath Dependencies (MySQL Connector)
To connect to MySQL, you need the MySQL JDBC driver jar:
1. Download **MySQL Connector/J** (e.g., version `8.3.0` or similar).
2. Copy the jar file (e.g. `mysql-connector-j-8.3.0.jar`) and paste it into the **`CravyFoods/WebContent/WEB-INF/lib/`** directory.
3. In Eclipse, it will automatically be added to your project's **Web App Libraries** compilation classpath.

### Step 3: Import the Project into Eclipse IDE
1. Open **Eclipse IDE**.
2. Click **File > Import...**
3. Select **General > Existing Projects into Workspace** and click **Next**.
4. Click **Browse** next to *Select root directory* and navigate to the `CravyFoods` project folder on your system.
5. Make sure `CravyFoods` is checked and click **Finish**.

### Step 4: Configure the Apache Tomcat Runtime
1. In Eclipse, right-click on the `CravyFoods` project in the Project Explorer.
2. Select **Properties** (Alt+Enter).
3. Click on **Targeted Runtimes** on the left menu.
4. Check **Apache Tomcat v10.1** (or your installed Tomcat v10.x runtime). Click **Apply and Close**.
   *Note: If no server runtime is configured, click "New...", select "Apache Tomcat v10.1", point it to your Tomcat folder location, and finish.*

### Step 5: Run the Web Application
1. Right-click on the `CravyFoods` project in Eclipse.
2. Select **Run As > Run on Server**.
3. Select the **Apache Tomcat v10.1** server and click **Finish**.
4. Tomcat will compile the Java source files, mount the context root, and start the application.
5. In your web browser, navigate to:
   [http://localhost:8080/CravyFoods/login.html](http://localhost:8080/CravyFoods/login.html)

---

## 🔑 Test Credentials
Use the pre-inserted test account to log in and test immediately:
- **Username**: `testuser`
- **Password**: `testpassword123`

You can also use the **Register** form to create a new user account and log in with it!
"# CravyFoods" 
