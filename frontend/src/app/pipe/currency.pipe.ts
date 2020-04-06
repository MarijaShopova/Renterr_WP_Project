import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'currency'
})
export class CurrencyPipe implements PipeTransform {

  currency: string;

  static convertToMKD(value: number): number {
    return Math.round(value * 61.5);
  }

  transform(value: number, curr: string): string {
    this.currency = curr;
    if (this.currency === 'EUR') {
      return this.formatNumber(value);
    }
    const inMKD = CurrencyPipe.convertToMKD(value);
    return this.formatNumber(inMKD);
  }

  formatNumber(value: number): string {
    let stringValue = value.toString();
    const result = [];
    while (stringValue.length > 3) {
      result.unshift(stringValue.substr(stringValue.length - 3));
      stringValue = stringValue.substr(0, stringValue.length - 3);
    }
    result.unshift(stringValue);
    result.push(this.currency);
    return result.join(' ');
  }
}
