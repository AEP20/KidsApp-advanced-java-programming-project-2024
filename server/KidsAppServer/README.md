# KidsAppServer

## About the Project
KidsAppServer is a server application developed for a question-and-answer application aimed at children. This server provides questions in various categories (math, clock, seasons, directions) and allows users to log in and register. The project is developed using Node.js and Express.js and uses MongoDB for data management.

## Installation and Startup

### Requirements
- Node.js and npm must be installed.
- MongoDB database must be running.

### Installation
1. Download or clone the project files:
    ```bash
    git clone https://github.com/username/kidsappserver.git
    cd kidsappserver
    ```

2. Install the required packages:
    ```bash
    npm install
    ```

3. Configure environment variables. Create a `.env` file in the project directory and configure it as follows:
    ```env
    PORT=3000
    DB_URL=mongodb://localhost:27017/kidsAppDatabase
    ```

### Starting the Application
1. Ensure the database is running.
2. Start the server:
    ```bash
    npm start
    ```
   This command will start the server and run it at `http://localhost:3000`.

## Usage

### API Endpoints
- `GET /` - Returns a simple response to verify the server is running.
- `POST /api/login` - Logs in a user.
- `POST /api/register` - Registers a new user.
- `GET /api/questions?type=type&language=language` - Fetches questions in the specified category and language. The `type` parameter can be `math`, `clock`, `seasons`, or `direction`. The `language` parameter specifies the language of the questions and defaults to `tr` (Turkish).

### Example API Usage
- **User Login:**
    ```bash
    curl -X POST http://localhost:3000/api/login -H "Content-Type: application/json" -d '{"username":"username", "password":"password"}'
    ```

- **New User Registration:**
    ```bash
    curl -X POST http://localhost:3000/api/register -H "Content-Type: application/json" -d '{"username":"newUser", "password":"password"}'
    ```

- **Fetch Questions:**
    ```bash
    curl -X GET "http://localhost:3000/api/questions?type=math&language=tr"
    ```

## Project Structure
- `src/` - Contains source code.
  - `config/` - Contains database connection settings.
    - `db.ts` - Configuration file for database connection.
  - `controllers/` - Contains the business logic for API endpoints.
    - `auth.ts` - Handles user authentication.
    - `quiz.ts` - Handles question-and-answer operations.
  - `models/` - Contains Mongoose models.
    - `fourChoiceQuestions.ts` - Model for four-choice questions.
    - `fourChoiceQuestionsDirection.ts` - Model for direction questions.
    - `fourChoiceQuestionsMath.ts` - Model for math questions.
    - `fourChoiceQuestionsSeason.ts` - Model for season questions.
    - `users.ts` - User model.
  - `routes/` - Contains API routes.
    - `authRoutes.ts` - Routes related to authentication.
    - `quizRoutes.ts` - Routes related to question-and-answer.
    - `routes.ts` - Main file that combines all routes.
  - `response.ts` - Contains response formatting functions.
- `.env` - Environment variables file.
- `package-lock.json` - Lock file for dependencies.
- `package.json` - Project dependencies and scripts.
- `tsconfig.json` - TypeScript configuration file.
- `app.ts` - Main entry point of the application.
