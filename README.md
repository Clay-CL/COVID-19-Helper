# COVID-19 Helper and Info App
<img src="https://img.shields.io/badge/version-v1.0.0-informational"> <img src="https://img.shields.io/badge/android-5.0%2B-success?style=plastic&logo=android">

<img src="https://img.shields.io/badge/kotlin-%230095D5.svg?&style=for-the-badge&logo=kotlin&logoColor=white"> 

This is an App which makes use of [COVID-19 India API](https://api.covid19india.org/) to display the Daily Increase of Corona Virus Cases in India,
Nationwide and State-wise. 

<p align="center"><img src="/gifs/medical_staff.gif" width="300px" height="300px"></p>

Firstly, A Big Shout out and thanks to all medical staff, police and everyone working in the frontlines of this pandemic, some of who also sacrified their lives. God bless their effort.

Originally it was taught by Rahul Pandey Sir, (check out his youtube tutorial [here](https://www.youtube.com/playlist?list=PL7NYbSE8uaBB1EiPYScD66ZVWyu6cOyrR))

This app makes use of MVVM architecture and Kotlin Coroutines.
The clean architecture of the app was inspired by the [MVVM news App](https://www.youtube.com/playlist?list=PLQkwcJG4YTCRF8XiCRESq1IFFW8COlxYJ) 
by [Philipp Lackner](https://github.com/androiddevs18/MVVMNewsApp)

A Big Shout-out and thanks to both of them, God Bless them. Do subscribe to both their YouTube channels!

UI is inspired by the Robinhood Stock Trading App.

Libraries used:
- [Spark from Robinhood](https://github.com/robinhood/spark)
- [Ticker from Robinhood](https://github.com/robinhood/ticker)
- [Nice Spinner](https://github.com/arcadefire/nice-spinner)
- [Dagger Hilt](https://dagger.dev/hilt/) for Dependency Injections
- [Lottie Animations](https://github.com/airbnb/lottie-android)

### Screenshots:<br/>
|<img src="/screenshots/splash_screen.png" height="512px"/>|<img src="screenshots/main_fragment_all_provinces.png" height="512px"/>|<img src="screenshots/main_fragment_total_cases.png" height="512px"/>|
|:--:|:--:|:--:|
|*Splash Screen*|*Main Fragment*|*Toggle between Daily and Total numbers*|

You could view statistics for other provinces in India using the spinner,
|<img src="/screenshots/main_fragment_spinner.png" height="512px"/>|<img src="screenshots/main_fragment_tamil_nadu.png" height="512px"/>|<img src="screenshots/main_fragment_tamil_nadu_recovered.png" height="512px"/>|
|:--:|:--:|:--:|
|*Select a province*|*Tamil Nadu*|*Tamil Nadu Daily Recovered*|

Also change between overall and monthly timeline view:
|<img src="/screenshots/main_fragment_daily_deaths.png" height="512px"/>|<img src="screenshots/main_fragment_month_timeline.png" height="512px"/>|
|:--:|:--:|
|*Daily death increase*|*Death increase over a month*|

In the Other Fragments,
made use of Google Maps API v2, [SDk for android](https://developers.google.com/maps/documentation/android-sdk/overview)
- India Fragment, used a Choropleth Plot to visualize the statistics
- World Fragment, used a Bubble Chart

For the Choropleth plot,
1. I've obtained the Boundaries from OpenMaps API, in a .geojson format
 <p align="center"><img src="http://marine.gov.scot/sites/default/files/styles/medium/public/openstreetmap.png?itok=luGfjZ5T"></p>
 
 I've collected the boundary data from their [Nominatim Search Tool](https://nominatim.openstreetmap.org/ui/search.html) and then used the OSM Id and downloaded the .geojson polygon from this [download tool](http://polygons.openstreetmap.fr/). The files are very large, so to shrink them while preserving most of the structure, I used this [MapShaper](https://mapshaper.org/) an [OpenSource Tool](https://github.com/mbloch/mapshaper)

