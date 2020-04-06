import {Component, OnInit} from '@angular/core';
import {ApartmentService} from '../service/apartment.service';
import {Page} from '../model/page';
import {map, switchMap} from 'rxjs/operators';
import {ActivatedRoute, Router} from '@angular/router';
import {NgxSpinnerService} from 'ngx-spinner';
import {ApartmentCard} from '../model/apartment-card';
import {combineLatest, Subject} from 'rxjs';

@Component({
  templateUrl: './listings-page.component.html',
  styleUrls: ['./listings-page.component.css']
})
export class ListingsPageComponent implements OnInit {

  page: Page<ApartmentCard>;
  params: { [k: string]: any };
  loading: boolean;
  mobile: boolean;
  reload$ = new Subject();

  constructor(private apartmentService: ApartmentService,
              private route: ActivatedRoute,
              private router: Router,
              private spinner: NgxSpinnerService) {
  }

  ngOnInit() {
    this.mobile = window.innerWidth <= 768;
    const queryParams$ = this.route.queryParams.pipe(
      map(params => {
        this.showLoading();
        const defaultParams = {currency: 'EUR', sort: 'datePosted,desc', page: 0};
        this.params = {...defaultParams, ...params};
      })
    );

    combineLatest([queryParams$, this.reload$]).pipe(
      switchMap(() => this.apartmentService.getMyListingsPage(this.params))
    ).subscribe(this.onSuccess, this.onError);

    this.reload$.next();
  }

  onSuccess = result => {
    this.page = result;
    this.hideLoading();
    if (this.page.content.length === 0 && this.params.page > 0) {
      this.params.page--;
      this.router.navigate(['/my-apartments'], {queryParams: this.params});
    }
  }

  onOptionsChange(params) {
    Object.keys(params).forEach(key => this.params[key] = params[key]);
    this.router.navigate(['/my-apartments'], {queryParams: {...params}});
  }

  onPageChange(newPage: number) {
    this.params.page = newPage - 1;
    this.router.navigate(['/my-apartments'], {queryParams: this.params});
  }

  onApartmentDelete(apartmentId: number) {
    this.showLoading();
    this.apartmentService.deleteApartment(apartmentId)
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
