import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'availableNow'
})
export class AvailableNowPipe implements PipeTransform {

  transform(dateString: string): string {
    const currentDate = new Date();
    const availableFrom = new Date(dateString);
    if (currentDate > availableFrom) {
      return 'Available now';
    }
    return dateString;
  }

}
