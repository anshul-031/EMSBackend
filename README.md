
# Safe Hiring (EMS)

Safe Hiring is a spring-boot application to make hiring process easy for employer. Application fully support by spring-security Role based authentication and authorisation.


## Documentation

[Documentation](http://www.safehiring.com)


## Groups/Roles

- Admin - A user with Admin group can GET, POST, UPDATE, and DELTE a job-offer
- Employer - A user with Employer group can GET, POST, UPDATE job offers into system
- Support -TO-DO
- Employee - A user with Employee group can GET job offer based on his PAN number only.


## Installation

Install EMS with maven

```bash
  mvn clean install 
  cd ems
```
    
## Run Locally

Clone the project

```bash
  git clone https://github.com/charlienegi/EMSBackend.git
```
Edit `application.properties` and change mysql db username and password.

Create a database with name `safehiring` in mysql.

Go to the project directory

```bash
  cd ems
```

Install dependencies

```bash
  mvn clean install -U
```

Start the server

```bash
  mvn spring-boot:run
```


## Running Tests with postman

To run api tests with postman. Import postman collection and set `BASE_URL` in global variable of postman app 
and execute api tests.


## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate

See `contributing.md` for ways to get started.

Please adhere to this project's `code of conduct`.

## Support

For support, email admin@safehiring.com or join our Slack channel.


## License

[EMS](http://www.safehiring.com)

