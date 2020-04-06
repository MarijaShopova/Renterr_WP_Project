import {Component, OnInit} from '@angular/core';
import {ApartmentService} from '../service/apartment.service';
import {Apartment} from '../model/apartment';
import {ActivatedRoute, Router} from '@angular/router';
import {flatMap} from 'rxjs/operators';
import {NgxSpinnerService} from 'ngx-spinner';

@Component({
  templateUrl: './details-page.component.html',
  styleUrls: ['./details-page.component.css']
})
export class DetailsPageComponent implements OnInit {

  apartment: Apartment;
  apartmentImages: string[];
  currency = 'EUR';
  readonly carouselInterval = 3000;

  constructor(private service: ApartmentService,
              private route: ActivatedRoute,
              private spinner: NgxSpinnerService,
              private router: Router) {
  }

  ngOnInit() {
    this.spinner.show();
    this.route.params.pipe(
      flatMap(params => {
        if (this.router.url.includes('my-apartment')) {
          return this.service.getApartmentDetails(+params.id);
        }
        return this.service.getActiveApartmentDetails(+params.id);
      })
    ).subscribe(data => {
      this.apartment = data.apartment;
      this.apartmentImages = data.images;
      this.spinner.hide();
    }, err => {
      this.spinner.hide();
      this.router.navigate(['/error'], {queryParams: {status: err.status}, replaceUrl: true});
    });
  }

  getFullAddress = () => `${this.apartment.address} ${this.apartment.municipality} ${this.apartment.city} Macedonia`;
}
