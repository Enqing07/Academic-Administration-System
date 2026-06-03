# Academic Administration System
## Overview
A standalone Java GUI application for academic administrators to manage student enrollment, subject registration, accommodation assignments, and automated billing. Designed for internal use, the system supports dynamic fee calculations based on course load and accommodation choices, enabling efficient and accurate record management.
<p align="center">
  <img height="400" src="https://github.com/user-attachments/assets/dd8c675a-6d9d-40b1-88b1-2b3bd8927c3e" />
</p>

## Main Features
- Supports student enrollment with Single Program or Stackup Program registration (up to 3 programs)
  <p align="center">
    <img height="400" src="https://github.com/user-attachments/assets/5c66e389-920f-4933-a923-344840eeabcd" />
  </p>
  
- Manage accommodation registration
  <p align="center">
    <img height="400" src="https://github.com/user-attachments/assets/a7f258cb-39c1-4ff9-8525-b85eb7aa245c" />
  </p>
  
- View automated billing based on course enrolled and accommodation type
  <p align="center">
    <img height="400" src="https://github.com/user-attachments/assets/db111e0f-d9e4-46d9-a108-a1757fe8ac5a" />
  </p>
  
- Supports updating fee and accommodation amounts, modifying discount values, and adding new discount strategies. No code changes required.
  <p align="center">
    <img height="400" src="https://github.com/user-attachments/assets/54078a37-c6a6-4b53-9539-69b427f2ecad" />
  </p>
  

## System Design
- [Use Case/Class Diagram/Sequence Diagram](https://enqing07.github.io/Academic-Administration-System/UML_Diagram.pdf) 

## Design Pattern
The system applies the **Facade** Design Pattern through a `SystemFacade` class that serves as the administrative layer of the application. It provides a unified interface to core admin functions such as login, enrollment, subject and accommodation registration, billing, and discount management. By encapsulating the complexities of underlying subsystems, the facade simplifies interaction and enhances maintainability, ensuring that the GUI interacts only with the facade, not individual modules.</br>
[Read Detailed Explanation](https://enqing07.github.io/Academic-Administration-System/Design_Pattern.pdf)

## Installation

1. In your terminal, clone the repository:
```bash
https://github.com/Enqing07/Academic-Administration-System.git
cd Academic-Administration-System/Code
```
2. Open `Code/Client.java` and run it.

## Login Credentials (for Testing)

| ID       | Password  |
|----------|-----------|
| admin1   | adminpass |

## Tools Used
[![Java](https://img.shields.io/badge/Java-%23ED8B00.svg?logo=openjdk&logoColor=white)](#)
