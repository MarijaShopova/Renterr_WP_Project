import {Component, OnInit} from '@angular/core';
import {ApartmentService} from '../service/apartment.service';
import {Page} from '../model/page';
import {ActivatedRoute, Router} from '@angular/router';
import {flatMap} from 'rxjs/operators';
import {NgbDateParserFormatter} from '@ng-bootstrap/ng-bootstrap';
import {AuthenticationService} from '../service/authentication.service';
import {FavouritesService} from '../service/favourites.service';
import {NgxSpinnerService} from 'ngx-spinner';
import {ApartmentCard} from '../model/apartment-card';

@Component({
  templateUrl: './search-page.component.html',
  styleUrls: ['./search-page.component.css']
})
export class SearchPageComponent implements OnInit {

  page: Page<ApartmentCard>;
  favourites: number[] = [];
  params: { [k: string]: any };
  loading: boolean;
  showFilters = true;
  mobile: boolean;

  constructor(private service: ApartmentService,
              private authenticationService: AuthenticationService,
              private favouritesService: FavouritesService,
              private router: Router,
              private route: ActivatedRoute,
              private formatter: NgbDateParserFormatter,
              private spinner: NgxSpinnerService) {
  }

  ngOnInit() {
    this.mobile = window.innerWidth <= 768;
    this.route.queryParams.pipe(
      flatMap(params => {
        this.loading = true;
        this.spinner.show();
        const defaultParams = {currency: 'EUR', sort: 'datePosted,desc', page: 0};
        this.params = {...defaultParams, ...params};
        return this.service.getApartmentsPage(this.params);
      })
    ).subscribe(data => {
      this.loading = false;
      this.spinner.hide();
      this.page = data;
    }, this.onError);

    if (this.authenticationService.isUserLoggedIn()) {
      this.favouritesService.getFavourites()
        .subscribe(favourites => this.favourites = favourites, this.onError);
    }
  }

  onFavouritesChange(apartmentId: number) {
    const isFavourite = this.favourites.includes(apartmentId);
    if (isFavourite) {
      this.deleteFromFavourites(apartmentId);
    } else {
      this.addToFavourites(apartmentId);
    }
  }

  deleteFromFavourites(apartmentId: number) {
    this.favouritesService.deleteFromFavourites(apartmentId)
      .subscribe(() => this.favourites = this.favourites.filter(it => it !== apartmentId), this.onError);
  }

  addToFavourites(apartmentId: number) {
    this.favouritesService.addToFavourites(apartmentId)
      .subscribe(() => this.favourites.push(apartmentId), this.onError);
  }

  onOptionsChange(params) {
    this.params = {...this.params, ...params};
    this.router.navigate(['/search'], {queryParams: this.params});
  }

  onPageChange(newPage: number) {
    this.params.page = newPage - 1;
    this.router.navigate(['/search'], {queryParams: this.params});
  }

  filter(filterForm) {
    this.showFilters = true;
    const dateAvailable = filterForm.dateAvailable ? this.formatter.format(filterForm.dateAvailable) : null;
    Object.keys(filterForm).forEach(key => this.params[key] = filterForm[key] ? filterForm[key] : null);
    const queryParams = {...this.params, dateAvailable};
    this.router.navigate(['/search'], {queryParams});
  }

  onError = err => {
    this.loading = false;
    this.spinner.hide();
    this.router.navigate(['/error'], {queryParams: {status: err.status}, replaceUrl: true});
  }
}
