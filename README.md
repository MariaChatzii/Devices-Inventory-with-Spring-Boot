RESTful backend system using edge technologies and best practices.

Used Technologies:
1. Spring-boot for the backend.
2. MySQL


**Project Description**
Nowadays, most of the companies are giving devices (Laptops, Tablets, Mobile phones) to their employees. This Project is about device inventory which makes companies able to manage the fleet of the devices. Project provides a global solution with which each company can use to manage their devices. 


**Basic entities:**

**Company** 

The company is an organization and every company has many employees. 

**Employees** 

The employees are unique for each company which means that an employee cannot appear in two or more companies at the same time. An employee can have zero or many devices.

**Devices** 

Every device has a unique serial number and belongs to only one company. A device belongs to zero or one employee.


**API** 

**Companies** 

Companies will be hardcoded in the database, no CRUD for companies. 

**Employees**

GET /employee/all : displays the data about all the employees located in the database.

GET /employee/{id} : displays the data about the employee specified by a unique id. 

GET /employee/name/{name} : displays the data about the employees who have the specific name.

GET /employee/email/{email} : displays the data about the employee who have the specific email.

GET /employee/company :  Two optional Path Variables: companyName and companyAaddress (of the company the employee work for).  If only company name is given as path variable, the endpoint displays all the employees who work for a company with the specific name. If only company address is given as path variable, the endpoint displays all the employees who work for a company with the specific address. If there is not path variable, the endpoint displays all the employees located in the database.

POST /employee/add : if the employee, are going to be added, does not already exist in the database, they are created and located in the database.

POST /employee/addMany : if all the employees, is going to be added, do not already exist in the database, they are created and located in the database. Even if only one of these employees already exists in the database, none of them is created and an appropriate message is displayed.

PUT /employee/update : if the employee, are going to be updated, already exists in the database, they are updated.

PUT /employee/updateMany : if all the employees, is going to be updated, already exist in the database, they are updated. Even if only one of these employees does not already exist the database, none of them is updated and an appropriate message is displayed.

DELETE /employee/delete/{id} : delete the employee specified by a unique id. If employee with this id already exists in database, they are successfully removed and an appropriate message is displayed. If there is no employee with this id already in the database, an appropriate error message is displayed.


**Devices**

GET /device/all : displays the data about all the devices located in the database.

GET /device/{serialNumber} : displays the data about the device specified by a unique serial number. 

GET /device : Two Path Variables: Name and type of device. All the devices with this specific combination of name and type are displayed.

GET /device/company : Two optional Path Variables: companyOwnerName and companyOwnerAddress (of the company the device belongs to). If only company name is given as path variable, the endpoint displays all the devices which belongs to a company with the specific name. If only company address is given as path variable, the endpoint displays all the employees which belongs to a company with the specific address. If there is not path variable, the endpoint displays all the devices located in the database.

POST /device/add : if the device's serial number, is going to be added, does not already exist in the database, they are created and located in the database.

POST /device/addMany : if all the devices, are going to be added, do not already exist in the database, they are created and located in the database. Even if only one of these devices already exists in the database, none of them is created and an appropriate message is displayed.

PUT /device/update : if the employee, are going to be updated, already exists in the database, they are updated.

PUT /device/updateMany : if all the employees, is going to be updated, already exist in the database, they are updated. Even if only one of these devices does not already exist the database, none of them is updated and an appropriate message is displayed.

DELETE /device/delete/{serialNumber} : delete the device specified by a unique serial number. If employee with this id already exists in database, they are successfully removed and an appropriate message is displayed. If there is no employee with this serial number already in the database, an appropriate error message is displayed.

*Before adding or updating a device, if employeeOwner work for the company the device belongs to is checked

**Data visible by the user for each entity:

**Device**

name

type

companyOwnerName

companyOwnerAddress

employeeOwnerName

employeeOwnerEmail

**Employee**

name

email

companyName

companyAddress

devicesCount

**ERP Diagram**

![](https://github.com/MariaChatzii/Devices-Inventory-with-Spring-Boot/blob/master/erp.png)
