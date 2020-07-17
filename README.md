# car
A project to improve the CarRentalManagement project

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
### Car Renting
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
### Renting Request Management
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
