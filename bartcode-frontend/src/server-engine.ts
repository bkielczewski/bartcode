import * as fs from 'fs';
import { renderModuleFactory } from '@angular/platform-server';

const templateCache = {};
const outputCache = {};

export function serverEngine(setupOptions: any) {

  return function (filePath: string, options: { req: Request }, callback: (err: Error, html: string) => void) {
    let url: string = options.req.url;
    let html: string = outputCache[url];
    if (html) {
      callback(null, html);
      return;
    }
    if (!templateCache[filePath]) {
      let file = fs.readFileSync(filePath);
      templateCache[filePath] = file.toString();
    }
    let appModuleFactory = setupOptions.bootstrap[0];

    renderModuleFactory(appModuleFactory, {
      document: templateCache[filePath],
      url: url
    }).then(str => {
      outputCache[url] = str;
      callback(null, str);
    });
  };

}
