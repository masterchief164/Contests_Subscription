#  Android-Study-Jams

Contests tracking App

<b> Problem Statement: </b>

A lot of students partcipate in online coding contests. One of the challenges they face is the lack of single place to track all ongoing and upcoming contests. This mobile application tries to solve that problem.


<b> Proposed Solution : </b>

This project proposes a “Contests Subscription” to keep track of the major competitive coding platforms and provides all the information about them. It's features include creating a user profile to filter only the contests from the platforms they wish to participate. The project's future scope is to add even more platforms and also to add a notification reminder to alert the user of an upcoming contest.

<b> Functionality & Concepts used : </b>

- The App has a very simple and interactive interface which helps the users select their respective coding platforms.
- Constraint Layout : Most of the activities in the app uses a flexible constraint layout, which is easy to handle for different screen sizes.
- Simple & Easy Views Design : Use of familiar audience EditText with hints and interactive buttons makes it easier for users to register or sign in without providing any detailed instructions pages. Apps also uses App Navigation to switch between different screens.
- RecyclerView : To present the list of upcoming contests the app uses the efficient recyclerview.
- LiveData & Room Database : The live data is used to manage user credetials and the room database is used to save the user's preferences.
- Retrofit & GSON : The app used the retrofit library to fetch the data from the web server and then uses the GSON parser to parse the data.

<b> Application Link & Future Scope : </b>

The app is currently in the Alpha testing phase with IIITDMJ with a limited no. of users, You can access the app : [Contests Subscription](https://github.com/masterchief164/Contests_Subscription/releases/download/initial_publish/constests_Subscription.apk).

Once the app is fully tested we are planning to add the notification feature and support other platforms.
