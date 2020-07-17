# car
A project to improve the [CarRentalManagement](https://github.com/khang00/CarRentalManagement) project.
The API is hosted at [Car](https://carredo.herokuapp.com/)

## API
All the data listed in this section are minimal requirements to use the endpoints.
### Authentication
* Authenticate employees: **POST "/authenticate"**
```
{
    account: "khang",
    password: "1"
}
```
### Car Renting ("/renting")
* Find car by its id: **GET "/car?id="...""**
* Find cars which are available for renting: **GET "/car/available"**
* Rent car: **POST "/renting"**
```
{
    car: {
        id: "..."
    },
    user: {},
}
```
### Renting Request Management ("/sale")
* Find rent requests which have been approved: **GET "/contract/approved?account="employee account"**
* Find rent requests which have been approved by an employee: **GET "/contract/approved"**
* Find rent requests which have not been approved: **GET "/contract/waiting"**
* To approve for a renting request: **PUT "/contract/approve"**
```
{
    employee: {
        id: "...",
    },
    contract: "[the contract id]",
}
```
* To make payment for a renting request: **PUT "/contract/payment"**
```
{
    employee: {
        id: "...",
    },
    contract: "[the contract id]",
}
```
