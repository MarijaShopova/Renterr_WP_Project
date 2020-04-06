import {Component, Input} from '@angular/core';
import {Statistics} from '../../model/statistics';

@Component({
  selector: 'app-city-card',
  templateUrl: './city-card.component.html',
  styleUrls: ['./city-card.component.css']
})
export class CityCardComponent {

  @Input() statistics: Statistics;
}
