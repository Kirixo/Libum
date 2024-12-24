# frontend/Dockerfile
FROM node:16

WORKDIR /app

COPY package*.json ./

RUN npm install

CMD ["sh", "-c", "npm run build"]