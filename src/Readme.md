# **Vidispine coding assignment** #

This is gradle built spring boot application.

To run this application you need to build and run with gradle

I used following commands on my Windows PC

* **Server** </br>
  gradle run --args="server 8080"
  gradle run --args="server 8081"
* **Client** </br>
  gradle run --args="client -2 -1 1 1 200 512 512 4 127.0.0.1:8080 127.0.0.1:8081"
  

This will start two servers that are listening to ports 8080 and 8081
and this will also start a client that will try to get
data from the servers. Client will then save image.pgm file 
into project directory.


There is a lot of room for improvement:
* Namings here and there
* Validation
* Error handling
* Unit test
* Probably some logic errors, for example, calculating the Mandelbrot set
feels shaky.
