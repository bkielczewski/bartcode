FROM node:lts-alpine

EXPOSE 4000

COPY ./dist /app/dist
COPY ./node_modules /app/node_modules

WORKDIR /app

RUN apk --no-cache add curl

HEALTHCHECK CMD curl --fail http://localhost:4000 || exit 1

CMD [ "node", "./dist/server.js" ]
