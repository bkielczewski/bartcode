FROM node:8

EXPOSE 4000

COPY ./dist /app/dist
COPY ./node_modules /app/node_modules

WORKDIR /app
CMD ["node", "./dist/server.js"]
