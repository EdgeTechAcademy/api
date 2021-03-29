# What can the application do?

### We have 4 End Points
* /req/movies/{title}
    * Ask IMDB for a list of movies matching the title
    * page rendered:
        *  "movieList"
    * model attributes passed:
        * movies: imdb.results        //  the list of movies from IMDB
    * JSON format:
        * models the Imdb object
            * String searchType;
            * String expression;
            * Movie[] results;            //  this is what we are looking for
            * String errorMessage;
* /req/examples
    * displays a test page to exercise different AJAX calls.
    * page rendered:
        * "apiExamples"
    * model attributes passed:    none
    * Movie search:       IMDB API request
        * click on the poster to to more details with a separate API request
        * click on the title to go to IMDB
    * Weather Forecast:   openWeather API request
        * enter a city or a lon/lat to get the weather report
        * city and lon/lat will use two different API requests. The JSON result is reformatted
        * to the so the page rendering can use the same JSON
    * APOD:               APOD API request
        * select a date and generate the Astronomy Picture Of the Day
        * if the picture of the day is a video the video will run
* /req/posts
    * makes a request to pseudo API service to return an array of Posts
    * page rendered:
        * "postList"
    * model attributes passed:
        * posts: posts                //  an array of Post objects
    * JSON format:
        * models a Post object
            * int userId;
            * int id;
            * String title;
            * String body;
* /req/posts/{userId}
    * makes a request to pseudo API service to return an array of Posts for a specific userId
    * The rest is the same as the /req/posts above
    