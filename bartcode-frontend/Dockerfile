FROM node:8

COPY ./dist /app/dist
COPY ./node_modules /app/node_modules

WORKDIR /app

CMD [ "node", "./dist/server.js" ]
