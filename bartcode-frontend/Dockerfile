FROM node:10

COPY ./dist /app/dist
COPY ./node_modules /app/node_modules

WORKDIR /app

CMD [ "node", "./dist/server.js" ]
