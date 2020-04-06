import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent implements OnInit {

  @Input() address: string;
  encoded: string;

  ngOnInit(): void {
    const URI = `https://maps.google.com/maps?q=${this.address}&t=&z=15&ie=UTF8&iwloc=&output=embed`;
    this.encoded = encodeURI(URI);
  }
}
