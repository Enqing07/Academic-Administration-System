# Academic Administration System
## Overview
A standalone Java GUI application for academic administrators to manage student enrollment, subject registration, accommodation assignments, and automated billing. Designed for internal use, the system supports dynamic fee calculations based on course load and accommodation choices, enabling efficient and accurate record management.

## Main Features
- Enroll new students and view existing enrollment records
- Manage subject and accommodation registration
- View automated billing based on course and accommodation
- Update fee structures and apply discounts

## System Design
- [Use Case/Class Diagram/Sequence Diagram](UML_Diagram.pdf)

## Design Pattern
The system applies the **Facade** Design Pattern through a `SystemFacade` class that serves as the administrative layer of the application. It provides a unified interface to core admin functions such as login, enrollment, subject and accommodation registration, billing, and discount management. By encapsulating the complexities of underlying subsystems, the facade simplifies interaction and enhances maintainability, ensuring that the GUI interacts only with the facade, not individual modules.</br>
[Read Detailed Explanation](Design_Pattern.pdf)

## Installation
1. Run `git clone "Repository Link"`
2. Go to `Code` Folder
3. Run `AdminLoginPane.java`</br>
To login => Admin ID: `admin1` Pass: `adminpass`

## Tools Used
[![Java](https://img.shields.io/badge/Java-%23ED8B00.svg?logo=openjdk&logoColor=white)](#)
