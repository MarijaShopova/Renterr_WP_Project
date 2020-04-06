import {Component, OnInit} from '@angular/core';
import {Page} from '../model/page';
import {ActivatedRoute, Router} from '@angular/router';
import {FavouritesService} from '../service/favourites.service';
import {map, switchMap} from 'rxjs/operators';
import {NgxSpinnerService} from 'ngx-spinner';
import {ApartmentCard} from '../model/apartment-card';
import {combineLatest, Subject} from 'rxjs';

@Component({
  templateUrl: './favourites-page.component.html',
  styleUrls: ['./favourites-page.component.css']
})
export class FavouritesPageComponent implements OnInit {

  page: Page<ApartmentCard>;
  params: { [k: string]: any };
  loading: boolean;
  reload$ = new Subject();

  constructor(private route: ActivatedRoute,
              private router: Router,
              private favouritesService: FavouritesService,
              private spinner: NgxSpinnerService) {
  }

  ngOnInit() {
    const queryParams$ = this.route.queryParams.pipe(
      map(params => {
        this.showLoading();
        const defaultParams = {currency: 'EUR', sort: 'datePosted,desc', page: 0};
        this.params = {...defaultParams, ...params};
      }));

    combineLatest([queryParams$, this.reload$]).pipe(
      switchMap(() => this.favouritesService.getFavouriteApartments(this.params))
    ).subscribe(this.onSuccess, this.onError);

    this.reload$.next();
  }

  onSuccess = result => {
    this.hideLoading();
    this.page = result;
    if (this.page.content.length === 0 && this.params.page > 0) {
      this.params.page--;
      this.router.navigate(['/favourites'], {queryParams: this.params});
    }
  }

  onOptionsChange(params) {
    this.params = {...this.params, ...params};
    this.router.navigate(['/favourites'], {queryParams: this.params});
  }

  onPageChange(newPage: number) {
    this.params.page = newPage - 1;
    this.router.navigate(['/favourites'], {queryParams: this.params});
  }

  getFavourites = () => this.page.content.map(apartment => apartment.id);

  onFavouritesChanged(apartmentId: number) {
    this.showLoading();
    this.favouritesService.deleteFromFavourites(apartmentId)
      .subscribe(() => this.reload$.next());
  }

  showLoading() {
    this.loading = true;
    this.spinner.show();
  }

  hideLoading() {
    this.loading = false;
    this.spinner.hide();
  }

  onError = err => {
    this.hideLoading();
    this.router.navigate(['/error'], {queryParams: {status: err.status}, replaceUrl: true});
  }
}
