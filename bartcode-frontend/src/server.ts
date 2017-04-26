import { enableProdMode } from '@angular/core';
import { ApplicationBuilderFromModuleFactory } from 'angular-ssr';
import { AppModuleNgFactory } from '../aot/src/app/app.module.ngfactory';

import { join } from 'path';
import * as express from 'express';
import * as url from 'url';
import * as compression from 'compression';
import * as apicache from 'apicache';

enableProdMode();

const PORT = process.env.PORT || 4000;
const dist = join(__dirname, '..', 'dist');
const documentAssets = join(__dirname, '..', '..', 'data', 'documents', 'assets');
const postAssets = join(__dirname, '..', '..', 'data', 'posts', 'assets');
const builder = new ApplicationBuilderFromModuleFactory(AppModuleNgFactory, join(dist, 'index.html'));
const application = builder.build();
const app = express();
const cache = apicache.middleware;

app.use(compression());

app.get('/documents/assets/**', express.static(documentAssets));
app.get('/posts/assets/**', express.static(postAssets));

app.get('*.*', express.static(dist));

app.get(/.*/, cache('5 minutes'), async (request, response) => {
  try {
    const uri = absoluteUri(request);
    const snapshot = await application.renderUri(uri);
    response.send(snapshot.renderedDocument);
  }
  catch (exception) {
    console.error(exception);
    response.send(builder.templateDocument());
  }
});

app.listen(PORT, () => {
  console.log(`listening on http://localhost:${PORT}!`);
});

const absoluteUri = (request: express.Request): string => {
  return url.format({
    protocol: request.protocol,
    host: request.get('host'),
    pathname: request.originalUrl
  });
};
