import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Login} from '../model/login';
import {catchError, tap} from 'rxjs/operators';
import {BehaviorSubject, throwError} from 'rxjs';
import {User} from '../model/user';
import {Router} from '@angular/router';
import {Account} from '../model/account';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  user = new BehaviorSubject<User>(null);
  private tokenExpirationTimer: any;

  constructor(private http: HttpClient, private router: Router) {
  }

  logIn(login: Login) {
    return this.http.post<User>('api/login', login).pipe(
      catchError(error => {
        if (error.error === 'Bad credentials') {
          return throwError('Invalid username or password');
        } else {
          return throwError('Sorry we are unavailable at the moment');
        }
      }),
      tap(resData => this.handleAuthentication(resData.account, resData.token, +resData.tokenExpirationDate))
    );
  }

  isUserLoggedIn = () => !!this.user.value;

  autoLogin() {
    const userData: User = JSON.parse(localStorage.getItem('userData'));
    if (!userData) {
      return;
    }
    const loadedUser = new User(userData.account, userData.token, new Date(userData.tokenExpirationDate));
    if (loadedUser.token) {
      this.user.next(loadedUser);
      const expirationDuration = new Date(userData.tokenExpirationDate).getTime() - new Date().getTime();
      this.autoLogout(expirationDuration);
    }
  }

  logout() {
    this.user.next(null);
    this.router.navigate(['/']);
    localStorage.removeItem('userData');
    if (this.tokenExpirationTimer) {
      clearTimeout(this.tokenExpirationTimer);
    }
    this.tokenExpirationTimer = null;
  }

  autoLogout(expirationDuration: number) {
    this.tokenExpirationTimer = setTimeout(() => this.logout(), expirationDuration);
  }

  private handleAuthentication(account: Account, token: string, expiresIn: number) {
    const expirationDate = new Date(expiresIn);
    const user = new User(account, token, expirationDate);
    const expiration = expiresIn - new Date().getTime();
    if (!user.account.profilePicture) {
      user.account.profilePicture = '../../assets/default-pictures/default-profile-pic.jpg';
    }
    this.user.next(user);
    this.autoLogout(expiration);
    localStorage.setItem('userData', JSON.stringify(user));
  }
}
