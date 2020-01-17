all:
	virtualenv . && source bin/activate && make install_requirements

install_requirements:
	pip3 install -r requirements.txt