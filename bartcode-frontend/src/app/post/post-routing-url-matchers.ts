import { UrlMatchResult } from '@angular/router/src/config';
import { UrlSegment } from '@angular/router';

export function year(url: UrlSegment[]): UrlMatchResult {
  const path = url.join('/');
  if (url.length == 1 && /^([0-9]{4,})$/.test(path)) {
    return { consumed: url, posParams: { year: url[0] } };
  }
  if (url.length == 3 && /^[0-9]{4,}\/page\/[0-9]+$/.test(path)) {
    return { consumed: url, posParams: { year: url[0], page: url[2] } };
  }
  return { consumed: url };
}

export function yearMonth(url: UrlSegment[]): UrlMatchResult {
  const path = url.join('/');
  if (url.length == 2 && /^[0-9]{4,}\/[0-9]{2,}$/.test(path)) {
    return { consumed: url, posParams: { year: url[0], month: url[1] } };
  }
  if (url.length == 4 && /^[0-9]{4,}\/[0-9]{2,}\/page\/[0-9]+$/.test(path)) {
    return { consumed: url, posParams: { year: url[0], month: url[1], page: url[3] } };
  }
  return { consumed: url };
}
