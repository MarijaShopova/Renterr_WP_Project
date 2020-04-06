# Renterr

### 1. Short Description
This is a project made by Marija Shopova (161044) for the course ‘Web Programming’ of the Faculty of computer science and engineering, Skopje, North Macedonia.

Renterr is a web application, whose main purpose is to help students in North Macedonia easily find apartments. Students can filter apartments by many criteria (like municipality, price, number of bedrooms, etc.. ), see all the details about the apartment and its owner details (so they can contact them) and if they  make an account, they can save some of the apartments to favorites, so they can view them later. Renterr also makes it easy for owners of apartments to activate and deactivate an apartment when they find a renter, so they don’t have to delete and add the apartment every time they change a renter.  The app is also mobile friendly.

### 2. Starting the application
#### 2.1 Back-end
For starting the back-end app, you only need to build the backend module and run the main class ***‘com.finki.renterr.RenterrApplication’***.<br>
To connect to the database, you need to create a database with:<br> <br>
**name: *renterr***, <br>
**user: *postgres***, <br>
**password: *root***,<br> <br>
or if you want to use your own name, user or password, you need to make the same changes in ***application.properties***.<br> With the help of flyway migrations the tables in the database will be created and also populated. A user is already created for you to test the app with credentials: <br> <br>
**Username: *User*** <br>
**Password: *P@ssw0rd*** <br> <br>
or if you want,  you can create your own user.

#### 2.2 Front-end
For starting the front-end app, you only need to open terminal, navigate to the folder ‘frontend’ and write the command:
```bash
npm install
npm start
```
The app will compile and you can see it on (http://localhost:4200/).

