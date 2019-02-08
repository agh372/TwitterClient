# *Twitter Client*

**Twitter Client** is an android app that allows a user to view his Twitter timeline and post a new tweet. The app utilizes [Fabric SDK](https://docs.fabric.io/android/twitter/twitter-core.html).

## Features

  * [x] User can **sign in to Twitter** using OAuth login
  * [x] User can **view tweets from their home timeline**
  * [x] User is displayed the username, name, and body for each tweet
  * [x] User is displayed the [relative timestamp](https://gist.github.com/nesquena/f786232f5ef72f6e10a7) for each tweet "8m", "7h"
  * [x] User can view more tweets as they scroll with infinite pagination. Number of tweets is unlimited.
    However there are [Twitter Api Rate Limits](https://dev.twitter.com/rest/public/rate-limiting) in place.
  * [x] User can **compose and post a new tweet**
  * [x] User can click a “Compose” icon in the Action Bar on the top right
  * [x] User can then enter a new tweet and post this to twitter
  * [x] User can add tweets to their favorite list


The following **bonus** features are implemented:

* [x] Retrofit is used as REST client to retrieve and update data
* [x] Implemented SQLite feature to store the favorite tweets
* [x] Tweets can be viewed offline which are stored as cache using weak reference  
* [x] Leverage the popular [GSON library](http://guides.codepath.com/android/Using-Android-Async-Http-Client#decoding-with-gson-library) to streamline the parsing of JSON data.
* [x] [Leverage RecyclerView](http://guides.codepath.com/android/Using-the-RecyclerView) as a replacement for the ListView and ArrayAdapter for all lists of tweets.
* [x] MVC pattern is implemented
* [x] Heroes tab feature allows user to search for any hero and tweet about it (Its a feature just developed to demonstrate API usage. [Marvel](https://developer.marvel.com/) API is used for it)


## License

    Copyright [2016] [Arjun Gurudatta Hegde]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
