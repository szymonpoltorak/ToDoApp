# ToDo App

A simple ToDo app created for the academic course with aim of creating an app with frontend, backend and database. It has support for Https, PWA and is using reverse proxy.

## How to run it ?

1. I use RSA private and public keys to sign jwt tokens so you need to generate them.

* Use the commands below to generate private key and create public key:

```bash
openssl genpkey -algorithm RSA -out private.pem -pkeyopt rsa_keygen_bits:4096
openssl rsa -pubout -in private.pem -out public.pem
```

* Place them inside `todo-app-backend/src/resources/*.pem`

2. Application is using `ssl` so you need appropriate certificates. In `todo-app-nginx/ssl` there is a script `gen.sh` developed by me and my friend [Igor](https://github.com/igorkedzierawski). When you use it you should have `server.crt`, `server.key` and `server.p12`. First two should be placed in `todo-app-frontend/src/assets/ssl/` and `todo-app-nginx/ssl/` and `server.p12` in `todo-app-backend/src/main/resources/`. You can modify particular values in scripts variables.

## Technology Stack

1. Frontend
*	Angular 16.2.0,
*   Angular PWA,
*	TypeScript 5.1.3,
*	Angular Material 16.2.8,
*	Scss 0.2.4,
*	Rxjs 7.8.0,
*	Html 5.

2. Backend
*	Java 17,
*	Spring Boot 3.14,
*	Spring Data JPA,
*   OAuth2,
*   Jwt,
*	Gradle Groovy,
*	PostgreSQL,
*	MapStruct,
*	Lombok.

3. Others
*	Docker,
*	Nginx,
*	Drawio.

## Diagrams

* Architecture diagram:

<div align="center">
    <img src="docs/architecture.png" alt="Architecture">
</div>

* Database:

<div align="center">
    <img src="docs/db.drawio.png" alt="Database">
</div>

## Screenshots

* Login

<div align="center">
    <img src="screenshots/1.png" alt="Login">
</div>

* Register

<div align="center">
    <img src="screenshots/2.png" alt="Regsiter">
</div>

* Forgot password

<div align="center">
    <img src="screenshots/3.png" alt="Login-Error">
</div>

* Groups

<div align="center">
    <img src="screenshots/groups.png" alt="groups">
</div>

* Side Menu

<div align="center">
    <img src="screenshots/side-menu.png" alt="side-menu">
</div>

* Profile

<div align="center">
    <img src="screenshots/profile.png" alt="profile">
</div>

* Collaborators

<div align="center">
    <img src="screenshots/collaborators.png" alt="collaborators">
</div>

* Search

<div align="center">
    <img src="screenshots/search.png" alt="search">
</div>

<div align="center">
    <img src="screenshots/search-autocomplete.png" alt="search-autocomplete">
</div>

<div align="center">
    <img src="screenshots/search-found.png" alt="search-found">
</div>

* Tasks

<div align="center">
    <img src="screenshots/tasks.png" alt="tasks">
</div>

<div align="center">
    <img src="screenshots/tasks-edit.png" alt="tasks-edit">
</div>

<div align="center">
    <img src="screenshots/tasks-date.png" alt="tasks-date">
</div>

<div align="center">
    <img src="screenshots/tasks-priority.png" alt="tasks-priority">
</div>

<div align="center">
    <img src="screenshots/tasks-collaborators.png" alt="tasks-collaborators">
</div>

<div align="center">
    <img src="screenshots/finished-task.png" alt="finished-task">
</div>

* Sessions

<div align="center">
    <img src="screenshots/7.png" alt="Login-Error">
</div>

* Add socials

<div align="center">
    <img src="screenshots/8.png" alt="Login-Error">
</div>
