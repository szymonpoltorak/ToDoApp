FROM node:18.15-alpine as build

WORKDIR /todo-app-frontend

COPY package*.json ./

RUN npm install

COPY . .

RUN npm run ng build --configuration=docker

FROM nginx:alpine

COPY --from=build /todo-app-frontend/dist/todo-app-frontend /usr/share/nginx/html

COPY ./ssl /etc/nginx/ssl

EXPOSE 443
