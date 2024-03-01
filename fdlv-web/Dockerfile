FROM node:18-alpine as build-stage

# Par défaut:
ARG POSTFIX=""

ENV BUILD_ENV=$POSTFIX

RUN apk add --no-cache make gcc g++ python3

RUN npm install -g node-gyp

WORKDIR /app

COPY package*.json ./

RUN npm install

RUN npm install -g @angular/cli@16.1.0

COPY . ./

RUN [ -z "$BUILD_ENV" ] && echo "BUILD_ENV is empty: $BUILD_ENV" || echo "BUILD_ENV is not empty: $BUILD_ENV"

RUN [ -z "$BUILD_ENV" ] && ng build || ng build -c $BUILD_ENV

FROM nginx:alpine as run-stage

COPY --from=build-stage /app/dist/fdlv-web/ /usr/share/nginx/html/

# Copy nginx configuration
COPY nginx.conf /etc/nginx/nginx.conf

# Expose the port
EXPOSE 80
EXPOSE 443
EXPOSE 8100
EXPOSE 9000

# Run nginx
CMD ["nginx", "-g", "daemon off;"]
