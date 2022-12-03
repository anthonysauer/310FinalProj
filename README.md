# 310FinalProj Description
This repository includes code to build the android application HomeShare. Built by Charlie Hill, Anthony Sauer, Shannon Brownlee, and Diba Farokh Hamedani.

We are Team #56 for the USC class CSCI 310.

# Improvements for 2.5
Since 2.4, the following improvements have been made:
1. Added more black-box Espresso tests to cover the create account, create invitation, and login features. We used Espresso idling resources to test the features that use Firebase to retrieve information in our database. This allowed us to expand test coverage from 2.4
2. Added a "RETURN TO HOME" button to the manage invitations page. Previously one was missing and could be confusing for users.
3. Added a Logout button to the main page. This allows users to logout of their accounts after logging in or creating an account. Then they can sign in to a different account or create another new account.
4. Improved email validation functionality to ensure users register with a ".edu" email address.
5. Improvements to UI clarity to help users navigate the app.

# To run this application:
1. Open the project in Android Studio: 
  a) After downloading the zipped repository, unzip the folder
  b) Open Android Studio. Go to File>Open and on your device, choose the folder 310FinalProj
2. Open an emulator for the Google Pixel 2, API 21. If you are unfamiliar with this, see https://developer.android.com/studio/run/managing-avds 
3. Turn the emulated device on. 
4. Go to Android Studio menu item Run>run 'app'
5. Interact with the application in the emulator.
