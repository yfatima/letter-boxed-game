# letter-boxed-game

features needed for the game

- LDAP functionality (login/logout, timeout, save user state in the DB)
- store data in json
- UI (simple) (canvas for connecting dots)
- Send data securely (use encryption, hash function, salts)
- Input validation
- Logging of user moves/ stored in the DB
- Have self signed certs (check them)
- Have session with timeout (users can continue even if they leave the game or logout if timeout is not triggered yet)
- Use one tool to find vulnerabilities and fix them
- Prevent DDOS attack
- serve the app on heroku

What to turn in for the project

- 5 minute video of the game
- 12 page report
    - intro
    - design/ architrcture
    - installation instructions
    - operating instructions
    - game rules
    - why do you believe it's secure (assurance case; most of the report)


    Two Players credentials

    username: yfatima
    password: hello

    username: ssmith
    password: admin


todo:

- check is user is loggedin before it can go to home page

[
    {
        "userName": "yfatima",
        "email": "yfatima@gmu.edu",
        "password": "$2a$10$Z2JZ3kNeg6RLIYz8Tw8Rc.SfqQWRKP9jCtNZwVY3XrsjTd99DjN0a",
        "wordList": [],
        "score": 0
    },
    {
        "userName": "ssmith",
        "email": "ssmith@gmu.edu",
        "password": "$2a$10$cQbhoEkL6buuGIMm1MRSLO2XygkLd6G5dXGx72F1S1jpgl1S5wMza",
        "wordList": [],
        "score": 0
    }
]
