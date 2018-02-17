# ==========Playlist Parent ============#
## Modules? ##
* [Playlist App](playlist-app/readme.md)
* [Playlist Manager](playlist-manager/readme.md)
* [Playlist API](playlist-api/readme.md)
* [Playlist Model](playlist-model/readme.md)
* [Playlist Repository](playlist-db/readme.md)

## Class Diagram ##
* [Playlist Class Diagram](Content Playlist Class Diagram.png)

## How to run application? ##
The Program can be run using com.playlist.app.ContentPlaylistCreationApplication file using main method defined inside of this file. 
This is a spring boot application that starts a tomcat container internally at default port 8080.

'''javascript
   @SpringBootApplication
	public class ContentPlaylistCreationApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContentPlaylistCreationApplication.class, args);
	}
}
'''


## How to Test? ##
I am attaching the postman collection in the same folder where this file is located, please refer to it for the various test cases.

##Steps to install postman in chrome browser##
One can download the postman app from link https://chrome.google.com/webstore/detail/postman/fhbjgbiflinjbdggehcddcbncdddomop?hl=en
See Other useful resources for running postman https://www.getpostman.com/docs/postman/collections/creating_collections

## Improvements If i had more time ##
- I would add exhaustive set of test cases in every  module. 
- Make use of Mockito or some other test mocking  framework
- Save the data in a database
- Enable Hystrix at Service layer
- Improve the documentation
- Improve the exception handling mechanism in the service






