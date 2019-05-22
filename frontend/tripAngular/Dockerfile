FROM node:10.15.3

RUN mkdir /usr/src/app
WORKDIR /usr/src/app

RUN npm install -g @angular/cli@7.3.6

RUN npm rebuild node-sass -no-bin-links

EXPOSE 4200 49153

CMD [ "npm", "start" ]
