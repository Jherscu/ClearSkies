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
- Repository layer
- Hilt
- Local storage using Room and Preferences Datastore
- Fragments and Jetpack Navigation Component
- SafeArgs
- JUnit, Espresso, Hamcrest
- ViewModel
- LiveData
- WorkManager (Keep weather cache for each stored city up to date, try to fetch at least once daily?)
- LeakCanary
- R8 for release build

# User Stories:

## First Use:

![IMG-0687](https://user-images.githubusercontent.com/62267982/161458505-fa626b1d-b27b-487e-b604-d1378881b8ec.jpg)
![IMG-0688](https://user-images.githubusercontent.com/62267982/161458497-f8546cfa-ace6-478c-9ad1-02cb82795498.jpg)

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

![IMG-0689](https://user-images.githubusercontent.com/62267982/161458474-cafb3f4d-9827-4289-a085-738b0e7e9802.jpg)

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

# SDLC TASKS:

## Planning/Analysis/Design Stage:

- Plan UI/Basic Functionality Diagram [X - 4/2/22]

- Write User Stories [X - 4/3/22]

- Write Preliminary Plan (TASKS) [X - 4/3/22]

- Add to Git [X = 4/3/22]

- Setup Gradle/Lint/Permissions [X = 4/8/22]

- Draw Preliminary Class/Architecture Map []

- Create Package/File Structure []

## Development/Testing Stage:

- Write Modules []

- Write Xml Files []

- Implement Basic Layout/Menu []

- Implement Nav Functionality/Tests []

- Write Networking Code/Unit Tests []

- Write Database to Hold Cached Results []

- Write Repository and Domain Layers []

- Wire Use Cases to the Appropriate ViewModels []

- Link Data to UI []

## QA Testing/Refining Stage:

- Profile/Fine Tune UI []

- Profile/Fine Tune Business Logic []

- TEST TEST TEST: Capture Edge Cases []

## Deployment/Maintenance Stage:

- Prep for Play Store []

- Post to Play Store []
