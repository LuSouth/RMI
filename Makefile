# Makefile
# 15-640/440 Project 2
# Douglas 


default:
	find ./abhi -name *.java > filelist
	javac @filelist
	rm filelist

clean: 
	rm -r ./out

# Run Instructions
# Server:
#   java -cp out/ distsys.RmiServer
# Client:
#   java -cp out/ distsys.RmiClientMaths server_hostname
#   java -cp out/ distsys.RmiClientSleep server_hostname [sleep_time]