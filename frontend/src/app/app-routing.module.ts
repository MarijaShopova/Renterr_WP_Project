import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomePageComponent } from './home-page/home-page.component';
import { SearchPageComponent } from './search-page/search-page.component';
import { DetailsPageComponent } from './details-page/details-page.component';
import { SignUpPageComponent } from './sign-up-page/sign-up-page.component';
import { NewListingPageComponent } from './new-listing-page/new-listing-page.component';
import { LoginPageComponent } from './login-page/login-page.component';
import { FavouritesPageComponent } from './favourites-page/favourites-page.component';
import { ListingsPageComponent } from './listings-page/listings-page.component';
import { AuthGuard } from './service/auth.guard';
import { AccountDetailsCardComponent } from './shared/account-details-card/account-details-card.component';
import { ErrorPageComponent } from './error-page/error-page.component';

const routes: Routes = [
  { path: '', component: HomePageComponent },
  { path: 'search', component: SearchPageComponent },
  { path: 'apartment/:id', component: DetailsPageComponent },
  { path: 'my-apartment/:id', component: DetailsPageComponent, canActivate: [AuthGuard] },
  { path: 'signup', component: SignUpPageComponent },
  { path: 'new', component: NewListingPageComponent, canActivate: [AuthGuard] },
  { path: 'favourites', component: FavouritesPageComponent, canActivate: [AuthGuard] },
  { path: 'login', component: LoginPageComponent},
  { path: 'my-apartments', component: ListingsPageComponent, canActivate: [AuthGuard] },
  { path: 'edit/:id', component: NewListingPageComponent, canActivate: [AuthGuard] },
  { path: 'my-profile', component: AccountDetailsCardComponent, canActivate: [AuthGuard] },
  { path: 'error', component: ErrorPageComponent },
  { path: '**',  redirectTo: '', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {scrollPositionRestoration: 'enabled', onSameUrlNavigation: 'reload'})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
