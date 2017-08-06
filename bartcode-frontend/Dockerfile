FROM node:8

EXPOSE 4000

COPY ./dist /app
COPY ./node_modules /app

WORKDIR /app
CMD ["node", "./dist/server.js"]
