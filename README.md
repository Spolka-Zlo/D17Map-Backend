# 🔵 D17Map - Backend Side 🗺 🔍
## Environment setup
There is no need to set up the environment manually, however you should ensure that the proper *active profile* is set in `./src/main/resources/application.properties`. For local development the `dev` profile should be set: `spring.profiles.active=dev`.


## Building & testing the project
To build and test the project locally you can use one of the following commands:
- `mvn clean test` - builds the project and runs tests
- `mvn clean package` - does the same as `mvn clean test` + creates a `.jar` file in `/target` directory
- `mvn clean install` - does the same as `mvn clean package` + installs the package in a local Maven repository (usually located in `.../home/.m2/repositories`)

> Note: Building the app for the first time may take some time to complete. It is expected, because Maven has to download all the dependencies and compile every class.

## Running the project locally  (using Intellij)
In order to run the application locally, you need to:
1. Run on of the commands from [Building & testing the project](#building--testing-the-project) section.
2. Right click `./src/main/kotlin/inc/evil/d17map/D17MapBackendApplication.kt` and choose the green run button from the context menu.

> Note: Running the app for the fist time may take some time before the actual app is launched. The following runs should be much faster.


## Manual API testing (locally)
The application's *REST API* can be tested using built-in *Swagger UI*. In order to access it, you should [run the app](#running-the-project-locally-using-intellij) and visit the locally hosted [Swagger UI](http://localhost:8080/swagger-ui/index.html) page.

Bear in mind, that some endpoints are accessible for not logged-in users, while some are only accessible to:
- authenticated users
- authenticated users with sufficient permissions (determined via their roles)

If you see a `401` HTTP status code in the response, you should go to the [Authentication](http://localhost:8080/swagger-ui/index.html#/Authentication) section in the Swagger UI and either create a new user or log in to existing one.

There are some example users provided in the local `dev` environment:

1. Student
    - Email: `example@student.agh.edu.pl`
    - Password: `student`
    - Roles: `ROLE_STUDENT`
2. Teacher
    - Email: `teacher@agh.edu.pl`
    - Password: `teacher`
    - Roles: `TEACHER`
3. Admin
    - Email: `admin`
    - Password: `admin`
    - Roles: `ADMIN`

[//]: # (Maybe it would be a good idea to restrict the admin role only to administration stuff and introduce a new role - for example "GOD" or "CEO" xD which will be only available in dev, meant for testing and not possible to create dynamically via the admin itself)
If you see a `403` HTTP status code, you should log in as a user with higher permissions (use `ADMIN` if you want to have access to everything)


## Simulating the production environment locally
It is possible to simulate the production environment locally. In order to do so, some configuration needs to be done.
### Setting up the environment
First of all, the *active profile* has to be changed to `prod`. It is explained how to accomplish that in the [Environment setup](#environment-setup) section. This profile is configured to connect with the `PostgreSQL` database which is used on production.

For the connection to be successful you need to:
1. Set up the `PostgreSQL` database server and create a new user and database. You choose whatever name and credentials you want for both the user and the db - it doesn't matter from the app's perspective. 

   Our recommendation is to use a docker postgres image, since it's the easiest and fastest way to set the server up.
2. Set environment variables:
    - DB_URL
    - DB_USER
    - DB_PASSWORD

    You can set them manually via the terminal or use a more convenient way and save them in the `.env` file.

    The example `.env` file could look like that:
    ```text
   DB_URL=jdbc:postgresql://localhost:5432/mydatabase
   DB_USER=my_user
   DB_PASSWORD=my_password
    ```
   
### Running the application
There are still 2 options to run the application:

1. Database production mode
2. Full production mode

[//]: # (&#40;The above names are made up, it's not a real thing 😂&#41;)

#### Database production mode
In this mode you only simulate the production database, while still running the project as described in the [Running The Project Locally (using Intellij)](#running-the-project-locally-using-intellij) section.

#### Full production mode
In this mode you also create a docker image of the app and run it inside a container. It is actually done in that way on the real production.

To create an image and run it as a container you have to issue some commands:
1. Build the project using: `mvn clean package` or `mvn clean install`
2. Build the docker image using: `docker build -t <your-custom-name>:latest .`
3. Run the container using: `docker run --env-file .env -p 8080:8080 <your-custom-name>`

You can now go back to the [Manual API testing](#manual-api-testing-locally) section and test the simulated production environment.

> Note: There won't be any default user present except for the admin.


## Manual API testing (Azure) 
[//]: # (This section will be deleted when the production will no longer be used for tests)
In order to test the real production app, you have to visit this [Deployed Swagger UI](http://d17-map.fvemdyeybebwdqcp.polandcentral.azurecontainer.io:8080/swagger-ui/index.html) site hosted on Azure.

Access to the production database:
```text
DB_URL=jdbc:postgresql://d17-db.postgres.database.azure.com:5432/d17-data?sslmode=require
DB_USER=maya_the_admin
DB_PASSWORD=oh4amcHxxr9WrE9c6x~5G4+6
```

## Contribution
This project only allows contributions from the members of the owning team, however every contributor should follow the rules and development process described below.

### Development process
1. Take one of the free tasks with the highest priority.
2. Create a branch *using JIRA* - this step is very important due to the implemented workflows.
3. Do some changes
4. Ensure your changes are working as expected, not breaking anything that has already been working well.
5. Create a PR, assign yourself and ask [Adam Mężydło](https://github.com/amezydlo) or [Joanna Kulig](https://github.com/YoC00lig) for a review.
6. Resolve every comment
7. When the PR is *approved* please ask somebody else from the team to test the changes before merging to the main branch.
8. Merge.
9. Check with the team if a release is necessary / can be done
10. If applicable - perform the release using a suitable workflow.
11. Go to the Azure Portal and restart the container.


## Useful commands
The following commands should be issued from the root directory of the project.

## 🔷 Cleaning Docker
Sometimes docker has problems with itself, but there is a couple of commands which you can try to fix it:

1. `docker compose down -v` - this command stops containers and removes them.
2. `docker system prune` - removes all dangling/untagged resources
3. `docker system prune -a` - removes a little bit more ;)
4. `docker volume rm <your volume name>` - obvious (use `docker volume ls` to list all volumes)
5. `docker volume prune` - removes all dangling volumes

Other useful commands may be found [here](https://contabo.com/blog/how-to-remove-docker-volumes-images-and-containers/)
