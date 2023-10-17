# Rest Server For Test
The primary objective of this project is to establish a dedicated server for testing REST API calls. 
Various applications can utilize this server to verify the functionality of operations 
such as "get, post, put, patch, delete". 
Additionally, the code demonstrates sophisticated operations using Java lambda functions 
to proficiently handle JSON files.  

## API end points

### Hello
http localhost:6969/hello

### Wait for n seconds
http localhost:6969/wait/2

### Domain update port
http PATCH localhost:6969/update/12 \
domain=00 \
environment_folder=stressc02

### Server update change all parameters
http PUT localhost:6969/change/stressc02 \
domain=00 \
environment_folder=stressc02 \
admin_server_port:=23400 \
admin_server_name=algeciras.cc.cec.eu.int

### delete server
curl -u peanton:pass -H 'Accept: application/json' -X DELETE localhost:6969/delete/00/stressc02
http -a peanton:pass DELETE localhost:6969/delete/00/stressc02