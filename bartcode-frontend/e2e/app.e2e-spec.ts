import { BartcodeFrontendPage } from './app.po';

describe('bartcode-frontend App', function () {
  let page: BartcodeFrontendPage;

  beforeEach(() => {
    page = new BartcodeFrontendPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
