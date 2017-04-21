import { enableProdMode } from '@angular/core';
import { ApplicationBuilderFromModuleFactory } from 'angular-ssr';
import { join } from 'path';
import { AppModuleNgFactory } from '../aot/src/app/app.module.ngfactory';

import express = require('express');
import url = require('url');

enableProdMode();

const PORT = process.env.PORT || 4000;
const dist = join(process.cwd(), 'dist');
const builder = new ApplicationBuilderFromModuleFactory(AppModuleNgFactory, join(dist, 'index.html'));
const application = builder.build();
const http = express();

http.get('*.*', express.static(join(__dirname, '..', 'dist')));

http.get(/.*/, async (request, response) => {
  try {
    const snapshot = await application.renderUri(absoluteUri(request));
    response.send(snapshot.renderedDocument);
  }
  catch (exception) {
    console.error(exception);
    response.send(builder.templateDocument());
  }
});

http.listen(PORT, () => {
  console.log(`listening on http://localhost:${PORT}!`);
});

const absoluteUri = (request: express.Request): string => {
  return url.format({
    protocol: request.protocol,
    host: request.get('host'),
    pathname: request.originalUrl
  });
};
