# CryptoPoll Server

Cryptopoll is an E-Voting app that stores votes in a secure and tamper-proof way on a Blockchain. 

## Group Members
Following are the group members for this project:
- Aagam Shah (2019A7PS1320H)
- Aryan Chaubal (2019A7PS0130H)

## Tech Stack
- Django
- SQLite3
- Kotlin for Native Android

## Installation
It is preferable to start the Django project by creating a virtual envionment and installing dependencies using Pipenv after entering this directory. 
```
pipenv shell
pipenv install
```
Run the migrations:
```
python manage.py migrate
```
Start the app on port 8000:
```
python manage.py runserver 0.0.0.0:8000
```

## Screenshots

## Description
CryptoPoll is an online polling and voting solution that uses a blockchain to store the voting data in a secure, tamperproof way. 

### Creating a new block
A new block is created every 5 minutes if there are any available votes that have not been verified yet.

The `create_block()` functionality is in `./blockchain/models.py`.

### Proof of Work
The blocks are verified using Proof of Work. This is done by hashing the block contents with an appropriate nounce, so that the hash starts with 5 zeros. This difficulty was found to be the most appropriate for this particular application. 

The miner runs every 5 minutes on a separate thread so as to not block the main application.
The miner can also be triggered manually by hitting the API Endpoint `/miner` using a POST request. 

The `mine_block()` function is in `./miner/miner.py`.

### Zero Knowledge Proof
In order to make sure that the app has the proper vote details (vote id), a zero knowledge proof is conducted between the backend and the frontend, so that the backend can verify that the app has the vote id without actually knowing it. 

The backend side functionality for the Zero Knowledge proof is stored in `./zkp/views.py`

### Viewing user votes
The user can view their votes within the app.