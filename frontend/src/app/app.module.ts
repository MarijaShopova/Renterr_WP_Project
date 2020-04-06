import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {
  NgbAlertModule,
  NgbCollapseModule,
  NgbDatepickerModule,
  NgbDropdownModule,
  NgbModalModule,
  NgbPaginationModule
} from '@ng-bootstrap/ng-bootstrap';
import {HeaderComponent} from './header/header.component';
import {HomePageComponent} from './home-page/home-page.component';
import {CityCardComponent} from './home-page/city-card/city-card.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {ApartmentCardComponent} from './shared/apartment-card/apartment-card.component';
import {DetailsPageComponent} from './details-page/details-page.component';
import {OwnerCardComponent} from './details-page/owner-card/owner-card.component';
import {CarouselModule} from 'ngx-bootstrap/carousel';
import {SearchPageComponent} from './search-page/search-page.component';
import {FiltersFormComponent} from './search-page/filters-form/filters-form.component';
import {AvailableNowPipe} from './pipe/available-now.pipe';
import {BedroomsPipe} from './pipe/bedrooms.pipe';
import {CurrencyPipe} from './pipe/currency.pipe';
import {FormsModule} from '@angular/forms';
import {ReactiveFormsModule} from '@angular/forms';
import {NgxSpinnerModule} from 'ngx-spinner';
import {FooterComponent} from './footer/footer.component';
import {SocialMediaButtonsComponent} from './footer/social-media-buttons/social-media-buttons.component';
import {BasicAuthHttpInterceptorService} from './service/httpInterceptor.service';
import {SignUpPageComponent} from './sign-up-page/sign-up-page.component';
import {NewListingPageComponent} from './new-listing-page/new-listing-page.component';
import {NgxDropzoneModule} from 'ngx-dropzone';
import {LoginComponent} from './modal/login/login.component';
import {MapComponent} from './details-page/map/map.component';
import {SafePipe} from './pipe/safe.pipe';
import {LoginPageComponent} from './login-page/login-page.component';
import {FavouritesPageComponent} from './favourites-page/favourites-page.component';
import {AccountDetailsCardComponent} from './shared/account-details-card/account-details-card.component';
import {EditAccountComponent} from './modal/edit-account/edit-account.component';
import {DeactivateAccountComponent} from './modal/deactivate-account/deactivate-account.component';
import {ListingsPageComponent} from './listings-page/listings-page.component';
import {ApartmentListingsCardComponent} from './listings-page/apartment-listings-card/apartment-listings-card.component';
import {GridOptionsComponent} from './shared/grid-options/grid-options.component';
import {ErrorPageComponent} from './error-page/error-page.component';
import {UiSwitchModule} from 'ngx-ui-switch';
import {NewPipe} from './pipe/new.pipe';

const components = [
  AppComponent,
  HeaderComponent,
  HomePageComponent,
  CityCardComponent,
  ApartmentCardComponent,
  DetailsPageComponent,
  OwnerCardComponent,
  SearchPageComponent,
  FiltersFormComponent,
  FooterComponent,
  SocialMediaButtonsComponent,
  SignUpPageComponent,
  NewListingPageComponent,
  LoginComponent,
  MapComponent,
  LoginPageComponent,
  ListingsPageComponent,
  ApartmentListingsCardComponent,
  FavouritesPageComponent,
  AccountDetailsCardComponent,
  EditAccountComponent,
  DeactivateAccountComponent,
  GridOptionsComponent,
  ErrorPageComponent
];

const pipes = [
  AvailableNowPipe,
  BedroomsPipe,
  CurrencyPipe,
  SafePipe,
  NewPipe
];

@NgModule({
  declarations: [...components, ...pipes],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbCollapseModule,
    NgbDatepickerModule,
    NgbPaginationModule,
    HttpClientModule,
    CarouselModule.forRoot(),
    FormsModule,
    ReactiveFormsModule,
    NgxSpinnerModule,
    NgxDropzoneModule,
    NgbModalModule,
    NgbDropdownModule,
    NgbAlertModule,
    UiSwitchModule
  ],
  entryComponents: [LoginComponent, EditAccountComponent, DeactivateAccountComponent],
  providers: [{provide: HTTP_INTERCEPTORS, useClass: BasicAuthHttpInterceptorService, multi: true}],
  bootstrap: [AppComponent]
})
export class AppModule {
}
