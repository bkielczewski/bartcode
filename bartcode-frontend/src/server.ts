import 'zone.js/dist/zone-node';
import 'reflect-metadata';
import { ngExpressEngine } from '@nguniversal/express-engine';
import { provideModuleMap } from '@nguniversal/module-map-ngfactory-loader';

import * as express from 'express';
import * as compression from 'compression';
import { join } from 'path';

const {AppServerModuleNgFactory, LAZY_MODULE_MAP} = require('../dist/server/main');
const PORT = 4000;
const DATA = join(process.cwd(), 'data');
const DIST = join(process.cwd(), 'dist');

const app = express();

app.use(compression());

app.engine('html', ngExpressEngine({
  bootstrap: AppServerModuleNgFactory,
  providers: [
    provideModuleMap(LAZY_MODULE_MAP)
  ]
}));

app.set('view engine', 'html');
app.set('views', join(DIST, 'browser'));
app.get('*.*',
  express.static(DATA, {maxage: '1y'}),
  express.static(join(DIST, 'browser'), {maxage: '1y'})
);
app.get('*', (req, res) => res.render(join(DIST, 'browser', 'index.html'), {req}));

app.listen(PORT, () => {
  console.log(`Express server listening on http://localhost:${PORT}`);
  console.log(`Data path is: ${DATA}`);
});
