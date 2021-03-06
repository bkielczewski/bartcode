import 'zone.js/dist/zone-node';
import 'reflect-metadata';
import { ngExpressEngine } from '@nguniversal/express-engine';
import { provideModuleMap } from '@nguniversal/module-map-ngfactory-loader';
import { enableProdMode } from '@angular/core';
import { join } from 'path';
import * as express from 'express';
import * as compression from 'compression';
import * as serveStatic from 'serve-static';
import * as Logger from 'node-json-logger';

enableProdMode();

const {AppServerModuleNgFactory, LAZY_MODULE_MAP} = require('../dist/server/main');
const BROWSER_BUNDLE = join(process.cwd(), 'dist', 'browser');
const DATA = join(process.cwd(), 'data');
const PORT = 4000;
const logger = new Logger();

const app = express();

app.use(compression());

app.engine('html', ngExpressEngine({
  bootstrap: AppServerModuleNgFactory,
  providers: [
    provideModuleMap(LAZY_MODULE_MAP)
  ]
}));

app.set('view engine', 'html');
app.set('views', BROWSER_BUNDLE);
app.get('*.*',
  serveStatic(DATA),
  serveStatic(BROWSER_BUNDLE));
app.get('*', (req, res) => res.render('index', {req, res}));

app.listen(PORT, () => {
  logger.info(`Express server listening on http://localhost:${PORT}`);
  logger.info(`Data path is: ${DATA}`);
});
