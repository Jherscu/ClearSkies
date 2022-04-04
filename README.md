# Clear Skies
## Simply Weather

# Best practices weather app utilizing:

- Navigation Drawer
- Databinding
- Paging library?
- RecyclerView
- Networking w/:
  - retrofit
  - moshi
  - Open Weather Map's RESTful API (Weather Reports)
  - Radar's RESTful API for Geocoding (Converting City Name to Lat/Long)
- Coroutines
- Domain layer
- Hilt
- Local storage using Room and Preferences Datastore
- Fragments and Jetpack Navigation Component
- SafeArgs
- JUnit, Espresso, Hamcrest
- ViewModel
- LiveData
- WorkManager (Keep weather cache for each stored city up to date, try to fetch at least once daily?)

# User Stories:

## First Use:
- User enters app at Landing/Home Fragment (Sees Message: No Location Set!)
- Clicks hamburger icon in menu bar to expand nav drawer
- Clicks locations and goes to Locations Fragment
- Clicks FAB in Locations Fragment to open Add City Fragment
- User enters desired city to search bar
- Resulting best matches pop up in case of misspelling/ambiguity/lack-of-state/etc.
- User selects best match
- Dialog pops up to confirm choice
    - User denies dialog and selects another choice
    - User accepts dialog and is returned to updated Locations Fragment
- User clicks the city to select it and is returned to Home Fragment displaying its preferred weather

## Add New City and Set it as Home Location then Delete Old City:
- Click hamburger icon from anywhere in app
- Select Locations and goes to Locations Fragment
- Clicks FAB in Locations Fragment to open Add City Fragment
- User enters desired city to search bar
- Resulting best matches pop up in case of misspelling/ambiguity/lack-of-state/etc.
- User selects best match
- Dialog pops up to confirm choice
    - User denies dialog and selects another choice
    - User accepts dialog and is returned to updated Locations Fragment
- Click options icon on new city
- Select "Set Home" dropdown to remove home designation from old city and set it to the selected city
- Click options icon on old city
- Select "Delete"

## Edit Preferences:
- Click hamburger icon from anywhere in app
- Select Preferences and goes to Preferences Fragment
- Click °C radio button to toggle unit
- Click Hourly radio button to toggle home screen to hourly mode
- Click 24Hr mode switch to change from 12Hr to 24Hr mode
- Click Dark mode switch to change from light to dark mode

## Navigate Weather for a City:
- In (Daily) Home Fragment swipe the Horizontal Circular RecyclerView to view days between yesterday and one week from today
- Click hamburger icon
- Select Hourly to see the same screen with Hourly representation for the same (Home) city instead of the daily view
- Swipe the Horizontal Circular RecyclerView to view hours between the start of the day, through now, up till the end of the day
- Click hamburger icon
- Select Locations to go to the Locations Fragment
- Click another city to select it and is returned to Home Fragment displaying its preferred (Daily) weather
- Repeat process to view hourly and switch cities again

# TASKS:

## - Plan UI/Basic Functionality Diagram [X - 4/2/22]

## - Write User Stories [X - 4/3/22]

## - Write Preliminary Plan (TASKS) [X - 4/3/22]

## - Add to Git [X = 4/3/22]

## - Draw Preliminary Class Map []

## - Setup Gradle/Lint/Permissions []

## - Create Module []

## - Create Xml Files []

## - Implement Basic Layout/Menu []

## - Implement Nav Functionality/Tests []

## - Write Networking Code/Unit Tests []

## - Create Database to Hold Cached Results []

## - Create Repository Layer []

## - Wire Repository to the Appropriate ViewModels []

## - Link Data to UI []

## - Fine Tune UI []

## - Fine Tune Business Logic []

## - TEST TEST TEST: Capture Edge Cases []

## - Prep for Play Store []



