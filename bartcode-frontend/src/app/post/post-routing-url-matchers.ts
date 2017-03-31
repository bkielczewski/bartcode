import { UrlMatchResult } from '@angular/router/src/config';
import { UrlSegment } from '@angular/router';

export function year(url: UrlSegment[]): UrlMatchResult {
  return (url.length == 1 && /^([0-9]{4,})$/.test(url[0].path)) ? { consumed: url, posParams: { year: url[0] } } : null;
}

export function yearMonth(url: UrlSegment[]): UrlMatchResult {
  return (url.length == 2 && /^([0-9]{4,})$/.test(url[0].path) && /^([0-9]{2,})$/.test(url[1].path)) ?
    { consumed: url, posParams: { year: url[0], month: url[1] } } : null;
}
