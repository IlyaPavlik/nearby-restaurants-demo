# nearby-restaurants-demo

## About

The application allows you to view restaurants around the current location on a map.

FourSquare is used for searching restaurants: https://developer.foursquare.com/docs/api/venues/search 

## Built With
- Kotlin
- Coroutines
- Flow 
- Android Architecture Components
  - LiveData 
  - ViewModel 
  - ViewBinding 
- Dependency Injection 
  - Hilt-Dagger 
  - Hilt-ViewModel
- Network
  - OkHTTP
  - Retrofit
- Jackson

## Task
Display restaurants around the user’s current location on a map 
- Use the FourSquare Search API to query for restaurants
- Load more restaurants when the user pans the map. 
  - Cache results in-memory (no need to persist the cache). 
  - Read restaurants from the cache to show results early, but only if the restaurants 
fit within the user’s current viewport. 
- Include a simple restaurant detail page.
