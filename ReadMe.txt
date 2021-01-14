To run client server program you need to :
	run command "javac *.java" on cmd/shell to compile all the files
	run each microserver at cmd/shell by running command javac "microserver" where you replace "microserver" by by the microserver name
	run master program by using the command java Master port where you replace port by the port number you want to use
	finally run client program from command prompt using command java Client port ip where port is the same port you used when running master and ip is the ip
	of the server master is running on.
	*note: Master and all the microservers must be running on the same server
the Client-master programs were only tested locally using port 6666 and localhost as ip