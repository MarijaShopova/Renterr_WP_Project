import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'bedrooms'
})
export class BedroomsPipe implements PipeTransform {

  transform(numberBedrooms: number): string {
    switch (numberBedrooms) {
      case 0: return 'Studio apartment';
      case 1: return 'One bedroom';
      case 2: return 'Two bedrooms';
      case 3: return 'Three bedrooms';
    }
  }
}
