
# HabitWise

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [User research]()
4. [Wireframes](#Wireframes)
4. [Schema](#Schema)

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
<img src="https://github.com/habitwise/HabitWise/blob/main/wireframes.jpg" width=600>

### [BONUS] Digital Wireframes & Mockups

### [BONUS] Interactive Prototype

## Schema 
[This section will be completed in Unit 9]
### Models
[Add table of models]
### Networking
- [Add list of network requests by screen ]
- [Create basic snippets for each Parse network request]
- [OPTIONAL: List endpoints if using existing API such as Yelp]
