import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'new'
})
export class NewPipe implements PipeTransform {

  transform(date: Date): boolean {
    const currentDate = new Date();
    const dayDifference = currentDate.getDate() - date.getDate();
    return dayDifference <= 3 &&
      currentDate.getMonth() === date.getMonth() &&
      currentDate.getFullYear() === date.getFullYear();
  }
}
