import { TestBed } from '@angular/core/testing';

import { CustomCurrencyFormatterDirective } from './custom-currency-formatter.directive';

describe('CustomCurrencyFormatterDirective', () => {
  it('should create an instance', () => {
    const directive: CustomCurrencyFormatterDirective = TestBed.get(CustomCurrencyFormatterDirective);
    expect(directive).toBeTruthy();
  });
});
