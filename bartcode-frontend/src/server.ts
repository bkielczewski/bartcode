import 'reflect-metadata';
import 'zone.js/dist/zone-node';

import { enableProdMode } from '@angular/core';
import { AppServerModuleNgFactory } from '../aot/src/app/app-server.module.ngfactory';
import { serverEngine } from './server-engine';

import * as express from 'express';

import { join } from 'path';


const PORT = process.env.PORT || 4000;

enableProdMode();

const app = express();


app.engine('html', serverEngine({
  bootstrap: [AppServerModuleNgFactory]
}));

app.set('views', 'src');
app.set('view engine', 'html');
app.get('*.*', express.static(join(__dirname, '..', 'dist')));
app.get('*', (req, res) => {
  res.render('index', { req });
});
app.listen(PORT, () => {
  console.log(`listening on http://localhost:${PORT}!`);
});
