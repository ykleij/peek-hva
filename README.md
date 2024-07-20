<!-- 

## Getting started

To make it easy for you to get started with GitLab, here's a list of recommended next steps.

Already a pro? Just edit this README.md and make it your own. Want to make it easy? [Use the template at the bottom](#editing-this-readme)!

## Add your files

- [ ] [Create](https://docs.gitlab.com/ee/user/project/repository/web_editor.html#create-a-file) or [upload](https://docs.gitlab.com/ee/user/project/repository/web_editor.html#upload-a-file) files
- [ ] [Add files using the command line](https://docs.gitlab.com/ee/gitlab-basics/add-file.html#add-a-file-using-the-command-line) or push an existing Git repository with the following command:

```
cd existing_repo
git remote add origin https://gitlab.fdmci.hva.nl/se-ewa/2023-2024-1/peek-4.git
git branch -M main
git push -uf origin main
```

## Integrate with your tools

- [ ] [Set up project integrations](https://gitlab.fdmci.hva.nl/se-ewa/2023-2024-1/peek-4/-/settings/integrations)

## Collaborate with your team

- [ ] [Invite team members and collaborators](https://docs.gitlab.com/ee/user/project/members/)
- [ ] [Create a new merge request](https://docs.gitlab.com/ee/user/project/merge_requests/creating_merge_requests.html)
- [ ] [Automatically close issues from merge requests](https://docs.gitlab.com/ee/user/project/issues/managing_issues.html#closing-issues-automatically)
- [ ] [Enable merge request approvals](https://docs.gitlab.com/ee/user/project/merge_requests/approvals/)
- [ ] [Set auto-merge](https://docs.gitlab.com/ee/user/project/merge_requests/merge_when_pipeline_succeeds.html)

## Test and Deploy

Use the built-in continuous integration in GitLab.

- [ ] [Get started with GitLab CI/CD](https://docs.gitlab.com/ee/ci/quick_start/index.html)
- [ ] [Analyze your code for known vulnerabilities with Static Application Security Testing(SAST)](https://docs.gitlab.com/ee/user/application_security/sast/)
- [ ] [Deploy to Kubernetes, Amazon EC2, or Amazon ECS using Auto Deploy](https://docs.gitlab.com/ee/topics/autodevops/requirements.html)
- [ ] [Use pull-based deployments for improved Kubernetes management](https://docs.gitlab.com/ee/user/clusters/agent/)
- [ ] [Set up protected environments](https://docs.gitlab.com/ee/ci/environments/protected_environments.html)

*** -->


<!-- ## Project
- **Name:** Peek<br>
- **Team:** 4 -->

## Project Introduction

Welcome to the **PEEK** web application, where the key focus is on our robust matching algorithm designed to connect experts with projects seamlessly. In this platform, experts and companies can discover ideal matches based on shared aspects, ensuring a harmonious fit for collaboration.
Core Features:

- **Matching Algorithm**<br>
The heart of our web application lies in the advanced matching algorithm that pairs experts with projects. Notably, experts or companies won't directly view their matches; instead, administrators have access to the top 10 matches.

- **Admin Privileges**<br>
Admins have a comprehensive view, being able to see all experts and projects across various companies. Admins play a pivotal role in registering experts by creating invite links, which experts can use to register their account/profile.

- **Project Management**<br>
Admins hold the responsibility of adding new projects to the platform. A dedicated feature allows them to input information about the company and project, shaping opportunities for expert-company synergy.

- **Target Users**<br>
*Experts:* Our primary target audience comprises seasoned professionals seeking projects aligned with their expertise. The platform ensures that experts find projects and companies that complement their skills and work environment preferences.

- **Alternatives**<br>
While platforms like Indeed and LinkedIn exist, they fall short in precisely matching projects or companies with experts based on compatibility within a work environment. Our web application addresses this gap.

- **Future Growth**<br>
*User Registration:* Currently, expert registration relies on joining one of Mr. Peek's events to receive an invitation link. As the platform expands, exploring alternative registration methods for experts is crucial.

- **Project Submission**<br>
Similarly, companies can currently submit projects through Mr. Peek. However, for future scalability, implementing a self-service project submission system may be considered.

- **Challenges and Considerations**<br> *Quality Control:* Opening registration to experts and companies brings the challenge of maintaining quality control. Striking a balance to ensure genuine expertise while accommodating growth is a key consideration for the future.

We are excited about the possibilities this platform offers to bring together experts and companies in a meaningful and impactful manner. Dive into our application and experience the tailored matching that sets us apart from traditional job platforms.

## Installation

### Mysql database Installation

Before running this project, you need to set up a mysql database.

#### Step 1: Install mysql server

Download and install mysql server. 
Visit [official mysql website](https://dev.mysql.com/downloads/) to find the installer for your operating system.

#### Step 2: Configure mysql server

During the installation process, you will be prompted to set a root password for the mysql server. 
Remember this password because you will need it later.

#### Step 3: Start mysql server

Start the mysql server on your pc.

- On Windows, you can start it as a service or use the mysql command client.
- Consult the mysql website for any other operating systems

#### Step 4: Database Schema Migration

While Spring Boot can auto-generate database schemas based on the entity model structure, we have opted to provide a MySQL database creation script. This decision was made because Spring Boot has limitations in altering specific MySQL details, such as foreign key constraints.

### Why Provide a MySQL Database Creation Script?

1. **Fine-Tuning MySQL Specifics:**
   - Spring Boot's automatic schema generation is powerful, but there are cases where fine-tuning specific MySQL details, such as foreign key constraints, is necessary. Providing a dedicated MySQL script allows us to control and customize these aspects.

2. **Ensuring Consistency:**
   - Sharing a common MySQL script ensures consistency across development, testing, and production environments. This reduces the risk of schema discrepancies that might occur with Spring Boot's auto-generation.

### Database Creation Script

In this project, we have included a MySQL database creation script (`create_schema.sql`) in the `src/main/resources/db_scripts` folder. This script contains the `CREATE SCHEMA` statement for initializing the database schema.

### How to Connect the Created Database Schema in Our Backend

To reference the MySQL database creation script in our Spring Boot backend:

1. **Update Database Configuration:**
   - Open the `application.properties` file in your backend project, which will be located at `backend/src/main/resources/application.properties`.
   - Ensure that the database connection properties are correctly configured:

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/your_database_name
   spring.datasource.username=your_username
   spring.datasource.password=your_password
 
***IMPORTANT:***
*When using this file to create your Peek database, an admin user is automatically inserted to provide you with a starting point for the application.*

***Admin login details:***
 1. email: `marius.peek@gmail.com`
 2. password: `hello`

## Project setup -backend

### Step 1: Install Java Development Kit (JDK)

Ensure you have the Java Development Kit (JDK) installed on your machine. 
You can download the latest version from the [official oracle website](https://www.oracle.com/java/technologies/javase-downloads.html).

### Step 2: Install Apache Maven

If you haven't installed maven, visit the [official maven website](https://maven.apache.org/download.cgi). 
Though IDEs like intellij idea come with built-in support for maven.

### Step 3: Build project (backend folder)

```
mvn clean install
```

### Step 4: Run the backend application

```
mvn spring-boot:run
```

## Project setup (frontend folder)

To navigate to the frontend file, type the following command in your terminal:
```
cd frontend
```
Now install all dependencies:
```
npm install
```

### Compiles and hot-reloads for development

To run the frontend server, paste this into your terminal:
```
npm run serve
```
<!-- 
### Compiles and minifies for production
```
npm run build
```

### Lints and fixes files
```
npm run lint
```

```
npm start
``` 

### Customize configuration -frontend
See [Configuration Reference](https://cli.vuejs.org/config/). -->

## Usage of our deployed version of the application

***IMPORTANT:*** *Be aware that the deployed website might take some time to start up, please be patient when using it. The initial request to the application might cause a "cold start." This happens when the server needs to spin up an instance of the application, and it can result in a delay for the first user after a period of inactivity.*
 
*frontend url:* https://ewa-frontend-app.onrender.com/#/ <br>
*backend url:* https://ewa-backend-app.onrender.com/

**Admin login details:**
 1. email: `marius.peek@gmail.com`
 2. password: `hello`

**Expert (normal user) login details:**
 1. email:  `sarahhanoff@gmail.com`
 2. password: `hello`

 **New user without a profile (which means you get to create their profile on login)**
 1. email:  `newuser@gmail.com`
 2. password: `hello`

#### Creating a New Account (Invite Link)

If you're logged in as an admin, the process of creating a new expert account is streamlined through the use of an invitation link. Follow these steps:

1. **Access the Invitation Link Page**<br>
Navigate to the invitation link page using the side navigation bar. This feature is accessible exclusively for admin accounts.

2. **Generate an Invitation Link**<br>
On the invitation link page, you have the capability to generate a new invitation link. This link serves as an exclusive invitation for potential experts to create an account on our platform.

3. **Copy and Paste the Invitation Link**<br>
After generating the link, copy it to your clipboard. You can then paste this link into a new browser tab.

4. **Create a New Expert Account**<br>
Opening the invitation link in a new tab initiates the account creation process. This streamlined approach eliminates the need for manual account setup steps.

By leveraging the invitation link system, we ensure a secure and controlled onboarding process for new experts. This feature simplifies account creation and aligns with our commitment to providing a seamless user experience.



### API Endpoints
This project provides several API endpoints for interacting with the backend, keep in mind that these endpoints require a valid jwt token to have access. Some examples:

#### Endpoint 1:
**(GET)** https://ewa-backend-app.onrender.com/api/v1/users

Retrieves all users from database.

#### Endpoint 2:
**(GET)** https://ewa-backend-app.onrender.com/api/v1/users/{userId}

Retrieves a certain user from the database depending on the parameter {userId}

#### Endpoint 3:
**(DELETE)** https://ewa-backend-app.onrender.com/api/v1/users/{userId}

Deletes a certain user from the database depending on the parameter {userId}

## Additional Configuration
Currently configured e-mail address: peekemploymentservice@gmail.com, ww:PEEK.m@team4

### Replace email (optional)

Edit e-mail at file: application.properties `spring.mail.username` 

For password, manage your Google Account > 
Security > 
enable 2-Step authentication > 
create an App Password > 
use App Password for `spring.mail.password`

Make to upgrade your google account limits, more information about this you can find on [Google Workspace Support](https://support.google.com/a/answer/166852?sjid=2752031946420776225-EU).
Otherwise, sending e-mails will fail to work after a while.

Of course, feel free to use another mail sender than gmail.

## Support
{anyAuthorFirstname}.{anyAuthorLastname}@hva.nl

## Roadmap

Due to time constraints, we couldn't implement an extensive notification system as initially envisioned. The envisioned notification system would include functionalities such as:

1. ***Admin Notifications***<br>
Admins would receive notifications for actions that require their attention, such as accepting/denying the creation of a new project role by an expert or when the matching tool has been activated.

2. ***User Notifications***<br>
Both admins and experts would receive notifications when someone posts a comment on their profile. Additionally, experts would receive notifications upon matching with a project.

3. ***Enhanced Matching Algorithm***<br>
We aimed to refine the matching algorithm further by implementing weights for each project role detail. This enhancement would allow for a more nuanced matching process, providing the client with accurate expert matches that precisely align with their criteria. We also intended to expand and refine project roles to offer more detailed and comprehensive options.

Despite these planned enhancements not being fully implemented, the existing features provide a solid foundation for collaboration and communication within the platform. Future iterations could explore these ideas to enhance user experience and system functionality.

## Authors
- Lisa Welling
- Yosef van der Kleij
- Dylan Worseling
- Samy Farahat
- Carolijn Pieterse

## License
(MIT)
Copyright (c) [2024] [Hogeschool van Amsterdam]

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

<!-- ## Project status
Development discontinued -->
