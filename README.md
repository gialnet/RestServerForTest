# Hello
http localhost:6969/hello

# Wait for n seconds
http localhost:6969/wait/2

# Domain update port
http PATCH localhost:6969/update/12 \
domain=00 \
environment_folder=stressc02

# Server update change all parameters
http PUT localhost:6969/change/stressc02 \
domain=00 \
environment_folder=stressc02 \
admin_server_port:=23400 \
admin_server_name=algeciras.cc.cec.eu.int