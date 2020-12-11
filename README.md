
# HabitWise

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [User research]()
4. [Wireframes](#Wireframes)
4. [Schema](#Schema)
4. [Milestone1](#Milestone1)
4. [Milestone2](#Milestone2)

## Overview
### Description
This app allows users to keep track of their habits with friends. On top of logging personal habits to form a streak (for example: read for 20 minutes every day for 7 days in a row), users will be able to share a habit with a friend so they will motivate each other to keep their streak (for example: BOTH people worked out for 5 days in a row!)

### App Evaluation
- **Category:** productivity / lifestyle / social 
- **Mobile:** since personal habits should be tracked anytime, anywhere, the mobile phone is the most convenient way to log the user's daily progress
- **Story:** this is a compelling story because personal habits can help improve productivity and lifestyle. Through our initial user research, we found that many people have tried building habits but gave up because of the lack of personal motivation, so we hope that building a habit with a friend would give them more accountability. 
- **Market:** this app is scalable by bringing personal habits to the social world. Currently, there are many habit-tracking apps in the Android market, but we are not aware of any that emphasize a social function so friends can build a streak for completing their habits together. 
- **Habit:** this literally is an app about building personal habits... so the average user will have to consume this app frequently (especially if they have to log their daily habits)
- **Scope:** it will be technically challenging to first create a habit-tracking app that works for the indiviudal user, and then connect them to their friends so they can see each other's progress and build a habit together. 

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**
* User can keep track of their daily and weekly habits
* User can add create a new habit to track by entering the frequency per day or week, and which days of the week they would like to complete this task
* User can view a streak of how long they've accomplished their habit in a row


**Optional Nice-to-have Stories**

* User can share a habit with a friend. Both of them must complete their goal in order to maintain their streak
* User can see analyses of their progress (bar chart, calendar view, completion statistics, etc.)

### 2. Screen Archetypes

* Log in
   * Enter username and password to authenticate their identity
* Sign up
   * Create a new user account 
* Habits (stream)
   * See their habits to complete for today. Can either press on toggle or the counter to complete it for today. 
* Habit details
   * See their personal streak or their streak with a friend
   * See a calendar view of how many days they've completed this month
* Analysis
   * View a list of their habits, in addition to the streaks that they hold 
* Profile
   * View their list of friends
* Notifications
   * View their friend requests
   * Scalable for other usages (ex. notification to complete a habit, app update, etc.)
* Add a new habit (creation)
   * Enter information for a new habit (ex. details, frequency, weekly/daily, friend to share with)


### 3. Navigation

**Tab Navigation** (Tab to Screen)

* Home
* Analysis
* Profile

**Flow Navigation** (Screen to Screen)
* Log in
   * => Habits 
* Sign up
   * => Habits
* Habits (stream & home)
   * => Habit details
* Habit details
   * => Habits
* Analysis
   * => Habit details
* Profile
   * => Notifications
* Add a new habit (creation)
   * => Home (after creation is complete)

## User research
We conducted a [survey](https://docs.google.com/forms/d/e/1FAIpQLSeBJ-VV-j40B85zXIQJ910V9O0WU_ECB5QWDoe_l-kGmavPag/viewform) to understand how people are tracking their habits in general. Currently, we have 48 survey respondents. Here are some research insights: 

1. 50% (24/48) are tracking their habits through methods such as relying on memory, using a planner, or using technology. Those who track their habits are motivated by visual cues to track their consistency. 
2. 73% (35/48) of respondents have tried to develop habits but have not been able to. The top three reasons are "lack of follow up", "laziness", "lack of accountability/incentives"
3. 33% (16/48) of respondents have started sharing new habits with friends or family during the pandemic, many reflecting that it "helps time pass" and "mutally benefit and push each other" and "eases some of the dread

Through these user insights, we are confident that a habit-tracking app where you can share habits with a friend will help people become more motivated to continue their progress! Moreover, we will pay a lot of attention to the UI in order to make it a nice experience to track your habits. 

## Wireframes


### [BONUS] Digital Wireframes & Mockups
<img src="https://github.com/habitwise/HabitWise/blob/main/wireframes.jpg" width=800>

### [BONUS] Interactive Prototype
<img src="https://github.com/habitwise/HabitWise/blob/main/wireframes.gif" width=300>

## Schema 
[This section will be completed in Unit 9]
## Models
### User


| Property | Type     | Description |
| -------- | -------- | -------- |
| ObjectID | String   | Unique ID for the user field     |
| username | String   | Username of the user     |
| password | String   | Password of the user     |
| email    | String   | email Id  of the user   |
| displayPic | File   | Profile pic (Image)   |
| emailVerified | boolean   | Flag to identify if user acocunt is verified|



### Habit

| Property | Type     | Description |
| -------- | -------- | -------- |
| ObjectID | String   | Unique ID for the habit object|
| title | String   | Title of the habit    |
| users  | Arrays<User>  | Author of the habit|
| recurrence |  Number  | Daily (0)/Weekly(1)|
| frequency |  Number  | Number of times per day|
| days | Arrays | If weekly, days on which habit should be accomplished|
| status |  String  | Complete / Incomplete|
| shared |  Boolean  | Whether the habit is shared between users|

### Activity

| Property | Type     | Description |
| -------- | -------- | -------- |
| ObjectID | String   | Unique ID for the habit's activity|
| date | DateTime   | Date for which activity is recorded    |
| counter  | Number   | Number of times user accomplished habit|
| user |  Pointer to User  | user who accomplished it|
| habit |  Pointer to Habit  | Many to one reference to Habit's object|

### HabitUserMapping

| Property | Type     | Description |
| -------- | -------- | -------- |
| ObjectID | String   | Unique ID for the habit object|
| habit |  Pointer to Habit  | Many to one reference to Habit's object|
| users  | Arrays<User>  | Author of the habit|


### Friends
This is a many to many relationship between User objects. This table defines user's friends

| Property | Type     | Description |
| -------- | -------- | -------- |
| ObjectID | String   | Unique ID for the user-friend relation|
| user1 |  Pointer to User  | User details|
| user2 |  Pointer to User  | Friend details|

### Requests

| Property | Type     | Description |
| -------- | -------- | -------- |
| ObjectID | String   | Unique ID for the user-friend relation|
| fromUser |  Pointer to User  | User who send the request|
| toUser |  Pointer to User  | User the request is sent to|
| status |  String  | Pending / Accepted( req will be removed from table) / Declined|





## Networking
### List of network requests by screen
NETWORK MODELS

*  Habits Screen
    1. (Read/GET) Query all habits where user is author and with respective date selected. 
    ```Java
     ParseQuery<Habit> query = ParseQuery.getQuery(Habit.class);
        query.include(Habit.KEY_USER);
        query.findInBackground(new FindCallback<Habit>() {
            @Override
            public void done(List<Habit> habits, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting habits", e);
                    return;
                }
                for (Habit habit : habits) {
                    Log.i(TAG, "Habit:" + habit.getTitle() );
                }
               
            }
        });
    ```
    2. (Create/POST) Create a new activity object on the habit.
    3. (Update/PUT) Update the activity object.
    4. (Read/GET) Query the activities of the habits displayed for that day.

* Login Screen
    1. (Read/GET) Query the user object.
    ```Java
    ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error occured while login");
                    return;
                } else {
                    goMainActivity();
                    Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    ```    

* Signup Screen
    1. (Create/POST) Create a new user object.
    2. (Read/GET) Query the user objects (to avoid reusing the username).


* Add new habit Screen
    1. (Create/POST) Create a new habit object.
    2. (Update/PUT) Update the habit object.


* User Profile Screen
    1. (Read/GET) Query logged in user object
    2. (Update/PUT) Update user profile image on the user object.
    3. (Read/GET) Query the friends objects which has the user in it.
    4. (Create/POST) Create a new requests object.
    5. (Read/GET) Query the requests objects if the user is the from_user or to_user.


* Notifications Screen
    1. (Read/GET) Query all requests object  where user is the to_user.
    2. (Update/PUT) Update requests objects if a req is accepted or ignored.
    3. (Delete) Delete existing requests object if the user accepts/declines .

* Habit Details Screen
    1. (Delete) Delete existing habit
    object.
    2. (Update/PUT) Update  status of the habit object to ‘completed’
    3. (Read/GET) Query the activity objects of the habit selected.

* Analysis Screen
    1. (Read/GET) Query all habit objects and related activity objects where user is author.
    
    
## Milestone1 

###  User Stories

In this milestone, we have 

[X] Implemented skeleton code for the application to enable easy integration of other functionalities.

[X] Build and integrated Parse with the application.

[X] Implemented backend of "Add a habit" activity to add a new habit and created a basic UI.

[X] Successfully integrated parse for the "Add a habit" activity.

[X] Implemented backend of "Homepage" activity by showing the list of habits in a recycler view and created a basic UI.

[X] Successfully integrated parse for the "Homepage" activity.


###  Build Progress

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='https://github.com/habitwise/HabitWise/blob/main/Milestone1.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

###  Next Sprint
[X] Have planned the milestone 2 and have created, assigned & added issues to the storyboard.


## Milestone2 

###  User Stories

In this milestone, we have 

[X] Implemented the SignUp Functionality with features like validating the password and email-ID, uploading the profile picture to the database.

[X] LogIn functionality was built by integrating the project with parse dashboard.

[X] Completed the "Add a habit" activity to add a new habit with features like the frequency of the habit, its recurrence(daily / weekly), with the exact days.

[X] Successfully integrated parse for the "Add a habit" activity.

[X] Improvised "Homepage" activity by showing the list of habits in a grid view and integrated with Task Model.

[X] Successfully connected the Task and Habit models to fetch the data of each tasks' habit.


###  Build Progress

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='https://github.com/habitwise/HabitWise/blob/main/Milestone2.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

###  Next Sprint
[X] Have planned the milestone 3 and have created, assigned & added issues to the storyboard.



