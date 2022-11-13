# Safe Hiring (EMS)

Safe Hiring is a spring-boot application to make hiring process easy for employer. Application fully support by spring-security Role based authentication and authorisation.

## Installation

Use the maven manager [maven](https://maven.apache.org/) to install EMS.

```bash
mvn clean install -U
```

## Usasge
1. Import [postman](https://www.postman.com/downloads/) collection in postman app and and global variable for BASE_URL

## Technologies

1. Java 11
2. Maven 3
3. Spring-boot
4. Spring-boot securities
5. Java mail


## Group

1. Employee - A user with Employee group can GET job offer based on his PAN number only.
2. Employer - A user with Employer group can GET, POST, UPDATE job offers into system
3. Support - TO-DO
4. Admin - A user with Admin group can GET, POST, UPDATE, and DELTE a job-offer


TO-DO
1. JWT token based login
2. Api for Employee
3. Allowed api for Support Group
4. Unit tests
5. Integration test



## Contributing

Pull requests are welcome. For major changes, please open an issue first
to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License

[EMS](http://www.safehiring.com)
