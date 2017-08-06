import { enableProdMode } from '@angular/core';
import { AppModuleNgFactory } from '../aot/src/app/app.module.ngfactory';

import { applicationBuilderFromModuleFactory } from 'angular-ssr';

import { join } from 'path';
import * as express from 'express';
import * as url from 'url';
import * as compression from 'compression';
import * as apicache from 'apicache';

enableProdMode();

const port = process.env.PORT || 4000;
const dist = join(__dirname, '/../dist');
const documentAssets = join(__dirname, '/../../data/documents/assets');
const postAssets = join(__dirname, '/../../data/posts/assets');
const builder = applicationBuilderFromModuleFactory(AppModuleNgFactory, join(dist, 'index.html'));
const application = builder.build();
const cache = apicache.middleware;
const app = express();

app.use(compression());

const appRouteHandler = async (request, response) => {
  try {
    const uri = getAbsoluteUri(request);
    const snapshot = await application.renderUri(uri);
    response.send(snapshot.renderedDocument);
  }
  catch (error) {
    console.error(error.message);
    response.status(500).send(error.message);
  }
};

const getAbsoluteUri = (request: express.Request): string => {
  return url.format({
    protocol: request.protocol,
    host: request.get('host'),
    pathname: request.originalUrl
  });
};

app.get('*.*', express.static(dist));
app.use('/documents/assets', express.static(documentAssets));
app.use('/posts/assets', express.static(postAssets));

const appRoutes: Promise<any> = application.discoverRoutes()
  .then(routes => routes
    .filter(route => route.path)
    .map(route => '/' + route.path.join('/'))
    .forEach(path => app.get(path, cache('5 minutes'), appRouteHandler))
  );

Promise.all([appRoutes]).then(() => {
  app.get('*', (request, response) => response.status(404).send('Page Not Found, url=' + request.originalUrl));
  app.listen(port, () => {
    console.log(`listening on http://localhost:${port}!`);
  })
});

