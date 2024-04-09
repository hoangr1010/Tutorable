# TROUBLESHOOTING GUIDE
## INTRODUCTION
TutorMe provides a seamless connection between tutors and students. We take pride in the quality
of our products. However, one day, if you encounter something odd with our service
(Nothing is perfect, they say), we hope this troubleshooting guide can support you and save you time
before you reach out to us.  
We are always happy to receive your feedback, so we can serve you better.

## A - COMMON PROBLEMS
## 1. APPLICATION
**Problem:** The application is frozen/crashed.  
**Solution:**
1. Go back to your home screen to quit the application
2. Open the application again
3. Check whether you have the latest version
4. Test whether things work
5. If not, uninstall your current TutorMe application and re-install it again
6. Re-test if the application functions properly
7. Contact technical support if the problem persists  
   (call: 780-xxx-xxxx (ext: 9) or email:  macrohard2024@gmail.com)

## 2. FUNCTIONALITIES
### 2.1 LOGIN/REGISTRATION
#### 2.1.1. **Unable to login**
**Problem:** Users are unable to log into their accounts.   
**Solution:**
1. Check the internet connection to make sure your device is connected properly.
2. Make sure you enter the correct username and password.
3. Reset password (if needed)
4. Ensure your account is registered
5. Contact technical support if the problem persists  
   (call: 780-xxx-xxxx (ext: 9) or email:  macrohard2024@gmail.com)

#### 2.1.2. **Forgot your password**
**Problem:** Users forget their password  
**Solution:**
1. Click on “Forgot your password?”
2. Follow the instructions to reset your password
3. Create a new password if needed
4. Contact technical support if the problem persists  
   (call: 780-xxx-xxxx (ext: 9) or email:  macrohard2024@gmail.com)

### 2.2. SCHEDULING PROBLEMS
### 2.2.1. FOR STUDENTS
#### 2.2.1.1. **Unable to add new sessions**
**Problem:** Users cannot add a new session  
**Solution:**
1. Check if there is a scheduling conflict
2. Choose only available sessions
3. If the problem persists, quit the application and log into it again
4. Redo the adding steps
5. If nothing is fixed, contact the technical support team  
   (call: 780-xxx-xxxx (ext: 9) or email:  macrohard2024@gmail.com)

#### 2.2.1.2. **Unable to see added sessions**
**Problem:** Users cannot see their added sessions  
**Solution:**
1. Click on the “Schedule” button
2. Wait a couple of few minutes (if the time exceeds 5 minutes, quit the application and log into it again)
3. Check the added sessions.
4. If the problem persists, contact the technical support team  
   (call: 780-xxx-xxxx (ext: 9) or email:  macrohard2024@gmail.com)

#### 2.2.1.3. **Unable to edit sessions**
**Problem:** Users cannot edit sessions  
**Solution:**
1. Quit the application
2. Log into the application again
3. Click on the session that you want to edit
4. Follow the steps again
5. If the problem persists, contact the technical support team  
   (call: 780-xxx-xxxx (ext: 9) or email:  macrohard2024@gmail.com)

#### 2.2.1.4. **Unable to cancel sessions**
**Problem:** Users cannot cancel sessions  
**Solution:**
1. Check whether you are allowed to cancel the chosen session  
   (users are allowed to cancel 1 day in advance)
2. If yes, then quit the application and log into it again
3. Following all the steps to cancel the sessions
4. If the problem persists, contact the technical support team  
   (call: 780-xxx-xxxx (ext: 9) or email:  macrohard2024@gmail.com)


### 2.2.2. FOR TUTORS
#### 2.2.2.1. **Unable to add availability**
**Problem:** Users cannot add availability  
**Solution:**
1. Check if the time slot was chosen or not
2. Choose only the available time slot
3. If the problem persists, quit the application and log into it again
4. Redo the adding steps
5. If nothing is fixed, contact the technical support team  
   (call: 780-xxx-xxxx (ext: 9) or email:  macrohard2024@gmail.com)

#### 2.2.2.2. **Unable to see scheduled sessions**
**Problem:** Users cannot see their scheduled sessions  
**Solution:**
1. Click on the “Schedule” button
2. Wait a couple of few minutes (if the time exceeds 5 minutes, quit the application and log into it again)
3. Check the scheduled sessions
4. If the problem persists, contact the technical support team  
   (call: 780-xxx-xxxx (ext: 9) or email:  macrohard2024@gmail.com)

#### 2.2.2.3. **Unable to edit sessions**
**Problem:** Users cannot edit sessions  
**Solution:**
1. Quit the application
2. Log into the application again
3. Click on the session that you want to edit
4. Follow the steps again
5. If the problem persists, contact the technical support team  
   (call: 780-xxx-xxxx (ext: 9) or email:  macrohard2024@gmail.com)

#### 2.2.2.4. **Unable to cancel sessions**
**Problem:** Users cannot cancel sessions  
**Solution:**
1. Check whether you are allowed to cancel the chosen session  
   (users are allowed to cancel 1 day in advance)
2. If yes, then quit the application and log into it again
3. Following all the steps to cancel the sessions
4. If the problem persists, contact the technical support team  
   (call: 780-xxx-xxxx (ext: 9) or email:  macrohard2024@gmail.com)

### 2.3. COMMUNICATION
### 2.3.1 **Unable to contact your tutor/student**
**Problem:** Users cannot communicate with their tutor/student via emails  
**Solution:**
1. Check the internet connection to make sure your device is connected properly
2. Check whether you have installed the GMAIL application yet
3. If you have done both already, check whether the recipient’s email address is correct
4. If the problem persists, close both TutorMe and Gmail applications
5. Login into TutorMe again
6. Redo every step
7. If nothing seems to be fixed, contact the technical support team  
   (call: 780-xxx-xxxx (ext: 9) or email:  macrohard2024@gmail.com)  

## B - FOR DEVELOPERS
## INSTRUCTION FOR TESTING BACKEND
These instructions will help you get started with testing the backend handlers used for our application.  
All our unit tests are on the handler functions because they contain most of the functions required 
to run the application.  
### Getting started:
*Assuming you have a terminal/code editor installed.*
1. Navigate to the .\w24MacroHard\server\handlers\ directory.
2. Run “go test -v -cover” to run all the tests on the handler functions.*
3. Preview the results in the terminal

\* : The coverage percentage is dependent on at least one successful test case. Since we use our real database for running tests such as deleting or editing tutor sessions, our coverage after a successful run is performed because the database is affected.

**Note:** EditTutorSession has its own file because it was used to run a mock database along with its test for ease of debugging.